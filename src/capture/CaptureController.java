package capture;

import analyse.AnalysisController;
import packets.Packet;
import ui.MainDataContainer;

import java.io.File;
import java.util.ArrayList;

public class CaptureController {
    private static final PacketCapturer capturer = new JNetPcapHandler();
    private static ArrayList<Packet> packetHolder;

    static MainDataContainer dataContainer;
    static boolean paused = false;

    public static ArrayList<NetworkInterface> getInterfaces() {
        return capturer.getNetworkInterfaces();
    }

    public static boolean openInterfaceForCapture(int interfaceID) {
        return capturer.openInterface(interfaceID);
    }

    public static void startCapture(MainDataContainer dataContainer) {
        CaptureController.dataContainer = dataContainer;
        packetHolder = new ArrayList<>();
        capturer.resetRawPackets();
        paused = false;

        AnalysisController.resetAnalytics();
        capturer.startCapture();
    }

    public static void resumeCapture() {
        paused = false;
        capturer.startCapture();
    }

    public static void stopCapture() {
        paused = true;
        capturer.stopCapture();
    }

    public static boolean openPcapFile(File file) {
        return capturer.openPcapFile(file);
    }

    public static void addNewPacket(Packet packet) {
        packetHolder.add(packet);
        dataContainer.addPacket(packetHolder.size() - 1, packet);
        AnalysisController.addPacket(packet);
    }

    public static Packet getPacket(int id) {
        return packetHolder.get(id);
    }

    public static void saveAsPcapFile(File file) {
        capturer.saveFile(file.getPath());
    }

    public static boolean isPaused() {
        return paused;
    }
}
