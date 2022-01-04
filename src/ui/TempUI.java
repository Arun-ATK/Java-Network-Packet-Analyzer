package ui;

import sysutil.SystemController;
import capture.CaptureController;

import javax.swing.*;
import java.awt.*;
import java.io.File;

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
        JButton stopCaptureButton = new JButton("Stop Capture");

        JButton openPcapFileButton = new JButton("Open Pcap File");

        JButton savePcapFileButton = new JButton("Save as Pcap File");

        /*
         * Adding ActionListeners lambdas to each Button before adding them to the frame
         * Each ActionListener will process the input data (if any) before calling
         * an appropriate function in the CaptureController
         */
        startWinPcapButton.addActionListener(e -> {
            System.out.println(e.toString());
            SystemController.startWin();
        });

        stopWinPcapButton.addActionListener(e -> {
            System.out.println(e.toString());
            SystemController.stopWin();
        });

        getInterfacesButton.addActionListener(e -> {
            System.out.println(e.toString());
            CaptureController.getInterfaces();
        });

        startCaptureButton.addActionListener(e -> {
            System.out.println(e.toString());

            int interfaceID = Integer.parseInt(selectInterfaceTextField.getText());
            CaptureController.openInterfaceForCapture(interfaceID);
        });

        stopCaptureButton.addActionListener(e -> {
            System.out.println(e);
            CaptureController.stopCapture();
        });

        openPcapFileButton.addActionListener(e -> {
            System.out.println(e);
            CaptureController.openPcapFile(new File("captures/less_http.pcap"));
        });

        savePcapFileButton.addActionListener(e -> {
            System.out.println(e);
            CaptureController.saveAsPcapFile("captures/newFile.pcap");
        });

        this.add(startWinPcapButton);
        this.add(stopWinPcapButton);
        this.add(getInterfacesButton);
        this.add(selectInterfacePanel);
        this.add(startCaptureButton);
        this.add(stopCaptureButton);
        this.add(openPcapFileButton);
        this.add(savePcapFileButton);

        this.pack();
        this.setVisible(true);
    }
}
