package ui;

import capture.CaptureController;
import packets.Packet;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Map;

public class PacketDetailsFrame extends JFrame {
    public PacketDetailsFrame(int id) {
        Packet packet = CaptureController.getPacket(id);

        this.setTitle("Packet Details");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainHeaderPanel = new JPanel();
        mainHeaderPanel.setLayout(new GridLayout(1, 0));

        /* *********************
         * NETWORK LAYER HEADERS
         * *********************/
        if (packet.getProtocolLayer() >= 3) {
            JPanel networkContainer = new JPanel();
            networkContainer.setLayout(new BorderLayout());
            networkContainer.setBorder(new TitledBorder(new EtchedBorder(), "Network Layer Headers"));

            JPanel networkHeaderPanel = new JPanel();
            networkHeaderPanel.setLayout(new BoxLayout(networkHeaderPanel, BoxLayout.Y_AXIS));
            JScrollPane networkHeaderScroll = new JScrollPane(networkHeaderPanel,
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            addHeaders(networkHeaderPanel, packet.getNetworkHeaders());

            networkContainer.add(networkHeaderScroll);
            mainHeaderPanel.add(networkContainer);
        }

        /* ***********************
         * TRANSPORT LAYER HEADERS
         * ***********************/
        if (packet.getProtocolLayer() >= 4) {
            JPanel transportContainer = new JPanel();
            transportContainer.setLayout(new BorderLayout());
            transportContainer.setBorder(new TitledBorder(new EtchedBorder(), "Transport Layer Headers"));

            JPanel transportHeaderPanel = new JPanel();
            transportHeaderPanel.setLayout(new BoxLayout(transportHeaderPanel, BoxLayout.Y_AXIS));
            JScrollPane transportHeaderScroll = new JScrollPane(transportHeaderPanel,
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            addHeaders(transportHeaderPanel, packet.getTransportHeaders());

            transportContainer.add(transportHeaderScroll);
            mainHeaderPanel.add(transportContainer);
        }

        /* *************************
         * APPLICATION LAYER HEADERS
         * *************************/
        if (packet.getProtocolLayer() >= 5) {
            JPanel applicationContainer = new JPanel();
            applicationContainer.setLayout(new BorderLayout());
            applicationContainer.setBorder(new TitledBorder(new EtchedBorder(), "Application Layer Headers"));

            JPanel applicationHeaderPanel = new JPanel();
            applicationHeaderPanel.setLayout(new BoxLayout(applicationHeaderPanel, BoxLayout.Y_AXIS));
            JScrollPane applicationHeaderScroll = new JScrollPane(applicationHeaderPanel,
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            addHeaders(applicationHeaderPanel, packet.getApplicationHeaders());

            applicationContainer.add(applicationHeaderScroll);
            mainHeaderPanel.add(applicationContainer);
        }

        /* ************
         * DATA SECTION
         * ************/
        JPanel dataPanel = new JPanel();
        dataPanel.setSize(new Dimension(500, 20));
        dataPanel.setBorder(new TitledBorder(new EtchedBorder(), "HEX DUMP"));

        JTextArea dataTextArea = new JTextArea(15, 80);
        Font monoFont = new Font("Courier New", Font.PLAIN, 14);
        dataTextArea.setFont(monoFont);
        dataTextArea.setText(packet.getData());
        dataTextArea.setEditable(false);

        JScrollPane dataScroll = new JScrollPane(dataTextArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        dataPanel.add(dataScroll);

        /* ***********
         * FINAL STUFF
         * ***********/
        if (packet.getProtocolLayer() >= 3) {
            this.add(mainHeaderPanel, BorderLayout.CENTER);
        }

        if (packet.getProtocol() == Packet.Protocol.Unknown) {
            this.add(dataPanel, BorderLayout.CENTER);
        }
        else {
            this.add(dataPanel, BorderLayout.SOUTH);
        }

        this.setMinimumSize(new Dimension(1300, 1000));
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private static void addHeaders(JPanel panel, Map<String, String> headers) {
        for (Map.Entry<String, String> header : headers.entrySet()) {
            JLabel keyLabel = new JLabel("<html>" + "<U>" + header.getKey() + ": " + "</U> " + "</html>" + "  ");
            JLabel valueLabel = new JLabel("<html>" + "<I>" + header.getValue() + "</I>" + "</html>" + "  ");

            JPanel keyValuePanel = new JPanel();
            keyValuePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
            keyValuePanel.add(keyLabel);
            keyValuePanel.add(valueLabel);

            panel.add(keyValuePanel);
        }
    }
}
