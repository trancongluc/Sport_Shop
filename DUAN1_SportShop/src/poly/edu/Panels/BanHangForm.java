/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package poly.edu.Panels;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import poly.edu.Model.ChatLieu;
import poly.edu.Model.DeGiay;
import poly.edu.Model.HangGiay;
import poly.edu.Model.HoaDon;
import poly.edu.Model.HoaDonChiTiet;
import poly.edu.Model.KhachHang;
import poly.edu.Model.KhuyenMai;
import poly.edu.Model.MauSac;
import poly.edu.Model.NhanVien;
import poly.edu.Model.SanPham;
import poly.edu.Model.SanPhamChiTiet;
import poly.edu.Model.Size;
import poly.edu.service.ChatLieuDao;
import poly.edu.service.DeGiayDao;
import poly.edu.service.HangDao;
import poly.edu.service.HoaDonChiTietService;
import poly.edu.service.HoaDonService;
import poly.edu.service.KhachHangServices;
import poly.edu.service.MauSacDao;
import poly.edu.service.NhanVienService;
import poly.edu.service.SanPhamChiTietDao;
import poly.edu.service.SanPhamDao;
import poly.edu.service.SizeDao;
import poly.edu.service.VoucherService;

/**
 *
 * @author Admin
 */
public class BanHangForm extends javax.swing.JPanel {

    ArrayList<SanPhamChiTiet> listSPCT = new ArrayList<>();
    ArrayList<Size> listSize = new ArrayList<>();
    ArrayList<ChatLieu> listCL = new ArrayList<>();
    ArrayList<MauSac> listMau = new ArrayList<>();
    ArrayList<HangGiay> listHang = new ArrayList<>();
    SanPhamChiTietDao spctd = new SanPhamChiTietDao();
    ArrayList<SanPham> listSP = new ArrayList<>();
    ArrayList<DeGiay> listDG = new ArrayList<>();
    ArrayList<KhachHang> listKH = new ArrayList<>();
    private Map<SanPhamChiTiet, Integer> maps;
    ArrayList<HoaDonChiTiet> listHDCT = new ArrayList<>();
    ArrayList<HoaDon> listHD = new ArrayList<>();
    ArrayList<NhanVien> listNV = new ArrayList<>();
    NhanVienService nvs = new NhanVienService();
    SanPhamDao spd = new SanPhamDao();
    ChatLieuDao cld = new ChatLieuDao();
    DeGiayDao dgd = new DeGiayDao();
    HangDao hgd = new HangDao();
    MauSacDao msd = new MauSacDao();
    SizeDao sd = new SizeDao();
    KhachHangServices khs = new KhachHangServices();
    HoaDonChiTietService hdcts = new HoaDonChiTietService();
    int row;
    DefaultTableModel dtmSPCT = new DefaultTableModel();
    DefaultTableModel dtmGioHang = new DefaultTableModel();
    DefaultTableModel dtmHoaDon = new DefaultTableModel();
    private int currentRow;
    ArrayList<HoaDon> list = new ArrayList<>();
    //DefaultComboBoxModel<HoaDon> modelCombo = new DefaultComboBoxModel<>();
    HoaDonService hds = new HoaDonService();
    int indext;
    HoaDon hd = new HoaDon();
    ArrayList<KhachHang> lst = new ArrayList<>();
    public final KhachHangServices khsv = new KhachHangServices();
    LocalDate ngayHomNay = LocalDate.now();
    private int addDataHoaDonCount = 0;
    private final KhachHang khb = new KhachHang();
    private KhachHang_BanHang khbh;
    private int indexHoaDonSelected;
    private Webcam webcam1 = null;
    private WebcamPanel panell = null;
    VoucherService vs = new VoucherService();
    ArrayList<KhuyenMai> listVC = new ArrayList<>();
//    private static final long serialVersionUID = 6441489157408381878L;
//    private Executor executor = Executors.newSingleThreadExecutor(this);
    Calendar calendar = Calendar.getInstance();
    Date today = calendar.getTime();
    String maHD;

    /**
     * Creates new form BanHangForm
     */
    public BanHangForm() {
        initComponents();
        dtmSPCT = (DefaultTableModel) tblSPCT.getModel();
        dtmHoaDon = (DefaultTableModel) tblHoaDon.getModel();
        dtmGioHang = (DefaultTableModel) tblGioHang.getModel();
        indexHoaDonSelected = 0;
        init();
        //BanHang(khbh, khb);
//        maps = new HashMap<>();
        listSPCT = spctd.selectAll();
        listSP = spd.selectAll();
        listHD = hds.getAllHoaDonChoThanhToan();
//        initWEBCAM();
//        fillComBoVoucherTaiQuay();
        txtTongTien.setText("0");
        txtTongTienDatHang.setText("0");
    }

    void loadKH() {
        listKH = khs.AllThongTinKhachHangTheoMa(KhachHang_BanHang.mkh);
        for (KhachHang kh : listKH) {
            txtMaKH.setText(kh.getMaKH());
            txtTenKH.setText(kh.getTenKH());
            txtNguoiNhan.setText(kh.getTenKH());
            txtSDTNguoiNhan.setText(kh.getSdt());
            lblMaKH.setText(kh.getMaKH());
        }

    }

    void loadNV() {
        listNV = nvs.getAllEmTheoMa(NguoiShip.MaNV);
        for (NhanVien nv : listNV) {
            TxtNguoiSHip.setText(nv.getTen());
            txtSDT.setText(nv.getSDT());
            lblMaNV.setText(nv.getMaNV());

        }
    }

