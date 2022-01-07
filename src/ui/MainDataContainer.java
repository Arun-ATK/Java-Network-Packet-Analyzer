package ui;

import sysutil.SystemController;
import capture.CaptureController;
import packets.Packet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainDataContainer extends JFrame {
    JPanel dataPanel;

    public MainDataContainer() {
        sysutil.SystemController.startCaptureLibrary();
        CaptureController.startCapture(MainDataContainer.this);


        // Closing the frame should first make a call to stop the underlying packet capture library
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeWindow(MainDataContainer.this);
                System.exit(0);
            }
        });

        /* *******************************************
         *              MENU BAR SECTION
         * *******************************************/
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");

        // Starting a new Capture should close the current window, and then open the Start Screen
        JMenuItem startNewCaptureMenuItem = new JMenuItem("Start new Capture");
        startNewCaptureMenuItem.addActionListener(e -> {
            closeWindow(MainDataContainer.this);
            new StartScreen();
        });

        fileMenu.add(startNewCaptureMenuItem);

        JMenu closeMenu = new JMenu("Close");
        closeMenu.addActionListener(e -> {
            closeWindow(MainDataContainer.this);
            System.exit(0);
        });

        menuBar.add(fileMenu);
        menuBar.add(closeMenu);

        /* **********************************************
         *                  MAIN PANEL
         * All Captured packets will be displayed here
         * TODO: Double click to view additional details
         * **********************************************/
        dataPanel = new JPanel();
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));
        JScrollPane dataScrollPane = new JScrollPane(dataPanel,
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
        SystemController.stopCaptureLibrary();
        frame.dispose();
    }

    public void addPacket(Packet packet) {
//        JScrollPanel panel = new JPanel();
//        JLabel label = new JLabel("Test");

        PacketDataPanel packetPanel = new PacketDataPanel(packet);
        dataPanel.add(packetPanel);
        dataPanel.revalidate();
        dataPanel.repaint();
    }
}
