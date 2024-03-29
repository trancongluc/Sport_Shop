/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */

package poly.edu.Panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import poly.edu.Model.TableAcTionEvent;
import poly.edu.Model.TableXemSPCT;

/**
 *
 * @author Admin
 */
public class xemPanel extends javax.swing.JPanel {

    /** Creates new form xemPanel */
    public xemPanel() {
        initComponents();
    }
    public void eventXem(TableAcTionEvent event , int row){
       btnXem.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               event.onView(row);
               }
           
       });
       btnEdit.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               event.edit(row);
               }
           
       });
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnXem = new poly.edu.Model.Xem();
        btnEdit = new poly.edu.Model.Xem();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnXem.setBackground(new java.awt.Color(255, 255, 255));
        btnXem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Eye.png"))); // NOI18N
        btnXem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXemActionPerformed(evt);
            }
        });
        add(btnXem, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        btnEdit.setBackground(new java.awt.Color(255, 255, 255));
        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Edit.png"))); // NOI18N
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        add(btnEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void btnXemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnXemActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private poly.edu.Model.Xem btnEdit;
    private poly.edu.Model.Xem btnXem;
    // End of variables declaration//GEN-END:variables

}