    void fillComboBoxDeGiay() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboDeGiay.getModel();
        model.removeAllElements();
        model.addElement("Tất cả");
        ArrayList<DeGiay> list = dgd.selectAll();
        for (DeGiay deGiay : list) {

            model.addElement(deGiay);
        }

    }

    void fillComboBoxMauSac() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboMS.getModel();

        model.removeAllElements();
        model.addElement("Tất cả");
        ArrayList<MauSac> list = msd.selectAll();
        for (MauSac mauSac : list) {

            model.addElement(mauSac);
        }

    }

    void fillComboBoxSize() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboKichThuoc.getModel();
        model.removeAllElements();
        model.addElement("Tất cả");
        ArrayList<Size> list = sd.selectAll();
        for (Size size : list) {

            model.addElement(size);
        }

    }

    void fillComboBoxHang() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboHang.getModel();
        model.removeAllElements();
        model.addElement("Tất cả");
        ArrayList<HangGiay> list = hgd.selectAll();
        for (HangGiay hangGiay : list) {

            model.addElement(hangGiay);
        }

    }

    void fillComboBoxChatLieu() {
//        DefaultComboBoxModel model = (DefaultComboBoxModel) cboChatLieu.getModel();
//        model.removeAllElements();
//        model.addElement("Tất cả");
//        ArrayList<ChatLieu> list = cld.selectAll();
//        for (ChatLieu chatLieu : list) {
//            model.addElement(chatLieu);
//        }

    }

    private void searchInTableHang(String selectedValue) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(dtmSPCT);
        tblSPCT.setRowSorter(sorter);
        RowFilter<Object, Object> rf = RowFilter.regexFilter(selectedValue, 6);
        sorter.setRowFilter(rf);
    }

    private void searchInTableMauSac(String selectedValue) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(dtmSPCT);
        tblSPCT.setRowSorter(sorter);

        RowFilter<Object, Object> rf = RowFilter.regexFilter(selectedValue, 3);
        sorter.setRowFilter(rf);
    }

    private void searchInTableSize(String selectedValue) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(dtmSPCT);
        tblSPCT.setRowSorter(sorter);

        RowFilter<Object, Object> rf = RowFilter.regexFilter(selectedValue, 4);
        sorter.setRowFilter(rf);
    }

    private void searchInTableChatLieu(String selectedValue) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(dtmSPCT);
        tblSPCT.setRowSorter(sorter);

        RowFilter<Object, Object> rf = RowFilter.regexFilter(selectedValue, 5);
        sorter.setRowFilter(rf);
    }

    private void searchInTableDeGiay(String selectedValue) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(dtmSPCT);
        tblSPCT.setRowSorter(sorter);

        RowFilter<Object, Object> rf = RowFilter.regexFilter(selectedValue, 7);
        sorter.setRowFilter(rf);
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
        jFrame2 = new javax.swing.JFrame();
        lblMaKH = new javax.swing.JLabel();
        lblMaNV = new javax.swing.JLabel();
        panelHD = new javax.swing.JPanel();
        cboTrangThai = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        btnTaoHoaDon = new javax.swing.JButton();
        cboHinhThucThanhToan = new javax.swing.JComboBox<>();
        cboDonHang = new javax.swing.JComboBox<>();
        lblTrangThaiHoaDon = new javax.swing.JLabel();
        panelKH = new javax.swing.JPanel();
        tab = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        panelThongTinKH = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtMaKH = new javax.swing.JTextField();
        txtTenKH = new javax.swing.JTextField();
        btnKhachhang = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtMaHD = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtTenNV = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtTongTien = new javax.swing.JTextField();
        txtGiamGia = new javax.swing.JTextField();
        txtTienKhachDua = new javax.swing.JTextField();
        cboHTThanhToan = new javax.swing.JComboBox<>();
        txtTienThua = new javax.swing.JTextField();
        btnTaoHoaDonChiTiet = new javax.swing.JButton();
        jLabel31 = new javax.swing.JLabel();
        txtThanhToanTQ = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        CboVoucherTaiQuay = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtNguoiNhan = new javax.swing.JTextField();
        txtSDTNguoiNhan = new javax.swing.JTextField();
        txtDiaChi = new javax.swing.JTextField();
        TxtNguoiSHip = new javax.swing.JTextField();
        txtSDT = new javax.swing.JTextField();
        btnNguoiNhan = new javax.swing.JButton();
        btnNguoiShip = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        txtMaHDDH = new javax.swing.JTextField();
        txtTenNVDH = new javax.swing.JTextField();
        txtTongTienDatHang = new javax.swing.JTextField();
        txtGiamGiaDH = new javax.swing.JTextField();
        txtTienShip = new javax.swing.JTextField();
        txtThanhToan = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        cboTrangThaiTT = new javax.swing.JComboBox<>();
        cboHTTT = new javax.swing.JComboBox<>();
        txtTienKhachDuaDH = new javax.swing.JTextField();
        txtTienThuaDatHang = new javax.swing.JTextField();
        btnXacNhanHD = new javax.swing.JButton();
        btnChoGiaoHD = new javax.swing.JButton();
        btnDangGiao = new javax.swing.JButton();
        btnHenGiaoLai = new javax.swing.JButton();
        btnDaGiao = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        cboVoucherDatHang = new javax.swing.JComboBox<>();
        ngayMuonNhan = new com.toedter.calendar.JDateChooser();
        jPanel2 = new javax.swing.JPanel();
        txtTK = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        cboMS = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        cboKichThuoc = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSPCT = new javax.swing.JTable();
        cboHang = new javax.swing.JComboBox<>();
        jLabel32 = new javax.swing.JLabel();
        cboDeGiay = new javax.swing.JComboBox<>();
        txtMSPCT = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblGioHang = new javax.swing.JTable();
        btnXoaGioHang = new javax.swing.JButton();
        chkTatCa = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btnMoQR = new javax.swing.JButton();
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

        javax.swing.GroupLayout jFrame2Layout = new javax.swing.GroupLayout(jFrame2.getContentPane());
        jFrame2.getContentPane().setLayout(jFrame2Layout);
        jFrame2Layout.setHorizontalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame2Layout.setVerticalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        lblMaKH.setText("jLabel34");

        lblMaNV.setText("jLabel35");

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelHD.setBackground(new java.awt.Color(255, 255, 255));
        panelHD.setBorder(javax.swing.BorderFactory.createTitledBorder("Hóa đơn"));

        cboTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chờ thanh toán", "Đã thanh toán", "Chờ giao hàng", "Đang vận chuyển", "Giao lại" }));
        cboTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTrangThaiActionPerformed(evt);
            }
        });

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã HĐ", "Ngày tạo", "Nhân Viên", "Khách hàng", "Số lượng", "Hình thức", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tblHoaDonMouseEntered(evt);
            }
        });
        jScrollPane1.setViewportView(tblHoaDon);

        btnTaoHoaDon.setBackground(new java.awt.Color(0, 204, 255));
        btnTaoHoaDon.setText("Tạo hóa đơn");
        btnTaoHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoHoaDonActionPerformed(evt);
            }
        });

        cboHinhThucThanhToan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Thanh toán trước", "Thanh toán khi nhận" }));

        cboDonHang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tại quầy", "Đặt hàng" }));
        cboDonHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboDonHangActionPerformed(evt);
            }
        });

        lblTrangThaiHoaDon.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout panelHDLayout = new javax.swing.GroupLayout(panelHD);
        panelHD.setLayout(panelHDLayout);
        panelHDLayout.setHorizontalGroup(
            panelHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelHDLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelHDLayout.createSequentialGroup()
                        .addComponent(btnTaoHoaDon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblTrangThaiHoaDon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 129, Short.MAX_VALUE)
                        .addComponent(cboHinhThucThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(cboDonHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelHDLayout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())))
        );
        panelHDLayout.setVerticalGroup(
            panelHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHDLayout.createSequentialGroup()
                .addGroup(panelHDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTaoHoaDon)
                    .addComponent(cboHinhThucThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboDonHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTrangThaiHoaDon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE))
        );

        add(panelHD, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 6, 650, 150));

        panelKH.setBackground(new java.awt.Color(255, 255, 255));
        panelKH.setBorder(javax.swing.BorderFactory.createTitledBorder("Đơn hàng"));

        tab.setBackground(new java.awt.Color(255, 255, 255));
        tab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tabMouseEntered(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelThongTinKH.setBackground(new java.awt.Color(255, 255, 255));
        panelThongTinKH.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông tin khách hàng"));

        jLabel4.setText("Mã KH:");

        jLabel5.setText("Tên KH:");

        btnKhachhang.setBackground(new java.awt.Color(0, 204, 255));
        btnKhachhang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/click1.png"))); // NOI18N
        btnKhachhang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKhachhangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelThongTinKHLayout = new javax.swing.GroupLayout(panelThongTinKH);
        panelThongTinKH.setLayout(panelThongTinKHLayout);
        panelThongTinKHLayout.setHorizontalGroup(
            panelThongTinKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelThongTinKHLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelThongTinKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelThongTinKHLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(txtMaKH))
                    .addGroup(panelThongTinKHLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnKhachhang, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelThongTinKHLayout.setVerticalGroup(
            panelThongTinKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelThongTinKHLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelThongTinKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelThongTinKHLayout.createSequentialGroup()
                        .addGroup(panelThongTinKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelThongTinKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelThongTinKHLayout.createSequentialGroup()
                        .addComponent(btnKhachhang)
                        .addContainerGap())))
        );

        jPanel4.add(panelThongTinKH, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 264, -1));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông tin hóa đơn"));

        jLabel6.setText("Mã hóa đơn:");

        txtMaHD.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        jLabel7.setText("Tên nhân viên:");

        txtTenNV.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        jLabel8.setText("Tổng tiền:");

        jLabel9.setText("Số tiền giảm:");

        jLabel11.setText("HT thanh toán");

        jLabel12.setText("Tiền khách đưa:");

        jLabel14.setText("Tiền thừa:");

        txtTongTien.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        txtGiamGia.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        txtTienKhachDua.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtTienKhachDua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTienKhachDuaActionPerformed(evt);
            }
        });

        cboHTThanhToan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tiền Mặt", "Chuyển Khoản" }));

        txtTienThua.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        btnTaoHoaDonChiTiet.setBackground(new java.awt.Color(0, 204, 255));
        btnTaoHoaDonChiTiet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Bill.png"))); // NOI18N
        btnTaoHoaDonChiTiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoHoaDonChiTietActionPerformed(evt);
            }
        });

        jLabel31.setText("Thanh toán:");

        jLabel13.setText("Voucher:");

        CboVoucherTaiQuay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CboVoucherTaiQuayActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(28, 28, 28))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addGap(18, 18, 18)))
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtMaHD, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                                    .addComponent(txtTenNV)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTongTien)
                                    .addComponent(txtGiamGia)
                                    .addComponent(txtThanhToanTQ)
                                    .addComponent(CboVoucherTaiQuay, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(59, 59, 59))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel7Layout.createSequentialGroup()
                                    .addComponent(jLabel11)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cboHTThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel7Layout.createSequentialGroup()
                                    .addComponent(jLabel12)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txtTienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel31)
                            .addComponent(jLabel9)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnTaoHoaDonChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTienThua, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(58, Short.MAX_VALUE))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(21, 21, 21)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(CboVoucherTaiQuay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addComponent(txtGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(txtThanhToanTQ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(cboHTThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtTienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtTienThua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnTaoHoaDonChiTiet)
                .addGap(15, 15, 15))
        );

        jPanel4.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 264, -1));

        tab.addTab("Tại quầy", jPanel4);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông tin người nhận và người ship"));

        jLabel15.setText("Ng/Nhận:");

        jLabel16.setText("SĐT:");

        jLabel17.setText("Địa chỉ:");

        jLabel18.setText("Ng/Ship:");

        jLabel19.setText("SĐT:");

        txtNguoiNhan.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        txtSDTNguoiNhan.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        txtDiaChi.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        TxtNguoiSHip.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        txtSDT.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        btnNguoiNhan.setBackground(new java.awt.Color(0, 204, 255));
        btnNguoiNhan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/click1.png"))); // NOI18N
        btnNguoiNhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNguoiNhanActionPerformed(evt);
            }
        });

        btnNguoiShip.setBackground(new java.awt.Color(0, 204, 255));
        btnNguoiShip.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/click1.png"))); // NOI18N
        btnNguoiShip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNguoiShipActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNguoiNhan)
                            .addComponent(txtSDTNguoiNhan)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(18, 18, 18)
                        .addComponent(txtDiaChi))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addComponent(jLabel19))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtSDT, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                            .addComponent(TxtNguoiSHip))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNguoiNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNguoiShip, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel8Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnNguoiNhan, btnNguoiShip});

        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(txtNguoiNhan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(txtSDTNguoiNhan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnNguoiNhan)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnNguoiShip)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(TxtNguoiSHip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );

        jPanel8Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnNguoiNhan, btnNguoiShip});

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông tin hóa đơn"));

        jLabel20.setText("Mã HĐ:");

        jLabel21.setText("Mã NV:");

        jLabel22.setText("Tổng tiền:");

        jLabel23.setText("Giảm giá:");

        jLabel24.setText("Tiền ship:");

        jLabel25.setText("Thanh toán:");

        jLabel26.setText("Mong muốn:");

        txtMaHDDH.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        txtTenNVDH.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        txtTongTienDatHang.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        txtGiamGiaDH.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        txtTienShip.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtTienShip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTienShipActionPerformed(evt);
            }
        });

        txtThanhToan.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        jLabel27.setText("Trạng thái TT");

        jLabel28.setText("HT thanh toán:");

        jLabel29.setText("Tiền khách đưa:");

        jLabel30.setText("Tiền thừa:");

        cboTrangThaiTT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Thanh toán khi nhận", "Thanh toán trước" }));
        cboTrangThaiTT.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        cboTrangThaiTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTrangThaiTTActionPerformed(evt);
            }
        });

        cboHTTT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chuyển Khoản", "Tiền Mặt" }));
        cboHTTT.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        txtTienKhachDuaDH.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtTienKhachDuaDH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTienKhachDuaDHActionPerformed(evt);
            }
        });

        txtTienThuaDatHang.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        btnXacNhanHD.setBackground(new java.awt.Color(0, 204, 255));
        btnXacNhanHD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/taoHD.png"))); // NOI18N
        btnXacNhanHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXacNhanHDActionPerformed(evt);
            }
        });

        btnChoGiaoHD.setBackground(new java.awt.Color(0, 204, 255));
        btnChoGiaoHD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/DOngHoCat.png"))); // NOI18N
        btnChoGiaoHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChoGiaoHDActionPerformed(evt);
            }
        });

        btnDangGiao.setBackground(new java.awt.Color(0, 204, 255));
        btnDangGiao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/VanChuyen.png"))); // NOI18N
        btnDangGiao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangGiaoActionPerformed(evt);
            }
        });

        btnHenGiaoLai.setBackground(new java.awt.Color(0, 204, 255));
        btnHenGiaoLai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Return.png"))); // NOI18N
        btnHenGiaoLai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHenGiaoLaiActionPerformed(evt);
            }
        });

        btnDaGiao.setBackground(new java.awt.Color(0, 204, 255));
        btnDaGiao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Approval.png"))); // NOI18N
        btnDaGiao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDaGiaoActionPerformed(evt);
            }
        });

        jLabel10.setText("Voucher:");

        cboVoucherDatHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboVoucherDatHangActionPerformed(evt);
            }
        });

        ngayMuonNhan.setDateFormatString("yyyy-MM-dd");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel20)
                                    .addComponent(jLabel21)
                                    .addComponent(jLabel22))
                                .addGap(28, 28, 28)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtTenNVDH, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                                    .addComponent(txtMaHDDH)
                                    .addComponent(txtTongTienDatHang)))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel30)
                                .addGap(38, 38, 38)
                                .addComponent(txtTienThuaDatHang, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel29)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtTienKhachDuaDH, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createSequentialGroup()
                                    .addComponent(jLabel28)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(cboHTTT, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createSequentialGroup()
                                    .addComponent(jLabel26)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(ngayMuonNhan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(3, 3, 3))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createSequentialGroup()
                                    .addComponent(jLabel27)
                                    .addGap(18, 18, 18)
                                    .addComponent(cboTrangThaiTT, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel24)
                                    .addComponent(jLabel23))
                                .addGap(31, 31, 31)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtGiamGiaDH, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                                    .addComponent(txtTienShip, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                                    .addComponent(cboVoucherDatHang, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel25)
                                .addGap(18, 18, 18)
                                .addComponent(txtThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel10)))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(btnXacNhanHD, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnChoGiaoHD, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDangGiao, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHenGiaoLai, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDaGiao, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnChoGiaoHD, btnDaGiao, btnDangGiao, btnHenGiaoLai, btnXacNhanHD});

        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtMaHDDH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtTenNVDH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(txtTongTienDatHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(cboVoucherDatHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(txtGiamGiaDH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(txtTienShip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(txtThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26)
                    .addComponent(ngayMuonNhan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(cboTrangThaiTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(cboHTTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(txtTienKhachDuaDH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(txtTienThuaDatHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnXacNhanHD, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnChoGiaoHD, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDangGiao, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnHenGiaoLai, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnDaGiao, javax.swing.GroupLayout.Alignment.TRAILING)))
        );

        jPanel9Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnChoGiaoHD, btnDaGiao, btnDangGiao, btnHenGiaoLai, btnXacNhanHD});

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 291, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tab.addTab("Đặt hàng", jPanel5);

        javax.swing.GroupLayout panelKHLayout = new javax.swing.GroupLayout(panelKH);
        panelKH.setLayout(panelKHLayout);
        panelKHLayout.setHorizontalGroup(
            panelKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelKHLayout.createSequentialGroup()
                .addComponent(tab, javax.swing.GroupLayout.PREFERRED_SIZE, 286, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelKHLayout.setVerticalGroup(
            panelKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelKHLayout.createSequentialGroup()
                .addComponent(tab)
                .addContainerGap())
        );

        add(panelKH, new org.netbeans.lib.awtextra.AbsoluteConstraints(888, 0, 310, 590));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh sach sản phẩm"));

        txtTK.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txtTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTKActionPerformed(evt);
            }
        });

        jLabel1.setText("Màu sắc:");

        cboMS.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả" }));
        cboMS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboMSMouseClicked(evt);
            }
        });
        cboMS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMSActionPerformed(evt);
            }
        });

        jLabel2.setText("Size");

        cboKichThuoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả" }));
        cboKichThuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboKichThuocActionPerformed(evt);
            }
        });

        jLabel3.setText("Hãng");

        tblSPCT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã CTSP", "Tên SP", "Màu sắc", "Kích thước", "Chất liệu", "Hãng", "Đế giày", "Giá", "Số lượng tồn"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSPCT.setRowHeight(20);
        tblSPCT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSPCTMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblSPCT);

        cboHang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả" }));
        cboHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboHangActionPerformed(evt);
            }
        });

        jLabel32.setText("Đế giày:");

        cboDeGiay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả" }));
        cboDeGiay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboDeGiayActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txtMSPCT)
                        .addGap(32, 32, 32))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtTK, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboMS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel32)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboDeGiay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 828, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(18, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cboMS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(cboHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32)
                    .addComponent(cboDeGiay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtMSPCT)
                .addGap(26, 26, 26))
        );

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 293, 870, 290));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Giỏ hàng"));

        tblGioHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã SPCT", "Tên sản phẩm", "Giá", "Số lượng", "Thành tiền", "Chọn"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblGioHang.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tblGioHangFocusLost(evt);
            }
        });
        tblGioHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGioHangMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblGioHang);

        btnXoaGioHang.setBackground(new java.awt.Color(0, 204, 255));
        btnXoaGioHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Delete.png"))); // NOI18N
        btnXoaGioHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaGioHangActionPerformed(evt);
            }
        });

        chkTatCa.setText("Tất cả");
        chkTatCa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkTatCaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 773, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(btnXoaGioHang, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkTatCa)))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(chkTatCa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnXoaGioHang)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 870, 130));

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        btnMoQR.setBackground(new java.awt.Color(0, 204, 255));
        btnMoQR.setText("Quét QR");
        btnMoQR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoQRActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnMoQR, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnMoQR, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 170, 130));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 10, 200, 150));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1210, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );

        add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void tblSPCTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSPCTMouseClicked
        addHDCT();
