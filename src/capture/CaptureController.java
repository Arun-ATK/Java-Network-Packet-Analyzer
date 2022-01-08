package capture;

import analyse.AnalysisController;
import packets.HTTPPacket;
import packets.Packet;
import ui.MainDataContainer;

import java.io.File;
import java.util.ArrayList;

public class CaptureController {
    private static final PacketCapturer capturer = new JNetPcapHandler();

    private static final ArrayList<Packet> packetHolder = new ArrayList<>();

    static MainDataContainer dataContainer;


    public static ArrayList<NetworkInterface> getInterfaces() {
        ArrayList<NetworkInterface> interfaces = capturer.getNetworkInterfaces();
        for (NetworkInterface anInterface : interfaces) {
            System.out.println("INTERFACE ID: " + anInterface.getId());
            System.out.println("Interface Name: " + anInterface.getDescription());
            System.out.println("----");
        }

        return interfaces;
    }

    public static void openInterfaceForCapture(int interfaceID) {
        capturer.openInterface(interfaceID);
    }

    public static void startCapture(MainDataContainer dataContainer) {
        CaptureController.dataContainer = dataContainer;
        capturer.startCapture();
    }
    public static void stopCapture() {
        capturer.stopCapture();
    }

    public static void openPcapFile(File file) {
        capturer.openPcapFile(file.getPath());
    }

    public static void addNewPacket(Packet packet) {
        // TEMP FILER
        // TODO: REMOVE THIS!!!
        if (!(packet instanceof HTTPPacket)) return;
        packetHolder.add(packet);
        dataContainer.addPacket(packetHolder.size() - 1, packet);
        AnalysisController.addPacket(packet);
    }

    public static Packet getPacket(int id) {
        return packetHolder.get(id);
    }

    public static void saveAsPcapFile(String s) {
        capturer.saveFile(s);
    }
}
