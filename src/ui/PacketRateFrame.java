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
        float packetRate = AnalysisController.getPacketRate();
        float sizeMB = AnalysisController.getTotalSize() / (1024 * 8);
        float transferRate = AnalysisController.getTransferRate();

        JPanel countPanel = new JPanel();
        JLabel countLabel = new JLabel("    Packet count: ");
        JLabel countValueLabel = new JLabel(count + "    ");
        countPanel.add(countLabel);
        countPanel.add(countValueLabel);

        JPanel durationPanel = new JPanel();
        JLabel durationLabel = new JLabel("    Time Elapsed: ");
        JLabel durationValueLabel = new JLabel(duration + "s    ");
        durationPanel.add(durationLabel);
        durationPanel.add(durationValueLabel);

        JPanel ratePanel = new JPanel();
        JLabel rateLabel = new JLabel("    Rate: ");
        JLabel rateValueLabel = new JLabel(packetRate + " P/s    ");
        ratePanel.add(rateLabel);
        ratePanel.add(rateValueLabel);

        JPanel totalDataPanel = new JPanel();
        JLabel totalDataLabel = new JLabel("    Total transferred data: ");
        JLabel totalDataValue = new JLabel(sizeMB + " MB    ");
        totalDataPanel.add(totalDataLabel);
        totalDataPanel.add(totalDataValue);

        JPanel dataRatePanel = new JPanel();
        JLabel dataRateLabel = new JLabel(" Transfer rate: ");
        JLabel dataRateValue = new JLabel(transferRate + "MB/s    ");
        dataRatePanel.add(dataRateLabel);
        dataRatePanel.add(dataRateValue);

        this.add(countPanel);
        this.add(durationPanel);
        this.add(ratePanel);
        this.add(new JPanel());
        this.add(totalDataPanel);
        this.add(dataRatePanel);

        this.setSize(275, 180);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
