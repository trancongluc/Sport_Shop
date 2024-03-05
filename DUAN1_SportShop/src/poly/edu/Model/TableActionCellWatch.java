/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.edu.Model;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import poly.edu.Model.TableAcTionEvent;
import poly.edu.Panels.xemPanel;

/**
 *
 * @author Admin
 */
public class TableActionCellWatch extends DefaultCellEditor{
    private TableAcTionEvent event;
    public TableActionCellWatch(TableAcTionEvent event) {
        super(new JCheckBox());
        this.event = event;
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        xemPanel xem = new xemPanel();
        xem.eventXem(event, row);
        xem.setBackground(table.getSelectionBackground());
        return xem; 
    }
    
}
