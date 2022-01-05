package ui;

import sysutil.SystemController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainDataContainer extends JFrame {
    public enum Mode {
        LIVE,
        OFFLINE,
    }

    public MainDataContainer(Mode mode) {
        sysutil.SystemController.startCaptureLibrary();

        JFrame frame = this;
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeWindow(frame);
                System.exit(0);
            }
        });

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu closeMenu = new JMenu("Close");

        JMenuItem newMenuItem = new JMenuItem("Start new Capture");
        newMenuItem.addActionListener(e -> {
            closeWindow(frame);
            new StartScreen();
        });

        fileMenu.add(newMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(closeMenu);

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
