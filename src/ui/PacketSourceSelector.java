package ui;

import capture.CaptureController;
import capture.NetworkInterface;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class PacketSourceSelector extends JFrame {
    public enum Mode {
        LIVE,
        OFFLINE,
    }

    String selectedFile;
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
            // TODO JFileChooser thing
            JFileChooser fileChooser = new JFileChooser("captures");
            setUpFileChooser(fileChooser);

            fileChooser.addActionListener(e -> selectedFile = fileChooser.getName());

        }

        /* -----------------------------
         * LOGIC FOR SELECTING INTERFACE
         * ----------------------------- */
        else if (mode == Mode.LIVE) {
            selectSourceButton.setText("Select Interface");
            // TODO DropDown Menu displaying all the available interfaces
            JComboBox<NetworkInterface> interfaceComboBox = new JComboBox<>();

            ArrayList<NetworkInterface> interfaces = CaptureController.getInterfaces();
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
            if (mode == Mode.LIVE) {
                CaptureController.openInterfaceForCapture(selectedInterfaceID);
            }
            else if (mode == Mode.OFFLINE) {
                CaptureController.openPcapFile(new File("captures/less_http.pcap"));
            }

            new MainDataContainer();
            this.dispose();
        });


        // Blank Panel for spacing
        this.add(new JPanel());

        this.add(selectSourceButton);

        this.pack();
        this.setResizable(false);
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
