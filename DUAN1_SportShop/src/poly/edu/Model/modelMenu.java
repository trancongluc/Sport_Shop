/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.edu.Model;

import javax.swing.Icon;

/**
 *
 * @author Admin
 */
public class modelMenu {

 
    public String getMenuName() {
        return menuName;
    }

  
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    
    public Icon getIcon() {
        return icon;
    }

    
    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public modelMenu(String menuName, Icon icon) {
        this.menuName = menuName;
        this.icon = icon;
    }

    public modelMenu() {
    }
    
    private String menuName;
    private Icon icon;
    
}
