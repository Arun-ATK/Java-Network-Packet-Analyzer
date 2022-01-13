package ui;

import javax.swing.*;
import java.awt.*;

public class StartScreen extends JFrame {
    public StartScreen() {
        this.setTitle("Java Packet Analyser");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(0, 1));
        this.setSize(300,125);

        JButton liveCaptureButton = new JButton("Start Live Capture");
        JButton openPcapFileButton = new JButton("Open Pcap File");

        /*
         * After selecting an option, open the window for selecting the
         * interface/file for getting the packets.
         * Close the window after selecting an option
         */

        liveCaptureButton.addActionListener(e -> {
            new PacketSourceSelector(PacketSourceSelector.Mode.LIVE);
            this.dispose();
        });

        openPcapFileButton.addActionListener(e -> {
            new PacketSourceSelector(PacketSourceSelector.Mode.OFFLINE);
            this.dispose();
        });

        this.add(liveCaptureButton);
        this.add(openPcapFileButton);

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
