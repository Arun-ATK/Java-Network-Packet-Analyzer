import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;

import java.io.IOException;

public class PacketAnalyser {
    public static void main(String[] args) {
        try {
            startup();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void startup() throws IOException{
        OsCheck.OSType osType = OsCheck.getOperatingSystemType();
        System.out.println("OS: " + osType);

        if (osType == OsCheck.OSType.Windows) {
            Runtime.getRuntime().exec("cmd.exe /C start net start npf");

            System.out.println("Started packet capture");
        }
        else {
            System.out.println("Non-windows system not supported!");
            System.exit(-1);
        }


    }
}
