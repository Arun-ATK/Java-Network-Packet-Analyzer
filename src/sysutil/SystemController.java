package sysutil;

import capture.CaptureController;
import capture.NetworkInterface;
import ui.StartScreen;

import java.io.IOException;
import java.util.ArrayList;

public class SystemController {
    private static OsCheck.OSType osType;
    private static boolean captureLibraryRunning = false;

    public static void main(String[] args) {
        // Adding ShutDown hook to ensure logger safely closes it's output stream
        Runtime.getRuntime().addShutdownHook(Logger.getLogger().shutDownThread);

        Logger.getLogger().writeMessage("Java Packet Sniffer starting...");

        osType = OsCheck.getOperatingSystemType();
        Logger.getLogger().writeMessage("Detected OS: " + osType);

        try {
            if (osType == OsCheck.OSType.Windows) {
                System.loadLibrary("jnetpcap");
                Logger.getLogger().writeMessage("WIN: jNetPcap Loaded");
            }
            else if (osType == OsCheck.OSType.Linux) {
                System.loadLibrary("jnetpcap");
                Logger.getLogger().writeMessage("LINUX: jNetPcap Loaded");
            }
        } catch (Exception | Error e) {
            Logger.getLogger().writeMessage(e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }

//        new StartScreen();
        ArrayList<NetworkInterface> interfaces = CaptureController.getInterfaces();
        for (NetworkInterface anInterface : interfaces) {
            System.out.println("- " + anInterface.getDescription());
        }
    }

    private static void startWinPcap() throws IOException {
        Runtime.getRuntime().exec("cmd.exe /C start net start npf");

        Logger.getLogger().writeMessage("WinPcap Started");
    }

    private static void stopWinPcap() throws IOException {
        Runtime.getRuntime().exec("cmd.exe /C start net stop npf");

        Logger.getLogger().writeMessage("WinPcap Stopped");
    }

    // Wrappers to starting and stopping necessary capture library
    public static void startCaptureLibrary() {
        if (captureLibraryRunning) {
            return;
        }
        try {
            if (osType == OsCheck.OSType.Windows) {
                startWinPcap();
                captureLibraryRunning = true;
            }
            else if (osType == OsCheck.OSType.Linux) {
                Logger.getLogger().writeMessage("LibPcap");
                captureLibraryRunning = true;
            }
            else {
                    Logger.getLogger().writeMessage("Unsupported OS");
                    System.exit(-1);
            }
        } catch (Exception e) {
            Logger.getLogger().writeMessage(e.getMessage());
        }
    }

    public static void stopCaptureLibrary() {
        try {
            if (osType == OsCheck.OSType.Windows) {
                stopWinPcap();
            }
            captureLibraryRunning = false;
        } catch (Exception e) {
            Logger.getLogger().writeMessage(e.getMessage());
        }
    }
}
