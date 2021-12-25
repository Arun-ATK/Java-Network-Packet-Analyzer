package capture;

import org.jnetpcap.*;

import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;

public class JNetPcapHandler extends PacketCapturer {
    ArrayList<PcapIf> interfaces;
    Pcap pcap;


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
    public void openInterface(int interfaceID) {
        String interfaceName = interfaces.get(interfaceID).getName();
        int snaplen = 64 * 1024;
        int promiscuous = Pcap.MODE_PROMISCUOUS;
        StringBuilder errbuf = new StringBuilder();

        final int seconds = 5;
        int timeout = seconds * 1000;

        pcap = Pcap.openLive(interfaceName, snaplen, promiscuous, timeout, errbuf);

        getNextPacket();
    }

    // TODO: Replace deprecated method handler
    @Override
    public void getNextPacket() {
        PcapHandler handler = new PcapHandler() {
            @Override
            public void nextPacket(Object o, long seconds, int usec, int caplen, int len, ByteBuffer byteBuffer) {
                PrintStream out = (PrintStream) o;

                String data = StandardCharsets.UTF_8.decode(byteBuffer).toString();
                int end = Math.min(data.length(), 25);

                out.println("Packet captured on: " + new Date(seconds * 1000));
                out.println("Captured Size: " + caplen);
                out.println("Size on wire: " + len);
                out.println(data.substring(0, end));
            }
        };

        int cnt = 10;
        PrintStream out = System.out;
        pcap.loop(cnt, handler, out);

        pcap.close();
    }

    @Override
    public void parseRawPacket() {

    }
}
