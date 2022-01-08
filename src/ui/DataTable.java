package ui;

import packets.Packet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DataTable extends JTable {
    DefaultTableModel defaultTableModel;

    public DataTable() {
        defaultTableModel = new DefaultTableModel(0, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        String[] tableHeaders = new String[] {"ID", "TIME", "PROTOCOL", "SIZE"};
        defaultTableModel.setColumnIdentifiers(tableHeaders);
        this.setModel(defaultTableModel);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                DataTable table = (DataTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    int id =  Integer.parseInt((String) table.getValueAt(row, 0));

                     new PacketDetailsFrame(id);
                }
            }
        });
    }

    public void addRow(int id, Packet packet) {
        String idString = String.valueOf(id);
        String protocol = packet.getProtocol().toString();
        String time = packet.getReceiveDate().toString();
        String size = String.valueOf(packet.getSize());

        defaultTableModel.addRow(new Object[] {idString, time, protocol, size});
    }
}
