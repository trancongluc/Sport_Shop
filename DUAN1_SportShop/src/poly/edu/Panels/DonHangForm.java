/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package poly.edu.Panels;

import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import poly.edu.Model.HoaDon;
import poly.edu.service.HoaDonService;

/**
 *
 * @author Admin
 */
public class DonHangForm extends javax.swing.JPanel {

    DefaultTableModel dtm = new DefaultTableModel();
    private int currentRow;
    ArrayList<HoaDon> list = new ArrayList<>();
    //DefaultComboBoxModel<HoaDon> modelCombo = new DefaultComboBoxModel<>();
    HoaDonService hds = new HoaDonService();
    int indext;
    HoaDon hd = new HoaDon();
    Double bHeight = 0.0;

    /**
     * Creates new form DonHangForm
     */
    public DonHangForm() {
        initComponents();
        setOpaque(false);
        //cboTrangThai.setModel((DefaultComboBoxModel) modelCombo);
        //do du lieu vao combo
        //loadDataToCombo();
        LoadDataToTable();
//        fillToTable();
        pnHoaDon.setVisible(true);
        pnCapNhat.setVisible(false);
    }

    public class BillPrintable implements Printable {

        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
                throws PrinterException {
            // int r = hds.getAllHoaDon().size
            double gia = 0;
            DefaultTableModel model = (DefaultTableModel) tbHoaDonChiTiet.getModel();
            // DefaultTableModel modelLS = (DefaultTableModel) tbLichSu.getModel();
            model.setRowCount(0);
            int index = tbDonHang.getSelectedRow();
            HoaDon selectedHoaDon = list.get(index);
            String MaHD = selectedHoaDon.getMaHoaDon();
            ArrayList<HoaDon> lstTransactions = hds.HoaDonChiTiet(MaHD);
            int in = tbDonHang.getSelectedRow();
            int in2 = tbHoaDonChiTiet.getSelectedRow();
            ImageIcon icon = new ImageIcon("C:\\Users\\Admin\\Desktop\\GD3_SportShop_demo\\SourceTree\\DUAN1_SportShop\\src\\icon\\icon.jpg");
            int result = NO_SUCH_PAGE;
            if (pageIndex == 0) {

                Graphics2D g2d = (Graphics2D) graphics;
                double width = pageFormat.getImageableWidth();
                g2d.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());

                //  FontMetrics metrics=g2d.getFontMetrics(new Font("Arial",Font.BOLD,7));
                try {
                    int y = 20;
                    int yShift = 10;
                    int headerRectHeight = 8;
                    // int headerRectHeighta=40;

                    g2d.setFont(new Font("Monospaced", Font.PLAIN, 9));
                    g2d.drawImage(icon.getImage(), 50, 20, 130, 30, jPanel6);
                    y += yShift + 30;
                    g2d.drawString("----------------------------------------", 12, y);
                    y += yShift;
                    g2d.drawString("           Sneaker SportShop        ", 12, y);
                    y += yShift;
                    g2d.drawString("               Địa Chỉ:       ", 12, y);
                    y += yShift;
                    g2d.drawString(" P. Kiều Mai, Phúc Diễn, Từ Liêm, Hà Nội  ", 12, y);
                    y += yShift;
                    g2d.drawString("      www.facebook.com/cogluc.tran  ", 12, y);
                    y += yShift;
                    g2d.drawString("            +84375361435       ", 12, y);
                    y += yShift;
                    g2d.drawString("----------------------------------------", 12, y);
                    y += headerRectHeight;

                    g2d.drawString(" Số lượng       Sản phẩm       Thành tiền   ", 10, y);
                    y += yShift;
                    g2d.drawString("----------------------------------------", 10, y);
                    y += headerRectHeight;

//                    for (int s = 0; s < r; s++) {
//                        g2d.drawString(" " + itemName.get(s) + "                            ", 10, y);
//                        y += yShift;
//                        g2d.drawString("      " + quantity.get(s) + " * " + itemPrice.get(s), 10, y);
//                        g2d.drawString(subtotal.get(s), 160, y);
//                        y += yShift;
//
//                    }
                    if (!lstTransactions.isEmpty()) {

                        for (HoaDon hoaDon : lstTransactions) {
                            gia += hoaDon.getGiaSP();
                            int soLuong = hoaDon.getSoLuong();
                            String tenSP = hoaDon.getTenSP();
                            String giaSP = String.format("%d", hoaDon.getGiaSP()); // Định dạng chuỗi cho giá trị
                            String formattedString = String.format("%-15s %-15s %s", soLuong, tenSP, giaSP);

                            g2d.drawString(formattedString, 10, y);
                            y += yShift;
                        }

                    }
                    g2d.drawString("----------------------------------------", 10, y);
                    y += yShift;
                    g2d.drawString("----------------------------------------", 10, y);
                    y += yShift;
                    g2d.drawString("Tổng tiền:                      " + gia, 10, y);
                    y += yShift;

                    g2d.drawString("****************************************", 10, y);
                    y += yShift;
                    g2d.drawString("          CẢM ƠN VÌ ĐÃ MUA HÀNG           ", 10, y);
                    y += yShift;
                    g2d.drawString("****************************************", 10, y);
                    y += yShift;
                    g2d.drawString("           SOFTWARE BY:PRO_68          ", 10, y);
                    y += yShift;
                    g2d.drawString("        CONTACT: TranCongLucDepZai       ", 10, y);
                    y += yShift;

                } catch (Exception e) {
                    e.printStackTrace();
                }

                result = PAGE_EXISTS;
            }
            return result;
        }
    }

    public PageFormat getPageFormat(PrinterJob pj) {

        PageFormat pf = pj.defaultPage();
        Paper paper = pf.getPaper();

        double bodyHeight = bHeight;
        double headerHeight = 5.0;
        double footerHeight = 5.0;
        double width = cm_to_pp(10);
        double height = cm_to_pp(headerHeight + bodyHeight + footerHeight + 5);
        paper.setSize(width, height);
        paper.setImageableArea(0, 10, width, height - cm_to_pp(1));

        pf.setOrientation(PageFormat.PORTRAIT);
        pf.setPaper(paper);

        return pf;
    }

    protected static double cm_to_pp(double cm) {
        return toPPI(cm * 0.393600787);
    }

    protected static double toPPI(double inch) {
        return inch * 72d;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        pnHoaDon = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cboTrangThai = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        cboHinhThuc = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbDonHang = new javax.swing.JTable();
        btnInHoaDon = new javax.swing.JButton();
        btnXemChiTiet = new javax.swing.JButton();
        btnXuatDanhSach = new javax.swing.JButton();
        btnHuy = new javax.swing.JButton();
        btnLichSu = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbHoaDonChiTiet = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbLichSu = new javax.swing.JTable();
        pnCapNhat = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtTimKiemLS = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtNguoiNhan = new javax.swing.JTextField();
        txtSDT = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtDiaChi = new javax.swing.JTextArea();
        lblMaHD = new javax.swing.JLabel();
        lblMaNV = new javax.swing.JLabel();
        lblNguoiDat = new javax.swing.JLabel();
        lblTienGiam = new javax.swing.JLabel();
        lblTongTien = new javax.swing.JLabel();
        lblTienShip = new javax.swing.JLabel();
        lblNgayTao = new javax.swing.JLabel();
        lblNguoiTao = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbSanPham = new javax.swing.JTable();
        btnCapNhat = new javax.swing.JButton();
        btnTraHang = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnHoaDon.setBackground(new java.awt.Color(255, 255, 255));
        pnHoaDon.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hóa đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N
        pnHoaDon.setForeground(new java.awt.Color(240, 240, 240));
        pnHoaDon.setPreferredSize(new java.awt.Dimension(1115, 666));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Đơn hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        jPanel1.setForeground(new java.awt.Color(240, 240, 240));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Tìm kiếm hóa đơn");

        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Trạng thái");

        cboTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Đã thanh toán", "Chưa thanh toán", "Đang giao", " " }));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Hình thức");

        cboHinhThuc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chuyển Khoản", "Tiền Mặt", "Quẹt Thẻ", " " }));

        tbDonHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã hóa đơn", "Tên KH", "Ngày tạo", "Ngày thanh toán", "Ngày xác nhận", "Tổng tiền", "Hình Thức Thanh Toán", "Trạng thái", "Trạng thái thanh toán"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbDonHang.setRowHeight(25);
        tbDonHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDonHangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbDonHang);
        if (tbDonHang.getColumnModel().getColumnCount() > 0) {
            tbDonHang.getColumnModel().getColumn(0).setPreferredWidth(20);
        }

        btnInHoaDon.setBackground(new java.awt.Color(0, 204, 255));
        btnInHoaDon.setText("In hóa đơn");
        btnInHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInHoaDonActionPerformed(evt);
            }
        });

        btnXemChiTiet.setBackground(new java.awt.Color(0, 204, 255));
        btnXemChiTiet.setText("Xem chi tiết");
        btnXemChiTiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXemChiTietActionPerformed(evt);
            }
        });

        btnXuatDanhSach.setBackground(new java.awt.Color(0, 204, 255));
        btnXuatDanhSach.setText("Xuất danh sách");
        btnXuatDanhSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatDanhSachActionPerformed(evt);
            }
        });

        btnHuy.setBackground(new java.awt.Color(255, 51, 51));
        btnHuy.setForeground(new java.awt.Color(240, 240, 240));
        btnHuy.setText("Hủy");
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });

        btnLichSu.setBackground(new java.awt.Color(0, 204, 255));
        btnLichSu.setText("Cập nhật");
        btnLichSu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLichSuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel3)
                                        .addGap(40, 40, 40)
                                        .addComponent(cboHinhThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(471, 471, 471)
                                .addComponent(btnLichSu)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 152, Short.MAX_VALUE)
                                .addComponent(btnInHoaDon)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnXemChiTiet)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnXuatDanhSach)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHuy)))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnInHoaDon, btnLichSu});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(cboHinhThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnInHoaDon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnXemChiTiet)
                        .addComponent(btnXuatDanhSach)
                        .addComponent(btnHuy)
                        .addComponent(btnLichSu)))
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hóa đơn chi tiết", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        tbHoaDonChiTiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã NV", "Tên SP", "Kích thước", "Màu", "Loại đế", "Chất liệu", "Hãng", "SL", "Giá"
            }
        ));
        tbHoaDonChiTiet.setMinimumSize(new java.awt.Dimension(200, 64));
        tbHoaDonChiTiet.setRowHeight(25);
        jScrollPane2.setViewportView(tbHoaDonChiTiet);
        if (tbHoaDonChiTiet.getColumnModel().getColumnCount() > 0) {
            tbHoaDonChiTiet.getColumnModel().getColumn(0).setPreferredWidth(20);
            tbHoaDonChiTiet.getColumnModel().getColumn(8).setPreferredWidth(20);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 781, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lịch sử hóa đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        jPanel5.setForeground(new java.awt.Color(255, 255, 255));

        tbLichSu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "STT", "Ngày", "Tên NV", "Trạng thái"
            }
        ));
        tbLichSu.setRowHeight(25);
        jScrollPane5.setViewportView(tbLichSu);
        if (tbLichSu.getColumnModel().getColumnCount() > 0) {
            tbLichSu.getColumnModel().getColumn(0).setPreferredWidth(20);
        }

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(296, 296, 296))
        );

        javax.swing.GroupLayout pnHoaDonLayout = new javax.swing.GroupLayout(pnHoaDon);
        pnHoaDon.setLayout(pnHoaDonLayout);
        pnHoaDonLayout.setHorizontalGroup(
            pnHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnHoaDonLayout.createSequentialGroup()
                .addGap(824, 824, 824)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
            .addGroup(pnHoaDonLayout.createSequentialGroup()
                .addGroup(pnHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnHoaDonLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 329, Short.MAX_VALUE))
                    .addGroup(pnHoaDonLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnHoaDonLayout.setVerticalGroup(
            pnHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnHoaDonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(pnHoaDon, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1170, 590));

        pnCapNhat.setBackground(new java.awt.Color(255, 255, 255));
        pnCapNhat.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cập nhật", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N
        pnCapNhat.setForeground(new java.awt.Color(240, 240, 240));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Home.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Tìm kiếm");

        txtTimKiemLS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemLSActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin hóa đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel3.setForeground(new java.awt.Color(240, 240, 240));

        jLabel6.setText("Mã HD");

        jLabel7.setText("Mã NV");

        jLabel8.setText("Người đặt");

        jLabel9.setText("Người nhận");

        jLabel10.setText("SDT");

        jLabel11.setText("Địa chỉ");

        jLabel12.setText("Tiền giảm giá");

        jLabel13.setText("Tổng tiền");

        jLabel14.setText("Tiền ship");

        jLabel15.setText("Ngày tạo");

        jLabel16.setText("Người tạo");

        txtDiaChi.setColumns(20);
        txtDiaChi.setRows(5);
        jScrollPane4.setViewportView(txtDiaChi);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel8)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16)
                    .addComponent(jLabel14)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(26, 26, 26)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblMaNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMaHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                    .addComponent(lblTienGiam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTongTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTienShip, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblNgayTao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblNguoiTao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtSDT)
                    .addComponent(txtNguoiNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNguoiDat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(120, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(lblMaHD, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(17, 17, 17))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(lblMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNguoiDat, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(13, 13, 13)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtNguoiNhan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel11)
                        .addGap(63, 63, 63))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel12)
                    .addComponent(lblTienGiam, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(lblTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblTienShip, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                    .addComponent(jLabel14))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel16))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblNgayTao, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNguoiTao, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {lblMaHD, lblMaNV, lblNgayTao, lblNguoiDat, lblNguoiTao, lblTienGiam, lblTienShip, lblTongTien, txtNguoiNhan, txtSDT});

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel4.setForeground(new java.awt.Color(240, 240, 240));

        tbSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã SP", "Thương hiệu", "Kích thước", "Số lượng", "Đơn giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbSanPham.setRowHeight(40);
        jScrollPane3.setViewportView(tbSanPham);

        btnCapNhat.setBackground(new java.awt.Color(0, 204, 255));
        btnCapNhat.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnCapNhat.setText("Cập nhật");
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });

        btnTraHang.setBackground(new java.awt.Color(0, 204, 255));
        btnTraHang.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnTraHang.setText("Trả hàng");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 626, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addComponent(btnCapNhat)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnTraHang)
                .addGap(95, 95, 95))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCapNhat)
                    .addComponent(btnTraHang))
                .addGap(58, 58, 58))
        );

        javax.swing.GroupLayout pnCapNhatLayout = new javax.swing.GroupLayout(pnCapNhat);
        pnCapNhat.setLayout(pnCapNhatLayout);
        pnCapNhatLayout.setHorizontalGroup(
            pnCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnCapNhatLayout.createSequentialGroup()
                .addGroup(pnCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnCapNhatLayout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(jLabel5)
                        .addGap(40, 40, 40)
                        .addComponent(txtTimKiemLS))
                    .addGroup(pnCapNhatLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnCapNhatLayout.setVerticalGroup(
            pnCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnCapNhatLayout.createSequentialGroup()
                .addGroup(pnCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnCapNhatLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtTimKiemLS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnCapNhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        add(pnCapNhat, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 600));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 610, Short.MAX_VALUE)
        );

        add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 500, 610));
    }// </editor-fold>//GEN-END:initComponents
