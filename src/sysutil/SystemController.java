package sysutil;

import capture.JNetPcapHandler;
import capture.PacketCapturer;
import ui.TempUI;

import java.io.IOException;

public class SystemController {

    public static void main(String[] args) {
        System.out.println("Java Packet Analyser project");

        OsCheck.OSType osType = OsCheck.getOperatingSystemType();
        System.out.println("OS: " + osType);

        ui.TempUI tui = new TempUI();
        tui.startUI();
    }

    private static void startWinPcap() throws IOException {
        Runtime.getRuntime().exec("cmd.exe /C start net start npf");

        System.out.println("WinPcap start cmd executed");
    }

    private static void stopWinPcap() throws IOException {
        Runtime.getRuntime().exec("cmd.exe /C start net stop npf");

        System.out.println("Issued WinPcap stop cmd");
    }

    // Temporary wrappers to starting and stopping winpcap
    public static void startWin() {
        try {
            startWinPcap();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void stopWin() {
        try {
            stopWinPcap();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
