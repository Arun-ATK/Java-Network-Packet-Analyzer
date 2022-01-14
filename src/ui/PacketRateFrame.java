package ui;

import analyse.AnalysisController;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

public class PacketRateFrame extends JFrame {
    public PacketRateFrame() {
        this.setTitle("Packet Rate");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new GridLayout(0, 1));

        int count = AnalysisController.getTotalPackets();
        long duration = TimeUnit.MILLISECONDS.toSeconds(AnalysisController.getElapsedTime());
        float rate = AnalysisController.getPacketRate();

        JPanel countPanel = new JPanel();
        JLabel countLabel = new JLabel("    Packet count: ");
        JLabel countValueLabel = new JLabel(count + "    ");
        countPanel.add(countLabel);
        countPanel.add(countValueLabel);

        JPanel durationPanel = new JPanel();
        JLabel durationLabel = new JLabel("    Time Elapsed: ");
        JLabel durationValueLabel = new JLabel(duration + "    ");
        durationPanel.add(durationLabel);
        durationPanel.add(durationValueLabel);

        JPanel ratePanel = new JPanel();
        JLabel rateLabel = new JLabel("    Rate: ");
        JLabel rateValueLabel = new JLabel(rate + "    ");
        ratePanel.add(rateLabel);
        ratePanel.add(rateValueLabel);

        this.add(countPanel);
        this.add(durationPanel);
        this.add(ratePanel);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
