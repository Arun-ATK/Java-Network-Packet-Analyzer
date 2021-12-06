import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;

import java.util.ArrayList;
import java.util.List;

public class PacketAnalyser {
    public static void main(String[] args) {

        StringBuilder errbuf = new StringBuilder();
        List<PcapIf> ifs = new ArrayList<>();
        int statusCode = Pcap.findAllDevs(ifs, errbuf);
        if (statusCode != Pcap.OK || ifs.isEmpty()) {
            System.out.println("Error occurred: " + errbuf);
            return;
        }

        System.out.println("No error");
        System.out.println(ifs.size());
        for (PcapIf p : ifs) {
            System.out.println(p.toString());
        }
    }
}
