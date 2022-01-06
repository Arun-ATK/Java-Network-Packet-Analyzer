package ui;

import capture.CaptureController;
import capture.NetworkInterface;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static capture.CaptureController.getInterfaces;

public class PacketSourceSelector extends JFrame {
    public enum Mode {
        LIVE,
        OFFLINE,
    }

    private String chosenFileOrInterface;

    public PacketSourceSelector(Mode mode) {
        this.setTitle("Select Source");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(0, 1));

        /* **********************************
         * PANEL FOR SELECTING INTERFACE/FILE
         * **********************************/
        JPanel displaySelectedSourcePanel = new JPanel();
        displaySelectedSourcePanel.setLayout(new GridLayout(1, 2));
        JLabel sourceLabel = new JLabel();

        if (mode == Mode.LIVE) {
            sourceLabel.setText("    Selected Interface:    ");
        }
        else if(mode == Mode.OFFLINE) {
            sourceLabel.setText("    Selected File:    ");
        }

        JLabel selectedLabel = new JLabel("Nil");

        displaySelectedSourcePanel.add(sourceLabel);
        displaySelectedSourcePanel.add(selectedLabel);

        this.add(displaySelectedSourcePanel);

        /* **********************************
         *      SOURCE SELECTOR PANEL
         * **********************************/
        JPanel selectSourcePanel = new JPanel();
        selectSourcePanel.setLayout(new GridLayout(0, 1));

        JButton selectSourceButton = new JButton();
        if (mode == Mode.OFFLINE) {
            selectSourceButton.setText("Select File");
            // TODO JFileChooser thing
            JFileChooser fileChooser = new JFileChooser("captures");
            setUpFileChooser(fileChooser);

            fileChooser.addActionListener(e -> {
                chosenFileOrInterface = fileChooser.getName();
            });

        }
        else if (mode == Mode.LIVE) {
            selectSourceButton.setText("Select Interface");
            // TODO DropDown Menu displaying all the available interfaces
            JComboBox<String> interfaceComboBox = new JComboBox<>();

            ArrayList<NetworkInterface> interfaces = CaptureController.getInterfaces();
            for (NetworkInterface anInterface : interfaces) {
                interfaceComboBox.addItem(anInterface.getDescription());
            }

            this.add(interfaceComboBox);
        }

//        selectSourcePanel.add(selectSourceButton);
        this.add(selectSourcePanel);
        this.add(selectSourceButton);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void setUpFileChooser(JFileChooser fileChooser) {
        JFrame fileFrame = new JFrame("Select File");
        fileFrame.add(fileChooser);
        fileFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        fileFrame.pack();
        fileFrame.setLocationRelativeTo(PacketSourceSelector.this);
        fileFrame.setVisible(true);

    }
}
