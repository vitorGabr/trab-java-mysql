package src.view.implementaion.components;

import java.awt.event.MouseAdapter;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Table {

    private DefaultTableModel dtm;
    private JTable table;
    private JScrollPane scrollPane;

    public Table(Object[] columns) {
        dtm = new DefaultTableModel(columns, 0);
        table = new JTable(dtm);
        scrollPane = new JScrollPane(table);
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public void addRow(Object[] rowData) {
        dtm.addRow(rowData);
    }

    public void clearTable() {
        dtm.setRowCount(0);
    }

    public void addTableListener(MouseAdapter md) {
        table.addMouseListener(md);
    }

}
