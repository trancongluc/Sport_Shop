/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package poly.edu.Panels;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import javax.naming.spi.DirStateFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import poly.edu.Model.NhanVien;
import poly.edu.service.NhanVienService;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author panha
 */
public class NhanVienForm extends javax.swing.JPanel implements Runnable, ThreadFactory {

    DefaultTableModel dm = new DefaultTableModel();
    NhanVienService sv = new NhanVienService();
    int current = 0;
    int size = sv.getAllEm().size();
    private WebcamPanel panel = null;
    private Webcam webcam = null;
    private static final long serialVersionUID = 6441489157408381878L;
    private Executor executor = Executors.newSingleThreadExecutor(this);
    String passwd = "";

    /**
     * Creates new form NhanVienFor
     */
    public NhanVienForm() {
        initComponents();
        loadData();
        fillText();
        initWebcam();
        jPanel7.setVisible(true);
        jPanel2.setVisible(false);

    }

    public void emailSend() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        String password = "";
        int length = 10;

        Random rand = new Random();

        for (int i = 0; i < length; i++) {
            int index = rand.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            passwd += randomChar;
        }
        try {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", 587);
            props.put("mail.smtp.ssl.protocols", "TLSv1.2");

            final String email = "luctcph35904@fpt.edu.vn";
            final String pass = "kcwf lbha hrpq qeef";
            Session s = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(email, pass);
                }

            });
            String sender = "luctcph35904@fpt.edu.vn";
            String receiver = txtEmail.getText();
            String subject = "Thư mời";
            String text = "Chào bạn\n"
                    + "\n"
                    + "Mình là Minh, thuộc bộ phận tuyển dụng của hệ thống bán giày thể thao Sport Shop\n"
                    + "Bạn đã ứng tuyển thành công vào vị trí: Nhân viên\n"
                    + "\n"
                    + "Tài khoản của bạn :" + txtMaNV.getText()
                    + "\n"
                    + "Mật khẩu :" + passwd
                    + "\n"
                    + "Để có thể nắm rõ về chi tiết công việc của bạn, bạn vui lòng đến Phòng P401, FPT Polytechnic cơ sở Kiểu Mai nhé\n"
                    + "Trân trọng!";
            Message msg = new MimeMessage(s);
            msg.setFrom(new InternetAddress(sender));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
            msg.setSubject(subject);
            msg.setText(text);
            Transport.send(msg);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void loadData() {
        int i = 1;
        dm = (DefaultTableModel) tblNV1.getModel();
        ArrayList<NhanVien> lst = sv.getAllEm();
        dm.setRowCount(0);
        for (NhanVien n : lst) {
            dm.addRow(new Object[]{i++, n.getMaNV(), n.getCCCD(), n.getTen(),
                n.getTuoi(), n.getEmail(), n.getSDT(), n.getDiaChi(), n.vaitro(n.getVaiTro()), n.getID()});
        }
    }

    public void searchData() {
        int i = 1;
        dm = (DefaultTableModel) tblNV1.getModel();
        ArrayList<NhanVien> lst = sv.searchEm(txtTK1.getText(), txtTK1.getText(), txtTK1.getText());
        dm.setRowCount(0);
        for (NhanVien n : lst) {
            dm.addRow(new Object[]{i++, n.getMaNV(), n.getCCCD(), n.getTen(),
                n.getTuoi(), n.getEmail(), n.getSDT(), n.getDiaChi(), n.vaitro(n.getVaiTro()), n.getID()});
        }
    }

    public NhanVien model() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String strDate = formatter.format(date);

        String DC = txtTP3.getText() + ", " + txtQ3.getText() + ", " + txtP3.getText() + ", " + txtDCCT3.getText();
        int vaitro = 0;
        if (rdoNV.isSelected()) {
            vaitro = 2;
        } else {
            vaitro = 3;
        }
        int id = 0;
        NhanVien nv = new NhanVien(id, vaitro, txtCCCD.getText(), txtTen.getText(),
                Integer.valueOf(txtTuoi.getText()), txtEmail.getText(), txtSDT.getText(),
                DC, passwd, txtMaNV.getText(), strDate);
        return nv;
    }

    public void detail(int index) {
        String vt = String.valueOf(tblNV1.getValueAt(index, 8).toString());
        if (vt == "1") {
            rdoNV.setSelected(true);
        } else {
            rdoAD.setSelected(true);
        }

        txtCCCD.setText(tblNV1.getValueAt(index, 2).toString());
        txtTen.setText(tblNV1.getValueAt(index, 3).toString());
        txtTuoi.setText(tblNV1.getValueAt(index, 4).toString());
        txtEmail.setText(tblNV1.getValueAt(index, 5).toString());
        txtSDT.setText(tblNV1.getValueAt(index, 6).toString());
        txtMaNV.setText(tblNV1.getValueAt(index, 1).toString());
        String input2 = tblNV1.getValueAt(index, 7).toString();
        String[] parts2 = input2.split(",");

        String part1 = parts2[0].trim();
        String part2 = parts2[1].trim();
        String part3 = parts2[2].trim();
        String part4 = parts2[3].trim();

        txtTP3.setText(part1);
        txtQ3.setText(part2);
        txtP3.setText(part3);
        txtDCCT3.setText(part4);
    }

    public void reset() {
        txtTP3.setText("");
        txtQ3.setText("");
        txtP3.setText("");
        txtDCCT3.setText("");
        txtCCCD.setText("");
        txtTen.setText("");
        txtTuoi.setText("");
        txtSDT.setText("");
        txtMaNV.setText("");
        txtEmail.setText("");
        result_field.setText("");

    }

    public String addvt() {
        String vt = null;
        if (rdoNV.isSelected()) {
            vt = "Nhan vien";
        } else {
            vt = "Admin";
        }
        return vt;
    }

    public boolean vali() {
        ArrayList<NhanVien> val = new ArrayList<>();
        val = sv.getAllEm();
        for (NhanVien nv : val) {
            if (txtMaNV.getText().equals(nv.getMaNV())) {
                JOptionPane.showMessageDialog(this, "Ma NV da ton tai");
                return false;
            }
//            if (txtEmail.getText().equals(nv.getEmail())) {
//                JOptionPane.showMessageDialog(this, "Email da ton tai");
//                return false;
//            }
            if (txtSDT.getText().equals(nv.getSDT())) {
                JOptionPane.showMessageDialog(this, "SDT da ton tai");
                return false;
            }
        }
        if (txtMaNV.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ma NV ko duoc bo trong");
            return false;
        }
        if (txtCCCD.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ma CCCD ko duoc bo trong");
            return false;
        }
        if (txtTen.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ten ko duoc bo trong");
            return false;
        }
        if (txtTuoi.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Tuoi ko duoc bo trong");
            return false;
        }
        if (txtEmail.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Email ko duoc bo trong");
            return false;
        }
        if (txtSDT.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "SDT ko duoc bo trong");
            return false;
        }

        return true;

    }

    public void fillText() {

        result_field.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                changed();
            }

            public void removeUpdate(DocumentEvent e) {
                changed();
            }

            public void insertUpdate(DocumentEvent e) {
                changed();
            }

            public void changed() {
//                if (result_field.getText().equals("")) {
//                    System.out.println("JTextField is empty");
//                } else {
//                    String input = result_field.getText();
//                    String[] parts = input.split("\\|");
//
//                    String id = parts[0];
//                    String name = parts[2];
//                    String dob = parts[3];
//                    String gender = parts[4];
//                    String address = parts[5];
//                    String date = parts[6];
//
//                    txtCCCD.setText(id);
//                    txtTen.setText(name);
//                    txtTuoi.setText(dob);
//                    //txtDCCT.setText(address);
//                    String input2 = address;
//                    String[] parts2 = input2.split(",");
//
//                    String part1 = parts2[0].trim();
//                    String part2 = parts2[1].trim();
//                    String part3 = parts2[2].trim();
//                    String part4 = parts2[3].trim();
//
//                    txtTP3.setText(part4);
//                    txtQ3.setText(part3);
//                    txtP3.setText(part2);
//                    txtDCCT3.setText(part1);
//
//                    String input3 = dob;
//                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
//                    LocalDate birthDate = LocalDate.parse(input3, formatter);
//                    LocalDate currentDate = LocalDate.now();
//
//                    Period age = Period.between(birthDate, currentDate);
//
//                    txtTuoi.setText(String.valueOf(age.getYears()));
//                }
                if (result_field.getText().equals("")) {
                    System.out.println("JTextField is empty");
                } else {
                    String input = result_field.getText();
                    String[] parts = input.split("\\|");

                    if (parts.length >= 7) {
                        String id = parts[0];
                        String name = parts[2];
                        String dob = parts[3];
                        String gender = parts[4];
                        String address = parts[5];
                        String date = parts[6];

                        txtCCCD.setText(id);
                        txtTen.setText(name);
                        txtTuoi.setText(dob);
                        //txtDCCT.setText(address);
                        String[] parts2 = address.split(",");

                        if (parts2.length >= 4) {
                            String part1 = parts2[0].trim();
                            String part2 = parts2[1].trim();
                            String part3 = parts2[2].trim();
                            String part4 = parts2[3].trim();

                            txtTP3.setText(part4);
                            txtQ3.setText(part3);
                            txtP3.setText(part2);
                            txtDCCT3.setText(part1);
                        }

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
                        LocalDate birthDate = LocalDate.parse(dob, formatter);
                        LocalDate currentDate = LocalDate.now();

                        Period age = Period.between(birthDate, currentDate);

                        txtTuoi.setText(String.valueOf(age.getYears()));
                    } else {
                        // Xử lý trường hợp mảng parts không chứa đủ các phần tử cần thiết
                    }
                }
            }
        });

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel6 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        txtTuoi = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        rdoAD = new javax.swing.JRadioButton();
        rdoNV = new javax.swing.JRadioButton();
        jPanel18 = new javax.swing.JPanel();
        txtTP3 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtQ3 = new javax.swing.JTextField();
        txtP3 = new javax.swing.JTextField();
        txtDCCT3 = new javax.swing.JTextField();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnClear = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        txtTen = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtSDT = new javax.swing.JTextField();
        jLabel54 = new javax.swing.JLabel();
        btnThemNV = new javax.swing.JButton();
        txtCCCD = new javax.swing.JTextField();
        result_field = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        btnBack = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        txtTK1 = new javax.swing.JTextField();
        btnTK1 = new javax.swing.JButton();
        btnthem1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblNV1 = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        btnDelte = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1115, 666));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setPreferredSize(new java.awt.Dimension(1115, 666));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(1115, 666));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Mã CCCD");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Tên");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Vai trò");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Tuổi");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 90, -1, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Email");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 50, -1, -1));

        txtMaNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaNVActionPerformed(evt);
            }
        });
        jPanel2.add(txtMaNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 270, -1));
        jPanel2.add(txtTuoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 90, 290, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Số điện thoại");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 130, -1, -1));

        buttonGroup1.add(rdoAD);
        rdoAD.setText("Admin");
        jPanel2.add(rdoAD, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 130, -1, -1));

        buttonGroup1.add(rdoNV);
        rdoNV.setText("Nhân viên");
        jPanel2.add(rdoNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 130, -1, -1));

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel17.setText("Thành phố");

        txtP3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPActionPerformed(evt);
            }
        });

        txtDCCT3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDCCTActionPerformed(evt);
            }
        });

        jLabel61.setText("Quận/Huyện");

        jLabel62.setText("Phường/xã");

        jLabel63.setText("Địa chỉ chi tiết");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtTP3, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(jLabel17)))
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtQ3, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(jLabel61)))
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(txtP3, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDCCT3)
                        .addContainerGap())
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(jLabel62)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 357, Short.MAX_VALUE)
                        .addComponent(jLabel63)
                        .addGap(177, 177, 177))))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel61)
                    .addComponent(jLabel62)
                    .addComponent(jLabel63))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTP3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQ3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtP3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDCCT3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 1090, 120));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Địa chỉ");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, -1, -1));

        btnClear.setBackground(new java.awt.Color(0, 204, 255));
        btnClear.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnClear.setForeground(new java.awt.Color(255, 255, 255));
        btnClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/form.png"))); // NOI18N
        btnClear.setText("Xóa Form");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });
        jPanel2.add(btnClear, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 530, 160, -1));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setText("Mã NV");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));
        jPanel2.add(txtTen, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 180, 270, -1));
        jPanel2.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 50, 290, -1));
        jPanel2.add(txtSDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 130, 290, -1));

        jLabel54.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel54.setText("Quét QR");
        jPanel2.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 230, -1, -1));

        btnThemNV.setBackground(new java.awt.Color(0, 204, 255));
        btnThemNV.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThemNV.setForeground(new java.awt.Color(255, 255, 255));
        btnThemNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/plus.png"))); // NOI18N
        btnThemNV.setText("Thêm nhân viên");
        btnThemNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemNVActionPerformed(evt);
            }
        });
        jPanel2.add(btnThemNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 530, 160, -1));

        txtCCCD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCCCDActionPerformed(evt);
            }
        });
        jPanel2.add(txtCCCD, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 90, 270, -1));
        jPanel2.add(result_field, new org.netbeans.lib.awtextra.AbsoluteConstraints(812, 260, 280, -1));

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 10, 290, 190));

        btnBack.setBackground(new java.awt.Color(0, 204, 255));
        btnBack.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBack.setForeground(new java.awt.Color(255, 255, 255));
        btnBack.setText("Quay lại");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });
        jPanel2.add(btnBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 530, 170, -1));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("FORM THÊM NHÂN VIÊN");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 200, -1));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        txtTK1.setToolTipText("");
        txtTK1.setBorder(javax.swing.BorderFactory.createTitledBorder("Nhập Tên, Email, Mã NV để tìm kiếm...."));
        txtTK1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTK1ActionPerformed(evt);
            }
        });

        btnTK1.setBackground(new java.awt.Color(0, 204, 255));
        btnTK1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTK1.setForeground(new java.awt.Color(255, 255, 255));
        btnTK1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/search (1).png"))); // NOI18N
        btnTK1.setText("Tìm");
        btnTK1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTK1ActionPerformed(evt);
            }
        });

        btnthem1.setBackground(new java.awt.Color(0, 204, 255));
        btnthem1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnthem1.setForeground(new java.awt.Color(255, 255, 255));
        btnthem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/plus.png"))); // NOI18N
        btnthem1.setText("Thêm nhân viên");
        btnthem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthem1ActionPerformed(evt);
            }
        });

        tblNV1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã NV", "Số CCCD", "Tên NV", "Tuổi", "Email", "SDT", "Địa chỉ", "Vai trò", "ID"
            }
        ));
        tblNV1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNV1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblNV1);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("DANH SÁCH NHÂN VIÊN");

        btnDelte.setBackground(new java.awt.Color(0, 204, 255));
        btnDelte.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDelte.setForeground(new java.awt.Color(255, 255, 255));
        btnDelte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/trash.png"))); // NOI18N
        btnDelte.setText("Xóa nhân viên");
        btnDelte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelteActionPerformed(evt);
            }
        });

        btnEdit.setBackground(new java.awt.Color(0, 205, 255));
        btnEdit.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/file-edit.png"))); // NOI18N
        btnEdit.setText("Sửa thông tin");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 971, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(txtTK1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnTK1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnthem1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(btnDelte, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTK1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTK1)
                    .addComponent(btnthem1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDelte)
                    .addComponent(btnEdit))
                .addGap(35, 35, 35))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblNV1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNV1MouseClicked
        // TODO add your handling code here:
        int index = tblNV1.getSelectedRow();
        detail(index);
    }//GEN-LAST:event_tblNV1MouseClicked

    private void btnthem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthem1ActionPerformed
        // TODO add your handling code here:
        jPanel2.setVisible(true);
        jPanel7.setVisible(false);
    }//GEN-LAST:event_btnthem1ActionPerformed

    private void btnTK1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTK1ActionPerformed
        // TODO add your handling code here:
        if (txtTK1.getText().equals("")) {
            loadData();
        } else {
            searchData();
        }
    }//GEN-LAST:event_btnTK1ActionPerformed

    private void txtTK1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTK1ActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        searchData();
        detail(0);

    }//GEN-LAST:event_txtTK1ActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        jPanel2.setVisible(false);
        jPanel7.setVisible(true);
    }//GEN-LAST:event_btnBackActionPerformed

    private void txtCCCDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCCCDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCCCDActionPerformed

    private void btnThemNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemNVActionPerformed
        // TODO add your handling code here:
        int i = JOptionPane.showConfirmDialog(null, "Bạn có muốn thêm?", "Trả lời", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            if (vali()) {
                emailSend();
                sv.AddVT(addvt());
                sv.AddEm(model());
                JOptionPane.showMessageDialog(null, "Them thanh cong");
                loadData();
                reset();
            }
        } else {
            return;
        }
    }//GEN-LAST:event_btnThemNVActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
        reset();
    }//GEN-LAST:event_btnClearActionPerformed

    private void txtDCCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDCCTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDCCTActionPerformed

    private void txtPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPActionPerformed

    private void txtMaNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaNVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaNVActionPerformed

    private void btnDelteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelteActionPerformed
        // TODO add your handling code here:

        int slid = tblNV1.getSelectedRow();
        String id = String.valueOf(tblNV1.getValueAt(slid, 9).toString());
        int i = JOptionPane.showConfirmDialog(null, "Xoa nhan vien?", "Xoa", JOptionPane.YES_OPTION);
        if (i == JOptionPane.YES_OPTION) {
            sv.DeleteEm(Integer.valueOf(id));
            sv.DeleteVT(Integer.valueOf(id));
            loadData();
            reset();
        }
    }//GEN-LAST:event_btnDelteActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        try {
            int slid = tblNV1.getSelectedRow();

            String id = String.valueOf(tblNV1.getValueAt(slid, 9).toString());
            String vt = "";
            if (rdoNV.isSelected()) {
                vt = "Nhân viên";
            } else {

                vt = "Admin";
            }

            sv.UpdateEm(model(), Integer.valueOf(id));
            sv.UpdateVT(Integer.valueOf(id), id);
            JOptionPane.showMessageDialog(null, "Cap nhat thanh cong");
            loadData();
            reset();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Chọn dòng cần sửa!");
            return;
        }


    }//GEN-LAST:event_btnEditActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelte;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnTK1;
    private javax.swing.JButton btnThemNV;
    private javax.swing.JButton btnthem1;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton rdoAD;
    private javax.swing.JRadioButton rdoNV;
    private javax.swing.JTextField result_field;
    private javax.swing.JTable tblNV1;
    private javax.swing.JTextField txtCCCD;
    private javax.swing.JTextField txtDCCT3;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtP3;
    private javax.swing.JTextField txtQ3;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTK1;
    private javax.swing.JTextField txtTP3;
    private javax.swing.JTextField txtTen;
    private javax.swing.JTextField txtTuoi;
    // End of variables declaration//GEN-END:variables

    private void initWebcam() {

        Dimension size = WebcamResolution.QVGA.getSize();
        webcam = Webcam.getWebcams().get(0); //0 is default webcam
        if (webcam.isOpen()) {
            webcam.close();
        }
        webcam.setViewSize(size);
        panel = new WebcamPanel(webcam);
        panel.setPreferredSize(size);
        panel.setFPSDisplayed(true);
        panel.setMirrored(true);
        jPanel2.add(panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 10, 290, 190));

        executor.execute(this);
        return;
    }

    @Override
    public void run() {
//        do {
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            Result result = null;
//            BufferedImage image = null;
//
//            if (webcam.isOpen()) {
//                if ((image = webcam.getImage()) == null) {
//                    continue;
//                }
//            }
//
//            LuminanceSource source = new BufferedImageLuminanceSource(image);
//            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
//
//            try {
//                result = new MultiFormatReader().decode(bitmap);
//            } catch (NotFoundException e) {
//                //No result...
//            }
//
//            if (result != null) {
//                result_field.setText(result.getText());
//            }
//        } while (true);
        do {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Result result = null;
            BufferedImage image = null;

            if (webcam.isOpen()) {
                image = webcam.getImage();
            }

            if (image == null) {
                continue;
            }

            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            try {
                result = new MultiFormatReader().decode(bitmap);
            } catch (NotFoundException e) {
                // No result...
            }

            if (result != null) {
                result_field.setText(result.getText());
            }
        } while (true);
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, "My Thread");
        t.setDaemon(true);
        return t;

    }
}
