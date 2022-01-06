package ui;

import packets.Packet;

import javax.swing.*;
import java.awt.*;

public class PacketDataPanel extends JPanel {
    public PacketDataPanel(Packet packet) {
        this.setLayout(new GridLayout(1, 0));

        JLabel protocolLabel = new JLabel(packet.getProtocol().toString());
        JLabel timeLabel = new JLabel(packet.getReceiveDate().toString());
        JLabel sizeLabel = new JLabel(String.valueOf(packet.getSize()));
        JLabel headerSizeLabel = new JLabel(String.valueOf(packet.getHeaderSize()));

        this.add(protocolLabel);
        this.add(timeLabel);
        this.add(sizeLabel);
        this.add(headerSizeLabel);
    }
}
