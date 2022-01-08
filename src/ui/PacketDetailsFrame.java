package ui;

import capture.CaptureController;
import packets.Packet;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class PacketDetailsFrame extends JFrame {
    public PacketDetailsFrame(int id) {
        this.setTitle("Packet Details");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Packet packet = CaptureController.getPacket(id);

        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BorderLayout());
        JScrollPane containerScrollPane = new JScrollPane(containerPanel,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JPanel leftAlignPanel = new JPanel();
        leftAlignPanel.setLayout(new BoxLayout(leftAlignPanel, BoxLayout.Y_AXIS));

        /* *********************
         * NETWORK LAYER HEADERS
         * *********************/
        JPanel networkHeaderPanel = new JPanel();
        networkHeaderPanel.setLayout(new BoxLayout(networkHeaderPanel, BoxLayout.PAGE_AXIS));

        for (Map.Entry<String, String> entry : packet.getNetworkHeaders().entrySet()) {
            JLabel keyLabel = new JLabel("<html>" + "<b>" + entry.getKey() + ": " + "</b> " + "</html>");
            JLabel valueLabel = new JLabel("<html>" + "<I>" + entry.getValue() + "</I>" + "</html>");

            JPanel keyValuePanel = new JPanel();
            keyValuePanel.setSize(new Dimension(200, 50));
            keyValuePanel.add(keyLabel);
            keyValuePanel.add(valueLabel);

            networkHeaderPanel.add(keyValuePanel);
        }
        leftAlignPanel.add(networkHeaderPanel);

        /* ***************
         * BOX GREEDY GLUE
         * ***************/
        Box.Filler glue = (Box.Filler) Box.createVerticalGlue();
        glue.changeShape(glue.getMinimumSize(),
                new Dimension(0, Short.MAX_VALUE),
                glue.getMaximumSize());

        leftAlignPanel.add(glue);


        containerPanel.add(leftAlignPanel, BorderLayout.WEST);

        this.add(containerScrollPane, BorderLayout.CENTER);
        this.setMaximumSize(new Dimension(500, 500));
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
