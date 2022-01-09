package sysutil;

import ui.StartScreen;

import java.io.IOException;

public class SystemController {
    private static OsCheck.OSType osType;

    public static void main(String[] args) {
        System.out.println("Java Packet Analyser project");

        osType = OsCheck.getOperatingSystemType();
        System.out.println("OS: " + osType);

        new StartScreen();
    }

    private static void startWinPcap() throws IOException {
        Runtime.getRuntime().exec("cmd.exe /C start net start npf");

        System.out.println("WinPcap start cmd executed");
    }

    private static void stopWinPcap() throws IOException {
        Runtime.getRuntime().exec("cmd.exe /C start net stop npf");

        System.out.println("Issued WinPcap stop cmd");
    }

    // Temporary wrappers to starting and stopping WinPcap
    public static void startCaptureLibrary() {
        try {
            if (osType == OsCheck.OSType.Windows) {
                System.out.println("Starting WinPcap...");
                startWinPcap();
            } else {
                System.out.println("Unsupported OS");
                System.exit(-1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void stopCaptureLibrary() {
        try {
            if (osType == OsCheck.OSType.Windows) {
                System.out.println("Stopping WinPcap...");
                stopWinPcap();
            }
            else {
                System.out.println("Unknown OS type while closing (not possible)!");
                System.exit(-1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
