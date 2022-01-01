package capture;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.format.FormatUtils;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.tcpip.Tcp;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class JNetPcapHandler extends PacketCapturer {
    private ArrayList<PcapIf> interfaces;
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
            else {
                startCapture();
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
        startCapture();
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
    public void parseRawPacket(Object p) {
        if (p instanceof PcapPacket pcapPacket) {
            System.out.println("TIME: " + new Date(pcapPacket.getCaptureHeader().timestampInMillis()));
            System.out.println("SIZE: " + pcapPacket.getCaptureHeader().caplen());

            Ip4 ip = new Ip4();
            String sourceIP;
            String destinationIP;

            Tcp tcp = new Tcp();

            if (pcapPacket.hasHeader(ip)) {
                System.out.printf("ip.version=%d\n", ip.version());

                sourceIP = FormatUtils.ip(ip.destination());
                destinationIP = FormatUtils.ip(ip.destination());

                System.out.println("Source IP:\t" + sourceIP);
                System.out.println("Destination IP:\t" + destinationIP);

                if (pcapPacket.hasHeader(tcp)) {
                    System.out.println("Source port:\t" + tcp.source());
                    System.out.println("Destination port:\t" + tcp.destination());
                }
            }
            else {
                System.out.println("I NULL");
            }

            System.out.println("-----");
        }
        else {
            System.out.println("Invalid object sent!");
        }
    }
}
