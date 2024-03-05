/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package poly.edu.Panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import poly.edu.Model.buttomCustom;
import poly.edu.Model.modelMenu;
import poly.edu.event.EventMenuSelected;
import poly.edu.uitility.MenuItem;

/**
 *
 * @author Admin
 */
public class Menu extends javax.swing.JPanel {

    public void setEvent(EventMenuSelected event) {
        this.event = event;
    }

    private MigLayout layout;
    private JPanel panelMenu;
    private JButton cmdMenu;
    private JButton cmdLogout;
    private Header header;
    private bottom bottom;
    private EventMenuSelected event;

    public Menu() {
        initComponents();
        setOpaque(false);
        init();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 109, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 487, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void init() {
        setLayout(new MigLayout("Wrap,fillx,insets 0", "0[fill]", "7[]0[]push[160]0"));
        panelMenu = new JPanel();
        header = new Header();
        bottom = new bottom();
        bottom.loadKH();
        createButtonMenu();
        createButtonLogout();
        panelMenu.setOpaque(false);
        layout = new MigLayout("fillx, wrap", "0[fill]0", "0[]3[]0");
        panelMenu.setLayout(layout);
        add(cmdMenu, "pos 1al 0al 80 50");
        add(cmdLogout, "pos 1al 1al 60 100,height 80!");
        add(header);
        add(panelMenu);
        add(bottom,"pos 1al 1al ");
    }

    public void addMenu(modelMenu menu) {
        MenuItem item = new MenuItem(menu.getIcon(), menu.getMenuName(), panelMenu.getComponentCount());
        item.addEvent(new EventMenuSelected() {
            @Override
            public void selected(int index) {
                clearMenu(index);
            }
        });
        item.addEvent(event);
        panelMenu.add(item);
    }

    private void createButtonMenu() {
        cmdMenu = new JButton();
        cmdMenu.setContentAreaFilled(false);
        cmdMenu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmdMenu.setIcon(new ImageIcon(getClass().getResource("/icon/List.png")));
        cmdMenu.setBorder(new EmptyBorder(5, 12, 5, 12));

    }

    private void createButtonLogout() {
        cmdLogout = new buttomCustom();
        cmdLogout.setIcon(new ImageIcon(getClass().getResource("/icon/Logout.png")));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        GradientPaint gra = new GradientPaint(0, 0, Color.decode("#56CCF2"), 0, getHeight(), Color.decode("#2F80ED"));
        g2.setPaint(gra);
        g2.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

    private void clearMenu(int exceptIndex) {
        for (Component com : panelMenu.getComponents()) {
            MenuItem item = (MenuItem) com;
            if (item.getIndex() != exceptIndex) {
                item.setSelected(false);
            }
        }
    }

    public void addEventMenu(ActionListener event) {
        cmdMenu.addActionListener(event);
    }

    public void addEventLogout(ActionListener event) {
        cmdLogout.addActionListener(event);
    }

    public void setAlpha(float al) {
        header.setAlpha(al);
        bottom.setAlpha(al);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