//        addHDCT1();
        tongTien();
    }//GEN-LAST:event_tblSPCTMouseClicked

    private void btnXoaGioHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaGioHangActionPerformed
        // TODO add your handling code here:
        DefaultTableModel gioHangModel = (DefaultTableModel) tblGioHang.getModel();
        DefaultTableModel hoaDonModel = (DefaultTableModel) tblHoaDon.getModel();
        int gioHangRow = tblGioHang.getSelectedRow();
        int hoaDonRow = tblHoaDon.getSelectedRow();
        if (gioHangRow >= 0 && hoaDonRow >= 0) {
            boolean tich = Boolean.parseBoolean(gioHangModel.getValueAt(gioHangRow, 6).toString());
            if (tich == true) {
                int i = JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa không?", "Trả lời", JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    String maHD = hoaDonModel.getValueAt(hoaDonRow, indext).toString();
                    ArrayList<HoaDonChiTiet> listHDCT = hdcts.getAllHoaDonChiTietMaHD(maHD);
                    if (!listHDCT.isEmpty()) {
                        HoaDonChiTiet hdct = listHDCT.get(gioHangRow);
                        hdcts.deleteSPInGioHangSP(hdct.getID_spct());
                        String maSPCT = gioHangModel.getValueAt(gioHangRow, 1).toString();
                        listSPCT = spctd.selectAllSPCTTheoMa(maSPCT);
                        for (SanPhamChiTiet spct : listSPCT) {
                            int slSPCT = Integer.parseInt(gioHangModel.getValueAt(gioHangRow, 4).toString()) + spct.getSoLuongTon(); // Sử dụng gioHangModel để lấy giá trị từ bảng
                            spct.setSoLuongTon(slSPCT);
                            spct.setMaCTSP(maSPCT);
                            spctd.UpdateSPCTSLT(spct);
                        }
                        listSPCT = spctd.selectAll();
                        fillToTableALLSPCT(listSPCT);
                        FillGioHang(listHDCT);
                        FillHoaDonChoThanhToan();
                        tblHoaDon.setRowSelectionInterval(indexHoaDonSelected, indexHoaDonSelected);
                        tblSPCT.scrollRectToVisible(tblSPCT.getCellRect(indexHoaDonSelected, 0, true));
                        tongTien();
//                        useVoucher();
                    }
                }
            } else {
                return;
            }
        }
    }//GEN-LAST:event_btnXoaGioHangActionPerformed

    private void btnKhachhangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKhachhangActionPerformed
        // TODO add your handling code here:
        new KhachHang_BanHang(jFrame1, true).setVisible(true);
        loadKH();

    }//GEN-LAST:event_btnKhachhangActionPerformed

    private void btnTaoHoaDonChiTietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoHoaDonChiTietActionPerformed
        // TODO add your handling code here:
        int indextt = tblHoaDon.getSelectedRow();
        if (indextt < 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn hóa đơn cần tạo");
            return;
        }
        String tienKhach = txtTienKhachDua.getText();
        String tienKhachThua = txtTienThua.getText();

        try {
            if (tblHoaDon.getSelectedRow() < 0) {
                JOptionPane.showMessageDialog(null, "Vui lòng  chọn hóa đơn cần tạo");
                return;
            }
            if (tienKhach.matches("\\d+")) {
                int tienKhachDua = Integer.parseInt(tienKhach);
                if (tienKhach.isEmpty() || tienKhachThua.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng  nhập tiền khách đưa");
                    return;
                }
                int tongtien = Integer.parseInt(tienKhach) + Integer.parseInt(tienKhachThua);
                if (tongtien < Integer.parseInt(txtTongTien.getText())) {
                    JOptionPane.showMessageDialog(null, "Khách đưa thiếu tiền!");
                    return;
                }
                if (tienKhachDua > 0) {
                    int i = JOptionPane.showConfirmDialog(null, "Bạn có muốn thanh toán ?", "Trả lời", JOptionPane.YES_NO_OPTION);
                    if (i == 0) {
                        try {
                            String maHD = txtMaHD.getText();
                            ArrayList<HoaDon> Listhd = hds.getAllHoaDon();
                            indext = tblHoaDon.getSelectedRow();
                            hd = Listhd.get(indext);
                            hd.setHinhThucThanhToan(cboHTThanhToan.getSelectedItem().toString());
                            hd.setMaHoaDon(maHD);
                            hd.setTongTien(Integer.parseInt(txtThanhToanTQ.getText()));
                            hd.setTrangThaiThanhToan("Tại quầy");
                            hd.setTenKH(txtTenKH.getText());
                            //Tạo để lấy nhân viên có mã NV như txtMaNV
                            String maKH = txtMaKH.getText();
                            listKH = khs.AllThongTinKhachHangTheoMa(maKH);
                            for (KhachHang kh : listKH) {
                                hd.setId_KH(kh.getID());
                            }
                            hd.setTienGiamGia(Integer.parseInt(txtGiamGia.getText()));
                            KhuyenMai vc = (KhuyenMai) CboVoucherTaiQuay.getSelectedItem();
                            hd.setId_VC(vc.getId());
                            hds.updateHDBH(hd);

                            FillHoaDonChoThanhToan();
                            JOptionPane.showMessageDialog(null, "Thanh toán thành công!");
                            clearForm();

                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "Thanh toán thất bại!");
                            e.printStackTrace();
                        }
                    } else {
                        return;
                    }
                } else {

                    JOptionPane.showMessageDialog(null, "Vui lòng nhập số tiền là số nguyên dương!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập số tiền là số nguyên dương!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Vui nhập tiền là số dương!");
            return;
        }


    }//GEN-LAST:event_btnTaoHoaDonChiTietActionPerformed

    private void btnTaoHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoHoaDonActionPerformed
        // TODO add your handling code here:   
        if (tblHoaDon.getRowCount() < 6) {
            insertHoaDon();
            cboTrangThai.setSelectedItem("Chờ thanh toán");
            FillHoaDonChoThanhToan();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(null, "Số hóa đơn chưa thanh toán đã vượt quá!");
            FillHoaDonChoThanhToan();
        }
//        insertHoaDon();
        txtTongTien.setText("0");
        txtTongTienDatHang.setText("0");
    }//GEN-LAST:event_btnTaoHoaDonActionPerformed

    private void txtTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTKActionPerformed
        // TODO add your handling code here:
        String keyword1 = txtTK.getText();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(dtmSPCT);
        tblSPCT.setRowSorter(sorter);

        if (keyword1.trim().length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + keyword1));
        }
    }//GEN-LAST:event_txtTKActionPerformed

    private void cboMSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMSActionPerformed
        // TODO add your handling code here:
        Object selectedValue = cboMS.getSelectedItem();
        if (selectedValue != null) {
            String value = selectedValue.toString();
            if (value.equals("Tất cả")) {
                fillToTableALLSPCT(listSPCT);
            } else {
                searchInTableMauSac(value);

            }
        } else {
            fillToTableALLSPCT(listSPCT);
        }
    }//GEN-LAST:event_cboMSActionPerformed

    private void cboKichThuocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboKichThuocActionPerformed
        // TODO add your handling code here:
        Object selectedValue = cboKichThuoc.getSelectedItem();
        if (selectedValue != null) {
            String value = selectedValue.toString();
            if (value.equals("Tất cả")) {
                fillToTableALLSPCT(listSPCT);
            } else {
                //            searchInTableHang(cboHang.getSelectedItem().toString());
                //            searchInTableMauSac(cboMauSac.getSelectedItem().toString());
                searchInTableSize(value);
                //            searchInTableChatLieu(cboChatLieu.getSelectedItem().toString());
            }
        } else {
            fillToTableALLSPCT(listSPCT);
        }
    }//GEN-LAST:event_cboKichThuocActionPerformed

    private void cboHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboHangActionPerformed
        // TODO add your handling code here:
        Object selectedValue = cboHang.getSelectedItem();
        if (selectedValue != null) {
            String value = selectedValue.toString();
            if (cboHang.getSelectedItem().equals("Tất cả")) {
                fillToTableALLSPCT(listSPCT);
            } else {
                searchInTableHang(value);
            }
        } else {
            fillToTableALLSPCT(listSPCT);
        }
    }//GEN-LAST:event_cboHangActionPerformed

    private void cboDeGiayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboDeGiayActionPerformed
        // TODO add your handling code here:
        Object selectedValue = cboDeGiay.getSelectedItem();
        if (selectedValue != null) {
            String value = selectedValue.toString();
            if (value.equals("Tất cả")) {
                fillToTableALLSPCT(listSPCT);
            } else {
                //            searchInTableHang(cboHang.getSelectedItem().toString());
                //            searchInTableMauSac(cboMauSac.getSelectedItem().toString());
                //            searchInTableSize(cboKichThuoc.getSelectedItem().toString());
                searchInTableDeGiay(value);
            }
        } else {
            fillToTableALLSPCT(listSPCT);
        }
    }//GEN-LAST:event_cboDeGiayActionPerformed

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        // TODO add your handling code here:
//        try {

        if (cboDonHang.getSelectedItem().equals("Tại quầy")) {
            row = tblHoaDon.getSelectedRow();
            txtMaHD.setText(tblHoaDon.getValueAt(row, 0).toString());
            txtTenNV.setText(tblHoaDon.getValueAt(row, 2).toString());
            FillGioHang(listHDCT);
            tongTien();
//                if (Integer.parseInt(tblGioHang.getValueAt(a, 4).toString()) > 0) {
//                    useVoucher();
////            }
//                }

        } else if (cboDonHang.getSelectedItem().equals("Đặt hàng")) {
            row = tblHoaDon.getSelectedRow();
            txtMaHDDH.setText(tblHoaDon.getValueAt(row, 0).toString());
            txtTenNVDH.setText(tblHoaDon.getValueAt(row, 2).toString());
            FillGioHang(listHDCT);
            tongTien();
//                if (Integer.parseInt(tblGioHang.getValueAt(a, 4).toString()) > 0 && cboDonHang.getSelectedItem().equals("Đặt hàng")) {
//                    useVoucherDatHang();
//                }

        }
        btnXacNhanHD.setEnabled(true);
        if (cboTrangThai.getSelectedItem().equals("Chờ thanh toán")) {
            btnChoGiaoHD.setEnabled(false);
            btnDangGiao.setEnabled(false);
            btnHenGiaoLai.setEnabled(false);
            btnDaGiao.setEnabled(false);
            btnXacNhanHD.setEnabled(true);

        } else if (cboTrangThai.getSelectedItem().equals("Đã thanh toán")) {
            btnChoGiaoHD.setEnabled(true);
            btnDangGiao.setEnabled(false);
            btnHenGiaoLai.setEnabled(false);
            btnDaGiao.setEnabled(false);
            btnXacNhanHD.setEnabled(false);
            showDataBanHang();
        } else if (cboTrangThai.getSelectedItem().equals("Chờ giao hàng")) {
            btnChoGiaoHD.setEnabled(false);
            btnDangGiao.setEnabled(true);
            btnHenGiaoLai.setEnabled(false);
            btnDaGiao.setEnabled(false);
            btnXacNhanHD.setEnabled(false);
            showDataBanHang();
        } else if (cboTrangThai.getSelectedItem().equals("Đang vận chuyển")) {
            btnChoGiaoHD.setEnabled(false);
            btnDangGiao.setEnabled(false);
            btnHenGiaoLai.setEnabled(true);
            btnDaGiao.setEnabled(true);
            btnXacNhanHD.setEnabled(false);
            showDataBanHang();
        } else if (cboTrangThai.getSelectedItem().equals("Giao lại")) {
            btnChoGiaoHD.setEnabled(false);
            btnDangGiao.setEnabled(true);
            btnHenGiaoLai.setEnabled(false);
            btnDaGiao.setEnabled(false);
            btnXacNhanHD.setEnabled(false);
            showDataBanHang();
        } else {
            btnChoGiaoHD.setEnabled(false);
            btnDangGiao.setEnabled(false);
            btnHenGiaoLai.setEnabled(false);
            btnDaGiao.setEnabled(false);
            btnXacNhanHD.setEnabled(false);

        }
        try {

            int a = tblHoaDon.getSelectedRow();
            if (Integer.parseInt(tblGioHang.getValueAt(a, 4).toString()) > 0) {
                useVoucher();
                useVoucherDatHang();
                fillComboDatHang();
            }
        } catch (Exception e) {
        }
//            } else if (Integer.parseInt(tblGioHang.getValueAt(a, 4).toString()) > 0 && cboDonHang.getSelectedItem().equals("Đặt hàng")) {
//                useVoucherDatHang();
//            }
        FillGioHang(listHDCT);
//        } catch (Exception e) {
//        }
        useVoucher();
        useVoucherDatHang();

