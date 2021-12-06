import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PacketAnalyser {
    public static void main(String[] args) {
        try {
            startup();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void startup() throws IOException{
        List<String> command = new ArrayList<>();
        OsCheck.OSType osType = OsCheck.getOperatingSystemType();
        System.out.println("OS: " + osType);

        String shell;
        if (osType == OsCheck.OSType.Windows) {
            command.add("cmd.exe");
            command.add("/c");
        }
        else {
            System.out.println("Non-windows system not supported!");
            System.exit(-1);
        }

        try {
            Process cmdPrompt = Runtime.getRuntime().exec("cmd.exe /C start dir");
            cmdPrompt.destroy();
            System.out.println("ok");

        } catch (IOException e) {
            e.printStackTrace();
        }

//        InputStream inputStream = null;
//        InputStream errorStream = null;

//        try {
//            ProcessBuilder processBuilder = new ProcessBuilder(command);
//            Process process = processBuilder.start();
//
//            inputStream = process.getInputStream();
//            errorStream = process.getErrorStream();
//
//            System.out.println("Process InputStream: " + inputStream.toString());
//            System.out.println("Process ErrorStream: " + errorStream.toString());
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (inputStream != null) {
//                inputStream.close();
//            }
//            if (errorStream != null) {
//                errorStream.close();
//            }
//        }
    }
}
