package capture;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapDumper;
import org.jnetpcap.PcapIf;
import org.jnetpcap.nio.JBuffer;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.format.FormatUtils;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.tcpip.Http;
import org.jnetpcap.protocol.tcpip.Tcp;
import org.jnetpcap.protocol.tcpip.Udp;
import packets.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JNetPcapHandler extends PacketCapturer {
    private ArrayList<PcapIf> interfaces;
    private final ArrayList<PcapPacket> rawPackets = new ArrayList<>();
    private Pcap pcap;

    @Override
    public ArrayList<NetworkInterface> getNetworkInterfaces() {
        StringBuilder errbuf = new StringBuilder();

        interfaces = new ArrayList<>();
        ArrayList<NetworkInterface> networkInterfaces = new ArrayList<>();

        int statusCode = Pcap.findAllDevs(interfaces, errbuf);
        if (statusCode != Pcap.OK) {
            System.out.println("Error occurred: " + errbuf);
        }
        else {
            System.out.println(interfaces.size() + " interfaces found!");

            for (int i = 0; i < interfaces.size(); ++i) {
                networkInterfaces.add(new NetworkInterface(
                        i,
                        interfaces.get(i).getName(),
                        interfaces.get(i).getDescription()));
            }
        }

        return networkInterfaces;
    }

    @Override
    public void openPcapFile(String filepath) {
        File file = new File(filepath);
        String filename = file.getName();

        // TODO: Replace with exceptions thrown to GUI
        if (!file.exists()) {
            System.out.println("File doesn't exist!");
        }
        else if (!filename.substring(filename.lastIndexOf(".") + 1).equals("pcap")) {
            System.out.println("File is not a pcap file!");
        }
        else {
            StringBuilder errbuf = new StringBuilder();
            System.out.println("FILEPATH: " + file.getPath());
            pcap = Pcap.openOffline(file.getPath(), errbuf);
            if (pcap == null) {
                System.out.println("ERR: " + errbuf);
            }
        }
    }

    @Override
    public void openInterface(int interfaceID) {
        String interfaceName = interfaces.get(interfaceID).getName();
        int snaplen = 64 * 1024;
        int promiscuous = Pcap.MODE_PROMISCUOUS;
        StringBuilder errbuf = new StringBuilder();

        final int seconds = 5;
        int timeout = seconds * 1000;

        /*
         * interfaceName - Name of the interface according to the system
         * snaplen - amount of data to capture per packet
         * promiscuous - Sniff all packets passing through the system
         * timeout - Amount of time to wait for reading packets before dispatching them
         * errbuf - Error message in case of errors
         */
        pcap = Pcap.openLive(interfaceName, snaplen, promiscuous, timeout, errbuf);
        if (pcap == null ) {
            System.out.println("ERR: " + errbuf);
        }
//        startCapture();
    }

    @Override
    public void startCapture() {
        if (captureThread == null || !captureThread.isAlive()) {
            captureThread = new Thread(new capturePacketTask(this, pcap));
            captureThread.start();
        }
        else {
            System.out.println("Already capturing packets!");
        }
    }

    @Override
    public void stopCapture() {
        captureThread.interrupt();
        captureThread = null;
    }

    @Override
    public void addRawPacket(Object p) {
        if (p instanceof PcapPacket pcapPacket) {
            rawPackets.add(pcapPacket);
        }
    }

    @Override
    public void parseRawPacket(Object p) {
        if (p instanceof PcapPacket pcapPacket) {
            Ip4 ip = new Ip4();

            Tcp tcp = new Tcp();
            Udp udp = new Udp();

            Http http = new Http();

            Packet packet = null;
            if (pcapPacket.hasHeader(ip)) {
                if (pcapPacket.hasHeader(tcp)) {
                    if (pcapPacket.hasHeader(http)) {
                        if (tcp.source() == 80 || tcp.destination() == 80) {
                            packet = PacketFactory.createPacket(pcapPacket, Packet.Protocol.HTTP);
                        }
                    }

                    // Unsupported TCP Packet
                    else {
                        packet = PacketFactory.createPacket(pcapPacket, Packet.Protocol.TCP);
                    }
                }
                else if (pcapPacket.hasHeader(udp)) {
                    packet = PacketFactory.createPacket(pcapPacket, Packet.Protocol.UDP);
                }

                // Unsupported IP Packet
                else {
                    packet = PacketFactory.createPacket(pcapPacket, Packet.Protocol.IP);
                }
            }

            if (packet == null) {
                packet = PacketFactory.createPacket(pcapPacket, Packet.Protocol.Unknown);
            }

            CaptureController.addNewPacket(packet);

        }
        else {
            System.out.println("Invalid object sent!");
        }
    }

    @Override
    public void saveFile(String fileName) {
        PcapDumper pcapDumper = pcap.dumpOpen(fileName);
        for (PcapPacket p : rawPackets) {
            pcapDumper.dump(p);
        }

        // Saves the file on the disk using provided filepath
        pcapDumper.flush();
    }

    private static class PacketFactory {
        private PacketFactory() {}

        static Packet createPacket(PcapPacket p, Packet.Protocol protocol) {
            switch (protocol) {
                case HTTP -> {
                    Map<String, String> ipHeaders = getIpHeaders(p);
                    Map<String, String> tcpHeader = getTcpHeaders(p);

                    Date recvTime = new Date(p.getCaptureHeader().timestampInMillis());
                    int size = p.getTotalSize();
                    int headerSize = p.getCaptureHeader().hdr_len();

                    Http http = new Http();
                    p.getHeader(http);

                    String[] headerLines = http.header().split("\n");

                    Map<String, String> httpHeaders = new HashMap<>();

                    // TODO: Handle first line of HTTP Message
                    for (String headerLine : headerLines) {
                        int splitIndex = headerLine.indexOf(':');

                        try {
                            String[] line = new String[] { headerLine.substring(0, splitIndex),
                                headerLine.substring(splitIndex + 1) };

                            httpHeaders.put(line[0].trim(), line[1].trim());
                        } catch (IndexOutOfBoundsException ignored) {
                            System.out.println(headerLine);
                        }
                    }

                    JBuffer buffer = new JBuffer(p.getTotalSize());
                    p.transferStateAndDataTo(buffer);
                    String data = buffer.toHexdump();

                    return new HTTPPacket(ipHeaders, tcpHeader, httpHeaders,
                            data, recvTime, size, headerSize);
                }
                case TCP -> {
                    return new TCPPacket();
                }
                case UDP -> {
                    return new UDPPacket();
                }
                default -> {
                    return new DefaultPacket();
                }
            }
        }

        private static Map<String, String> getIpHeaders(PcapPacket p) {
            Map<String, String> headers = new HashMap<>();
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
            Map<String, String> headers = new HashMap<>();
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
    }
}
