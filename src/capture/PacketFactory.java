package capture;

import org.jnetpcap.nio.JBuffer;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.format.FormatUtils;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.tcpip.Http;
import org.jnetpcap.protocol.tcpip.Tcp;
import org.jnetpcap.protocol.tcpip.Udp;
import packets.*;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class PacketFactory {
    private PacketFactory() {}

    static Packet createPacket(PcapPacket p, Packet.Protocol protocol) {
        Date receiveDate = getReceiveDate(p);
        int size = getTotalSize(p);
        int headerSize = getHeaderSize(p);

        JBuffer buffer = new JBuffer(p.getTotalSize());
        p.transferStateAndDataTo(buffer);
        String hexdump = buffer.toHexdump();

        switch (protocol) {
            case HTTP -> {
                Map<String, String> ipHeaders = getIpHeaders(p);
                Map<String, String> tcpHeader = getTcpHeaders(p);

                Http http = new Http();
                p.getHeader(http);

                // CRLF Escape Sequence: \r\n
                String[] headerLines = http.header().split("\r\n");
                Map<String, String> httpHeaders = new LinkedHashMap<>();

                // TODO: Replace with own parsing
                if (http.isResponse()) {
                    httpHeaders.put("Response Line", headerLines[0]);
                }
                else {
                    httpHeaders.put("Request Line", headerLines[0]);
                }

                for (String headerLine : headerLines) {
                    // Ignoring empty lines (double CRLF to mark ending)
                    if (headerLine.length() == 0) {
                        continue;
                    }

                    int splitIndex = headerLine.indexOf(':');
                    try {
                        String[] line = new String[]{headerLine.substring(0, splitIndex),
                                headerLine.substring(splitIndex + 1)};

                        httpHeaders.put(line[0].trim(), line[1].trim());
                    } catch (IndexOutOfBoundsException ignored) {}
                }

                // Retrieving HTTP data
                if (http.getPayload().length > 0) {
                    httpHeaders.put("HTTP Payload", new String(http.getPayload()));
                }

                return new ApplicationPacket(ipHeaders, tcpHeader, httpHeaders,
                        hexdump, receiveDate, size, headerSize,
                        protocol);
            }
            case TCP -> {
                Map<String, String> ipHeaders = getIpHeaders(p);
                Map<String, String> tcpHeaders = getTcpHeaders(p);

                return new TransportPacket(ipHeaders, tcpHeaders, hexdump,
                        receiveDate, size, headerSize,
                        protocol);
            }
            case UDP -> {
                Map<String, String> ipHeaders = getIpHeaders(p);

                Udp udp = new Udp();
                p.getHeader(udp);

                Map<String, String> udpHeaders = new HashMap<>();
                udpHeaders.put("Source Port", String.valueOf(udp.source()));
                udpHeaders.put("Destination Port", String.valueOf(udp.destination()));
                udpHeaders.put("UDP Length", String.valueOf(udp.length()));
                udpHeaders.put("Checksum", String.valueOf(udp.checksum()));
                udpHeaders.put("[Checksum validity]", String.valueOf(udp.isChecksumValid()));

                return new TransportPacket(ipHeaders, udpHeaders, hexdump,
                        receiveDate, size, headerSize, protocol);
            }
            case IP -> {
                Map<String, String> ipHeaders = getIpHeaders(p);

                return new NetworkPacket(ipHeaders, hexdump,
                        receiveDate, size, headerSize, protocol);
            }
        }
        return new DefaultPacket(hexdump, receiveDate, size, headerSize);
    }

    private static Map<String, String> getIpHeaders(PcapPacket p) {
        Map<String, String> headers = new LinkedHashMap<>();
        Ip4 ip = new Ip4();
        p.getHeader(ip);

        headers.put("Version", String.valueOf(ip.version()));
        headers.put("Header Length", String.valueOf(ip.hlen()));
        headers.put("Service Type", String.valueOf(ip.type()));
        headers.put("Total Length", String.valueOf(ip.length()));
        headers.put("Identification", String.valueOf(ip.id()));

        int f = ip.flags();
        String flags = "";
        for (int i = 2; i >= 0; --i) {
            flags = flags.concat(String.valueOf((f >>> i) & 1));
        }

        headers.put("Flags", flags);
        headers.put("Fragmentation Offset", String.valueOf(ip.offset()));
        headers.put("Source IP", FormatUtils.ip(ip.source()));
        headers.put("Destination IP", FormatUtils.ip(ip.destination()));

        return headers;
    }

    private static Map<String, String> getTcpHeaders(PcapPacket p) {
        Map<String, String> headers = new LinkedHashMap<>();
        Tcp tcp = new Tcp();
        p.getHeader(tcp);

        headers.put("Source Port", String.valueOf(tcp.source()));
        headers.put("Destination Port", String.valueOf(tcp.destination()));
        headers.put("Sequence Number", String.valueOf(tcp.seq()));
        headers.put("Acknowledgement Number", String.valueOf(tcp.ack()));
        headers.put("Header Length", String.valueOf(tcp.hlen()));

        int f = tcp.flags();
        String flags = "";
        for (int i = 5; i >= 0; --i) {
            flags = flags.concat(String.valueOf((f >>> i) & 1));
        }
        headers.put("Flags", flags);
        headers.put("Advertised Window", String.valueOf(tcp.window()));
        headers.put("Checksum", String.valueOf(tcp.checksum()));
        headers.put("Urgent Pointer", String.valueOf(tcp.urgent()));

        return headers;
    }

    private static int getTotalSize(PcapPacket p) {
        return p.getTotalSize();
    }

    private static Date getReceiveDate(PcapPacket p) {
        return new Date(p.getCaptureHeader().timestampInMillis());
    }

    private static int getHeaderSize(PcapPacket p) {
        return p.getCaptureHeader().hdr_len();
    }
}
