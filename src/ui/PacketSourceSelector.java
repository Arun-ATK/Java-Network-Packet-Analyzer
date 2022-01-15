package ui;

import capture.CaptureController;
import capture.NetworkInterface;
import sysutil.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class PacketSourceSelector extends JFrame {
    public enum Mode {
        LIVE,
        OFFLINE,
    }

    File selectedFile = null;
    int selectedInterfaceID;

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
        JButton selectSourceButton = new JButton();

        /* -----------------------------
         * LOGIC FOR SELECTING PCAP FILE
         * ----------------------------- */
        if (mode == Mode.OFFLINE) {
            selectSourceButton.setText("Select File");

            JButton browseButton = new JButton("Browse...");
            browseButton.addActionListener(e -> {
                JFrame fileSelectFrame = new JFrame("Select File");
                fileSelectFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                JFileChooser fileChooser = new JFileChooser(".");
                int result = fileChooser.showOpenDialog(fileSelectFrame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                    selectedLabel.setText(selectedFile.getName());
                }
            });

            this.add(browseButton);
        }

        /* -----------------------------
         * LOGIC FOR SELECTING INTERFACE
         * ----------------------------- */
        else if (mode == Mode.LIVE) {
            selectSourceButton.setText("Select Interface");
            JComboBox<NetworkInterface> interfaceComboBox = new JComboBox<>();

            ArrayList<NetworkInterface> interfaces = CaptureController.getInterfaces();

            if (interfaces.size() == 0) {
                new ErrorScreen("Run program as administrator!", true);

            }
            for (NetworkInterface anInterface : interfaces) {
                interfaceComboBox.addItem(anInterface);
            }


            selectedLabel.setText(interfaceComboBox.getItemAt(0).getDescription());
            interfaceComboBox.addItemListener(e -> {
                NetworkInterface selectedNetworkInterface = (NetworkInterface) interfaceComboBox.getSelectedItem();
                assert selectedNetworkInterface != null;
                selectedLabel.setText(selectedNetworkInterface.getDescription());

                selectedInterfaceID = selectedNetworkInterface.getId();
            });

            this.add(interfaceComboBox);
        }

        /* ********************************
         * ACTIONLISTENER FOR SELECT BUTTON
         * ********************************/
        selectSourceButton.addActionListener(e -> {
            boolean allGood = false;
            if (mode == Mode.LIVE) {
                allGood = CaptureController.openInterfaceForCapture(selectedInterfaceID);
            }
            else if (mode == Mode.OFFLINE) {
                if (selectedFile != null) {
                    allGood = CaptureController.openPcapFile(selectedFile);
                }
                else {
                    new ErrorScreen("No file selected!", false);
                    Logger.getLogger().writeMessage("No file selected");
                }
            }

            if (allGood) {
                new MainDataContainer();
                this.dispose();
            }
        });

        // Blank Panel for spacing
        this.add(new JPanel());

        this.add(selectSourceButton);

        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