public String record() {
        int index = tbDonHang.getSelectedRow();
        return "Số hóa đơn: " + (index + 1) + list.size();
    }
    private void btnInHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInHoaDonActionPerformed
        // TODO add your handling code here:
        int index = tbDonHang.getSelectedRow();

        if (index < 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn dòng cần in");
            return;
        }
        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setPrintable(new BillPrintable(), getPageFormat(pj));
        try {
            pj.print();

        } catch (PrinterException ex) {
            ex.printStackTrace();
        }
        JOptionPane.showMessageDialog(null, "In thanh cong!");
        FillLS();
    }//GEN-LAST:event_btnInHoaDonActionPerformed

    private void btnXemChiTietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXemChiTietActionPerformed
        // TODO add your handling code here:
        //new ChiTietSanPham().setVisible(true);
        int index = tbDonHang.getSelectedRow();
        if (index < 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn dòng cần xem!");
            return;
        }
        HoaDon hd = list.get(index);
        ChiTietHoaDon ctsp = new ChiTietHoaDon(DonHangForm.this, hd);
//        ChiTietSanPham ctsp = new ChiTietSanPham(jFrame1, true);
        ctsp.setVisible(true);

    }//GEN-LAST:event_btnXemChiTietActionPerformed

    private void btnXuatDanhSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatDanhSachActionPerformed
        try {

            dtm = (DefaultTableModel) tbDonHang.getModel();
            XSSFWorkbook wordkbook = new XSSFWorkbook();
            XSSFSheet sheet = wordkbook.createSheet("HoaDon");
            XSSFRow row_sheet = null;

            row_sheet = sheet.createRow(3);
            XSSFCell cell = row_sheet.createCell(0, CellType.STRING);
            cell.setCellValue("STT");

            cell = row_sheet.createCell(1, CellType.STRING);
            cell.setCellValue("MaHoaDon");

            cell = row_sheet.createCell(2, CellType.STRING);
            cell.setCellValue("TenKH");

            cell = row_sheet.createCell(3, CellType.STRING);
            cell.setCellValue("NgayTao");
            cell = row_sheet.createCell(4, CellType.STRING);
            cell.setCellValue("NgayThanhToan");
            cell = row_sheet.createCell(5, CellType.STRING);
            cell.setCellValue("NgayXacNhan");
            cell = row_sheet.createCell(6, CellType.STRING);
            cell.setCellValue("TongTien");
            cell = row_sheet.createCell(7, CellType.STRING);
            cell.setCellValue("HinhThuc");
            cell = row_sheet.createCell(8, CellType.STRING);
            cell.setCellValue("TrangThai");

            for (int i = 0; i < dtm.getRowCount(); i++) {
                //Modelbook book =arr.get(i);
                row_sheet = sheet.createRow(5 + i);
                cell = row_sheet.createCell(0, CellType.NUMERIC);
                cell.setCellValue(i + 1);

                cell = row_sheet.createCell(1, CellType.STRING);
                cell.setCellValue(list.get(i).getMaHoaDon());

                cell = row_sheet.createCell(2, CellType.STRING);
                cell.setCellValue(list.get(i).getTenKH());

                cell = row_sheet.createCell(3, CellType.STRING);
                cell.setCellValue(list.get(i).getNgayTao());

                cell = row_sheet.createCell(4, CellType.STRING);
                cell.setCellValue(list.get(i).getNgayThanhToan());

                cell = row_sheet.createCell(5, CellType.STRING);
                cell.setCellValue(list.get(i).getNgayXacNhan());

                cell = row_sheet.createCell(6, CellType.STRING);
                cell.setCellValue(list.get(i).getTongTien());

                cell = row_sheet.createCell(7, CellType.STRING);
                cell.setCellValue(list.get(i).getHinhThucThanhToan());

                cell = row_sheet.createCell(8, CellType.STRING);
                cell.setCellValue(list.get(i).getTrangThai());

            }

            File f = new File("D:\\DUAN1\\SourceTree\\ExcelSPCT\\DanhSachDonHang.xlsx");
            try {
                FileOutputStream fis = new FileOutputStream(f);
                wordkbook.write(fis);
                fis.close();
                JOptionPane.showMessageDialog(null, "in thanh cong");

            } catch (FileNotFoundException ex) {
                ex.printStackTrace();

            } catch (IOException ex) {
                ex.printStackTrace();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Loi mo file");
        }
    }//GEN-LAST:event_btnXuatDanhSachActionPerformed

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        // TODO add your handling code here:
        updateHD();
    }//GEN-LAST:event_btnHuyActionPerformed

    private void tbDonHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDonHangMouseClicked
        // TODO add your handling code here:
        try {
            int index = tbDonHang.getSelectedRow();
            if (index > -1) {
                FillLS();
                FillLSLS();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_tbDonHangMouseClicked

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
        searchTable();
    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void btnLichSuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLichSuActionPerformed
        // TODO add your handling code here:
        pnCapNhat.setVisible(true);
        pnHoaDon.setVisible(false);
    }//GEN-LAST:event_btnLichSuActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        pnCapNhat.setVisible(false);
        pnHoaDon.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtTimKiemLSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemLSActionPerformed
        // TODO add your handling code here:
        list = hds.getAllHoaDon();
        String tk = txtTimKiemLS.getText().trim();
        ArrayList<HoaDon> hd = hds.getAllHoaDon();
        boolean found = false;
        for (HoaDon hoaDon : hd) {
            if (tk.equalsIgnoreCase(hoaDon.getMaHoaDon())) {
                found = true;
                break;
            }
        }
        if (found) {
            searchTableCapNhat();
        } else {
            JOptionPane.showMessageDialog(null, "Không tồn tại mã hóa đơn như vậy!");
        }

        //setTable();
        //setDataToTable(TOOL_TIP_TEXT_KEY);
        //lblMaHD.setText(hd.getMaHoaDon());
    }//GEN-LAST:event_txtTimKiemLSActionPerformed

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        // TODO add your handling code here:
        //updateSP();
    }//GEN-LAST:event_btnCapNhatActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnInHoaDon;
    private javax.swing.JButton btnLichSu;
    private javax.swing.JButton btnTraHang;
    private javax.swing.JButton btnXemChiTiet;
    private javax.swing.JButton btnXuatDanhSach;
    private javax.swing.JComboBox<String> cboHinhThuc;
    private javax.swing.JComboBox<String> cboTrangThai;
    private javax.swing.JButton jButton2;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblMaHD;
    private javax.swing.JLabel lblMaNV;
    private javax.swing.JLabel lblNgayTao;
    private javax.swing.JLabel lblNguoiDat;
    private javax.swing.JLabel lblNguoiTao;
    private javax.swing.JLabel lblTienGiam;
    private javax.swing.JLabel lblTienShip;
    private javax.swing.JLabel lblTongTien;
    private javax.swing.JPanel pnCapNhat;
    private javax.swing.JPanel pnHoaDon;
    private javax.swing.JTable tbDonHang;
    private javax.swing.JTable tbHoaDonChiTiet;
    private javax.swing.JTable tbLichSu;
    private javax.swing.JTable tbSanPham;
    private javax.swing.JTextArea txtDiaChi;
    private javax.swing.JTextField txtNguoiNhan;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txtTimKiemLS;
    // End of variables declaration//GEN-END:variables

    public void LoadDataToTable() {
        int STT = 1;
        dtm = (DefaultTableModel) tbDonHang.getModel();

        dtm.setRowCount(0);
        list = hds.getAllHoaDon();
        for (HoaDon hoaDon : list) {
            dtm.addRow(new Object[]{STT++,
                hoaDon.getMaHoaDon(),
                hoaDon.getTenKH(),
                hoaDon.getNgayTao(),
                hoaDon.getNgayThanhToan(),
                hoaDon.getNgayXacNhan(),
                hoaDon.getTongTien(),
                hoaDon.getHinhThucThanhToan(),
                hoaDon.getTrangThai(),
                hoaDon.getTrangThaiThanhToan()});

        }

    }

    void FillLS() {
        DefaultTableModel model = (DefaultTableModel) tbHoaDonChiTiet.getModel();
        // DefaultTableModel modelLS = (DefaultTableModel) tbLichSu.getModel();
        model.setRowCount(0);
        int index = tbDonHang.getSelectedRow();

        if (index == -1) {

            return;
        }

        HoaDon selectedHoaDon = list.get(index);

        String MaHD = selectedHoaDon.getMaHoaDon();

        ArrayList<HoaDon> lstTransactions = hds.HoaDonChiTiet(MaHD);

        if (!lstTransactions.isEmpty()) {
            int STT = 1;
            for (HoaDon hoaDon : lstTransactions) {
                model.addRow(new Object[]{
                    STT++,
                    hoaDon.getMaNV(),
                    hoaDon.getTenSP(),
                    hoaDon.getKichCo(),
                    hoaDon.getMausac(),
                    hoaDon.getDeGiay(),
                    hoaDon.getChatLieu(),
                    hoaDon.getHang(),
                    hoaDon.getSoLuong(),
                    hoaDon.getGiaSP()
                });
            }

        }

    }

    public void HanhDong() {
        int index = tbDonHang.getSelectedRow();

        if (index != -1) {
            HoaDon hd = (HoaDon) list.get(index);
            dtm.removeRow(index);
            tbDonHang.setModel(dtm);
            //((AbstractTableModel) .getModel()).fireTableDataChanged();
            if (hd != null) {
                // Pass the reference of the existing KhachHangForm instance
                System.out.println("" + index);
//                            poly.edu.Panels.ChiTietSanPham ctsp = new ChiTietSanPham(DonHangForm.this,hd);
                ChiTietHoaDon ctsp = new ChiTietHoaDon(DonHangForm.this, hd);
                ctsp.setVisible(true);
                //FillTable();
            }
        }
    }

    private void searchTable() {
        String keyword = txtTimKiem.getText();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(dtm);
        tbDonHang.setRowSorter(sorter);

        if (keyword.trim().length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + keyword));
        }
    }

    void updateHD() {
        indext = tbDonHang.getSelectedRow();
        int index = tbDonHang.getSelectedRow();
        HoaDon hd = list.get(index);
        String ma = tbDonHang.getValueAt(indext, 1).toString();
        hd.setMaHoaDon(String.valueOf(ma));
        if (hds.updateHoaDon(ma) > 0) {
            JOptionPane.showMessageDialog(null, "Hủy thành công");
            LoadDataToTable();
        } else {
            JOptionPane.showMessageDialog(null, "Hủy thất bại");
        }
    }

    private void searchTableCapNhat() {
        String keyword = txtTimKiemLS.getText();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(dtm);
        tbDonHang.setRowSorter(sorter);
        list = hds.getAllHoaDon();
        if (keyword.trim().length() == 0) {
            sorter.setRowFilter(null);

            clearText();
        }
        for (HoaDon hoaDon : list) {
            if (!keyword.equalsIgnoreCase(hoaDon.getMaHoaDon())) {
                JOptionPane.showMessageDialog(null, "Không tồn tại mã hóa đơn như vậy!");
                return;
            } else {
//                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + keyword));
                updateText();
                //setDataToTable(keyword);
                LoadDataToTableSanPham(keyword);
            }
        }

    }

    void FillLSLS() {

        DefaultTableModel modelLS = (DefaultTableModel) tbLichSu.getModel();
        modelLS.setRowCount(0);
        int index = tbDonHang.getSelectedRow();

        if (index == -1) {

            return;
        }

        // Get the selected KhachHang object from the list
        HoaDon selectedHoaDon = list.get(index);

        // Get the ID of the selected KhachHang
        String MaHD = selectedHoaDon.getMaHoaDon();

        // Get the list of transactions for the selected KhachHang
        ArrayList<HoaDon> lstTransactions = hds.HoaDonChiTiet(MaHD);

        // Check if there are transactions for the selected KhachHang
        if (!lstTransactions.isEmpty()) {
            int STT = 1;

            for (HoaDon hoaDon : lstTransactions) {
                modelLS.addRow(new Object[]{
                    STT++,
                    hoaDon.getNgayXacNhan(),
                    hoaDon.getTenNV(),
                    hoaDon.getTrangThai()
                });
            }
        } else {

        }
    }

    private void clearText() {
        lblMaHD.setText("");
        lblMaNV.setText("");
        lblNguoiDat.setText("");
        lblTienGiam.setText("");
        lblTongTien.setText("");
        lblTienShip.setText("");
        lblNgayTao.setText("");
        lblNguoiTao.setText("");
    }

    private void updateText() {

        int index = tbDonHang.convertRowIndexToModel(0);
        if (index < 0) {
            return;
        }
        if (index != -1) {
            HoaDon hd = (HoaDon) list.get(index);
            lblMaHD.setText(hd.getMaHoaDon());
            lblMaNV.setText(hd.getMaNV());
            lblNguoiDat.setText(hd.getTenKH());
            lblTienGiam.setText(hd.getTienGiamGia() + "");
            lblTongTien.setText(hd.getTongTien() + "");
            lblTienShip.setText(hd.getPhiShip() + "");
            lblNgayTao.setText(hd.getNgayTao() + "");
            lblNguoiTao.setText(hd.getNguoiTao());

        } else {

            clearText();
        }

    }

    private void setDataToTable(String maHD) {
//        int STT = 1;
//        dtm = (DefaultTableModel) tbSanPham.getModel();
//        dtm.setRowCount(0);
//         int index = tbDonHang.getSelectedRow();
//        
//        if (index == -1) {
//            
//            return;
//        }
//         HoaDon selectedHoaDon = list.get(index);
//
//
//        String MaHD = selectedHoaDon.getMaHoaDon();
//        list = hds.getToanSanPham(MaHD);
//        for (HoaDon hoaDon : list) {
//                                HoaDon hd = (HoaDon) hoaDon;
//                                dtm.addRow(new Object[]{STT++,
//                                hd.getMaSP(),
//                                hd.getThuongHieu(),
//                                hd.getKichCo(),
//                                hd.getSoLuong(),
//                                hd.getTongTien()});
//    }
        DefaultTableModel modelLS = (DefaultTableModel) tbSanPham.getModel();

        int index = tbDonHang.getSelectedRow();

        if (index == -1) {

            return;
        }

        // Get the selected KhachHang object from the list
        HoaDon selectedHoaDon = list.get(index);

        // Get the ID of the selected KhachHang
        String MaHD = selectedHoaDon.getMaHoaDon();

        // Get the list of transactions for the selected KhachHang
        //ArrayList<HoaDon> lstTransactions = hds.HoaDonChiTiet(MaHD);
        int STT = 1;
        dtm = (DefaultTableModel) tbSanPham.getModel();
        dtm.setRowCount(0);
        list = hds.getAllHoaDon();
        for (HoaDon hoaDon : list) {
            HoaDon hd = (HoaDon) hoaDon;
            dtm.addRow(new Object[]{STT++,
                hd.getMaSP(),
                hd.getThuongHieu(),
                hd.getKichCo(),
                hd.getSoLuong(),
                hd.getTongTien()});
        }
    }

    public void LoadDataToTableSanPham(String maHD) {
        int STT = 1;
        dtm = (DefaultTableModel) tbSanPham.getModel();
        dtm.setRowCount(0);
        list = hds.getToanSanPham(maHD);
        for (HoaDon hoaDon : list) {
            dtm.addRow(new Object[]{STT++,
                hoaDon.getMaSP(),
                hoaDon.getThuongHieu(),
                hoaDon.getKichCo(),
                hoaDon.getSoLuong(),
                hoaDon.getTongTien()});
        }
    }

    void updateSP() {
        indext = tbDonHang.getSelectedRow();
        int index = tbDonHang.getSelectedRow();
        HoaDon hd = list.get(index);
        String ma = tbDonHang.getValueAt(indext, 1).toString();
        hd.setMaHoaDon(String.valueOf(ma));
        if (hds.updateSanPham(ma) > 0) {
            JOptionPane.showMessageDialog(null, "Hủy thành công");
            LoadDataToTable();
        } else {
            JOptionPane.showMessageDialog(null, "Hủy thất bại");
        }
    }

    private static void openPdfFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists() && file.isFile()) {
                Desktop.getDesktop().open(file);
            } else {
                System.out.println("Không thể mở tệp PDF.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
