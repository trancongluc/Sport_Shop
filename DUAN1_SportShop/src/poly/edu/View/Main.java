package poly.edu.View;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import org.jdesktop.animation.timing.Animator;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import poly.edu.Model.modelMenu;

import javax.swing.Timer;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import poly.edu.Panels.BanHangForm;
import poly.edu.Panels.DonHangForm;
import poly.edu.Panels.KhachHangForm;

import poly.edu.Panels.Menu;
import poly.edu.Panels.NhanVienForm;
import poly.edu.Panels.SanPhamForm;
import poly.edu.Panels.TaiKhoanForm;
import poly.edu.Panels.ThongKeForm;
import poly.edu.Panels.VoucherForm;
import poly.edu.event.EventMenuSelected;
import poly.edu.uitility.Auth;

public class Main extends javax.swing.JFrame {

    private Menu menu = new Menu();
    private JPanel main = new JPanel();
    private MigLayout layout;
    private Animator animator;
    private boolean menuShow;
    private Timer timer;
    private Webcam webcam = null;

    public Main() {
        initComponents();
        init();
        body.setBackground(Color.white);

    }

    private void init() {

        layout = new MigLayout("fill", "0[]10[]0", "0[fill]0");
        body.setLayout(layout);
        main.setOpaque(false);
        main.setLayout(new BorderLayout());
        menu.addEventLogout(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openDangXuat();
            }
        });
        menu.addEventMenu(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!animator.isRunning()) {
                    animator.start();

                }
            }
        });
        menu.setEvent(new EventMenuSelected() {
            @Override
            public void selected(int index) {
                if (!Auth.isLogin()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng đăng nhập!");
                    return;
                } else {
                    if (Auth.NhanVien()) {
                        if (index == 1 || index == 3 || index == 5 || index == 7) {
                            JOptionPane.showMessageDialog(null, "Bạn không có quyền truy cập!");
                            return;
                        }
                    }
                    if (index == 0) {
                        if (Auth.isLogin()) {
                            initWebcam();
                            showForm(new BanHangForm());
                        }
                    } else if (index == 1) {
                        if (Auth.isLogin() && Auth.Admin()) {
                            JOptionPane.showMessageDialog(null, "Đang phát triển. Vui lòng đợi !");
                            initWebcam();
                        }
                    } else if (index == 2) {
                        if (Auth.isLogin()) {
                            initWebcam();
                            showForm(new SanPhamForm());
                        }
                    } else if (index == 3) {
                        if (Auth.isLogin() && Auth.Admin()) {
                            initWebcam();
                            showForm(new NhanVienForm());
                        }
                    } else if (index == 4) {
                        if (Auth.isLogin()) {
                            initWebcam();
                            showForm(new KhachHangForm());
                        }
                    } else if (index == 5) {
                        if (Auth.isLogin() && Auth.Admin()) {
                            initWebcam();
                            showForm(new ThongKeForm());
                        }
                    } else if (index == 6) {
                        if (Auth.isLogin()) {
                            initWebcam();
                            showForm(new DonHangForm());
                        }
                    } else if (index == 7) {
                        if (Auth.isLogin() && Auth.Admin()) {
                            initWebcam();
                            showForm(new VoucherForm());
                        }
                    }
                }
            }
        });
        menu.addMenu(new modelMenu("Bán hàng", new ImageIcon(getClass().getResource("/icon/banhang.png"))));
        menu.addMenu(new modelMenu("Tài Khoản", new ImageIcon(getClass().getResource("/icon/MaleUser.png"))));
        menu.addMenu(new modelMenu("Sản Phẩm", new ImageIcon(getClass().getResource("/icon/Sneaker1.png"))));
        menu.addMenu(new modelMenu("Nhân Viên", new ImageIcon(getClass().getResource("/icon/Agent.png"))));
        menu.addMenu(new modelMenu("Khách hàng", new ImageIcon(getClass().getResource("/icon/khachhang.png"))));
        menu.addMenu(new modelMenu("Thống Kê", new ImageIcon(getClass().getResource("/icon/thongke.png"))));
        menu.addMenu(new modelMenu("Đơn Hàng", new ImageIcon(getClass().getResource("/icon/Order.png"))));
        menu.addMenu(new modelMenu("Voucher", new ImageIcon(getClass().getResource("/icon/Voucher.png"))));

        body.add(menu, "w 50!");
        body.add(main, "w 100%");
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                double width;
                if (menuShow) {
                    width = 50 + (150 * (1f - fraction));
                    menu.setAlpha(1f - fraction);
                } else {
                    width = 50 + (150 * fraction);
                    menu.setAlpha(fraction);
                }
                layout.setComponentConstraints(menu, "w " + width + "!");
                body.revalidate();
            }

            @Override
            public void end() {
                menuShow = !menuShow;
            }

        };

        animator = new Animator(400, target);
        animator.setResolution(0);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
//        showForm(new TaiKhoanForm());
    }

    private void showForm(Component com) {
        main.removeAll();
        main.add(com);
        main.repaint();
        main.revalidate();
    }

    void openDangXuat() {
//        Auth.clear();
        new Login(this, true).setVisible(true);
    }

    private void initWebcam() {

        Dimension size = WebcamResolution.QVGA.getSize();
        webcam = Webcam.getWebcams().get(0); //0 is default webcam
        if (webcam.isOpen()) {
            webcam.close();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        body = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SPORT SHOP");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        body.setBackground(new java.awt.Color(245, 245, 245));
        body.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout bodyLayout = new javax.swing.GroupLayout(body);
        body.setLayout(bodyLayout);
        bodyLayout.setHorizontalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1442, Short.MAX_VALUE)
        );
        bodyLayout.setVerticalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 603, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(body, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel body;
    // End of variables declaration//GEN-END:variables
}
