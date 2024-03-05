/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.edu.Model;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import poly.edu.Model.Xem;
import poly.edu.Panels.xemPanel;

/**
 *
 * @author Admin
 */
public class TableXemSPCT extends DefaultTableCellRenderer{

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component com = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); 
        xemPanel xem = new xemPanel();
        if(isSelected==false && row%2 ==0){
            xem.setBackground(Color.WHITE);
        }else{
            xem.setBackground(com.getBackground());
        }
        return xem;
    }
    
}
