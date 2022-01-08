package ui;

import capture.CaptureController;
import packets.Packet;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class PacketDetailsFrame extends JFrame {
    public PacketDetailsFrame(int id) {
        Packet packet = CaptureController.getPacket(id);

        this.setTitle("Packet Details");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new GridLayout(1, 0));

        /* *********************
         * NETWORK LAYER HEADERS
         * *********************/
        JPanel networkHeaderPanel = new JPanel();
        networkHeaderPanel.setLayout(new BoxLayout(networkHeaderPanel, BoxLayout.Y_AXIS));
        JScrollPane networkHeaderScroll = new JScrollPane(networkHeaderPanel,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        displayHeaders(networkHeaderPanel, packet.getNetworkHeaders());
        this.add(networkHeaderScroll);

        /* ***********************
         * TRANSPORT LAYER HEADERS
         * ***********************/
        JPanel transportHeaderPanel = new JPanel();
        transportHeaderPanel.setLayout(new BoxLayout(transportHeaderPanel, BoxLayout.Y_AXIS));
        JScrollPane transportHeaderScroll = new JScrollPane(transportHeaderPanel,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        displayHeaders(transportHeaderPanel, packet.getTransportHeaders());
        this.add(transportHeaderScroll);

        /* *************************
         * APPLICATION LAYER HEADERS
         * *************************/
        JPanel applicationHeaderPanel = new JPanel();
        applicationHeaderPanel.setLayout(new BoxLayout(applicationHeaderPanel, BoxLayout.Y_AXIS));
        JScrollPane applicationHeaderScroll = new JScrollPane(applicationHeaderPanel,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        displayHeaders(applicationHeaderPanel, packet.getApplicationHeaders());
        this.add(applicationHeaderScroll);

        this.setMinimumSize(new Dimension(1000, 500));
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private static void displayHeaders(JPanel panel, Map<String, String> headers) {
        for (Map.Entry<String, String> header : headers.entrySet()) {
            JLabel keyLabel = new JLabel("<html>" + "<U>" + header.getKey() + ": " + "</U> " + "</html>" + "  ");
            JLabel valueLabel = new JLabel("<html>" + "<I>" + header.getValue() + "</I>" + "</html>" + "  ");

            JPanel keyValuePanel = new JPanel();
            keyValuePanel.setMaximumSize(new Dimension(1000, 30));

            keyValuePanel.add(keyLabel);
            keyValuePanel.add(valueLabel);

            panel.add(keyValuePanel);
        }
    }
}
