package com.cout970.editor.gui;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 * Created by cout970 on 22/06/2016.
 */
public class KeymapHelper {

    private JTable table1;
    private JPanel panel1;

    public KeymapHelper() {

        table1.setModel(new TableModel() {
            @Override
            public int getRowCount() {
                return 0;
            }

            @Override
            public int getColumnCount() {
                return 2;
            }

            @Override
            public String getColumnName(int columnIndex) {
                return columnIndex == 0 ? "Action" : "HotKey" ;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return String.class;
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                return columnIndex == 0 ? KeymapKt.getActionMap().get(rowIndex) : KeymapKt.getKeyMap().get(rowIndex);
            }

            @Override
            public void setValueAt(Object aValue, int rowIndex, int columnIndex) {}

            @Override
            public void addTableModelListener(TableModelListener l) {}

            @Override
            public void removeTableModelListener(TableModelListener l) {}
        });
    }
}