//        clickHD();
//        useVoucher();

    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void chkTatCaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkTatCaActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_chkTatCaActionPerformed

    private void txtTienKhachDuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTienKhachDuaActionPerformed
        // TODO add your handling code here:

        try {
            // Lấy giá trị tiền khách đưa từ người dùng
            String tienKhach = txtTienKhachDua.getText();

            // Kiểm tra xem chuỗi nhập vào có thể chuyển đổi thành số nguyên hay không
            if (tienKhach.matches("\\d+")) {
                int tienKhachDua = Integer.parseInt(tienKhach);
                int tienThua = tienKhachDua - Integer.parseInt(txtThanhToanTQ.getText());
                txtTienThua.setText(tienThua + "");
                // Kiểm tra xem tiền khách đưa có là số nguyên dương hay không
                if (tienKhachDua > 0) {
                    // Tiền khách đưa là số nguyên dương
                    // Thực hiện các thao tác khác ở đây
                } else {
                    // Tiền khách đưa không là số nguyên dương
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập số tiền là số nguyên dương!");
                }
            } else {
                // Chuỗi không thể chuyển đổi thành số nguyên
                JOptionPane.showMessageDialog(null, "Vui lòng nhập số tiền là số nguyên dương!");
            }
        } catch (NumberFormatException e) {
            // Xảy ra lỗi khi không thể chuyển đổi thành số nguyên
            JOptionPane.showMessageDialog(null, "Vui lòng nhập số tiền là số nguyên dương!");
        }
    }//GEN-LAST:event_txtTienKhachDuaActionPerformed

    private void btnHenGiaoLaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHenGiaoLaiActionPerformed
        // TODO add your handling code here:
        int indextt = tblHoaDon.getSelectedRow();
        if (indextt < 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn hóa đơn cần tạo");
            return;
        }
        Date ngayNhanDate = ngayMuonNhan.getDate();
        Date today = new Date();
        if (ngayNhanDate.before(today)) {
            JOptionPane.showMessageDialog(null, "Vui lòng điền ngày nhận lớn hơn ngày hiện tại!");
            return;
        }
        int i = JOptionPane.showConfirmDialog(null, "Bạn có muốn cập nhật hóa đơn thành Giao lại?", "Tl", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            row = tblHoaDon.getSelectedRow();
            ArrayList<HoaDon> listDd = hds.getAllHoaDonDangGiao();
            HoaDon hd = listDd.get(row);
            hd.setTrangThai("Hẹn giao lại");
            cboTrangThai.setSelectedItem("Giao lại");

            try {
                hds.updateTrangThai(hd);
                JOptionPane.showMessageDialog(null, "Cập nhật thành công!");
                FillHoaDonGiaoLai();
                btnHenGiaoLai.setEnabled(false);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Cập nhật thất bại!");
            }
        } else {
            return;
        }
    }//GEN-LAST:event_btnHenGiaoLaiActionPerformed

    private void btnNguoiNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNguoiNhanActionPerformed
        // TODO add your handling code here:
        new KhachHang_BanHang(jFrame1, true).setVisible(true);
        loadKH();
    }//GEN-LAST:event_btnNguoiNhanActionPerformed

    private void btnNguoiShipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNguoiShipActionPerformed
        // TODO add your handling code here:

        new NguoiShip(jFrame2, true).setVisible(true);
        loadNV();

    }//GEN-LAST:event_btnNguoiShipActionPerformed

    private void btnXacNhanHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanHDActionPerformed
        // TODO add your handling code here:
        int indextt = tblHoaDon.getSelectedRow();
        if (indextt < 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn hóa đơn cần tạo");
            return;
        }
        cboHinhThucThanhToan.setSelectedItem(cboTrangThaiTT.getSelectedItem());
        thanhToanDatHang();
        clearForm();


    }//GEN-LAST:event_btnXacNhanHDActionPerformed

    private void cboMSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboMSMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_cboMSMouseClicked

    private void tabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabMouseClicked
        // TODO add your handling code here:

        if (tab.getSelectedIndex() == 0) {
            cboDonHang.setSelectedItem("Tại quầy");
            cboHinhThucThanhToan.setEnabled(false);
            cboTrangThai.setEnabled(false);
            cboTrangThai.setSelectedItem("Chờ thanh toán");
            FillHoaDonChoThanhToan();
            return;
        } else if (tab.getSelectedIndex() == 1) {
            cboDonHang.setSelectedItem("Đặt hàng");
            cboHinhThucThanhToan.setEnabled(true);
            cboTrangThai.setEnabled(true);
            cboTrangThai.setSelectedItem("Chờ thanh toán");
            if (cboTrangThai.getSelectedItem().equals("Đã thanh toán")) {
                FillHoaDonDaThanhToan();
            }
            FillHoaDonChoThanhToan();
            return;
        }
        btnChoGiaoHD.setEnabled(false);
        btnDangGiao.setEnabled(false);
        btnHenGiaoLai.setEnabled(false);
        btnDaGiao.setEnabled(false);
        btnXacNhanHD.setEnabled(false);
        btnTaoHoaDonChiTiet.setEnabled(false);
    }//GEN-LAST:event_tabMouseClicked

    private void txtTienKhachDuaDHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTienKhachDuaDHActionPerformed
        // TODO add your handling code here:

        try {
            // Lấy giá trị tiền khách đưa từ người dùng
            String tienKhach = txtTienKhachDuaDH.getText();

            // Kiểm tra xem chuỗi nhập vào có thể chuyển đổi thành số nguyên hay không
            if (tienKhach.matches("\\d+")) {
                int tienKhachDua = Integer.parseInt(tienKhach);
                int tienThua = tienKhachDua - Integer.parseInt(txtThanhToan.getText());
                txtTienThuaDatHang.setText(tienThua + "");
                // Kiểm tra xem tiền khách đưa có là số nguyên dương hay không
                if (tienKhachDua > 0) {
                    // Tiền khách đưa là số nguyên dương
                    // Thực hiện các thao tác khác ở đây
                } else {
                    // Tiền khách đưa không là số nguyên dương
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập số tiền là số nguyên dương!");
                }
            } else {
                // Chuỗi không thể chuyển đổi thành số nguyên
                JOptionPane.showMessageDialog(null, "Vui lòng nhập số tiền là số nguyên dương!");
            }
        } catch (NumberFormatException e) {
            // Xảy ra lỗi khi không thể chuyển đổi thành số nguyên
            JOptionPane.showMessageDialog(null, "Vui lòng nhập số tiền là số nguyên dương!");
        }
    }//GEN-LAST:event_txtTienKhachDuaDHActionPerformed

    private void txtTienShipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTienShipActionPerformed
        // TODO add your handling code here:      

        try {
            // Lấy giá trị tiền khách đưa từ người dùng
            String tongTien = txtTongTienDatHang.getText();

            // Kiểm tra xem chuỗi nhập vào có thể chuyển đổi thành số nguyên hay không
            if (tongTien.matches("\\d+")) {
                int tongTienInt = Integer.parseInt(tongTien);
                int thanhtoanTien = tongTienInt - Integer.parseInt(txtGiamGiaDH.getText()) + Integer.parseInt(txtTienShip.getText());
                txtThanhToan.setText(thanhtoanTien + "");
                // Kiểm tra xem tiền khách đưa có là số nguyên dương hay không

            } else {
                // Chuỗi không thể chuyển đổi thành số nguyên
                JOptionPane.showMessageDialog(null, "Vui lòng nhập tiền Ship là số nguyên dương!");
            }
        } catch (NumberFormatException e) {
            // Xảy ra lỗi khi không thể chuyển đổi thành số nguyên
            JOptionPane.showMessageDialog(null, "Vui lòng nhập tiền Ship là số nguyên dương!");
        }
    }//GEN-LAST:event_txtTienShipActionPerformed

    private void cboTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTrangThaiActionPerformed
        // TODO add your handling code here:
        if (cboTrangThai.getSelectedItem().equals("Chờ thanh toán")) {
            FillHoaDonChoThanhToan();
            if (!txtMaHD.getText().equalsIgnoreCase("")) {
                btnTaoHoaDonChiTiet.setEnabled(true);
            } else {
//                btnTaoHoaDonChiTiet.setEnabled(false);
            }
            btnChoGiaoHD.setEnabled(false);
            btnDangGiao.setEnabled(false);
            btnHenGiaoLai.setEnabled(false);
            btnDaGiao.setEnabled(false);
            btnXacNhanHD.setEnabled(true);
        } else if (cboTrangThai.getSelectedItem().equals("Đã thanh toán") && cboDonHang.getSelectedItem().equals("Đặt hàng")) {
            FillHoaDonDaThanhToan();
            btnChoGiaoHD.setEnabled(true);
            btnDangGiao.setEnabled(false);
            btnHenGiaoLai.setEnabled(false);
            btnDaGiao.setEnabled(false);
            btnXacNhanHD.setEnabled(false);
        } else if (cboTrangThai.getSelectedItem().equals("Chờ giao hàng")) {
            FillHoaDonChoGiaoHang();
            btnChoGiaoHD.setEnabled(false);
            btnDangGiao.setEnabled(true);
            btnHenGiaoLai.setEnabled(false);
            btnDaGiao.setEnabled(false);
            btnXacNhanHD.setEnabled(false);
        } else if (cboTrangThai.getSelectedItem().equals("Đang vận chuyển")) {
            FillHoaDonDangGiao();
            btnChoGiaoHD.setEnabled(false);
            btnDangGiao.setEnabled(false);
            btnHenGiaoLai.setEnabled(true);
            btnDaGiao.setEnabled(true);
            btnXacNhanHD.setEnabled(false);
        } else if (cboTrangThai.getSelectedItem().equals("Giao lại")) {
            FillHoaDonGiaoLai();
            btnChoGiaoHD.setEnabled(false);
            btnDangGiao.setEnabled(true);
            btnHenGiaoLai.setEnabled(false);
            btnDaGiao.setEnabled(false);
            btnXacNhanHD.setEnabled(false);
        } else if (cboTrangThai.getSelectedItem().equals("Đã giao")) {
            FillHoaDonDaGiao();
        } else {
            FillHoaDonDaThanhToan();

        }

    }//GEN-LAST:event_cboTrangThaiActionPerformed

    private void btnChoGiaoHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChoGiaoHDActionPerformed
        // TODO add your handling code here:
        int indextt = tblHoaDon.getSelectedRow();
        if (indextt < 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn hóa đơn cần tạo");
            return;
        }
        int i = JOptionPane.showConfirmDialog(null, "Bạn có muốn cập nhật hóa đơn thành chờ giao hàng?", "Tl", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            if (cboTrangThaiTT.getSelectedItem().equals("Thanh toán trước")) {
                row = tblHoaDon.getSelectedRow();
                ArrayList<HoaDon> listDd = hds.getAllHoaDonDaThanhToan();
                HoaDon hd = listDd.get(row);
                hd.setTrangThai("Chờ giao hàng");
                cboTrangThai.setSelectedItem("Chờ giao hàng");
                try {
                    hds.updateTrangThai(hd);
                    JOptionPane.showMessageDialog(null, "Cập nhật thành công!");
                    FillHoaDonChoGiaoHang();
                    lblTrangThaiHoaDon.setText(tblHoaDon.getRowCount() + " Hóa đơn chờ giao");
                    btnChoGiaoHD.setEnabled(false);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Cập nhật thất bại!");
                }
            } else if (cboTrangThaiTT.getSelectedItem().equals("Thanh toán khi nhận")) {
                String trangThaiTT = cboTrangThaiTT.getSelectedItem().toString();
                String hinhThucTT = cboHTTT.getSelectedItem().toString();
                String tenKH = txtNguoiNhan.getText();
                String sdt = txtSDTNguoiNhan.getText();
                String diaChi = txtDiaChi.getText();
                String maHD = txtMaHDDH.getText();
                String nguoiShip = TxtNguoiSHip.getText();
                String sdtNguoiShip = txtSDT.getText();
                HoaDon hd = new HoaDon();
                hd.setMaHoaDon(maHD);

                hd.setTrangThaiThanhToan(trangThaiTT);
                hd.setHinhThucThanhToan(hinhThucTT);
                hd.setTenKH(tenKH);
                hd.setSdt(sdt);
                hd.setDiaChi(diaChi);

                hd.setTenNguoiShip(nguoiShip);
                hd.setSdtNGuoiShip(sdtNguoiShip);
                String phiShip = txtTienShip.getText();
                hd.setPhiShip(Integer.parseInt(phiShip));

                hd.setSdt(txtSDTNguoiNhan.getText());
                hd.setDiaChi(txtDiaChi.getText());

                Date ngayMuonNhan = this.ngayMuonNhan.getDate();
                hd.setNgayMuonNhan(ngayMuonNhan);
                hd.setTrangThaiThanhToan(cboTrangThaiTT.getSelectedItem().toString());
                hd.setHinhThucThanhToan(cboHTTT.getSelectedItem().toString());
                String tongTien = txtThanhToan.getText();
                hd.setTongTien(Integer.parseInt(tongTien));
                hd.setTrangThai("Chờ giao hàng");
                cboTrangThai.setSelectedItem("Chờ giao hàng");
                String maKH = txtMaKH.getText();
                listKH = khs.AllThongTinKhachHangTheoMa(maKH);
                for (KhachHang kh : listKH) {
                    hd.setId_KH(kh.getID());
                }
                String maNV = lblMaNV.getText();
                listNV = nvs.getAllEmTheoMa(maNV);
                for (NhanVien nv : listNV) {
                    hd.setId_NV(nv.getID());
                }
                hd.setTienGiamGia(Integer.parseInt(txtGiamGiaDH.getText()));
                KhuyenMai vc = (KhuyenMai) cboVoucherDatHang.getSelectedItem();
                hd.setId_VC(vc.getId());
                if (phiShip.isEmpty() || ngayMuonNhan == null || diaChi.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin");
                    return;
                }

                Date ngayGioHienTai = new Date();
                int soSanh = ngayMuonNhan.compareTo(ngayGioHienTai);

                if (soSanh < 0) {
                    JOptionPane.showMessageDialog(null, "Vui lòng điền ngày nhận lớn hơn bằng ngày hiện tại!");
                    return;
                }
                try {
                    hds.updateDatHang(hd);
                    JOptionPane.showMessageDialog(null, "Cập nhật thành công!");
                    FillHoaDonChoGiaoHang();
                    lblTrangThaiHoaDon.setText(tblHoaDon.getRowCount() + " Hóa đơn chờ giao");
                    btnChoGiaoHD.setEnabled(false);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Cập nhật thất bại!");
                }

            }
        } else {
            return;
        }
    }//GEN-LAST:event_btnChoGiaoHDActionPerformed

    private void btnDangGiaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangGiaoActionPerformed
        // TODO add your handling code here:
        int indextt = tblHoaDon.getSelectedRow();
        if (indextt < 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn hóa đơn cần tạo");
            return;
        }
        int i = JOptionPane.showConfirmDialog(null, "Bạn có muốn cập nhật hóa đơn thành đang giao hàng?", "Tl", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            if (cboTrangThai.getSelectedItem().equals("Chờ giao hàng")) {
                row = tblHoaDon.getSelectedRow();
                ArrayList<HoaDon> listDd = hds.getAllHoaDonChoGiaoHang();
                HoaDon hd = listDd.get(row);
                hd.setTrangThai("Đang vận chuyển");
                Date today = Calendar.getInstance().getTime();
                hd.setNgayShip(today);
                cboTrangThai.setSelectedItem("Đang vận chuyển");
                try {

                    hds.ngayShip(hd);
                    JOptionPane.showMessageDialog(null, "Cập nhật thành công!");
                    FillHoaDonDangGiao();
                    btnDangGiao.setEnabled(false);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Cập nhật thất bại!");
                }
            } else {
                row = tblHoaDon.getSelectedRow();
                ArrayList<HoaDon> listDd = hds.getAllHoaDonGiaoLai();
                HoaDon hd = listDd.get(row);
                hd.setTrangThai("Đang vận chuyển");
                cboTrangThai.setSelectedItem("Đang vận chuyển");
                try {
                    hds.updateTrangThai(hd);
                    JOptionPane.showMessageDialog(null, "Cập nhật thành công!");
                    FillHoaDonDangGiao();
                    btnDangGiao.setEnabled(false);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Cập nhật thất bại!");
                }
            }
        } else {
            return;
        }

    }//GEN-LAST:event_btnDangGiaoActionPerformed

    private void btnDaGiaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDaGiaoActionPerformed
        // TODO add your handling code here:
        int indextt = tblHoaDon.getSelectedRow();
        if (indextt < 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn hóa đơn cần tạo");
            return;
        }
        int i = JOptionPane.showConfirmDialog(null, "Bạn có chắc đã giao và đã thu đủ tiền ?", "Tl", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            row = tblHoaDon.getSelectedRow();
            if (cboTrangThai.getSelectedItem().equals("Đang vận chuyển")) {
                ArrayList<HoaDon> listDd = hds.getAllHoaDonDangGiao();
                HoaDon hd = listDd.get(row);
                Date today = Calendar.getInstance().getTime();
                hd.setDeleted(true);
                hd.setNgayThanhToan(today);
                hd.setNgayXacNhan(today);
                hd.setTrangThai("Thành công");
                cboTrangThai.setSelectedItem("Chờ thanh toán");
                try {
                    hds.hoanThanhGiaoHang(hd);
                    JOptionPane.showMessageDialog(null, "Cập nhật thành công!");
                    FillHoaDonChoThanhToan();
                    btnDaGiao.setEnabled(false);
                    clearForm();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Cập nhật thất bại!");
                }
            } else if (cboTrangThai.getSelectedItem().equals("Giao lại")) {
                ArrayList<HoaDon> listDd = hds.getAllHoaDonGiaoLai();
                HoaDon hd = listDd.get(row);
                hd.setTrangThai("Thành công");
                cboTrangThai.setSelectedItem("Chờ thanh toán");
                try {
                    hds.updateTrangThai(hd);
                    JOptionPane.showMessageDialog(null, "Cập nhật thành công!");
                    FillHoaDonDaGiao();
                    btnDaGiao.setEnabled(false);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Cập nhật thất bại!");
                }
            }
        } else {
            return;
        }
        clearForm();
    }//GEN-LAST:event_btnDaGiaoActionPerformed

    private void cboDonHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboDonHangActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_cboDonHangActionPerformed

    private void tblGioHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGioHangMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            row = tblGioHang.getSelectedRow();
            if (row >= 0) {
                String soluongBanDau = tblGioHang.getValueAt(row, 4).toString();
                String soLuongSau = JOptionPane.showInputDialog("Số lượng", 0);
                indext = tblHoaDon.getSelectedRow();
                String maHD = tblHoaDon.getValueAt(indext, 0).toString();
                listHDCT = hdcts.getAllHoaDonChiTietMaHD(maHD);

                HoaDonChiTiet hdct = listHDCT.get(row);
                String maSPCT = tblGioHang.getValueAt(row, 1).toString();
                listSPCT = spctd.selectAllSPCTTheoMa(maSPCT);
                try {
                    int soluong = Integer.parseInt(soLuongSau);

                    if (soluong <= 0) {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập số lượng là dương!");
                        return;
                    }

                    // Thực hiện các xử lý khác khi số lượng hợp lệ
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập số lượng là một số nguyên!");
                    return;
                }
                for (SanPhamChiTiet spct : listSPCT) {
                    if (Integer.parseInt(soLuongSau) > spct.getSoLuongTon() + Integer.parseInt(tblHoaDon.getValueAt(row, 4).toString())) {
                        JOptionPane.showMessageDialog(null, "Số lượng không đủ!");
                        return;
                    }
                    if (Integer.parseInt(soLuongSau) < Integer.parseInt(soluongBanDau)) {
                        hdct.setSl(Integer.parseInt(soLuongSau));
                        hdct.setID_spct(spct.getId());
                        hdcts.updateSLHDCTKhiXoa(hdct);
                        FillGioHang(listHDCT);
                        int slSPCT = Integer.parseInt(soluongBanDau) - Integer.parseInt(soLuongSau) + spct.getSoLuongTon();
                        spct.setSoLuongTon(slSPCT);
                        spct.setMaCTSP(maSPCT);
                        spctd.UpdateSPCTSLT(spct);
                    } else {
                        hdct.setSl(Integer.parseInt(soLuongSau));
                        hdct.setID_spct(spct.getId());
                        hdcts.updateSLHDCTKhiXoa(hdct);
                        FillGioHang(listHDCT);
                        int slSPCT = spct.getSoLuongTon() + Integer.parseInt(soluongBanDau) - Integer.parseInt(soLuongSau);
                        spct.setSoLuongTon(slSPCT);
                        spct.setMaCTSP(maSPCT);
                        spctd.UpdateSPCTSLT(spct);
                    }

                }
                listSPCT = spctd.selectAll();
                fillToTableALLSPCT(listSPCT);

            }
        }


    }//GEN-LAST:event_tblGioHangMouseClicked

    private void tblHoaDonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tblHoaDonMouseEntered

    private void tblGioHangFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tblGioHangFocusLost
        // TODO add your handling code here:
