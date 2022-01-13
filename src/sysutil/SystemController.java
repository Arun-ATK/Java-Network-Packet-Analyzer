package sysutil;

import ui.StartScreen;

import java.io.IOException;

public class SystemController {
    private static OsCheck.OSType osType;

    public static void main(String[] args) {
        // Adding ShutDown hook to ensure logger safely closes it's output stream
        Runtime.getRuntime().addShutdownHook(Logger.getLogger().shutDownThread);

        Logger.getLogger().writeMessage("Java Packet Sniffer starting...");

        try {
            System.loadLibrary("jnetpcap");
            Logger.getLogger().writeMessage("JNetPcap Loaded");
        } catch (Exception exception) {
            Logger.getLogger().writeMessage(exception.getMessage());
            System.exit(-1);
        }

        osType = OsCheck.getOperatingSystemType();
        Logger.getLogger().writeMessage("Detected OS: " + osType);

        new StartScreen();
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
        try {
            if (osType == OsCheck.OSType.Windows) {
                startWinPcap();
            } else {
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
            else {
                Logger.getLogger().writeMessage("Unsupported OS while closing");
                System.exit(-1);
            }
        } catch (Exception e) {
            Logger.getLogger().writeMessage(e.getMessage());
        }
    }
}
