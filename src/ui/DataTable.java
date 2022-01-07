package ui;

import packets.Packet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class DataTable extends JTable {
    int nextRowID = 1;
    DefaultTableModel defaultTableModel;

    public DataTable() {
        defaultTableModel = new DefaultTableModel(0, 0);

        String[] tableHeaders = new String[] {"ID", "TIME", "PROTOCOL", "SIZE", "HEADER SIZE"};
        defaultTableModel.setColumnIdentifiers(tableHeaders);
        this.setModel(defaultTableModel);

    }

    public void addRow(int id, Packet packet) {
        String idString = String.valueOf(id);
        String protocol = packet.getProtocol().toString();
        String time = packet.getReceiveDate().toString();
        String size = String.valueOf(packet.getSize());
        String headerSize = String.valueOf(packet.getHeaderSize());

        defaultTableModel.addRow(new Object[] {idString, time, protocol, size, headerSize});
    }
}
