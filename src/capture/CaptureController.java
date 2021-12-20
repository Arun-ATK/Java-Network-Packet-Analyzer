package capture;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CaptureController implements ActionListener {
    private static final PacketCapturer capturer = new JNetPcapHandler();

    public static ArrayList<NetworkInterface> getInterfaces() {
        return capturer.getNetworkInterfaces();
    }

    public static void startCapture(int interfaceNum) {
        capturer.openInterface(interfaceNum);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Nothing yet...");
    }
}
