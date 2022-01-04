package capture;

import packets.Packet;

import java.io.File;
import java.util.ArrayList;

public class CaptureController {
    private static final PacketCapturer capturer = new JNetPcapHandler();
    private static PacketHolder packetHolder = new PacketHolder();

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

    public static void stopCapture() {
        capturer.stopCapture();
    }

    public static void openPcapFile(File file) {
        capturer.openPcapFile(file.getPath());
    }

    public static void addNewPacket(Packet p) {
        packetHolder.addPacket(p);
    }

    public static void saveAsPcapFile(String s) {
        capturer.saveFile(s);
    }
}