//        row = tblGioHang.getSelectedRow();
//        if (row >= 0) {
//            String soluongBanDau = tblGioHang.getValueAt(row, 4).toString();
//            String soLuongSau = tblGioHang.getValueAt(row, 4).toString();
//            indext = tblHoaDon.getSelectedRow();
//            String maHD = tblHoaDon.getValueAt(indext, 0).toString();
//            listHDCT = hdcts.getAllHoaDonChiTietMaHD(maHD);
//
//            HoaDonChiTiet hdct = listHDCT.get(row);
//
//            if (!soluongBanDau.equals(soLuongSau)) {
//                String maSPCT = tblGioHang.getValueAt(row, 1).toString();
//                listSPCT = spctd.selectAllSPCTTheoMa(maSPCT);
//                for (SanPhamChiTiet spct : listSPCT) {
//                    hdct.setSl(Integer.parseInt(soLuongSau));
//                    hdct.setID_spct(spct.getId());
//                    hdcts.updateSLHDCTKhiXoa(hdct);
//                    int slSPCT = Integer.parseInt(soluongBanDau) - Integer.parseInt(soLuongSau);
//                    spct.setSoLuongTon(slSPCT);
//                    spct.setMaCTSP(maSPCT);
//                    spctd.UpdateSPCTSLT(spct);
//                }
//                listSPCT = spctd.selectAll();
//                fillToTableALLSPCT(listSPCT);
//            }
//        }

    }//GEN-LAST:event_tblGioHangFocusLost

    private void cboTrangThaiTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTrangThaiTTActionPerformed
        // TODO add your handling code here:
        if (cboTrangThaiTT.getSelectedItem().equals("Thanh toán khi nhận") && cboTrangThai.getSelectedItem().equals("Chờ thanh toán")) {
            btnChoGiaoHD.setEnabled(true);
            btnXacNhanHD.setEnabled(false);
            txtTienKhachDuaDH.setEnabled(false);
            txtTienKhachDuaDH.setText(".");
            txtTienThuaDatHang.setText(".");
        } else if (cboTrangThaiTT.getSelectedItem().equals("Thanh toán trước") && cboTrangThai.getSelectedItem().equals("Chờ thanh toán")) {
            btnChoGiaoHD.setEnabled(false);
            btnXacNhanHD.setEnabled(true);
            txtTienKhachDuaDH.setEnabled(true);
        }
    }//GEN-LAST:event_cboTrangThaiTTActionPerformed


    private void btnMoQRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoQRActionPerformed
        QuetQR qr = new QuetQR(jFrame1, true);
        qr.phanBietQR = true;
        qr.setVisible(true);
        int rowIndex = -1; // Lưu chỉ số dòng chứa mã SPCT trùng khớp

        String maSpct = QuetQR.maSPCT;
        System.out.println(maSpct);
        for (int i = 0; i < tblSPCT.getRowCount(); i++) {
            String currentMaSPCT = (String) tblSPCT.getValueAt(i, 1); // Giả sử cột mã SPCT là cột đầu tiên
            if (maSpct.equalsIgnoreCase(currentMaSPCT)) {
                rowIndex = i;
                break;
            }
        }
        if (rowIndex != -1) {
            // Chọn dòng tương ứng trong bảng
            tblSPCT.setRowSelectionInterval(rowIndex, rowIndex);
            // Scroll đến dòng được chọn
            tblSPCT.scrollRectToVisible(tblSPCT.getCellRect(rowIndex, 0, true));
            addHDCT();
        }
    }//GEN-LAST:event_btnMoQRActionPerformed

    private void CboVoucherTaiQuayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CboVoucherTaiQuayActionPerformed
        // TODO add your handling code here:
        KhuyenMai selectedValue = (KhuyenMai) CboVoucherTaiQuay.getSelectedItem();
        if (selectedValue != null) {
            String tongTien = txtTongTien.getText();

            if (tongTien != null) {
                if (selectedValue.getHinhThucGiamGia().equalsIgnoreCase("Giảm theo số tiền")) {
                    int tongTienInt = Integer.parseInt(tongTien);
                    txtGiamGia.setText(String.valueOf(selectedValue.getMucGiamGia()));
                    txtThanhToanTQ.setText(String.valueOf(tongTienInt - Integer.parseInt(selectedValue.getMucGiamGia())));
                }
                if (selectedValue.getHinhThucGiamGia().equalsIgnoreCase("Giảm theo %")) {
                    int tongTienInt = Integer.parseInt(tongTien);
                    int phanTram = Integer.parseInt(String.valueOf(selectedValue.getMucGiamGia()));
                    int tienGiam = tongTienInt * phanTram / 100;
                    txtGiamGia.setText(String.valueOf(tienGiam));
                    txtThanhToanTQ.setText(String.valueOf(tongTienInt - tienGiam));
                }
            } else {
                return;
            }
        }

    }//GEN-LAST:event_CboVoucherTaiQuayActionPerformed

    private void cboVoucherDatHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboVoucherDatHangActionPerformed
        // TODO add your handling code here:
        KhuyenMai selectedValue = (KhuyenMai) cboVoucherDatHang.getSelectedItem();
        if (selectedValue != null) {
            String tongTien = txtTongTienDatHang.getText();

            if (tongTien != null) {
                if (selectedValue.getHinhThucGiamGia().equalsIgnoreCase("Giảm theo số tiền")) {
                    int tongTienInt = Integer.parseInt(tongTien);
                    txtGiamGiaDH.setText(String.valueOf(selectedValue.getMucGiamGia()));
//                    txtThanhToan.setText(String.valueOf(tongTienInt - Integer.parseInt(selectedValue.getMucGiamGia())));
                }
                if (selectedValue.getHinhThucGiamGia().equalsIgnoreCase("Giảm theo %")) {
                    int tongTienInt = Integer.parseInt(tongTien);
                    int phanTram = Integer.parseInt(String.valueOf(selectedValue.getMucGiamGia()));
                    int tienGiam = tongTienInt * phanTram / 100;
                    txtGiamGiaDH.setText(String.valueOf(tienGiam));
//                    txtThanhToanTQ.setText(String.valueOf(tongTienInt - tienGiam));
                }
            } else {
                return;
            }
        }
    }//GEN-LAST:event_cboVoucherDatHangActionPerformed

    private void tabMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tabMouseEntered


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CboVoucherTaiQuay;
    private javax.swing.JTextField TxtNguoiSHip;
    private javax.swing.JButton btnChoGiaoHD;
    private javax.swing.JButton btnDaGiao;
    private javax.swing.JButton btnDangGiao;
    private javax.swing.JButton btnHenGiaoLai;
    private javax.swing.JButton btnKhachhang;
    private javax.swing.JButton btnMoQR;
    private javax.swing.JButton btnNguoiNhan;
    private javax.swing.JButton btnNguoiShip;
    private javax.swing.JButton btnTaoHoaDon;
    private javax.swing.JButton btnTaoHoaDonChiTiet;
    private javax.swing.JButton btnXacNhanHD;
    private javax.swing.JButton btnXoaGioHang;
    private javax.swing.JComboBox<String> cboDeGiay;
    private javax.swing.JComboBox<String> cboDonHang;
    private javax.swing.JComboBox<String> cboHTTT;
    private javax.swing.JComboBox<String> cboHTThanhToan;
    private javax.swing.JComboBox<String> cboHang;
    private javax.swing.JComboBox<String> cboHinhThucThanhToan;
    private javax.swing.JComboBox<String> cboKichThuoc;
    private javax.swing.JComboBox<String> cboMS;
    private javax.swing.JComboBox<String> cboTrangThai;
    private javax.swing.JComboBox<String> cboTrangThaiTT;
    private javax.swing.JComboBox<String> cboVoucherDatHang;
    private javax.swing.JCheckBox chkTatCa;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JFrame jFrame2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblMaKH;
    private javax.swing.JLabel lblMaNV;
    private javax.swing.JLabel lblTrangThaiHoaDon;
    private com.toedter.calendar.JDateChooser ngayMuonNhan;
    private javax.swing.JPanel panelHD;
    private javax.swing.JPanel panelKH;
    private javax.swing.JPanel panelThongTinKH;
    private javax.swing.JTabbedPane tab;
    private javax.swing.JTable tblGioHang;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblSPCT;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtGiamGia;
    private javax.swing.JTextField txtGiamGiaDH;
    private javax.swing.JLabel txtMSPCT;
    private javax.swing.JTextField txtMaHD;
    private javax.swing.JTextField txtMaHDDH;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtNguoiNhan;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtSDTNguoiNhan;
    private javax.swing.JTextField txtTK;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTenNV;
    private javax.swing.JTextField txtTenNVDH;
    private javax.swing.JTextField txtThanhToan;
    private javax.swing.JTextField txtThanhToanTQ;
    private javax.swing.JTextField txtTienKhachDua;
    private javax.swing.JTextField txtTienKhachDuaDH;
    private javax.swing.JTextField txtTienShip;
    private javax.swing.JTextField txtTienThua;
    private javax.swing.JTextField txtTienThuaDatHang;
    private javax.swing.JTextField txtTongTien;
    private javax.swing.JTextField txtTongTienDatHang;
    // End of variables declaration//GEN-END:variables

    private void init() {
        fillToTableALLSPCT(listSPCT);
        txtMaKH.setText("KH00");
        txtTenKH.setText("Khách bán lẻ");
        fillComboBoxChatLieu();
        fillComboBoxDeGiay();
        fillComboBoxHang();
        fillComboBoxMauSac();
        fillComboBoxSize();
        if (tab.getSelectedIndex() == 0) {
            cboDonHang.setSelectedItem("Tại quầy");
            cboHinhThucThanhToan.setEnabled(false);
            cboTrangThai.setEnabled(false);
        } else {
            cboDonHang.setSelectedItem("Đặt hàng");

        }
        btnChoGiaoHD.setEnabled(false);
        btnDangGiao.setEnabled(false);
        btnHenGiaoLai.setEnabled(false);
        btnDaGiao.setEnabled(false);
        btnXacNhanHD.setEnabled(false);
//        btnTaoHoaDonChiTiet.setEnabled(false);
        FillHoaDonChoThanhToan();
        txtMaKH.setEnabled(false);
        txtTenKH.setEnabled(false);
        txtTongTien.setEnabled(false);
        txtTienThua.setEnabled(false);
        TxtNguoiSHip.setEnabled(false);
        txtSDT.setEnabled(false);
        txtMaHDDH.setEnabled(false);
        txtTenNV.setEnabled(false);
        txtTienThuaDatHang.setEnabled(false);
        txtTongTienDatHang.setEnabled(false);
        txtMaHDDH.setEnabled(false);
        txtTenNVDH.setEnabled(false);
        txtMaHD.setEnabled(false);
        txtGiamGia.setEnabled(false);
        txtThanhToanTQ.setEnabled(false);
    }

    private void fillToTableALLSPCT(ArrayList<SanPhamChiTiet> listspct) {
        dtmSPCT = (DefaultTableModel) tblSPCT.getModel();
        dtmSPCT.setRowCount(0);
        listspct = spctd.selectAll();
        int size = listspct.size();
        for (int i = 0; i < size; i++) {
            SanPhamChiTiet spct = listspct.get(i);
            Object[] rowData = spct.toDataRowBanHang(i + 1);
            dtmSPCT.addRow(rowData);
        }

    }

    private void fillToTableGioHang(ArrayList<SanPhamChiTiet> listspct) {
        dtmSPCT = (DefaultTableModel) tblGioHang.getModel();
        dtmSPCT.setRowCount(0);
//        row = tblSPCT.getSelectedRow();
//        String maspct = tblSPCT.getValueAt(row, 1).toString();
        listspct = spctd.selectAll();
        int size = listspct.size();
        for (int i = 0; i < size; i++) {
            SanPhamChiTiet spct = listspct.get(i);
            Object[] rowData = spct.gioHang(i + 1);
            dtmSPCT.addRow(rowData);
        }

    }

    public void setTenKH(String value) {
        txtTenKH.setText(value);
    }

    public void setMaKH(String value) {
        txtMaKH.setText(value);
    }

    void FillHoaDonChoThanhToan() {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        // DefaultTableModel modelLS = (DefaultTableModel) tbLichSu.getModel();
        model.setRowCount(0);
        ArrayList<HoaDon> lstTransactions = hds.getAllHoaDonChoThanhToan();
        if (!lstTransactions.isEmpty()) {

            for (HoaDon hoaDon : lstTransactions) {
                model.addRow(new Object[]{
                    hoaDon.getMaHoaDon(),
                    hoaDon.getNgayTao(),
                    hoaDon.getMaNV(),
                    hoaDon.getTenKH(),
                    hoaDon.getSoLuong(),
                    hoaDon.getHinhThucThanhToan(),
                    hoaDon.getTrangThai()
                });
            }
        }

    }

    void FillHoaDonDaThanhToan() {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        // DefaultTableModel modelLS = (DefaultTableModel) tbLichSu.getModel();
        model.setRowCount(0);
        ArrayList<HoaDon> lstTransactions = hds.getAllHoaDonDaThanhToan();
        if (!lstTransactions.isEmpty()) {

            for (HoaDon hoaDon : lstTransactions) {
                model.addRow(new Object[]{
                    hoaDon.getMaHoaDon(),
                    hoaDon.getNgayTao(),
                    hoaDon.getMaNV(),
                    hoaDon.getTenKH(),
                    hoaDon.getSoLuong(),
                    hoaDon.getHinhThucThanhToan(),
                    hoaDon.getTrangThai()
                });
            }
        }

    }

    void FillHoaDonChoGiaoHang() {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        // DefaultTableModel modelLS = (DefaultTableModel) tbLichSu.getModel();
        model.setRowCount(0);
        ArrayList<HoaDon> lstTransactions = hds.getAllHoaDonChoGiaoHang();
        if (!lstTransactions.isEmpty()) {

            for (HoaDon hoaDon : lstTransactions) {
                model.addRow(new Object[]{
                    hoaDon.getMaHoaDon(),
                    hoaDon.getNgayTao(),
                    hoaDon.getMaNV(),
                    hoaDon.getTenKH(),
                    hoaDon.getSoLuong(),
                    hoaDon.getHinhThucThanhToan(),
                    hoaDon.getTrangThai()
                });
            }
        }
        lblTrangThaiHoaDon.setText(tblHoaDon.getRowCount() + " Hóa đơn chờ giao");
    }

    void FillHoaDonDangGiao() {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        // DefaultTableModel modelLS = (DefaultTableModel) tbLichSu.getModel();
        model.setRowCount(0);
        ArrayList<HoaDon> lstTransactions = hds.getAllHoaDonDangGiao();
        if (!lstTransactions.isEmpty()) {

            for (HoaDon hoaDon : lstTransactions) {
                model.addRow(new Object[]{
                    hoaDon.getMaHoaDon(),
                    hoaDon.getNgayTao(),
                    hoaDon.getMaNV(),
                    hoaDon.getTenKH(),
                    hoaDon.getSoLuong(),
                    hoaDon.getHinhThucThanhToan(),
                    hoaDon.getTrangThai()
                });
            }
        }

    }

    void FillHoaDonGiaoLai() {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        // DefaultTableModel modelLS = (DefaultTableModel) tbLichSu.getModel();
        model.setRowCount(0);
        ArrayList<HoaDon> lstTransactions = hds.getAllHoaDonGiaoLai();
        if (!lstTransactions.isEmpty()) {

            for (HoaDon hoaDon : lstTransactions) {
                model.addRow(new Object[]{
                    hoaDon.getMaHoaDon(),
                    hoaDon.getNgayTao(),
                    hoaDon.getMaNV(),
                    hoaDon.getTenKH(),
                    hoaDon.getSoLuong(),
                    hoaDon.getHinhThucThanhToan(),
                    hoaDon.getTrangThai()
                });
            }
        }

    }

    void FillHoaDonDaGiao() {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        // DefaultTableModel modelLS = (DefaultTableModel) tbLichSu.getModel();
        model.setRowCount(0);
        ArrayList<HoaDon> lstTransactions = hds.getAllHoaDonDaGiao();
        if (!lstTransactions.isEmpty()) {

            for (HoaDon hoaDon : lstTransactions) {
                model.addRow(new Object[]{
                    hoaDon.getMaHoaDon(),
                    hoaDon.getNgayTao(),
                    hoaDon.getTenNV(),
                    hoaDon.getTenKH(),
                    hoaDon.getHinhThucThanhToan(),
                    hoaDon.getTrangThai()
                });
            }
        }

    }

    void FillHoaDonTaiQuay() {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        // DefaultTableModel modelLS = (DefaultTableModel) tbLichSu.getModel();
        model.setRowCount(0);
        ArrayList<HoaDon> lstTransactions = hds.getAllHoaDonTaiQuay();
        if (!lstTransactions.isEmpty()) {

            for (HoaDon hoaDon : lstTransactions) {
                model.addRow(new Object[]{
                    hoaDon.getMaHoaDon(),
                    hoaDon.getNgayTao(),
                    hoaDon.getTenNV(),
                    hoaDon.getTenKH(),
                    hoaDon.getSoLuong(),
                    hoaDon.getHinhThucThanhToan(),
                    hoaDon.getTrangThai()
                });
            }
        }

    }

    void insertHoaDonChiTiet() {

        HoaDonChiTiet hd = checkForm();
        int i = JOptionPane.showConfirmDialog(null, "Bạn có muốn thêm ?", "Trả lời", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            if (hd != null) {

                try {
                    hdcts.insertHoaDonCT(hd);
                    FillHoaDonChoThanhToan();
                    JOptionPane.showMessageDialog(null, "Thêm thành công!");
                    cboTrangThai.setSelectedItem("Tất cả");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Thêm thất bại!");
//            }
                }
            }
        } else {
            return;
        }
    }

    private HoaDon checkFormHD() {
        HoaDon hd = new HoaDon();
        Random random1 = new Random();
        int Id = 100000 + random1.nextInt(900000);;
        String maHD = "HD" + Id;
        ZonedDateTime zonedDateTime = ngayHomNay.atStartOfDay(ZoneId.systemDefault());
        java.time.Instant instant = zonedDateTime.toInstant();
        Date date = Date.from(instant);
        row = tblHoaDon.getSelectedRow();
        hd.setMaHoaDon(maHD);
        hd.setNgayTao(date);
        hd.setTenNV("Trần Công Lực");
        hd.setTenKH(txtTenKH.getText());
        hd.setTrangThai("Chờ thanh toán");
        hd.setHinhThucThanhToan(" ");
        hd.setNgayThanhToan(date);

        ArrayList<KhachHang> listkh = khsv.AllThongTinKhachHangKCoKhachBanLe();
        for (KhachHang kh : listkh) {
            if (txtMaKH.getText().equals(kh.getMaKH())) {
                hd.setId_KH(kh.getID());
            }
        };

        return hd;
    }

    private HoaDonChiTiet checkForm() {
        //        ZonedDateTime zonedDateTime = ngayHomNay.atStartOfDay(ZoneId.systemDefault());
//        java.time.Instant instant = zonedDateTime.toInstant();
//        Date dateNow = Date.from(instant);
//        row = tblHoaDon.getSelectedRow();
//        String mahd = txtMaHD.getText();
//        Date ngayTao = dateNow;
//        hd.setNgayTao(dateNow);
//        String maKH = txtMaKH.getText();
//        String httt = cboHTThanhToan.getSelectedItem().toString();
//        String tongTien = txtTongTien.getText();
        HoaDonChiTiet hdct = new HoaDonChiTiet();
        HoaDon hd = new HoaDon();
        SanPhamChiTiet spct = new SanPhamChiTiet();
        String tenNV = txtTenNV.getText();
        //lấy dữ liệu cột maSPCT
//        hdct.setSpct(spct);
        int columnIndex = 1;
        int rowCount = tblGioHang.getRowCount();
        ArrayList<String> maCTSPList = new ArrayList<>();
        for (int i = 0; i < rowCount; i++) {
            Object value = tblGioHang.getValueAt(i, columnIndex);
//            spct.setMaCTSP((String) value);
            maCTSPList.add((String) value);

        }

        //lấy dữ liệu cột số lượng
        int columnSL = 3;
        for (int i = 0; i < rowCount; i++) {
            Object value = tblGioHang.getValueAt(i, columnSL);
            hdct.setSl((int) value);
        }
        //lấy dữ liệu cột giá
        int columnGia = 2;
        for (int i = 0; i < rowCount; i++) {
            Object value = tblGioHang.getValueAt(i, columnGia);
            hdct.setGia((int) value);
        }
        hd.setMaHoaDon(txtMaHD.getText());

        hd.setTenNV(tenNV);
        hd.setTenKH(txtTenKH.getText());
        hd.setTrangThaiThanhToan("Đã thanh toán");
        hd.setHinhThucThanhToan(cboHTThanhToan.getSelectedItem().toString());

        ArrayList<KhachHang> listkh = khsv.AllThongTinKhachHang();
        for (KhachHang kh : listkh) {
            if (txtMaKH.getText().equals(kh.getMaKH())) {
                hd.setId_KH(kh.getID());
            }
        };
        ArrayList<SanPhamChiTiet> spctAll = spctd.selectAll();
//        for (SanPhamChiTiet spctt : spctAll) {
//            if (hdct.getSpct().getMaCTSP().equalsIgnoreCase(spctt.getMaCTSP())) {
//                hdct.getSpct().setId(spctt.getId());
//            }
//        }
//        for (SanPhamChiTiet spctt : spctAll) {
//            SanPhamChiTiet spctData = hdct.getSpct();
//            if (spctData != null && spctData.getMaCTSP().equalsIgnoreCase(spctt.getMaCTSP())) {
//                hdct.getSpct().setId(spctt.getId());
//            }
//        }
        return hdct;
    }

    void showSPCTToGioHang() {
        int index = 0;
        index = tblSPCT.getSelectedRow();
        row++;
        int soluong = Integer.parseInt(JOptionPane.showInputDialog(null, "Nhập số lượng"));
        int soluongton = Integer.parseInt(tblSPCT.getValueAt(index, 9).toString());
        int gia = Integer.parseInt(tblSPCT.getValueAt(index, 8).toString());
        tblGioHang.setValueAt(tblSPCT.getValueAt(index, 1), row, 0);
        tblGioHang.setValueAt(tblSPCT.getValueAt(index, 2), row, 1);
        tblGioHang.setValueAt(gia, row, 2);
        tblGioHang.setValueAt(soluong, row, 3);
        tblGioHang.setValueAt(0, row, 4);
        tblGioHang.setValueAt(gia * soluong, row, 5);
        tblSPCT.setValueAt(soluongton - soluong, index, 9);

//        row = tblHoaDon.getSelectedRow();
//        index = tblGioHang.getSelectedRow();
        txtTongTien.setText(gia * soluong + "");
    }

    void fillTableGioHang() {
        dtmGioHang = (DefaultTableModel) tblGioHang.getModel();
        dtmGioHang.setRowCount(0);
//        row = tblSPCT.getSelectedRow();
//        int soluong = Integer.parseInt(JOptionPane.showInputDialog(this, "Nhập số lượng"));
//        int soluongton = Integer.parseInt(tblSPCT.getValueAt(row, 9).toString());
//        String ma = tblSPCT.getValueAt(row, 1).toString();
//        ArrayList<HoaDonChiTiet> listHDCT = hdcts.getAllHoaDon(ma);
//        for (HoaDonChiTiet hd : listHDCT) {
//            hd.setSl(soluong);
//            dtmGioHang.addRow(new Object[]{hd.getSpct().getMaCTSP(), hd.getSpct().getSp().getTen(),
//                hd.getGia(), hd.getSl(), 0, hd.getGia() * hd.getSl(), hd.isDelete()});
//        }

    }

    void addDataGioHang() {

        dtmGioHang = (DefaultTableModel) tblGioHang.getModel();
//        listSPCT = spctd.selectAll();
        row = tblSPCT.getSelectedRow();
        int soluong = Integer.parseInt(JOptionPane.showInputDialog("Số lượng", 0));

        listHD = hds.getAllHoaDonChoThanhToan();
        indexHoaDonSelected = tblHoaDon.getSelectedRow();
        SanPhamChiTiet spct = listSPCT.get(row);
        HoaDon hd = listHD.get(indexHoaDonSelected);
        HoaDonChiTiet hdct = new HoaDonChiTiet();
        hdct.setGia(spct.getGia());
        hdct.setSl(soluong);
//        hdct.setSpct(spct);
//        hdct.setHd(hd);
        listHDCT.add(hdct);

        //tru soluong
        spct.setSoLuongTon(spct.getSoLuongTon() - soluong);
        listSPCT.set(row, spct);
        fillToTableALLSPCT(listSPCT);
        maps.put(spct, spct.getSoLuongTon());
//        FillGioHang(listHDCT);
        int rowHD = tblHoaDon.getSelectedRow();
        String maHD = tblHoaDon.getValueAt(row, 0).toString();
        int soluongton = Integer.parseInt(tblSPCT.getValueAt(row, 9).toString());
        String maspct = tblSPCT.getValueAt(row, 1).toString();
        String tenSP = tblSPCT.getValueAt(row, 2).toString();
        int gia = Integer.parseInt(tblSPCT.getValueAt(row, 8).toString());
        String giamGia = "0";
        int thanhTien = gia * soluong;
        dtmGioHang.addRow(new Object[]{maspct, tenSP, gia, soluong, giamGia, thanhTien});
        tblSPCT.setValueAt(soluongton - soluong, row, 9);
    }

    void addDataHoaDon() {
        Random random1 = new Random();
        int Id = 100000 + random1.nextInt(900000);;
        String maHD = "HD" + Id;
        int i = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn tạo?", "Trả lời", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            row = 0;
            dtmHoaDon = (DefaultTableModel) tblHoaDon.getModel();
            dtmHoaDon.addRow(new Object[]{maHD, ngayHomNay, "Trần Công Lực", "Khách bán lẻ", "Tại quầy", "Chờ thanh toán"});
            JOptionPane.showMessageDialog(null, "Tạo hóa đơn thành công");
            txtMaHD.setText(maHD);
            txtGiamGia.setText("0");
            txtTenNV.setText(tblHoaDon.getValueAt(row, 2).toString());
//            showSPCTToGioHang();
            addDataGioHang();

        } else {
            return;
        }
    }

    void tongTien() {
        int column = 5;
        int rowCount = tblGioHang.getRowCount();
        int total = 0;
        for (int j = 0; j < rowCount; j++) {
            int value = Integer.parseInt(tblGioHang.getValueAt(j, column).toString());
            total += value;
            txtTongTien.setText(total + "");
            txtTongTienDatHang.setText(total + "");
        }

    }

    void insertHoaDon() {
        HoaDon hd = checkFormHD();
        if (hd != null) {

            try {
                hds.insertHoaDon(hd);

                JOptionPane.showMessageDialog(null, "Thêm thành công!");
                cboTrangThai.setSelectedItem("Tất cả");
                FillHoaDonChoThanhToan();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Thêm thất bại!");
//            }
            }
        }

    }

    void FillGioHang(ArrayList<HoaDonChiTiet> listHDCT) {
        dtmGioHang = (DefaultTableModel) tblGioHang.getModel();
        // DefaultTableModel modelLS = (DefaultTableModel) tbLichSu.getModel();
        dtmGioHang.setRowCount(0);
        int index = tblHoaDon.getSelectedRow();

        if (index == -1) {

            return;
        }
        int STT = 1;
        String ma = tblHoaDon.getValueAt(index, 0).toString();
        listHDCT = hdcts.getAllHoaDonChiTietMaHD(ma);
        for (HoaDonChiTiet hd : listHDCT) {
            dtmGioHang.addRow(new Object[]{
                STT++,
                hd.getMaSPCT(),
                hd.getTenSP(),
                hd.getGia(),
                hd.getSl(),
                hd.getGia() * hd.getSl(),
                hd.isDelete()
            });
        }

    }

    void tichAll() {
        indext = tblHoaDon.getSelectedRow();
        listHD = hds.getAllHoaDonChoThanhToan();
        HoaDon hd = listHD.get(indext);
        hdcts.deleteSPInGioHang(hd.getId());
        if (chkTatCa.isSelected()) {

        }

    }

    void clearForm() {
        txtMaHD.setText("");
        txtMaHDDH.setText("");
        txtDiaChi.setText("");
//        ngayMuonNhan.setDateFormatString(" ");
        txtTienKhachDuaDH.setText("");
        txtTenNV.setText("");
        txtTongTien.setText("");
        txtTienKhachDua.setText("");
        txtTienThua.setText("");
        dtmGioHang.setRowCount(0);
        TxtNguoiSHip.setText("");
        TxtNguoiSHip.setText("");
        txtSDT.setText("");
        txtSDTNguoiNhan.setText("");
        txtTienThuaDatHang.setText("");
        txtTongTienDatHang.setText("");
        txtTienShip.setText("");
        txtThanhToan.setText("");
        dtmGioHang.setRowCount(0);
    }

    void addHDCT() {
        try {

            row = tblSPCT.getSelectedRow();
            SanPhamChiTiet spct = listSPCT.get(row);
            listHD = hds.getAllHoaDonChoThanhToan();
            indexHoaDonSelected = tblHoaDon.getSelectedRow();
            if (indexHoaDonSelected < 0) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn hóa đơn bạn muốn nhập sản phẩm!");
                return;
            }
            HoaDon hd = listHD.get(indexHoaDonSelected);
            String sl = JOptionPane.showInputDialog("Số lượng", 0);
            try {
                int soluong = Integer.parseInt(sl);

                if (soluong <= 0) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập số lượng là dương!");
                    return;
                }

                // Thực hiện các xử lý khác khi số lượng hợp lệ
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập số lượng là một số nguyên!");
                return;
            }
            HoaDonChiTiet hdct = new HoaDonChiTiet();
            hdct.setGia(spct.getGia());
            hdct.setSl(Integer.parseInt(sl));
            hdct.setID_spct(spct.getId());
            hdct.setID_hd(hd.getId());
            listHDCT.add(hdct);//tru soluong

            int slt = Integer.parseInt(tblSPCT.getValueAt(row, 9).toString());
            row = tblSPCT.getSelectedRow();
            String maSPCT = tblSPCT.getValueAt(row, 1).toString();
            listSPCT = spctd.selectAllSPCTTheoMa(maSPCT);
            for (SanPhamChiTiet spctSL : listSPCT) {
                SanPhamChiTiet sltSPCT = new SanPhamChiTiet();

                if (Integer.parseInt(sl) == slt) {
//                spctSL.setSoLuongTon(slt - soluong);
//
//                spctd.UpdateSPCT(spctSL);
//                hdcts.insertHoaDonCT(hdct);
//                FillGioHang(listHDCT);
                    if (dtmGioHang.getRowCount() == 0) {
                        spctSL.setSoLuongTon(slt - Integer.parseInt(sl));
                        spctSL.setTrangThai("Hết hàng");
                        spctSL.setDeleted(false);
                        spctd.UpdateSPCT(spctSL);
                        hdcts.insertHoaDonCT(hdct);
                        FillGioHang(listHDCT);
                    } else {
                        int index = tblHoaDon.getSelectedRow();
                        String ma = tblHoaDon.getValueAt(index, 0).toString();
                        listHDCT = hdcts.getAllHoaDonChiTietMaHD(ma);

                        boolean foundMatchingCondition = false;

                        for (HoaDonChiTiet hdct1 : listHDCT) {
                            for (SanPhamChiTiet sanPhamChiTiet : listSPCT) {
                                if (hdct1.getID_spct() == sanPhamChiTiet.getId()) {
                                    hdct1.setSl(Integer.parseInt(sl));
                                    hdct1.setID_spct(sanPhamChiTiet.getId());
                                    hdcts.updateSLHDCT(hdct1);
                                    spctSL.setSoLuongTon(slt - Integer.parseInt(sl));
                                    spctSL.setTrangThai("Hết hàng!");
                                    spctSL.setDeleted(true);
                                    spctd.UpdateSPCT(spctSL);
                                    foundMatchingCondition = true;
                                    break;
                                }
                            }

                            if (foundMatchingCondition) {
                                break;
                            }
                        }

                        if (!foundMatchingCondition) {
                            spctSL.setSoLuongTon(slt - Integer.parseInt(sl));
                            spctd.UpdateSPCT(spctSL);
                            hdcts.insertHoaDonCT(hdct);
                        }

                        FillGioHang(listHDCT);
                    }
                } else if (Integer.parseInt(sl) > slt) {
                    JOptionPane.showMessageDialog(null, "Số lượng trong kho không đủ!");
                } else if (Integer.parseInt(sl) < slt) {

                    if (dtmGioHang.getRowCount() == 0) {
                        spctSL.setSoLuongTon(slt - Integer.parseInt(sl));
                        spctd.UpdateSPCT(spctSL);
                        hdcts.insertHoaDonCT(hdct);
                        FillGioHang(listHDCT);
                    } else {
                        int index = tblHoaDon.getSelectedRow();
                        String ma = tblHoaDon.getValueAt(index, 0).toString();
                        listHDCT = hdcts.getAllHoaDonChiTietMaHD(ma);

                        boolean foundMatchingCondition = false;
                        for (HoaDonChiTiet hdct1 : listHDCT) {
                            for (SanPhamChiTiet sanPhamChiTiet : listSPCT) {
                                if (hdct1.getID_spct() == sanPhamChiTiet.getId()) {
                                    hdct1.setSl(Integer.parseInt(sl));
                                    hdct1.setID_spct(sanPhamChiTiet.getId());
                                    hdcts.updateSLHDCT(hdct1);
                                    spctSL.setSoLuongTon(slt - Integer.parseInt(sl));
                                    spctd.UpdateSPCT(spctSL);
                                    foundMatchingCondition = true;
                                    break;
                                }
                            }

                            if (foundMatchingCondition) {
                                break;
                            }
                        }

                        if (!foundMatchingCondition) {
                            spctSL.setSoLuongTon(slt - Integer.parseInt(sl));
                            spctd.UpdateSPCT(spctSL);
                            hdcts.insertHoaDonCT(hdct);
                        }

                        FillGioHang(listHDCT);

                    }
                }

            }
            FillHoaDonChoThanhToan();
            tblHoaDon.setRowSelectionInterval(indexHoaDonSelected, indexHoaDonSelected);
            tblHoaDon.scrollRectToVisible(tblHoaDon.getCellRect(indexHoaDonSelected, 0, true));
            int a = tblHoaDon.getSelectedRow();
            if (cboDonHang.getSelectedItem().equals("Tại quầy")) {
                if (Integer.parseInt(tblGioHang.getValueAt(a, 4).toString()) > 0) {
                    useVoucher();
                }
            } else if (cboDonHang.getSelectedItem().equals("Đặt hàng")) {
                if (Integer.parseInt(tblGioHang.getValueAt(a, 4).toString()) > 0) {
                    useVoucherDatHang();
                }
            }
            listSPCT = spctd.selectAll();
            fillToTableALLSPCT(listSPCT);

        } catch (Exception e) {
        }
    }

    void addHDCTQuetQR() {
        row = tblSPCT.getSelectedRow();
        SanPhamChiTiet spct = listSPCT.get(row);
        listHD = hds.getAllHoaDonChoThanhToan();
        indexHoaDonSelected = tblHoaDon.getSelectedRow();
//        if (indexHoaDonSelected < 0) {
//            JOptionPane.showMessageDialog(null, "Vui lòng chọn hóa đơn bạn muốn nhập sản phẩm!");
//            return;
//        }
        HoaDon hd = listHD.get(indexHoaDonSelected);
        String sl = JOptionPane.showInputDialog("Số lượng", 0);
        try {
            int soluong = Integer.parseInt(sl);

            if (soluong <= 0) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập số lượng là dương!");
                return;
            }

            // Thực hiện các xử lý khác khi số lượng hợp lệ
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập số lượng là một số nguyên!");
            return;
        }
        HoaDonChiTiet hdct = new HoaDonChiTiet();
        hdct.setGia(spct.getGia());
        hdct.setSl(Integer.parseInt(sl));
        hdct.setID_spct(spct.getId());
        hdct.setID_hd(hd.getId());
        listHDCT.add(hdct);//tru soluong

        int slt = Integer.parseInt(tblSPCT.getValueAt(row, 9).toString());
        row = tblSPCT.getSelectedRow();
        String maSPCT = tblSPCT.getValueAt(row, 1).toString();
        listSPCT = spctd.selectAllSPCTTheoMa(maSPCT);
        for (SanPhamChiTiet spctSL : listSPCT) {
            SanPhamChiTiet sltSPCT = new SanPhamChiTiet();

            if (Integer.parseInt(sl) == slt) {
//                spctSL.setSoLuongTon(slt - soluong);
//
//                spctd.UpdateSPCT(spctSL);
//                hdcts.insertHoaDonCT(hdct);
//                FillGioHang(listHDCT);
                if (dtmGioHang.getRowCount() == 0) {
                    spctSL.setSoLuongTon(slt - Integer.parseInt(sl));
                    spctSL.setTrangThai("Hết hàng");
                    spctSL.setDeleted(false);
                    spctd.UpdateSPCT(spctSL);
                    hdcts.insertHoaDonCT(hdct);
                    FillGioHang(listHDCT);
                } else {
                    int index = tblHoaDon.getSelectedRow();
                    String ma = tblHoaDon.getValueAt(index, 0).toString();
                    listHDCT = hdcts.getAllHoaDonChiTietMaHD(ma);

                    boolean foundMatchingCondition = false;

                    for (HoaDonChiTiet hdct1 : listHDCT) {
                        for (SanPhamChiTiet sanPhamChiTiet : listSPCT) {
                            if (hdct1.getID_spct() == sanPhamChiTiet.getId()) {
                                hdct1.setSl(Integer.parseInt(sl));
                                hdct1.setID_spct(sanPhamChiTiet.getId());
                                hdcts.updateSLHDCT(hdct1);
                                spctSL.setSoLuongTon(slt - Integer.parseInt(sl));
                                spctSL.setTrangThai("Hết hàng!");
                                spctSL.setDeleted(true);
                                spctd.UpdateSPCT(spctSL);
                                foundMatchingCondition = true;
                                break;
                            }
                        }

                        if (foundMatchingCondition) {
                            break;
                        }
                    }

                    if (!foundMatchingCondition) {
                        spctSL.setSoLuongTon(slt - Integer.parseInt(sl));
                        spctd.UpdateSPCT(spctSL);
                        hdcts.insertHoaDonCT(hdct);
                    }

                    FillGioHang(listHDCT);
                }
            } else if (Integer.parseInt(sl) > slt) {
                JOptionPane.showMessageDialog(null, "Số lượng trong kho không đủ!");
            } else if (Integer.parseInt(sl) < slt) {

                if (dtmGioHang.getRowCount() == 0) {
                    spctSL.setSoLuongTon(slt - Integer.parseInt(sl));
                    spctd.UpdateSPCT(spctSL);
                    hdcts.insertHoaDonCT(hdct);
                    FillGioHang(listHDCT);
                } else {
                    int index = tblHoaDon.getSelectedRow();
                    String ma = tblHoaDon.getValueAt(index, 0).toString();
                    listHDCT = hdcts.getAllHoaDonChiTietMaHD(ma);

                    boolean foundMatchingCondition = false;
                    for (HoaDonChiTiet hdct1 : listHDCT) {
                        for (SanPhamChiTiet sanPhamChiTiet : listSPCT) {
                            if (hdct1.getID_spct() == sanPhamChiTiet.getId()) {
                                hdct1.setSl(Integer.parseInt(sl));
                                hdct1.setID_spct(sanPhamChiTiet.getId());
                                hdcts.updateSLHDCT(hdct1);
                                spctSL.setSoLuongTon(slt - Integer.parseInt(sl));
                                spctd.UpdateSPCT(spctSL);
                                foundMatchingCondition = true;
                                break;
                            }
                        }

                        if (foundMatchingCondition) {
                            break;
                        }
                    }

                    if (!foundMatchingCondition) {
                        spctSL.setSoLuongTon(slt - Integer.parseInt(sl));
                        spctd.UpdateSPCT(spctSL);
                        hdcts.insertHoaDonCT(hdct);
                    }

                    FillGioHang(listHDCT);

                }
            }

        }

        listSPCT = spctd.selectAll();
        fillToTableALLSPCT(listSPCT);
        FillHoaDonChoThanhToan();
        tblHoaDon.setRowSelectionInterval(indexHoaDonSelected, indexHoaDonSelected);
        tblSPCT.scrollRectToVisible(tblSPCT.getCellRect(indexHoaDonSelected, 0, true));
    }

    HoaDon checkFormHDDatHang() {

        String trangThaiTT = cboTrangThaiTT.getSelectedItem().toString();
        String hinhThucTT = cboHTTT.getSelectedItem().toString();
        String tenKH = txtNguoiNhan.getText();
        String sdt = txtSDTNguoiNhan.getText();
        String thanhToan = txtThanhToan.getText();
        String tienThua = txtTienThuaDatHang.getText();
        String diaChi = txtDiaChi.getText();
        String maHD = txtMaHDDH.getText();

        String nguoiShip = TxtNguoiSHip.getText();
        String sdtNguoiShip = txtSDT.getText();
        int tHInt = Integer.parseInt(tienThua);
        if (tHInt < 0) {
            JOptionPane.showMessageDialog(null, "Xảy ra lỗi!");
            return null;
        }
        HoaDon hd = new HoaDon();

        hd.setMaHoaDon(maHD);

        hd.setTrangThaiThanhToan(trangThaiTT);
        hd.setHinhThucThanhToan(hinhThucTT);
        hd.setTenKH(tenKH);
        hd.setSdt(sdt);
        hd.setDiaChi(diaChi);

        hd.setTenNguoiShip(nguoiShip);
        hd.setSdtNGuoiShip(sdtNguoiShip);
        return hd;
    }

    void thanhToanDatHang() {
        try {
            String tienShip = txtTienShip.getText();
            String tienKhach = txtTienKhachDuaDH.getText();
            String ngayNhan = ngayMuonNhan.getDateFormatString();
//            Date ngayNhanDate = ngayMuonNhan.getDate();
//            String diaCHi = txtDiaChi.getText();
//            if (tienKhach.isEmpty() || tienShip.isEmpty() || ngayNhan.isEmpty() || diaCHi.isEmpty()) {
//                JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin");
//                return;
//            }
//            Date ngayGioHienTai = new Date();
//            if(ngayNhan < ngayGioHienTai){
//                
//            }
            Date ngayNhanDate = ngayMuonNhan.getDate();
            String diaChi = txtDiaChi.getText();

            if (tienKhach.isEmpty() || tienShip.isEmpty() || ngayNhanDate == null || diaChi.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin");
                return;
            }

            Date ngayGioHienTai = new Date();
            int soSanh = ngayNhanDate.compareTo(ngayGioHienTai);

            if (soSanh < 0) {
                JOptionPane.showMessageDialog(null, "Vui lòng điền ngày nhận lớn hơn bằng ngày hiện tại!");
                return;
            }
            int tongtien = Integer.parseInt(tienKhach) + Integer.parseInt(txtTienThuaDatHang.getText());
            if (tongtien < Integer.parseInt(txtThanhToan.getText())) {
                JOptionPane.showMessageDialog(null, "Khách đưa thiếu tiền!");
                return;
            }
            HoaDon hd = checkFormHDDatHang();

            String phiShip = txtTienShip.getText();
            hd.setPhiShip(Integer.parseInt(phiShip));
            String tienKhachDua = txtTienKhachDuaDH.getText();
            Date ngayMuonNhan = this.ngayMuonNhan.getDate();
            hd.setNgayMuonNhan(ngayMuonNhan);
            hd.setTrangThaiThanhToan(cboTrangThaiTT.getSelectedItem().toString());
            hd.setHinhThucThanhToan(cboHTTT.getSelectedItem().toString());
            String tongTien = txtTongTien.getText();
            hd.setTongTien(Integer.parseInt(tongTien));
            String nguoiNhan = txtNguoiNhan.getText();
            hd.setTenKH(nguoiNhan);
            String sdt = txtSDTNguoiNhan.getText();
            hd.setSdt(sdt);
            String nguoiShip = TxtNguoiSHip.getText();
            hd.setTenNguoiShip(nguoiShip);
            String sdtNguoiShip = txtSDT.getText();
            hd.setSdtNGuoiShip(sdtNguoiShip);
            //Tạo để lấy nhân viên có mã NV như txtMaNV
            String maKH = txtMaKH.getText();
            listKH = khs.AllThongTinKhachHangTheoMa(maKH);
            for (KhachHang kh : listKH) {
                hd.setId_KH(kh.getID());
            }
            String maNV = lblMaNV.getText();
            listNV = nvs.getAllEmTheoMa(maNV);
            for (NhanVien nv : listNV) {
                hd.setId_NV(nv.getID());
            }
            hd.setTienGiamGia(Integer.parseInt(txtGiamGiaDH.getText()));
            KhuyenMai vc = (KhuyenMai) cboVoucherDatHang.getSelectedItem();
            hd.setId_VC(vc.getId());
            if (hd != null) {
                int i = JOptionPane.showConfirmDialog(null, "Bạn có muốn thanh toán ?", "Trả lời", JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    try {
                        if (cboTrangThaiTT.getSelectedItem().equals("Thanh toán khi nhận")) {
                            hd.setTrangThai("Chờ giao hàng");
                            cboTrangThai.setSelectedItem("Chờ giao hàng");

                        } else {
                            hd.setTrangThai("Đã thanh toán");
                            cboTrangThai.setSelectedItem("Đã thanh toán");

                        }
                        hds.updateDatHang(hd);

                        JOptionPane.showMessageDialog(null, "Thanh toán thành công!");
                        if (cboTrangThai.getSelectedItem().equals("Thanh toán khi nhận")) {

                            FillHoaDonChoThanhToan();
                        } else {

                            FillHoaDonDaThanhToan();
                        }

                        btnXacNhanHD.setEnabled(false);

                        clearForm();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Thanh toán thất bại!");
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin!");
        }
    }

    void thanhToanDatHangThanhToanSau() {
        try {
            String tienShip = txtTienShip.getText();
            String ngayNhan = ngayMuonNhan.getDateFormatString();
            String diaCHi = txtDiaChi.getText();
            if (tienShip.isEmpty() || ngayNhan.isEmpty() || diaCHi.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin");
                return;
            }

            HoaDon hd = new HoaDon();

            String phiShip = txtTienShip.getText();
            hd.setPhiShip(Integer.parseInt(phiShip));

            Date ngayMuonNhan = this.ngayMuonNhan.getDate();
            hd.setNgayMuonNhan(ngayMuonNhan);
            hd.setTrangThaiThanhToan(cboTrangThaiTT.getSelectedItem().toString());
            hd.setHinhThucThanhToan(cboHTTT.getSelectedItem().toString());
            String tongTien = txtTongTien.getText();
            hd.setTongTien(Integer.parseInt(tongTien));
            String nguoiNhan = txtNguoiNhan.getText();
            hd.setTenKH(nguoiNhan);
            String sdt = txtSDTNguoiNhan.getText();
            hd.setSdt(sdt);
            if (hd != null) {
                int i = JOptionPane.showConfirmDialog(null, "Bạn có muốn thêm ?", "Trả lời", JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    try {
                        if (cboTrangThaiTT.getSelectedItem().equals("Thanh toán khi nhận")) {
                            hd.setTrangThai("Chờ giao hàng");
                            cboTrangThai.setSelectedItem("Chờ giao hàng");

                        } else {
                            hd.setTrangThai("Đã thanh toán");
                            cboTrangThai.setSelectedItem("Đã thanh toán");

                        }
                        hds.updateDatHang(hd);

                        JOptionPane.showMessageDialog(null, "Thêm thành công!");
                        if (cboTrangThai.getSelectedItem().equals("Thanh toán khi nhận")) {

                            FillHoaDonChoThanhToan();
                        } else {

                            FillHoaDonDaThanhToan();
                        }

                        btnXacNhanHD.setEnabled(false);

                        clearForm();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Thêm thất bại!");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void showDataBanHang() {
        row = tblHoaDon.getSelectedRow();
        String maHD = tblHoaDon.getValueAt(row, 0).toString();
        ArrayList<HoaDon> listHd = hds.getAllHoaDonTheoMa(maHD);
        for (HoaDon hd : listHd) {
//            if (row >= 0 && row < tblHoaDon.getRowCount()) {
            txtNguoiNhan.setText(hd.getTenKH());
            txtSDTNguoiNhan.setText(hd.getSdt());
            txtTongTienDatHang.setText(String.valueOf(hd.getTongTien()));
            txtTienShip.setText(String.valueOf(hd.getPhiShip()));
            txtDiaChi.setText(hd.getDiaChi());
            ngayMuonNhan.setDate(hd.getNgayMuonNhan());
            txtSDT.setText(hd.getSdtNGuoiShip());
            cboTrangThaiTT.setSelectedItem(hd.getTrangThaiThanhToan());
            cboHinhThucThanhToan.setSelectedItem(hd.getTrangThaiThanhToan());
            cboHTTT.setSelectedItem(hd.getHinhThucThanhToan());
            cboVoucherDatHang.setSelectedItem(hd.getTenVC());
            txtTienKhachDuaDH.setText(String.valueOf(hd.getTienKhachDua()));
            txtThanhToan.setText(Integer.parseInt(txtTongTienDatHang.getText()) + Integer.parseInt(txtTienShip.getText()) + "");
            TxtNguoiSHip.setText(hd.getTenNV());
            txtSDT.setText(hd.getSdtNGuoiShip());

            tongTien();
            if (CboVoucherTaiQuay == null) {
                txtGiamGia.setText("0");
                txtThanhToanTQ.setText(txtTongTien.getText());
                return;
            }
            if (cboVoucherDatHang == null) {
                txtGiamGiaDH.setText("0");
                txtThanhToan.setText(txtTongTienDatHang.getText());
                return;
            }
//                System.out.println(hd.getNgayMuonNhan());
//            } else {
//                return;
//            }
        }

    }

    void fillComBoVoucherTaiQuay() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) CboVoucherTaiQuay.getModel();
        model.removeAllElements();
        ArrayList<KhuyenMai> list = vs.getAllKhuyenMaiDangDienRa();
        for (KhuyenMai khuyenMai : list) {
            model.addElement(khuyenMai);
        }
    }

    void fillComBoVoucherDatHang() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboVoucherDatHang.getModel();
        model.removeAllElements();
        ArrayList<KhuyenMai> list = vs.getAllKhuyenMaiDangDienRa();
        for (KhuyenMai khuyenMai : list) {
            model.addElement(khuyenMai);
        }
    }

    void useVoucher() {
        try {
            tongTien();
            ArrayList<KhuyenMai> list = vs.getAllKhuyenMaiDangDienRa();
            int tongTien = Integer.parseInt(txtTongTien.getText());
            for (KhuyenMai km : list) {
                if (tongTien >= km.getGiaTriDHToiThieu()) {
                    DefaultComboBoxModel model = (DefaultComboBoxModel) CboVoucherTaiQuay.getModel();
                    model.removeAllElements();
                    list = vs.getAllVoucherBanHang(tongTien);
                    for (KhuyenMai khuyenMai : list) {
                        model.addElement(khuyenMai);
                    }
                    KhuyenMai selectedValue = (KhuyenMai) CboVoucherTaiQuay.getSelectedItem();
                    String tongTien1 = txtTongTien.getText();
                    if (selectedValue != null) {

                        if (tongTien1 != null) {
                            if (selectedValue.getHinhThucGiamGia().equalsIgnoreCase("Giảm theo số tiền")) {
                                int tongTienInt = Integer.parseInt(tongTien1);
                                txtGiamGia.setText(String.valueOf(selectedValue.getMucGiamGia()));
                                txtThanhToanTQ.setText(String.valueOf(tongTienInt - Integer.parseInt(selectedValue.getMucGiamGia())));
                            }
                            if (selectedValue.getHinhThucGiamGia().equalsIgnoreCase("Giảm theo %")) {
                                int tongTienInt = Integer.parseInt(tongTien1);
                                int phanTram = Integer.parseInt(String.valueOf(selectedValue.getMucGiamGia()));
                                int tienGiam = tongTienInt * phanTram / 100;
                                txtGiamGia.setText(String.valueOf(tienGiam));
                                txtThanhToanTQ.setText(String.valueOf(tongTienInt - tienGiam));
                            }
                        }
                    }
                } else {
                    txtGiamGia.setText("0");
                    txtThanhToanTQ.setText(txtTongTien.getText());
                }
            }
        } catch (Exception e) {
        }
    }

    void useVoucherDatHang() {
        ArrayList<KhuyenMai> list = vs.getAllKhuyenMaiDangDienRa();
        int tongTien = Integer.parseInt(txtTongTienDatHang.getText());
        for (KhuyenMai km : list) {
            if (tongTien >= km.getGiaTriDHToiThieu()) {
                DefaultComboBoxModel md = (DefaultComboBoxModel) cboVoucherDatHang.getModel();
                md.removeAllElements();
                list = vs.getAllVoucherBanHang(tongTien);
                for (KhuyenMai khuyenMai : list) {
                    md.addElement(khuyenMai);
                }
                KhuyenMai selectedValue = (KhuyenMai) cboVoucherDatHang.getSelectedItem();
                String tongTien1 = txtTongTienDatHang.getText();
                if (selectedValue != null) {

                    if (tongTien1 != null) {
                        if (selectedValue.getHinhThucGiamGia().equalsIgnoreCase("Giảm theo số tiền")) {
                            int tongTienInt = Integer.parseInt(tongTien1);
                            txtGiamGiaDH.setText(String.valueOf(selectedValue.getMucGiamGia()));
//                            txtThanhToan.setText(String.valueOf(tongTienInt - Integer.parseInt(selectedValue.getMucGiamGia())));
                        }
                        if (selectedValue.getHinhThucGiamGia().equalsIgnoreCase("Giảm theo %")) {
                            int tongTienInt = Integer.parseInt(tongTien1);
                            int phanTram = Integer.parseInt(String.valueOf(selectedValue.getMucGiamGia()));
                            int tienGiam = tongTienInt * phanTram / 100;
                            txtGiamGiaDH.setText(String.valueOf(tienGiam));
//                            txtThanhToanTQ.setText(String.valueOf(tongTienInt - tienGiam));
                        }
                    }
                }
            } else {
                txtGiamGia.setText("0");

            }
        }
    }

    void fillComboDatHang() {
        int tongTien = Integer.parseInt(txtTongTienDatHang.getText());
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboVoucherDatHang.getModel();
        model.removeAllElements();
        ArrayList<KhuyenMai> list = vs.getAllVoucherBanHang(tongTien);
        for (KhuyenMai khuyenMai : list) {
            model.addElement(khuyenMai);
        }
    }
}
