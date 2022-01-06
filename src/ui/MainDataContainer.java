package ui;

import sysutil.SystemController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainDataContainer extends JFrame {


    public MainDataContainer() {
        sysutil.SystemController.startCaptureLibrary();

        // Closing the frame should first make a call to stop the underlying packet capture library
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
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
        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new GridLayout(0, 1));

        this.add(menuBar, BorderLayout.NORTH);

        this.setSize(300, 300);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void closeWindow(JFrame frame) {
        SystemController.stopCaptureLibrary();
        frame.dispose();
    }
}
