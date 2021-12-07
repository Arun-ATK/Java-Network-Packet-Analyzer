import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PacketAnalyser {

    private static OsCheck.OSType osType;

    public static void main(String[] args) {
        osType = OsCheck.getOperatingSystemType();
        System.out.println("OS: " + osType);

        try {
            startup();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void startup() throws IOException {
        if (osType == OsCheck.OSType.Windows) {
            Runtime.getRuntime().exec("cmd.exe /C start net start npf");

            System.out.println("Started packet capture");
        }
        else {
            System.out.println("Non-windows system not yet supported!");
            System.exit(-1);
        }
    }

    private static void shutdown() throws IOException {
        if (osType == OsCheck.OSType.Windows) {
            Runtime.getRuntime().exec("cmd.exe /C start net stop npf");

            System.out.println("Stopped packet capture");
        }
        else {
            System.out.println("Non-windows system not yet supported!");
            System.exit(-1);
        }
    }
}
