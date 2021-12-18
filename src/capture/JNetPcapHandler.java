package capture;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;

import java.util.ArrayList;

public class JNetPcapHandler extends PacketCapturer {


    @Override
    public ArrayList<NetworkInterface> getNetworkInterfaces() {
        StringBuilder errbuf = new StringBuilder();

        ArrayList<PcapIf> interfaces = new ArrayList<>();
        ArrayList<NetworkInterface> networkInterfaces = new ArrayList<>();

        int statusCode = Pcap.findAllDevs(interfaces, errbuf);
        if (statusCode != Pcap.OK) {
            System.out.println("Error occurred: " + errbuf);
        }
        else {
            System.out.println(interfaces.size() + " interfaces found!");

            for (PcapIf anInterface : interfaces) {
                networkInterfaces.add(new NetworkInterface(
                        anInterface.getName(),
                        anInterface.getDescription()));
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
    public void openInterface(String interfaceName) {

    }

    @Override
    public void getNextPacket() {

    }

    @Override
    public void parseRawPacket() {

    }
}
