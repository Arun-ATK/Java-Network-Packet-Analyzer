package capture;

import java.util.ArrayList;

public class CaptureController {
    private static final PacketCapturer capturer = new JNetPcapHandler();

    public static ArrayList<NetworkInterface> getInterfaces() {
        ArrayList<NetworkInterface> interfaces = capturer.getNetworkInterfaces();
        for (NetworkInterface anInterface : interfaces) {
            System.out.println("INTERFACE ID: " + anInterface.getId());
            System.out.println("Interface Name: " + anInterface.getDescription());
            System.out.println("----");
        }

        return interfaces;
    }

    public static void startCapture(int interfaceID) {
        capturer.openInterface(interfaceID);
    }
}
