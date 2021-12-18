package sysutil;

import java.io.IOException;

public class SystemController {

    public static void main(String[] args) {
        System.out.println("Java Packet Analyser project");

        OsCheck.OSType osType = OsCheck.getOperatingSystemType();
        System.out.println("OS: " + osType);

        if (osType == OsCheck.OSType.Windows) {
            try {
                startWinPcap();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            try {
               stopWinPcap();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        else if (osType == OsCheck.OSType.Linux) {
            // TODO: Try in linux installation
            System.out.println("Linux not supported yet!");
        }
        else {
            System.out.println("Unknown operating system");
        }
    }

    private static void startWinPcap() throws IOException {
        Runtime.getRuntime().exec("cmd.exe /C start net start npf");

        System.out.println("WinPcap start cmd executed");
    }

    private static void stopWinPcap() throws IOException {
        Runtime.getRuntime().exec("cmd.exe /C start net stop npf");

        System.out.println("Issued WinPcap stop cmd");
    }

    private static void startLibPcap() {}
    private static void stopLibPcap() {}
}
