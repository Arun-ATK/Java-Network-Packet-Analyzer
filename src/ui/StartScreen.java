package ui;

import javax.swing.*;
import java.awt.*;

public class StartScreen extends JFrame {
    public StartScreen() {
        this.setTitle("Java Packet Analyser");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(0, 1));
        this.setSize(300,100);

        JButton liveCaptureButton = new JButton("Start Live Capture");
        JButton openPcapFileButton = new JButton("Open Pcap File");

        liveCaptureButton.addActionListener(e -> {
            new MainDataContainer(MainDataContainer.Mode.LIVE);
            this.dispose();
        });

        openPcapFileButton.addActionListener(e -> {
            new MainDataContainer(MainDataContainer.Mode.OFFLINE);
            this.dispose();
        });

        this.add(liveCaptureButton);
        this.add(openPcapFileButton);

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
