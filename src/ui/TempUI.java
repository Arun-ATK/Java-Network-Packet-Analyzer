package ui;

import org.jnetpcap.nio.JBuffer;

import sysutil.SystemController;
import capture.CaptureController;
import capture.NetworkInterface;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class TempUI extends JFrame {

    public void startUI() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        // Frame contents...
        JButton startWinPcapButton = new JButton("Start WinPcap");
        JButton stopWinPcapButton = new JButton("Stop WinPcap");
        JButton getInterfacesButton = new JButton("List interfaces");

        JLabel selectInterfaceLabel = new JLabel("Enter interface number: ");
        JTextField selectInterfaceTextField = new JTextField();
        selectInterfaceTextField.setPreferredSize(new Dimension(75, 25));
        JPanel selectInterfacePanel = new JPanel();
        selectInterfacePanel.add(selectInterfaceLabel);
        selectInterfacePanel.add(selectInterfaceTextField);

        JButton startCaptureButton = new JButton("Start Capture");

        this.add(startWinPcapButton);
        this.add(stopWinPcapButton);
        this.add(getInterfacesButton);
        this.add(selectInterfacePanel);
        this.add(startCaptureButton);

        this.pack();
        this.setVisible(true);
    }
}
