package ui;

import sysutil.SystemController;
import capture.CaptureController;
import packets.Packet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class MainDataContainer extends JFrame {
    DataTable dataTable;

    public MainDataContainer() {
        sysutil.SystemController.startCaptureLibrary();
        CaptureController.startCapture(MainDataContainer.this);

        // Closing the frame should first make a call to stop the underlying packet capture library
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeWindow(MainDataContainer.this);
                SystemController.stopCaptureLibrary();
                System.exit(0);
            }
        });

        this.setTitle("Packet Capture");

        /* *******************************************
         *              MENU BAR SECTION
         * *******************************************/
        JMenuBar menuBar = new JMenuBar();

        // Menu for saving pcap files and starting new captures
        JMenu fileMenu = new JMenu("File");

        JMenuItem startNewLiveCaptureMenuItem = new JMenuItem("Start new Live Capture");
        startNewLiveCaptureMenuItem.addActionListener(e -> {
            closeWindow(MainDataContainer.this);
            new PacketSourceSelector(PacketSourceSelector.Mode.LIVE);
        });

        JMenuItem openPcapFileMenuItem = new JMenuItem("Open Pcap File");
        openPcapFileMenuItem.addActionListener(e -> {
            closeWindow(MainDataContainer.this);
            new PacketSourceSelector(PacketSourceSelector.Mode.OFFLINE);
        });

        JMenuItem saveAsPcapFileMenuItem = new JMenuItem("Save Capture");
        saveAsPcapFileMenuItem.addActionListener(e -> {
            JFrame saveFileFrame = new JFrame("Save As...");
            saveFileFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JFileChooser fileChooser = new JFileChooser(".");
            int result = fileChooser.showOpenDialog(saveFileFrame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File saveFile = fileChooser.getSelectedFile();
                CaptureController.saveAsPcapFile(saveFile);
            }
        });

        fileMenu.add(startNewLiveCaptureMenuItem);
        fileMenu.add(openPcapFileMenuItem);
        fileMenu.add(saveAsPcapFileMenuItem);

        // Menu for pausing/resuming capturing
        JMenu captureMenu = new JMenu("Capture");
        JMenuItem pauseResumeMenuItem = new JMenuItem("Pause");
        pauseResumeMenuItem.setSize(20, 10);
        pauseResumeMenuItem.addActionListener(e -> {
            if (CaptureController.isPaused()) {
                pauseResumeMenuItem.setText("Pause");
                CaptureController.resumeCapture();
            }
            else {
                pauseResumeMenuItem.setText("Resume");
                CaptureController.stopCapture();
            }
        });
        captureMenu.add(pauseResumeMenuItem);

        // Menu for displaying analytical information
        JMenu analyticsMenu = new JMenu("Analytics");
        JMenuItem openPacketCountMenuItem = new JMenuItem("Packet Count");
        openPacketCountMenuItem.addActionListener(e -> new PacketCountFrame());
        JMenuItem openPacketRateMenuItem = new JMenuItem("Packet Rate");
        openPacketRateMenuItem.addActionListener(e -> new PacketRateFrame());
        analyticsMenu.add(openPacketCountMenuItem);
        analyticsMenu.add(openPacketRateMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(captureMenu);
        menuBar.add(analyticsMenu);

        /* **********************************************
         *                  MAIN PANEL
         * All Captured packets will be displayed here
         * **********************************************/
        dataTable = new DataTable();
        JScrollPane dataScrollPane = new JScrollPane(dataTable,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        dataScrollPane.getVerticalScrollBar().setUnitIncrement(16);


        this.add(menuBar, BorderLayout.NORTH);
        this.getContentPane().add(dataScrollPane, BorderLayout.CENTER);

        this.setSize(300, 300);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void closeWindow(JFrame frame) {
        CaptureController.stopCapture();
        frame.dispose();
    }

    public void addPacket(int id, Packet packet) {
//        if (dataTable == null) {
//            dataTable = new DataTable();
//        }
        dataTable.addRow(id, packet);
        dataTable.revalidate();
        dataTable.repaint();
    }
}
