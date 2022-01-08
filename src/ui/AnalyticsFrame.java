package ui;

import analyse.AnalysisController;
import packets.Packet;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;

public class AnalyticsFrame extends JFrame {
    public AnalyticsFrame() {
        this.setTitle("Analytics");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        /* *************
         * PACKET COUNTS
         * *************/
        JPanel packetCountContainer = new JPanel();
        packetCountContainer.setLayout(new BorderLayout());
        packetCountContainer.setBorder(new TitledBorder(new EtchedBorder(), "Packet Count"));

        DefaultTableModel defaultTableModel =
                new DefaultTableModel(0, 0);

        String[] tableHeaders = new String[] { "PROTOCOL", "COUNT" };
        defaultTableModel.setColumnIdentifiers(tableHeaders);

        JTable packetCountTable = new JTable();
        packetCountTable.setModel(defaultTableModel);

        // Get count for each protocol and add it to the table
        for (Map.Entry<Packet.Protocol, Integer> protocolCount : AnalysisController.getCounts().entrySet()) {
            String key = protocolCount.getKey().toString();
            String value = protocolCount.getValue().toString();

            defaultTableModel.addRow(new Object[] { key, value });
        }
        // TODO: Get pure TCP/IP packet count

        packetCountTable.revalidate();
        packetCountTable.repaint();

        JScrollPane packetCountScroll = new JScrollPane(packetCountTable,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        packetCountContainer.add(packetCountScroll);

        /* ***********
         * FINAL STUFF
         * ***********/
        this.add(packetCountScroll);
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
