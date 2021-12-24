package capture;

import org.jnetpcap.*;
import org.jnetpcap.nio.JBuffer;

import java.util.ArrayList;

public class JNetPcapHandler extends PacketCapturer {
    ArrayList<PcapIf> interfaces;


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
    public void openPcapFile(String filename) {

    }

    @Override
    public void closePcapFile(String filename) {

    }

    @Override
    public void openInterface(int interfaceNum) {
        String interfaceName = interfaces.get(interfaceNum).getName();
        int snaplen = 64 * 1024;
        int flags = Pcap.MODE_PROMISCUOUS;
        int timeout = 10 * 1000;
        StringBuilder errbuf = new StringBuilder();

        Pcap pcap = Pcap.openLive(interfaceName, snaplen, flags, timeout, errbuf);

//        PcapHeader header = new PcapHeader();
//        JBuffer buffer = new JBuffer(snaplen);
//        int stat = pcap.nextEx(header, buffer);

        PcapPktHdr pktHdr = new PcapPktHdr();
        PcapPktBuffer pktBuffer = new PcapPktBuffer();
        int stat = pcap.nextEx(pktHdr, pktBuffer);

        if (stat == 1) {
            System.out.println(pktHdr.getCaplen());
        }
        else {
            System.out.println("Status: " + stat);
        }

        pcap.close();

    }

    @Override
    public void getNextPacket() {

    }

    @Override
    public void parseRawPacket() {

    }
}
