/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package poly.edu.Panels;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import java.awt.Dimension;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import poly.edu.Model.ChatLieu;
import poly.edu.Model.HangGiay;
import poly.edu.Model.MauSac;
import poly.edu.Model.SanPham;
import poly.edu.Model.SanPhamChiTiet;
import poly.edu.Model.Size;
import poly.edu.service.ChatLieuDao;
import poly.edu.service.DeGiayDao;
import poly.edu.service.HangDao;
import poly.edu.service.MauSacDao;
import poly.edu.service.SanPhamChiTietDao;
import poly.edu.service.SanPhamDao;
import poly.edu.service.SizeDao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import javax.swing.JComboBox;
import net.glxn.qrgen.image.ImageType;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import poly.edu.Model.DeGiay;
import poly.edu.Model.TableAcTionEvent;
import poly.edu.Model.TableActionCellWatch;
import poly.edu.Model.TableXemSPCT;
import poly.edu.uitility.DBcontext;

/**
 *
 * @author Admin
 */
public class SanPhamForm extends javax.swing.JPanel implements Runnable, ThreadFactory {

    ArrayList<SanPhamChiTiet> listSPCT = new ArrayList<>();
    ArrayList<Size> listSize = new ArrayList<>();
    ArrayList<ChatLieu> listCL = new ArrayList<>();
    ArrayList<MauSac> listMau = new ArrayList<>();
    ArrayList<HangGiay> listHang = new ArrayList<>();
    SanPhamChiTietDao spctd = new SanPhamChiTietDao();
    ArrayList<SanPham> listSP = new ArrayList<>();
    ArrayList<DeGiay> listDG = new ArrayList<>();
    DefaultTableModel dtm = new DefaultTableModel();
    DefaultTableModel dtm1 = new DefaultTableModel();
    DefaultTableModel dtmThuocTinh = new DefaultTableModel();
    SanPhamDao spd = new SanPhamDao();
    ChatLieuDao cld = new ChatLieuDao();
    DeGiayDao dgd = new DeGiayDao();
    HangDao hgd = new HangDao();
    MauSacDao msd = new MauSacDao();
    SizeDao sd = new SizeDao();
    int row;
    int count, soTrang, trang = 1;
    private WebcamPanel panel = null;
    private Webcam webcam = null;
    private static final long serialVersionUID = 6441489157408381878L;
    private Executor executor = Executors.newSingleThreadExecutor(this);

    /**
     * Creates new form SanPhamForm
     */
    public SanPhamForm() {
        initComponents();
        setOpaque(false);
        xemCTSP();
        dtm1 = (DefaultTableModel) tblChiTietSanPham7.getModel();
        dtm = (DefaultTableModel) tblDSSP.getModel();
        dtmThuocTinh = (DefaultTableModel) tblThuocTinh.getModel();

        init();
        AddUpdateSPCT.setVisible(false);
        Random random1 = new Random();
        int Id = 100000 + random1.nextInt(900000);;
        String maCTSP = "CTSP" + Id;
        if (txtMaCTSP != null) {
            txtMaCTSP.setEnabled(false);
            txtMaCTSP.setText(maCTSP);
        }
    }

    public void xemCTSP() {
        TableAcTionEvent event = new TableAcTionEvent() {
            @Override
            public void onView(int row) {

                XemSPCT xem = new XemSPCT(jFrame1, true);
                String ten = getTenSP();
                String maspct = getMaSPCT();
                String ms = getMS();
                String kt = getKichThuoc();
                String cl = getChatLieu();
                String hang = getHang();
                String dg = getDeGiay();
                String gia = getGia();
                String mota = getMoTa();
                String slt = getSLTon();

                xem.setTenSP(ten);
                xem.setMaSPCT(maspct);
                xem.setSLTon(slt);
                xem.setMS(ms);
                xem.setKichThuoc(kt);
                xem.setChatLieu(cl);
                xem.setHang(hang);
                xem.setDeGiay(dg);
                xem.setGia(gia);
                xem.setMoTa(mota);
                xem.setVisible(true);
            }

            @Override
            public void edit(int row) {
                AddUpdateSPCT.setVisible(true);
                tab.setVisible(false);
                showdataCTSP();
                cboTenSp.setEnabled(false);
                btnThemCTSP.setEnabled(false);
                cboMauSac.setEnabled(false);
                cboKichThuoc.setEnabled(false);
                cboChatLieu.setEnabled(false);
                cboHang.setEnabled(false);
                cboDe.setEnabled(false);
            }

        };
        tblChiTietSanPham7.getColumnModel().getColumn(13).setCellRenderer(new TableXemSPCT());
        tblChiTietSanPham7.getColumnModel().getColumn(13).setCellEditor(new TableActionCellWatch(event));
    }

    private void fillTableDeGiay(ArrayList<DeGiay> listDG) {
        dtmThuocTinh.setRowCount(0);
        listDG = dgd.selectAll();
        int s = listDG.size();
        for (int i = 0; i < s; i++) {
            DeGiay size = listDG.get(i);
            dtmThuocTinh.addRow(size.toDataRow(i + 1));
        }
    }

    private void fillTableChatLieu(ArrayList<ChatLieu> listCL) {
        dtmThuocTinh.setRowCount(0);
        listCL = cld.selectAll();
        int s = listCL.size();
        for (int i = 0; i < s; i++) {
            ChatLieu size = listCL.get(i);
            dtmThuocTinh.addRow(size.toDataRow(i + 1));
        }
    }

    private void loadDataChatLieu(int page) {
        int litmit = 3;
        int offset = (page - 1) * litmit;
        try {
            int rowCount = cld.countChatLieu();
            int tong = (int) Math.ceil(rowCount / litmit);
            fillTableChatLieu(cld.selectAll(offset, litmit));
//            PhanTrangChatLieu.setPagegination(page, tong);
        } catch (Exception e) {

        }

    }

    private void loadDataSP(int page) {
        int litmit = 5;
        int offset = (page - 1) * litmit;
        try {
            int rowCount = spd.countSP();
            int tong = (int) Math.ceil(rowCount / litmit);
            fillTable(spd.selectAll(offset, litmit));
        } catch (Exception e) {

        }
    }

    private void fillTableSize(ArrayList<Size> listSize) {
        dtmThuocTinh.setRowCount(0);
        listSize = sd.selectAll();
        int s = listSize.size();
        for (int i = 0; i < s; i++) {
            Size size = listSize.get(i);
            dtmThuocTinh.addRow(size.toDataRow(i + 1));
        }

    }

    private void fillTableHang(ArrayList<HangGiay> listHang) {
        dtmThuocTinh.setRowCount(0);

        listHang = hgd.selectAll();
        int s = listHang.size();
        for (int i = 0; i < s; i++) {
            HangGiay size = listHang.get(i);
            dtmThuocTinh.addRow(size.toDataRow(i + 1));
        }

    }

    private void fillTableMau(ArrayList<MauSac> listMau) {
        dtmThuocTinh.setRowCount(0);
        listMau = msd.selectAll();
        for (int i = 0; i < listMau.size(); i++) {
            MauSac ms = listMau.get(i);
            dtmThuocTinh.addRow(ms.toDataRow(i + 1));
        }
    }

    private void showDataThuocTinh() {
        row = tblThuocTinh.getSelectedRow();
        txtMaThuocTinh.setText(tblThuocTinh.getValueAt(row, 1).toString());
        txtTenThuocTinh.setText(tblThuocTinh.getValueAt(row, 2).toString());
    }

    SanPham checkForm() {
        SanPham sp = new SanPham();
        String maSP = txtMaSanPham.getText();
        String tenSP = txtTenSanPham.getText();
        Date dateNow = Calendar.getInstance().getTime();
        sp.setMaSP(maSP);
        sp.setTen(tenSP);
        sp.setCreateAt(dateNow);
        sp.setUpdateAt(dateNow);
        if (tenSP.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin");
            return null;
        }
        ArrayList<SanPham> listSp = spd.selectAll();
        for (SanPham sanPham : listSp) {
            if (tenSP.equalsIgnoreCase(sanPham.getTen())) {
                JOptionPane.showMessageDialog(null, "Đã tồn tại");
                return null;
            }
        }

        return sp;
    }

    Size CheckFormSize() {
        Size size = new Size();

        String tenThuocTinh = txtTenThuocTinh.getText();
        if (tenThuocTinh.equals("")) {
            JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin");
            return null;
        }

        int sizeInt;
        try {
            sizeInt = Integer.parseInt(tenThuocTinh);
            if (sizeInt <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Vui lòng điền size số nguyên dương");
            return null;
        }

        ArrayList<Size> listCL = sd.selectAll();
        for (Size size1 : listCL) {
            if (sizeInt == size1.getSize()) {
                JOptionPane.showMessageDialog(null, "Đã tồn tại");

                return null;
            }
//            size.setId(size1.getId());
        }

        Date dateNow = Calendar.getInstance().getTime();
        size.setSize(sizeInt);
        size.setUpdateAt(dateNow);
        size.setCreateAt(dateNow);
        return size;
    }

    ChatLieu CheckFormChatLieu() {
        ChatLieu cl = new ChatLieu();

        String tenThuocTinh = txtTenThuocTinh.getText();
        ArrayList<ChatLieu> listCL = new ArrayList<>();
        listCL = cld.selectAll();
        for (ChatLieu chatLieu : listCL) {
            if (tenThuocTinh.equalsIgnoreCase(chatLieu.getTenChatLieu())) {
                JOptionPane.showMessageDialog(null, "Đã tồn tại");
                return null;
            }
        }
        if (tenThuocTinh.equals("")) {
            JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin");
            return null;
        }
        Date dateNow = Calendar.getInstance().getTime();
        cl.setTenChatLieu(tenThuocTinh);

        cl.setUpdateAt(dateNow);
        cl.setCreateAt(dateNow);
        return cl;
    }

    HangGiay CheckFormhang() {
        HangGiay hang = new HangGiay();
        row = tblChiTietSanPham7.getSelectedRow();

        String tenThuocTinh = txtTenThuocTinh.getText();
        ArrayList<HangGiay> listCL = new ArrayList<>();
        listCL = hgd.selectAll();
        for (HangGiay hangGiay : listCL) {
            if (tenThuocTinh.equalsIgnoreCase(hangGiay.getTen())) {
                JOptionPane.showMessageDialog(null, "Đã tồn tại");
                return null;
            }
        }
        if (tenThuocTinh.equals("")) {
            JOptionPane.showMessageDialog(null, "Vui long điền đầy đủ thông tin");
            return null;
        }
        Date dateNow = Calendar.getInstance().getTime();
        hang.setTen(tenThuocTinh);
        hang.setUpdateAt(dateNow);
        hang.setCreateAt(dateNow);
        return hang;
    }

    DeGiay CheckFormDG() {
        DeGiay dg = new DeGiay();
        row = tblChiTietSanPham7.getSelectedRow();

        String tenThuocTinh = txtTenThuocTinh.getText();
        ArrayList<DeGiay> listCL = new ArrayList<>();
        listCL = dgd.selectAll();
        for (DeGiay deGiay : listCL) {
            if (tenThuocTinh.equalsIgnoreCase(deGiay.getTen())) {
                JOptionPane.showMessageDialog(null, "Đã tồn tại");
                return null;
            }
        }
        if (tenThuocTinh.equals("")) {
            JOptionPane.showMessageDialog(null, "Vui long điền đầy đủ thông tin");
            return null;
        }
        Date dateNow = Calendar.getInstance().getTime();
        dg.setTen(tenThuocTinh);
//        dg.setUpdateAt(dateNow);
//        dg.setCreateAt(dateNow);
        return dg;
    }

    MauSac CheckFormMauSac() {
        MauSac ms = new MauSac();

        String tenThuocTinh = txtTenThuocTinh.getText();
        ArrayList<MauSac> listCL = new ArrayList<>();
        listCL = msd.selectAll();
        for (MauSac mauSac : listCL) {
            if (tenThuocTinh.equalsIgnoreCase(mauSac.getTenMau())) {
                JOptionPane.showMessageDialog(null, "Đã tồn tại");
                return null;
            }
        }
        if (tenThuocTinh.equals("")) {
            JOptionPane.showMessageDialog(null, "Vui long điền đầy đủ thông tin");
            return null;
        }
        if (tenThuocTinh.equalsIgnoreCase(ms.getTenMau())) {
            JOptionPane.showMessageDialog(null, "Đã có");
            return null;
        }
        Date dateNow = Calendar.getInstance().getTime();
        ms.setTenMau(tenThuocTinh);
        ms.setUpdateAt(dateNow);
        ms.setCreateAt(dateNow);
        return ms;
    }

    void insert() {
        SanPham sp = checkForm();
        int i = JOptionPane.showConfirmDialog(null, "Bạn có muốn thêm ?", "Trả lời", JOptionPane.YES_NO_OPTION);
        if (i == 0) {
            if (sp != null) {

                try {
                    spd.insert(sp);
                    fillTable(listSP);

                    JOptionPane.showMessageDialog(null, "Thêm thành công!");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Thêm thất bại!");
//            }
                }
            }
        } else {
            return;
        }
    }

    void update() {
        row = tblDSSP.getSelectedRow();
        SanPham sp = checkForm();

        if (sp == null) {
            return;
        }
        String ma = tblDSSP.getValueAt(row, 1).toString();
        sp.setMaSP(ma);
        try {
            spd.update(sp);
            fillTable(listSP);
            JOptionPane.showMessageDialog(null, "Update  thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Update  thất bại!");
        }

    }

    private void fillTable(ArrayList<SanPham> listSP) {
        dtm.setRowCount(0);
        listSP = spd.selectAll();
        int size = listSP.size();
        for (int i = 0; i < size; i++) {
            SanPham sp = listSP.get(i);
            dtm.addRow(sp.toDataRow(i + 1));

        }

    }

    private void fillToTableSPCT() {

        dtm1.setRowCount(0);
        row = tblDSSP.getSelectedRow();
        int stt = 1;
        if (row >= 0 && row < tblDSSP.getRowCount()) { // Kiểm tra giá trị hợp lệ của 'row'
            String ma = tblDSSP.getValueAt(row, 1).toString();
            ArrayList<SanPhamChiTiet> list = spctd.selectById(ma);
//            System.out.println("sizeee" + list.size());
            for (SanPhamChiTiet spct : list) {

                dtm1.addRow(new Object[]{spct.isDeleted(), stt++, spct.getMaCTSP(), spct.getSp().getTen(), spct.getMauSac().getTenMau(), spct.getSize().getSize(),
                    spct.getChatLieu().getTenChatLieu(), spct.getHang().getTen(), spct.getDeGiay().getTen(), spct.getGia(), spct.getMoTa(), spct.getSoLuongTon(), spct.isDeleted() == false ? "Hoạt động" : "Không hoạt động"});

            }
            btnXoa7.setEnabled(true);
            btnKhoiPHuc7.setEnabled(false);
        }
    }

    void clearForm() {
        txtMaSanPham.setText("");
        txtTenSanPham.setText("");
        txtMaThuocTinh.setText("");
        txtTenThuocTinh.setText("");
    }

    void showData() {
        row = tblDSSP.getSelectedRow();
        txtMaSanPham.setText(tblDSSP.getValueAt(row, 1).toString());
        txtTenSanPham.setText(tblDSSP.getValueAt(row, 2).toString());
    }

    void fillComboBoxDeGiay() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboDe.getModel();
        model.removeAllElements();
        ArrayList<DeGiay> list = dgd.selectAll();
        for (DeGiay deGiay : list) {
            model.addElement(deGiay);
        }

    }

    void fillComboBoxTenSP() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboTenSp.getModel();
        model.removeAllElements();
        ArrayList<SanPham> list = spd.selectAll();
        for (SanPham sanPham : list) {
            model.addElement(sanPham);
        }

    }

    void fillComboBoxMauSac() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboMauSac.getModel();

        model.removeAllElements();
        ArrayList<MauSac> list = msd.selectAll();
        for (MauSac mauSac : list) {
            model.addElement(mauSac);
        }

    }

    void fillComboBoxSize() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboKichThuoc.getModel();
        model.removeAllElements();
        ArrayList<Size> list = sd.selectAll();
        for (Size size : list) {
            model.addElement(size);
        }

    }

    void fillComboBoxHang() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboHang.getModel();
        model.removeAllElements();
        ArrayList<HangGiay> list = hgd.selectAll();
        for (HangGiay hangGiay : list) {
            model.addElement(hangGiay);
        }

    }

    void fillComboBoxChatLieu() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboChatLieu.getModel();
        model.removeAllElements();
        ArrayList<ChatLieu> list = cld.selectAll();
        for (ChatLieu chatLieu : list) {
            model.addElement(chatLieu);
        }

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
        buttonGroup1 = new javax.swing.ButtonGroup();
        tab = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtMaSanPham = new javax.swing.JTextField();
        txtTenSanPham = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        txtTK = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDSSP = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tblChiTietSanPham7 = new javax.swing.JTable();
        btnXoa7 = new javax.swing.JButton();
        btnKhoiPHuc7 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txtTK8 = new javax.swing.JTextField();
        cboHang8 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cboMauSac8 = new javax.swing.JComboBox<>();
        cboKichThuoc8 = new javax.swing.JComboBox<>();
        cboChatLieu8 = new javax.swing.JComboBox<>();
        CboTrangThai7 = new javax.swing.JComboBox<>();
        cboDeGiay8 = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtMin = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtMax = new javax.swing.JTextField();
        btnClear = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        quetQR = new javax.swing.JButton();
        txtmaSPCTQR = new javax.swing.JLabel();
        btnTKGia = new javax.swing.JButton();
        btnThemSP = new javax.swing.JButton();
        btnXuatExcel = new javax.swing.JButton();
        btnTaiQR = new javax.swing.JButton();
        chkSPCT = new javax.swing.JCheckBox();
        jPanel18 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        AddTHuocTinh = new javax.swing.JButton();
        SuaThuocTinh = new javax.swing.JButton();
        btnMOi = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        txtMaThuocTinh = new javax.swing.JTextField();
        txtTenThuocTinh = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        rdoKichThuoc = new javax.swing.JRadioButton();
        rdoMauSac = new javax.swing.JRadioButton();
        rdoHang = new javax.swing.JRadioButton();
        rdoChatLieu = new javax.swing.JRadioButton();
        rdoDeGiay = new javax.swing.JRadioButton();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblThuocTinh = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        AddUpdateSPCT = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        cboTenSp = new javax.swing.JComboBox<>();
        txtMaCTSP = new javax.swing.JTextField();
        txtSlTon = new javax.swing.JTextField();
        txtGiaBan = new javax.swing.JTextField();
        txtMoTa = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        cboMauSac = new javax.swing.JComboBox<>();
        cboKichThuoc = new javax.swing.JComboBox<>();
        cboHang = new javax.swing.JComboBox<>();
        cboChatLieu = new javax.swing.JComboBox<>();
        Mausac = new javax.swing.JButton();
        size = new javax.swing.JButton();
        hang = new javax.swing.JButton();
        ChatLieu = new javax.swing.JButton();
        TenSP = new javax.swing.JButton();
        jLabel39 = new javax.swing.JLabel();
        cboDe = new javax.swing.JComboBox<>();
        degiay = new javax.swing.JButton();
        idSP = new javax.swing.JLabel();
        IDMauSac = new javax.swing.JLabel();
        idKichThuoc = new javax.swing.JLabel();
        IDChatLieu = new javax.swing.JLabel();
        IDHang = new javax.swing.JLabel();
        IDDeGiay = new javax.swing.JLabel();
        btnThemCTSP = new javax.swing.JButton();
        btnCapNhatCTSP = new javax.swing.JButton();
        btnQuayLai = new javax.swing.JButton();

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

        tab.setBackground(new java.awt.Color(255, 255, 255));
        tab.setMaximumSize(new java.awt.Dimension(32767, 1700));
        tab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabMouseClicked(evt);
            }
        });

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setPreferredSize(new java.awt.Dimension(1200, 900));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.lightGray, java.awt.Color.darkGray));

        jLabel15.setText("Mã sản phẩm:");

        jLabel16.setText("Tên sản phẩm:");

        txtMaSanPham.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        txtTenSanPham.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        btnThem.setBackground(new java.awt.Color(0, 204, 255));
        btnThem.setText("Thêm sản phẩm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(0, 204, 255));
        btnUpdate.setText("Cập nhật sản phẩm");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnLamMoi.setBackground(new java.awt.Color(0, 204, 255));
        btnLamMoi.setText("Làm mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(btnUpdate)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnThem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLamMoi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        jPanel8Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnLamMoi, btnThem, btnUpdate});

        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(btnThem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnUpdate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLamMoi)
                .addContainerGap())
        );

        jPanel8Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnLamMoi, btnThem, btnUpdate});

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel16))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel15)))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTenSanPham)
                    .addComponent(txtMaSanPham))
                .addGap(33, 33, 33)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(180, 180, 180))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtMaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel24.setText("Thông tin sản phẩm");

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel25.setText("Danh sách sản phẩm");

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.lightGray, java.awt.Color.darkGray));

        jLabel26.setText("Tìm kiếm:");

        txtTK.setText("Tìm kiếm theo mã sản phẩm, tên sản phẩm, trạng thái, số lượng ?");
        txtTK.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTKFocusLost(evt);
            }
        });
        txtTK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTKMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtTKMouseEntered(evt);
            }
        });
        txtTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTKActionPerformed(evt);
            }
        });

        tblDSSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDSSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDSSPMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblDSSP);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addGap(30, 30, 30)
                        .addComponent(txtTK, javax.swing.GroupLayout.PREFERRED_SIZE, 657, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 430, Short.MAX_VALUE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(356, 356, 356))
        );

        tab.addTab("Sản phẩm", jPanel6);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setPreferredSize(new java.awt.Dimension(1200, 900));

        jLabel38.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel38.setText("Danh sách sản phẩm");

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));
        jPanel22.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.gray, java.awt.Color.gray));
        jPanel22.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblChiTietSanPham7.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Selected", "STT", "Mã CTSP", "Tên Sản phẩm", "Màu sắc", "Kích thước", "Chất liệu", "Hãng", "Đế giày", "Giá", "Mô tả", "Số lượng tồn", "Trạng thái", "Xem"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false, false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblChiTietSanPham7.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tblChiTietSanPham7.setRowHeight(40);
        tblChiTietSanPham7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblChiTietSanPham7MouseClicked(evt);
            }
        });
        jScrollPane11.setViewportView(tblChiTietSanPham7);

        jPanel22.add(jScrollPane11, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 2, 1150, 155));

        btnXoa7.setBackground(new java.awt.Color(0, 204, 255));
        btnXoa7.setText("Xóa");
        btnXoa7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoa7ActionPerformed(evt);
            }
        });
        jPanel22.add(btnXoa7, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 170, 80, -1));

        btnKhoiPHuc7.setBackground(new java.awt.Color(0, 204, 255));
        btnKhoiPHuc7.setText("Khôi phục");
        btnKhoiPHuc7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKhoiPHuc7ActionPerformed(evt);
            }
        });
        jPanel22.add(btnKhoiPHuc7, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 170, -1, -1));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Bộ lọc"));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel17.setText("Tìm kiếm:");
        jPanel3.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 34, -1, -1));

        txtTK8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTK8ActionPerformed(evt);
            }
        });
        jPanel3.add(txtTK8, new org.netbeans.lib.awtextra.AbsoluteConstraints(88, 31, 450, -1));

        cboHang8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", " " }));
        cboHang8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboHang8ActionPerformed(evt);
            }
        });
        jPanel3.add(cboHang8, new org.netbeans.lib.awtextra.AbsoluteConstraints(88, 82, 291, -1));

        jLabel2.setText("Hãng");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 86, -1, -1));

        jLabel3.setText("Màu sắc");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, -1, -1));

        jLabel4.setText("Đế giày:");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 180, -1, 28));

        jLabel5.setText("Kích thước:");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(397, 86, -1, -1));

        jLabel6.setText("Chất liệu");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 140, -1, -1));

        jLabel7.setText("Trạng thái:");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 190, -1, -1));

        cboMauSac8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả" }));
        cboMauSac8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMauSac8ActionPerformed(evt);
            }
        });
        jPanel3.add(cboMauSac8, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 130, 291, -1));

        cboKichThuoc8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả" }));
        cboKichThuoc8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboKichThuoc8ActionPerformed(evt);
            }
        });
        jPanel3.add(cboKichThuoc8, new org.netbeans.lib.awtextra.AbsoluteConstraints(475, 82, 291, -1));

        cboChatLieu8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả" }));
        cboChatLieu8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboChatLieu8ActionPerformed(evt);
            }
        });
        jPanel3.add(cboChatLieu8, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 130, 291, -1));

        CboTrangThai7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Active", "Available" }));
        CboTrangThai7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CboTrangThai7ActionPerformed(evt);
            }
        });
        jPanel3.add(CboTrangThai7, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 186, 270, -1));

        cboDeGiay8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboDeGiay8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboDeGiay8ActionPerformed(evt);
            }
        });
        jPanel3.add(cboDeGiay8, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 180, 291, -1));

        jLabel8.setText("Khoảng giá:");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, -1));

        jLabel9.setText("Min");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 190, -1, -1));

        txtMin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMinActionPerformed(evt);
            }
        });
        jPanel3.add(txtMin, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 190, 80, -1));

        jLabel10.setText("Max:");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 190, -1, -1));
        jPanel3.add(txtMax, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 190, 87, -1));

        btnClear.setBackground(new java.awt.Color(0, 204, 255));
        btnClear.setText("Làm mới");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });
        jPanel3.add(btnClear, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 150, 163, -1));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        quetQR.setBackground(new java.awt.Color(0, 204, 255));
        quetQR.setText("QR CODE");
        quetQR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quetQRActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addComponent(quetQR, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(quetQR, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 20, 160, 120));
        jPanel3.add(txtmaSPCTQR, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 70, -1, -1));

        btnTKGia.setText("OK");
        btnTKGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTKGiaActionPerformed(evt);
            }
        });
        jPanel3.add(btnTKGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 190, 50, -1));

        btnThemSP.setBackground(new java.awt.Color(0, 204, 255));
        btnThemSP.setText("Thêm Sản Phẩm");
        btnThemSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSPActionPerformed(evt);
            }
        });

        btnXuatExcel.setBackground(new java.awt.Color(0, 204, 255));
        btnXuatExcel.setText("Xuất Excel");
        btnXuatExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatExcelActionPerformed(evt);
            }
        });

        btnTaiQR.setBackground(new java.awt.Color(0, 204, 255));
        btnTaiQR.setText("Tải QR Code");
        btnTaiQR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaiQRActionPerformed(evt);
            }
        });

        chkSPCT.setBackground(new java.awt.Color(255, 255, 255));
        chkSPCT.setText("ALL");
        chkSPCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSPCTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel38)
                                .addGap(414, 414, 414)
                                .addComponent(chkSPCT)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnTaiQR))
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 1166, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(31, Short.MAX_VALUE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(btnXuatExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31)
                                .addComponent(btnThemSP, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, 1155, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        jPanel10Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnTaiQR, btnThemSP});

        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(btnThemSP)
                    .addComponent(btnXuatExcel)
                    .addComponent(btnTaiQR)
                    .addComponent(chkSPCT))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tab.addTab("Chi tiết sản phẩm", jPanel10);

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setPreferredSize(new java.awt.Dimension(1200, 900));

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));
        jPanel19.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.lightGray, java.awt.Color.darkGray));

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));
        jPanel20.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.gray, java.awt.Color.gray));

        AddTHuocTinh.setBackground(new java.awt.Color(0, 204, 255));
        AddTHuocTinh.setText("Thêm");
        AddTHuocTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddTHuocTinhActionPerformed(evt);
            }
        });

        SuaThuocTinh.setBackground(new java.awt.Color(0, 204, 255));
        SuaThuocTinh.setText("Sửa");
        SuaThuocTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SuaThuocTinhActionPerformed(evt);
            }
        });

        btnMOi.setBackground(new java.awt.Color(0, 204, 255));
        btnMOi.setText("Làm mới");
        btnMOi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMOiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel20Layout.createSequentialGroup()
                            .addGap(21, 21, 21)
                            .addComponent(AddTHuocTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(SuaThuocTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnMOi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addComponent(AddTHuocTinh)
                .addGap(18, 18, 18)
                .addComponent(SuaThuocTinh)
                .addGap(18, 18, 18)
                .addComponent(btnMOi)
                .addGap(32, 32, 32))
        );

        jPanel20Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {AddTHuocTinh, SuaThuocTinh, btnMOi});

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.gray, java.awt.Color.gray));

        jLabel58.setText("Mã thuộc tính:");

        jLabel59.setText("Tên thuộc tính:");

        txtMaThuocTinh.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        txtTenThuocTinh.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel59)
                    .addComponent(jLabel58))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMaThuocTinh, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
                    .addComponent(txtTenThuocTinh))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMaThuocTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel58))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel59)
                    .addComponent(txtTenThuocTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.gray, java.awt.Color.gray));

        buttonGroup1.add(rdoKichThuoc);
        rdoKichThuoc.setText("Kích thước");
        rdoKichThuoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoKichThuocMouseClicked(evt);
            }
        });
        rdoKichThuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoKichThuocActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoMauSac);
        rdoMauSac.setText("Màu sắc");
        rdoMauSac.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoMauSacMouseClicked(evt);
            }
        });

        buttonGroup1.add(rdoHang);
        rdoHang.setText("Hãng");
        rdoHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoHangMouseClicked(evt);
            }
        });

        buttonGroup1.add(rdoChatLieu);
        rdoChatLieu.setText("Chất liệu");
        rdoChatLieu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoChatLieuMouseClicked(evt);
            }
        });

        buttonGroup1.add(rdoDeGiay);
        rdoDeGiay.setText("Đế giày");
        rdoDeGiay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoDeGiayMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(rdoKichThuoc))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(rdoHang)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 132, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdoMauSac)
                    .addComponent(rdoChatLieu))
                .addGap(37, 37, 37))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(128, 128, 128)
                .addComponent(rdoDeGiay)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoKichThuoc)
                    .addComponent(rdoChatLieu))
                .addGap(18, 18, 18)
                .addComponent(rdoDeGiay)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoHang)
                    .addComponent(rdoMauSac))
                .addGap(36, 36, 36))
        );

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(45, 45, 45)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel20, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel63.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel63.setText("Thông tin sản phẩm");

        jLabel64.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel64.setText("Danh sách sản phẩm");

        tblThuocTinh.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "STT", "Mã Thuộc Tính", "Tên Thuộc Tính"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblThuocTinh.setRowHeight(30);
        tblThuocTinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblThuocTinhMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblThuocTinh);

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane5)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel63)
                            .addComponent(jLabel64))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel63)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel64)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(444, Short.MAX_VALUE))
        );

        tab.addTab("Thuộc tính sản phẩm", jPanel18);

        add(tab, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 1220, 550));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 51, 51));
        jLabel1.setText("Sản Phẩm");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        AddUpdateSPCT.setBackground(new java.awt.Color(255, 255, 255));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.lightGray, java.awt.Color.darkGray));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel27.setText("Tên sản phẩm:");
        jPanel11.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 19, -1, -1));

        jLabel28.setText("Mã CTSP:");
        jPanel11.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 99, -1, -1));

        jLabel29.setText("Số lượng tồn:");
        jPanel11.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 164, -1, -1));

        jLabel30.setText("Giá:");
        jPanel11.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 231, -1, -1));

        jLabel31.setText("Mô tả:");
        jPanel11.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 302, -1, -1));

        cboTenSp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTenSpActionPerformed(evt);
            }
        });
        jPanel11.add(cboTenSp, new org.netbeans.lib.awtextra.AbsoluteConstraints(108, 15, 345, -1));

        txtMaCTSP.setBorder(new javax.swing.border.MatteBorder(null));
        jPanel11.add(txtMaCTSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(108, 96, 342, 22));

        txtSlTon.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel11.add(txtSlTon, new org.netbeans.lib.awtextra.AbsoluteConstraints(103, 164, 347, -1));

        txtGiaBan.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel11.add(txtGiaBan, new org.netbeans.lib.awtextra.AbsoluteConstraints(103, 231, 347, -1));

        txtMoTa.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel11.add(txtMoTa, new org.netbeans.lib.awtextra.AbsoluteConstraints(103, 302, 347, -1));

        jLabel32.setText("Màu sắc:");
        jPanel11.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(563, 19, -1, -1));

        jLabel33.setText("Kích thước:");
        jPanel11.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(575, 101, -1, -1));

        jLabel34.setText("Hãng");
        jPanel11.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(575, 164, -1, -1));

        jLabel35.setText("Chất liệu:");
        jPanel11.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(563, 231, -1, -1));

        cboMauSac.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboMauSac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMauSacActionPerformed(evt);
            }
        });
        jPanel11.add(cboMauSac, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 20, 310, -1));

        cboKichThuoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboKichThuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboKichThuocActionPerformed(evt);
            }
        });
        jPanel11.add(cboKichThuoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(675, 97, 315, -1));

        cboHang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboHangActionPerformed(evt);
            }
        });
        jPanel11.add(cboHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(675, 160, 315, -1));

        cboChatLieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboChatLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboChatLieuActionPerformed(evt);
            }
        });
        jPanel11.add(cboChatLieu, new org.netbeans.lib.awtextra.AbsoluteConstraints(675, 227, 315, -1));

        Mausac.setBackground(new java.awt.Color(0, 204, 255));
        Mausac.setText("+");
        Mausac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MausacActionPerformed(evt);
            }
        });
        jPanel11.add(Mausac, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 20, -1, -1));

        size.setBackground(new java.awt.Color(0, 204, 255));
        size.setText("+");
        size.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sizeActionPerformed(evt);
            }
        });
        jPanel11.add(size, new org.netbeans.lib.awtextra.AbsoluteConstraints(1008, 97, -1, -1));

        hang.setBackground(new java.awt.Color(0, 204, 255));
        hang.setText("+");
        hang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hangActionPerformed(evt);
            }
        });
        jPanel11.add(hang, new org.netbeans.lib.awtextra.AbsoluteConstraints(1008, 160, -1, -1));

        ChatLieu.setBackground(new java.awt.Color(0, 204, 255));
        ChatLieu.setText("+");
        ChatLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChatLieuActionPerformed(evt);
            }
        });
        jPanel11.add(ChatLieu, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 230, -1, -1));

        TenSP.setBackground(new java.awt.Color(0, 204, 255));
        TenSP.setText("+");
        TenSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TenSPActionPerformed(evt);
            }
        });
        jPanel11.add(TenSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 15, -1, -1));

        jLabel39.setText("Đế giày:");
        jPanel11.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(563, 302, -1, -1));

        cboDe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel11.add(cboDe, new org.netbeans.lib.awtextra.AbsoluteConstraints(675, 298, 315, -1));

        degiay.setBackground(new java.awt.Color(0, 204, 255));
        degiay.setText("+");
        degiay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                degiayActionPerformed(evt);
            }
        });
        jPanel11.add(degiay, new org.netbeans.lib.awtextra.AbsoluteConstraints(1008, 298, -1, -1));
        jPanel11.add(idSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(454, 70, -1, -1));
        jPanel11.add(IDMauSac, new org.netbeans.lib.awtextra.AbsoluteConstraints(453, 108, -1, -1));
        jPanel11.add(idKichThuoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(485, 185, -1, -1));
        jPanel11.add(IDChatLieu, new org.netbeans.lib.awtextra.AbsoluteConstraints(485, 195, -1, -1));
        jPanel11.add(IDHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(485, 70, -1, -1));
        jPanel11.add(IDDeGiay, new org.netbeans.lib.awtextra.AbsoluteConstraints(485, 129, -1, -1));

        btnThemCTSP.setBackground(new java.awt.Color(0, 204, 255));
        btnThemCTSP.setText("Thêm sản phẩm");
        btnThemCTSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemCTSPActionPerformed(evt);
            }
        });
        jPanel11.add(btnThemCTSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 400, 137, -1));

        btnCapNhatCTSP.setBackground(new java.awt.Color(0, 204, 255));
        btnCapNhatCTSP.setText("Cập nhật sản phẩm");
        btnCapNhatCTSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatCTSPActionPerformed(evt);
            }
        });
        jPanel11.add(btnCapNhatCTSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 400, -1, -1));

        btnQuayLai.setBackground(new java.awt.Color(0, 204, 255));
        btnQuayLai.setText("Quay lại");
        btnQuayLai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuayLaiActionPerformed(evt);
            }
        });
        jPanel11.add(btnQuayLai, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, -1, -1));

        javax.swing.GroupLayout AddUpdateSPCTLayout = new javax.swing.GroupLayout(AddUpdateSPCT);
        AddUpdateSPCT.setLayout(AddUpdateSPCTLayout);
        AddUpdateSPCTLayout.setHorizontalGroup(
            AddUpdateSPCTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddUpdateSPCTLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 1183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(65, Short.MAX_VALUE))
        );
        AddUpdateSPCTLayout.setVerticalGroup(
            AddUpdateSPCTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddUpdateSPCTLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(43, 43, 43))
        );

        add(AddUpdateSPCT, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1260, 580));
    }// </editor-fold>//GEN-END:initComponents
//    void showDataSPCT() {
//        cboTenSp.setSelectedItem();
//    }//    private void searchTable() {
//        String keyword = txtTimKiem.getText();
//
//        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(dtm);
//        tblDanhSachSanPham.setRowSorter(sorter);
//
//        if (keyword.trim().length() == 0) {
//            sorter.setRowFilter(null);
//        } else {
//            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + keyword));
//        }
//
//    }

    private void searchTable1() {

        String keyword1 = txtTK.getText();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(dtm);
        tblDSSP.setRowSorter(sorter);

        if (keyword1.trim().length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + keyword1));
        }

    }
    private void tblThuocTinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblThuocTinhMouseClicked
        // TODO add your handling code here:

        showDataThuocTinh();
    }//GEN-LAST:event_tblThuocTinhMouseClicked

    private void rdoChatLieuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoChatLieuMouseClicked
        // TODO add your handling code here:
        this.fillTableChatLieu(listCL);
    }//GEN-LAST:event_rdoChatLieuMouseClicked

    private void rdoHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoHangMouseClicked
        // TODO add your handling code here:
        this.fillTableHang(listHang);
    }//GEN-LAST:event_rdoHangMouseClicked

    private void rdoMauSacMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoMauSacMouseClicked
        // TODO add your handling code here:
        this.fillTableMau(listMau);
    }//GEN-LAST:event_rdoMauSacMouseClicked

    private void rdoKichThuocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoKichThuocMouseClicked
        // TODO add your handling code here:

        this.fillTableSize(listSize);
    }//GEN-LAST:event_rdoKichThuocMouseClicked

    private void btnMOiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMOiActionPerformed
        // TODO add your handling code here:
        clearForm();
    }//GEN-LAST:event_btnMOiActionPerformed

    private void SuaThuocTinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SuaThuocTinhActionPerformed
        row = tblThuocTinh.getSelectedRow();
        if (rdoKichThuoc.isSelected()) {

            Size size = CheckFormSize();
            if (size == null) {
                return;
            }
            String ma = tblThuocTinh.getValueAt(row, 0).toString();;
            size.setId(Integer.valueOf(ma));

            try {
                sd.update(size);
                fillTableSize(listSize);

                JOptionPane.showMessageDialog(null, "Update thành công!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Update thất bại!");
            }

        } else if (rdoChatLieu.isSelected()) {
            ChatLieu cl = CheckFormChatLieu();
            if (cl == null) {
                return;
            }
            String ma = tblThuocTinh.getValueAt(row, 0).toString();;
            cl.setId(Integer.valueOf(ma));
            try {
                cld.update(cl);
                fillTableChatLieu(listCL);

                JOptionPane.showMessageDialog(null, "Update  thành công!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Update  thất bại!");
            }
        } else if (rdoHang.isSelected()) {
            HangGiay hg = CheckFormhang();
            if (hg == null) {
                return;
            }
            String ma = tblThuocTinh.getValueAt(row, 0).toString();;
            hg.setId(Integer.valueOf(ma));
            try {
                hgd.update(hg);
                fillTableHang(listHang);

                JOptionPane.showMessageDialog(null, "Update  thành công!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Update  thất bại!");
            }
        } else {
            MauSac ms = CheckFormMauSac();
            if (ms == null) {
                return;
            }
            String ma = tblThuocTinh.getValueAt(row, 0).toString();;
            ms.setId(Integer.valueOf(ma));
            try {
                msd.update(ms);
                fillTableMau(listMau);

                JOptionPane.showMessageDialog(null, "Update  thành công!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Update  thất bại!");
            }
        }
    }//GEN-LAST:event_SuaThuocTinhActionPerformed

    private void AddTHuocTinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddTHuocTinhActionPerformed
        // TODO add your handling code here:

        if (rdoKichThuoc.isSelected()) {

            Size size = CheckFormSize();

            if (size != null) {
                int i = JOptionPane.showConfirmDialog(null, "Bạn có muốn thêm ?", "Trả lời", JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    try {
                        sd.insert(size);
                        fillTableSize(listSize);

                        JOptionPane.showMessageDialog(null, "Thêm thành công!");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Thêm thất bại!");
                    }
                } else {
                    return;
                }
            }

        } else if (rdoChatLieu.isSelected()) {

            ChatLieu cl = CheckFormChatLieu();

            if (cl != null) {
                int i = JOptionPane.showConfirmDialog(null, "Bạn có muốn thêm ?", "Trả lời", JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    try {
                        cld.insert(cl);
                        fillTableChatLieu(listCL);

                        JOptionPane.showMessageDialog(null, "Thêm thành công!");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Thêm thất bại!");
                    }
                } else {
                    return;
                }
            }
        } else if (rdoHang.isSelected()) {
            HangGiay hg = CheckFormhang();

            if (hg != null) {
                int i = JOptionPane.showConfirmDialog(null, "Bạn có muốn thêm ?", "Trả lời", JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    try {
                        hgd.insert(hg);
                        fillTableHang(listHang);

                        JOptionPane.showMessageDialog(null, "Thêm thành công!");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Thêm thất bại!");
                    }
                } else {
                    return;
                }
            }
        } else if (rdoDeGiay.isSelected()) {
            DeGiay dg = CheckFormDG();

            if (dg != null) {
                int i = JOptionPane.showConfirmDialog(null, "Bạn có muốn thêm ?", "Trả lời", JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    try {
                        dgd.insert(dg);
                        fillTableDeGiay(listDG);

                        JOptionPane.showMessageDialog(null, "Thêm thành công!");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Thêm thất bại!");
                    }
                } else {
                    return;
                }
            }
        } else if (rdoDeGiay.isSelected()) {
            DeGiay dg = CheckFormDG();

            if (dg != null) {
                int i = JOptionPane.showConfirmDialog(null, "Bạn có muốn thêm ?", "Trả lời", JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    try {
                        dgd.insert(dg);
                        fillTableDeGiay(listDG);

                        JOptionPane.showMessageDialog(null, "Thêm thành công!");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Thêm thất bại!");
                    }
                } else {
                    return;
                }
            }
        } else {
            MauSac ms = CheckFormMauSac();

            if (ms != null) {
                int i = JOptionPane.showConfirmDialog(null, "Bạn có muốn thêm ?", "Trả lời", JOptionPane.YES_NO_OPTION);
                if (i == 0) {
                    try {
                        msd.insert(ms);
                        fillTableMau(listMau);

                        JOptionPane.showMessageDialog(null, "Thêm thành công!");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Thêm thất bại!");
                    }
                } else {
                    return;
                }
            }
        }
        this.fillComboBoxDeGiay();
        this.fillComboBoxChatLieu();
        this.fillComboBoxHang();
        this.fillComboBoxMauSac();
        this.fillComboBoxSize();
        this.fillComboBoxTenSP();
        this.fillComboBoxChatLieu8();
        this.fillComboBoxHang8();
        this.fillComboBoxMauSac8();
        this.fillComboBoxSize8();
        this.fillComboBoxDeGiay8();
    }//GEN-LAST:event_AddTHuocTinhActionPerformed

    private void btnKhoiPHuc7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKhoiPHuc7ActionPerformed
        // TODO add your handling code here:

        this.khoiPhuc();
        CboTrangThai7.setSelectedItem("Active");

    }//GEN-LAST:event_btnKhoiPHuc7ActionPerformed

    private void btnXoa7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoa7ActionPerformed
        this.deletedRows();
        CboTrangThai7.setSelectedIndex(1);
    }//GEN-LAST:event_btnXoa7ActionPerformed

    private void tblChiTietSanPham7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChiTietSanPham7MouseClicked
        // TODO add your handling code here:
        stt();
    }//GEN-LAST:event_tblChiTietSanPham7MouseClicked

    private void CboTrangThai7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CboTrangThai7ActionPerformed
        // TODO add your handling code here:
        if (CboTrangThai7.getSelectedItem().equals("Active")) {
            fillToTableALLSPCT(listSPCT);
            btnXoa7.setEnabled(true);
            btnKhoiPHuc7.setEnabled(false);
        } else if (CboTrangThai7.getSelectedItem().equals("Available")) {
            fillToTableDaXoa(listSPCT);
            btnXoa7.setEnabled(false);
            btnKhoiPHuc7.setEnabled(true);

        }
    }//GEN-LAST:event_CboTrangThai7ActionPerformed

    private void cboChatLieu8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboChatLieu8ActionPerformed
        Object selectedValue = cboChatLieu8.getSelectedItem();
        if (selectedValue != null) {
            String value = selectedValue.toString();
            if (value.equals("Tất cả")) {
                fillToTableALLSPCT(listSPCT);
            } else {
                searchInTableChatLieu(value);
            }
        } else {
            fillToTableALLSPCT(listSPCT);
        }
    }//GEN-LAST:event_cboChatLieu8ActionPerformed

    private void cboKichThuoc8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboKichThuoc8ActionPerformed
        // TODO add your handling code here:
        Object selectedValue = cboKichThuoc8.getSelectedItem();
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
    }//GEN-LAST:event_cboKichThuoc8ActionPerformed

    private void cboMauSac8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMauSac8ActionPerformed
        Object selectedValue = cboMauSac8.getSelectedItem();
        if (selectedValue != null) {
            String value = selectedValue.toString();
            if (cboMauSac8.getSelectedItem().equals("Tất cả")) {
                fillToTableALLSPCT(listSPCT);
            } else {
                searchInTableMauSac(value);

            }
        } else {
            fillToTableALLSPCT(listSPCT);
        }
    }//GEN-LAST:event_cboMauSac8ActionPerformed

    private void cboHang8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboHang8ActionPerformed

        Object selectedValue = cboHang8.getSelectedItem();
        if (selectedValue != null) {
            String value = selectedValue.toString();
            if (cboHang8.getSelectedItem().equals("Tất cả")) {
                fillToTableALLSPCT(listSPCT);
            } else {
                searchInTableHang(value);
            }
        } else {
            fillToTableALLSPCT(listSPCT);
        }
    }//GEN-LAST:event_cboHang8ActionPerformed

    private void txtTK8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTK8ActionPerformed
        // TODO add your handling code here:
        searchTable();
    }//GEN-LAST:event_txtTK8ActionPerformed

    private void tblDSSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDSSPMouseClicked
        showData();
        if (evt.getClickCount() == 2) {
            tab.setSelectedIndex(1);
            fillToTableSPCT();
        }

    }//GEN-LAST:event_tblDSSPMouseClicked

    private void txtTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTKActionPerformed
        // TODO add your handling code here:

        searchTable1();
    }//GEN-LAST:event_txtTKActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here:
        clearForm();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        this.update();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        this.insert();
    }//GEN-LAST:event_btnThemActionPerformed

    private void rdoKichThuocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoKichThuocActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_rdoKichThuocActionPerformed

    private void txtTKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTKMouseClicked
        // TODO add your handling code here:
        txtTK.setText("");

    }//GEN-LAST:event_txtTKMouseClicked

    private void txtTKFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTKFocusLost
        // TODO add your handling code here:
        if (!txtTK.isFocusOwner()) {
            txtTK.setText("Tìm kiếm theo mã sản phẩm, tên sản phẩm, Số lượng, trạng thái");
        }
    }//GEN-LAST:event_txtTKFocusLost

    private void txtTKMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTKMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTKMouseEntered

    private void cboTenSpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTenSpActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_cboTenSpActionPerformed

    private void cboMauSacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMauSacActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboMauSacActionPerformed

    private void cboKichThuocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboKichThuocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboKichThuocActionPerformed

    private void cboHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboHangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboHangActionPerformed

    private void cboChatLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboChatLieuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboChatLieuActionPerformed

    private void btnThemCTSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemCTSPActionPerformed
        // TODO add your handling code here:
        insertSPCT();
        fillTable(listSP);

        try {
            row = tblChiTietSanPham7.getSelectedRow();
            ByteArrayOutputStream out = net.glxn.qrgen.QRCode.from(txtMaCTSP.getText()).to(ImageType.PNG).stream();
            String f_name = txtMaCTSP.getText();
            String Path_name = "D:\\DUAN1\\SourceTree\\QRCodeSPCT\\";
            FileOutputStream fout = new FileOutputStream(new File(Path_name + (f_name + ".PNG")));
            fout.write(out.toByteArray());
            fout.flush();
        } catch (Exception e) {
            System.out.println(e);
        }
       
        fillToTableALLSPCT(listSPCT);
    }//GEN-LAST:event_btnThemCTSPActionPerformed

    private void btnCapNhatCTSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatCTSPActionPerformed
        // TODO add your handling code here:

        updateSPCT();
        listSPCT = spctd.selectAll();
        fillToTableALLSPCT(listSPCT);
        fillTable(listSP);
    }//GEN-LAST:event_btnCapNhatCTSPActionPerformed

    private void MausacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MausacActionPerformed
        // TODO add your handling code here:
        new MauSacDialog(jFrame1, true).setVisible(true);
        fillComboBoxMauSac();
    }//GEN-LAST:event_MausacActionPerformed

    private void sizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sizeActionPerformed
        // TODO add your handling code here:
        new SizeDialog(jFrame1, true).setVisible(true);
        fillComboBoxSize();
    }//GEN-LAST:event_sizeActionPerformed

    private void hangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hangActionPerformed
        // TODO add your handling code here:
        new HangDialog(jFrame1, true).setVisible(true);
        fillComboBoxHang();
    }//GEN-LAST:event_hangActionPerformed

    private void ChatLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChatLieuActionPerformed
        // TODO add your handling code here:
        new ChatLieuDialog(jFrame1, true).setVisible(true);
        fillComboBoxChatLieu();
    }//GEN-LAST:event_ChatLieuActionPerformed

    private void TenSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TenSPActionPerformed
        // TODO add your handling code here:
        new SanPhamDialog(jFrame1, true).setVisible(true);
        fillComboBoxTenSP();
    }//GEN-LAST:event_TenSPActionPerformed

    private void degiayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_degiayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_degiayActionPerformed

    private void btnThemSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSPActionPerformed
        // TODO add your handling code here:
//        row = tblDSSP.getSelectedRow();
//        String idsp= tblDSSP.getValueAt(row, 1).toString();
//        String idTen= tblDSSP.getValueAt(row, 2).toString();
//        SanPham sp = (SanPham) cboTenSp.getSelectedItem();
//        cboTenSp.setSelectedItem(tblDSSP.getValueAt(row, 2).toString());
//        sp.setMaSP(idsp);

        AddUpdateSPCT.setVisible(true);
        tab.setVisible(false);
        btnCapNhatCTSP.setEnabled(false);
    }//GEN-LAST:event_btnThemSPActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:

        txtMin.setText("");
        txtMax.setText("");
        txtTK8.setText("");
        this.fillComboBoxChatLieu8();
        this.fillComboBoxHang8();
        this.fillComboBoxMauSac8();
        this.fillComboBoxSize8();
        this.fillComboBoxDeGiay8();
        this.fillComboBoxTenSP();
        listSPCT = spctd.selectAll();
        fillToTableALLSPCT(listSPCT);
    }//GEN-LAST:event_btnClearActionPerformed

    private void rdoDeGiayMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoDeGiayMouseClicked
        // TODO add your handling code here:
        fillTableDeGiay(listDG);
    }//GEN-LAST:event_rdoDeGiayMouseClicked

    private void cboDeGiay8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboDeGiay8ActionPerformed
        // TODO add your handling code here:
        Object selectedValue = cboDeGiay8.getSelectedItem();
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
    }//GEN-LAST:event_cboDeGiay8ActionPerformed

    private void btnXuatExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatExcelActionPerformed

        try {
            XSSFWorkbook wordkbook = new XSSFWorkbook();
            XSSFSheet sheet = wordkbook.createSheet("SanPhamChiTiet");
            XSSFRow row_sheet = null;

            row_sheet = sheet.createRow(0);
            XSSFCell cell = row_sheet.createCell(0, CellType.STRING);
            cell.setCellValue("STT");

            cell = row_sheet.createCell(1, CellType.STRING);
            cell.setCellValue("MaSPCT");

            cell = row_sheet.createCell(2, CellType.STRING);
            cell.setCellValue("Tên Sản Phẩm");

            cell = row_sheet.createCell(3, CellType.STRING);
            cell.setCellValue("Màu Sắc");
            cell = row_sheet.createCell(4, CellType.STRING);
            cell.setCellValue("Kích Thước");
            cell = row_sheet.createCell(5, CellType.STRING);
            cell.setCellValue("Chất Liệu");
            cell = row_sheet.createCell(6, CellType.STRING);
            cell.setCellValue("Hãng");
            cell = row_sheet.createCell(7, CellType.STRING);
            cell.setCellValue("Đế Giày");
            cell = row_sheet.createCell(8, CellType.STRING);
            cell.setCellValue("Giá");
            cell = row_sheet.createCell(9, CellType.STRING);
            cell.setCellValue("Mô tả");
            cell = row_sheet.createCell(10, CellType.STRING);
            cell.setCellValue("Số Lượng Tồn");
            cell = row_sheet.createCell(11, CellType.STRING);
            cell.setCellValue("Trạng Thái");
            row = tblDSSP.getSelectedRow();

            try {
                row = tblDSSP.getSelectedRow();
                String ma = tblDSSP.getValueAt(row, 1).toString();
                listSPCT = spctd.selectById(ma);
            } catch (Exception e) {
                listSPCT = spctd.selectAll();
            }
            for (int i = 0; i < listSPCT.size(); i++) {
                //Modelbook book =arr.get(i);
                row_sheet = sheet.createRow(1 + i);

                cell = row_sheet.createCell(0, CellType.NUMERIC);
                cell.setCellValue(i + 1);

                cell = row_sheet.createCell(1, CellType.STRING);
                cell.setCellValue(listSPCT.get(i).getMaCTSP());

                cell = row_sheet.createCell(2, CellType.STRING);
                cell.setCellValue(listSPCT.get(i).getSp().getTen());

                cell = row_sheet.createCell(3, CellType.STRING);
                cell.setCellValue(listSPCT.get(i).getMauSac().getTenMau());

                cell = row_sheet.createCell(4, CellType.STRING);
                cell.setCellValue(listSPCT.get(i).getSize().getSize());

                cell = row_sheet.createCell(5, CellType.STRING);
                cell.setCellValue(listSPCT.get(i).getChatLieu().getTenChatLieu());

                cell = row_sheet.createCell(6, CellType.STRING);
                cell.setCellValue(listSPCT.get(i).getHang().getTen());

                cell = row_sheet.createCell(7, CellType.STRING);
                cell.setCellValue(listSPCT.get(i).getDeGiay().getTen());

                cell = row_sheet.createCell(8, CellType.NUMERIC);
                cell.setCellValue(listSPCT.get(i).getGia());

                cell = row_sheet.createCell(9, CellType.STRING);
                cell.setCellValue(listSPCT.get(i).getMoTa());

                cell = row_sheet.createCell(10, CellType.NUMERIC);
                cell.setCellValue(listSPCT.get(i).getSoLuongTon());

                cell = row_sheet.createCell(11, CellType.STRING);
                cell.setCellValue(listSPCT.get(i).getTrangThai());

            }

            File f = new File("D:\\DUAN1\\SourceTree\\ExcelSPCT\\SPCT.xlsx");
            try {
                FileOutputStream fis = new FileOutputStream(f);
                wordkbook.write(fis);
                fis.close();

            } catch (FileNotFoundException ex) {
                ex.printStackTrace();

            } catch (IOException ex) {
                ex.printStackTrace();
            }

            JOptionPane.showMessageDialog(null, "in thanh cong");

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Loi mo file");
        }
    }//GEN-LAST:event_btnXuatExcelActionPerformed

    private void txtMinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMinActionPerformed
        // TODO add your handling code here:


    }//GEN-LAST:event_txtMinActionPerformed

    private void btnTKGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTKGiaActionPerformed
        // TODO add your handling code here:
        tkSPTheoGia();
    }//GEN-LAST:event_btnTKGiaActionPerformed

    private void quetQRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quetQRActionPerformed
        // TODO add your handling code here:
        //        row = tblChiTietSanPham7.getSelectedRow();
        //        QuetQR qr = new QuetQR(jFrame1, true);
        //        qr.setVisible(true);
        //
        //        String maSPCT = qr.getText();
        //        if (maSPCT != null) {
        //            qr.dispose();
        //            String ma = tblChiTietSanPham7.getValueAt(row, 2).toString();
        //            if (maSPCT.equals(ma)) {
        //
        //                // Hiển thị form sản phẩm chi tiết và đổ dữ liệu
        //                XemSPCT xemSPCT = new XemSPCT(jFrame1, true);
        //                xemSPCT.setTenSP(tblChiTietSanPham7.getValueAt(row, 3).toString());
        //                xemSPCT.setMaSPCT(maSPCT);
        //                xemSPCT.setMS(tblChiTietSanPham7.getValueAt(row, 4).toString());
        //                xemSPCT.setKichThuoc(tblChiTietSanPham7.getValueAt(row, 5).toString());
        //                xemSPCT.setChatLieu(tblChiTietSanPham7.getValueAt(row, 6).toString());
        //                xemSPCT.setHang(tblChiTietSanPham7.getValueAt(row, 7).toString());
        //                xemSPCT.setDeGiay(tblChiTietSanPham7.getValueAt(row, 8).toString());
        //                xemSPCT.setGia(tblChiTietSanPham7.getValueAt(row, 9).toString());
        //                xemSPCT.setMoTa(tblChiTietSanPham7.getValueAt(row, 10).toString());
        //                xemSPCT.setSLTon(tblChiTietSanPham7.getValueAt(row, 11).toString());
        //                xemSPCT.setVisible(true);
        //
        //            } else {
        //                JOptionPane.showMessageDialog(this, "Fill thất bại");
        //            }
        //
        //        }
        //    QuetQR qr = new QuetQR(jFrame1, true);
        //        qr.setVisible(true);
        //        String maSPCT = qr.getText();
        //        Syste initWebcame();m.out.println(maSPCT);
//        initWebcame();
        new QuetQR(jFrame1, true).setVisible(true);
        loadQR();
    }//GEN-LAST:event_quetQRActionPerformed

    private void tabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabMouseClicked
        // TODO add your handling code here:
        if (tab.getSelectedIndex() == 1) {
            fillToTableALLSPCT(listSPCT);
        }
    }//GEN-LAST:event_tabMouseClicked

    private void btnTaiQRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaiQRActionPerformed
        // TODO add your handling code here:
//        JFileChooser jfc = = new JFileChooser("D:\\DUAN1\\SourceTree\\TaiQR");
//        jfc.setDialogTitle("Save QR");
//        int excelChooser = jfc.showSaveDialog(null);
//        if(excelChooser == JFileChooser.APPROVE_OPTION){
       
        for (int i = 0; i < dtm1.getRowCount(); i++) {
            row = tblChiTietSanPham7.getSelectedRow();
            boolean tt = (boolean) tblChiTietSanPham7.getValueAt(i, 0);
            if (tt == true) {
                try {
                    row = tblChiTietSanPham7.getSelectedRow();
                    ByteArrayOutputStream out = net.glxn.qrgen.QRCode.from(tblChiTietSanPham7.getValueAt(i, 2).toString()).to(ImageType.PNG).stream();
                    String f_name = tblChiTietSanPham7.getValueAt(i, 2).toString();
                    String Path_name = "D:\\DUAN1\\SourceTree\\TaiQR\\";
                    FileOutputStream fout = new FileOutputStream(new File(Path_name + (f_name + ".PNG")));
                    fout.write(out.toByteArray());
                    fout.flush();

                } catch (Exception e) {
                    System.out.println(e);
                }

            }
        }
        JOptionPane.showMessageDialog(null, "Tải thành công!");
    }//GEN-LAST:event_btnTaiQRActionPerformed

    private void chkSPCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkSPCTActionPerformed
        // TODO add your handling code here:
        if (chkSPCT.isSelected()) {
            checkAll();
        }
    }//GEN-LAST:event_chkSPCTActionPerformed

    private void btnQuayLaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuayLaiActionPerformed
        // TODO add your handling code here:
        AddUpdateSPCT.setVisible(false);
        tab.setVisible(true);
    }//GEN-LAST:event_btnQuayLaiActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddTHuocTinh;
    private javax.swing.JPanel AddUpdateSPCT;
    private javax.swing.JComboBox<String> CboTrangThai7;
    private javax.swing.JButton ChatLieu;
    private javax.swing.JLabel IDChatLieu;
    private javax.swing.JLabel IDDeGiay;
    private javax.swing.JLabel IDHang;
    private javax.swing.JLabel IDMauSac;
    private javax.swing.JButton Mausac;
    private javax.swing.JButton SuaThuocTinh;
    private javax.swing.JButton TenSP;
    private javax.swing.JButton btnCapNhatCTSP;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnKhoiPHuc7;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnMOi;
    private javax.swing.JButton btnQuayLai;
    private javax.swing.JButton btnTKGia;
    private javax.swing.JButton btnTaiQR;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThemCTSP;
    private javax.swing.JButton btnThemSP;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnXoa7;
    private javax.swing.JButton btnXuatExcel;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboChatLieu;
    private javax.swing.JComboBox<String> cboChatLieu8;
    private javax.swing.JComboBox<String> cboDe;
    private javax.swing.JComboBox<String> cboDeGiay8;
    private javax.swing.JComboBox<String> cboHang;
    private javax.swing.JComboBox<String> cboHang8;
    private javax.swing.JComboBox<String> cboKichThuoc;
    private javax.swing.JComboBox<String> cboKichThuoc8;
    private javax.swing.JComboBox<String> cboMauSac;
    private javax.swing.JComboBox<String> cboMauSac8;
    private javax.swing.JComboBox<String> cboTenSp;
    private javax.swing.JCheckBox chkSPCT;
    private javax.swing.JButton degiay;
    private javax.swing.JButton hang;
    private javax.swing.JLabel idKichThuoc;
    private javax.swing.JLabel idSP;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JButton quetQR;
    private javax.swing.JRadioButton rdoChatLieu;
    private javax.swing.JRadioButton rdoDeGiay;
    private javax.swing.JRadioButton rdoHang;
    private javax.swing.JRadioButton rdoKichThuoc;
    private javax.swing.JRadioButton rdoMauSac;
    private javax.swing.JButton size;
    private javax.swing.JTabbedPane tab;
    private javax.swing.JTable tblChiTietSanPham7;
    private javax.swing.JTable tblDSSP;
    private javax.swing.JTable tblThuocTinh;
    private javax.swing.JTextField txtGiaBan;
    private javax.swing.JTextField txtMaCTSP;
    private javax.swing.JTextField txtMaSanPham;
    private javax.swing.JTextField txtMaThuocTinh;
    private javax.swing.JTextField txtMax;
    private javax.swing.JTextField txtMin;
    private javax.swing.JTextField txtMoTa;
    private javax.swing.JTextField txtSlTon;
    private javax.swing.JTextField txtTK;
    private javax.swing.JTextField txtTK8;
    private javax.swing.JTextField txtTenSanPham;
    private javax.swing.JTextField txtTenThuocTinh;
    private javax.swing.JLabel txtmaSPCTQR;
    // End of variables declaration//GEN-END:variables

    private void init() {
        rdoKichThuoc.setSelected(true);
        fillTableSize(listSize);
        fillTable(listSP);

        txtMaThuocTinh.setEnabled(false);
        this.fillComboBoxDeGiay();
        this.fillComboBoxChatLieu();
        this.fillComboBoxHang();
        this.fillComboBoxMauSac();
        this.fillComboBoxSize();
        this.fillComboBoxTenSP();
        this.fillComboBoxChatLieu8();
        this.fillComboBoxHang8();
        this.fillComboBoxMauSac8();
        this.fillComboBoxSize8();
        this.fillComboBoxDeGiay8();
//        txtMaCTSP.setEnabled(false);
        txtMaSanPham.setEditable(false);
//        fillToTableALLSPCT(listSPCT);
    }

    private void stt() {
//        row = tblChiTietSanPham7.getSelectedRow();
//        int rowCount = tblChiTietSanPham7.getRowCount();
//        lblStt7.setText(row + 1 + " of " + rowCount);
    }

    private void searchTable() {
        String keyword = txtTK8.getText();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(dtm1);
        tblChiTietSanPham7.setRowSorter(sorter);

        if (keyword.trim().length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + keyword));
        }
    }

    private void searchInTableHang(String selectedValue) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(dtm1);
        tblChiTietSanPham7.setRowSorter(sorter);
        RowFilter<Object, Object> rf = RowFilter.regexFilter(selectedValue, 7);
        sorter.setRowFilter(rf);
    }

    private void searchInTableSP(String selectedValue) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(dtm1);
        tblChiTietSanPham7.setRowSorter(sorter);
        RowFilter<Object, Object> rf = RowFilter.regexFilter(selectedValue, 3);
        sorter.setRowFilter(rf);
    }

    private void searchInTableMauSac(String selectedValue) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(dtm1);
        tblChiTietSanPham7.setRowSorter(sorter);

        RowFilter<Object, Object> rf = RowFilter.regexFilter(selectedValue, 4);
        sorter.setRowFilter(rf);
    }

    private void searchInTableSize(String selectedValue) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(dtm1);
        tblChiTietSanPham7.setRowSorter(sorter);

        RowFilter<Object, Object> rf = RowFilter.regexFilter(selectedValue, 5);
        sorter.setRowFilter(rf);
    }

    private void searchInTableChatLieu(String selectedValue) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(dtm1);
        tblChiTietSanPham7.setRowSorter(sorter);

        RowFilter<Object, Object> rf = RowFilter.regexFilter(selectedValue, 6);
        sorter.setRowFilter(rf);
    }

    private void searchInTableDeGiay(String selectedValue) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(dtm1);
        tblChiTietSanPham7.setRowSorter(sorter);

        RowFilter<Object, Object> rf = RowFilter.regexFilter(selectedValue, 8);
        sorter.setRowFilter(rf);
    }

//    private void updateRowNumbers() {
//        row = dtm.getRowCount();
//        for (int i = 0; i < row; i++) {
//            dtm.setValueAt(i + 1, i, 0);
//        }
//    }
    private void deletedRows() {
        int row = tblChiTietSanPham7.getSelectedRow();
        SanPhamChiTiet spct = new SanPhamChiTiet();
        if (row < 0) {
            JOptionPane.showMessageDialog(null, "Chọn dòng cần xóa");
            return;
        }

        String ma = tblChiTietSanPham7.getValueAt(row, 2).toString();
        spct.setMaCTSP(ma);
        System.out.println(ma);
        try {
            int i = JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa ?", "Trả lời", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                spctd.delete(ma);
                JOptionPane.showMessageDialog(null, "Xóa thành công");
                fillToTableDaXoa(listSPCT);
                btnXoa7.setEnabled(false);
                btnKhoiPHuc7.setEnabled(true);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Xóa thất bại!");
        }

    }

    private void fillToTableDaXoa(ArrayList<SanPhamChiTiet> listspct) {
        dtm1.setRowCount(0);
        listspct = spctd.selectAllSPCTDaXoa();
        int size = listspct.size();
        for (int i = 0; i < size; i++) {
            SanPhamChiTiet spct = listspct.get(i);

            Object[] rowData = spct.toDataRow(i + 1);
            dtm1.addRow(rowData);

        }
    }

    void khoiPhuc() {
        row = tblChiTietSanPham7.getSelectedRow();
        SanPhamChiTiet spct = new SanPhamChiTiet();
        if (row < 0) {
            JOptionPane.showMessageDialog(null, "Chọn dòng cần khôi phục");
            return;
        }

        String ma = tblChiTietSanPham7.getValueAt(row, 2).toString();
        spct.setMaCTSP(ma);

        try {
            int i = JOptionPane.showConfirmDialog(null, "Bạn có muốn khôi phục?", "Trả lời", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                spctd.khoiPhuc(ma);
                JOptionPane.showMessageDialog(null, "Khôi thành công");

                CboTrangThai7.setSelectedItem("Active");
                fillToTableALLSPCT(listSPCT);
                btnXoa7.setEnabled(true);
                btnKhoiPHuc7.setEnabled(false);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Khôi phục thất bại!");
        }

    }

    public void phanTrang(int page) {
//        int limit = 3;
//        try ( Connection cn = DBcontext.getConnection();  PreparedStatement ps = cn.prepareStatement("select COUNT(*) from SanPham");  ResultSet rs = ps.executeQuery()) {
//            
//            int count = 0;
//            if (rs.next()) {
//                count = rs.getInt(1);
//            }
//            rs.close();
//            ps.close();
//            int totalPage = (int) Math.ceil(count / (double) limit);
//            String sqlPT ="""
//                          select SanPham.ID, SanPham.MaSP, SanPham.Ten, SanPham.TrangThai,SanPham.create_at,SanPham.create_by,SanPham.update_at,SanPham.update_by,SanPham.deleted, sum(SoLuongTon) AS 'SoLuong' from SanPhamChiTiet join SanPham on SanPhamChiTiet.ID_SanPham=SanPham.ID 
//                                               group by MaSP,SanPham.Ten,SanPham.TrangThai,SanPham.ID,SanPham.create_at,SanPham.create_by,SanPham.update_at,SanPham.update_by,SanPham.deleted limit
//                          """ +(page -1)*limit +" ,"+limit;
//            ps = cn.prepareStatement(sqlPT);
//            ResultSet rsPT = psPT.executeQuery();
//            
//            pagination1.setPagegination(page, totalPage);
//
//           
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        dtm.setRowCount(0);
//        listSP = spd.selectAll();
//        int size = listSP.size();
//        for (int i = 0; i < size; i++) {
//            SanPham sp = listSP.get(i);
//            dtm.addRow(sp.toDataRow(i + 1));
//
//        }
        try {
            int limit = 10;
            String sqlCount = """
                             select COUNT(*) from SanPham
                             """;
            PreparedStatement p = DBcontext.getConnection().prepareStatement(sqlCount);
            count = 0;
            ResultSet r = p.executeQuery();
            if (r.first()) {
                count = r.getInt(1);
            }
            r.close();
            p.close();
            int totalPage = (int) Math.ceil(count / (double) limit);
            String sql = """
                         select SanPham.ID, SanPham.MaSP, SanPham.Ten, SanPham.TrangThai,SanPham.create_at,SanPham.create_by,SanPham.update_at,SanPham.update_by,SanPham.deleted, sum(SoLuongTon) AS 'SoLuong' from SanPhamChiTiet join SanPham on SanPhamChiTiet.ID_SanPham=SanPham.ID 
                     group by MaSP,SanPham.Ten,SanPham.TrangThai,SanPham.ID,SanPham.create_at,SanPham.create_by,SanPham.update_at,SanPham.update_by,SanPham.deleted limit
                         """ + (page - 1) * limit + " ," + limit;
            p = DBcontext.getConnection().prepareStatement(sql);
            r = p.executeQuery();
            while (r.next()) {
//                 int id = r.getInt(1);
                String maSP = r.getString(9);
                String ten = r.getString(2);
                String trangThai = r.getString(3);
//                Date createAt = r.getDate(4);
//                String createBy = r.getString(5);
//                Date updateAt = r.getDate(6);
//                String updateBy = r.getString(7);
//                boolean deleted = r.getBoolean(8);
                int soLuong = r.getInt(10);
                dtm.addRow(new Object[]{maSP, ten, trangThai, soLuong});
            }
            r.close();
            p.close();
//            phanTrangChatLieu.setPagegination(page, totalPage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void fillComboBoxMauSac8() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboMauSac8.getModel();
        model.removeAllElements();
        model.addElement("Tất cả");
        ArrayList<MauSac> list = msd.selectAll();
        for (MauSac mauSac : list) {

            model.addElement(mauSac);
        }

    }

    void fillComboBoxSize8() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboKichThuoc8.getModel();
        model.removeAllElements();
        model.addElement("Tất cả");
        ArrayList<Size> list = sd.selectAll();
        for (Size size : list) {

            model.addElement(size);
        }

    }

    void fillComboBoxHang8() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboHang8.getModel();
        model.removeAllElements();
        model.addElement("Tất cả");
        ArrayList<HangGiay> list = hgd.selectAll();
        for (HangGiay hangGiay : list) {

            model.addElement(hangGiay);
        }

    }

    void fillComboBoxDeGiay8() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboDeGiay8.getModel();
        model.removeAllElements();
        model.addElement("Tất cả");
        ArrayList<DeGiay> list = dgd.selectAll();
        for (DeGiay deGiay : list) {
            model.addElement(deGiay);
        }

    }

    void fillComboBoxChatLieu8() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboChatLieu8.getModel();
        model.removeAllElements();
        model.addElement("Tất cả");
        ArrayList<ChatLieu> list = cld.selectAll();
        for (ChatLieu chatLieu : list) {

            model.addElement(chatLieu);
        }

    }

    void showdataCTSP() {

        row = tblChiTietSanPham7.getSelectedRow();
        if (row >= 0 && row < tblChiTietSanPham7.getRowCount()) {
            txtMaCTSP.setText(tblChiTietSanPham7.getValueAt(row, 2).toString());
            txtSlTon.setText(tblChiTietSanPham7.getValueAt(row, 11).toString());
            txtGiaBan.setText(tblChiTietSanPham7.getValueAt(row, 9).toString());
            txtMoTa.setText(tblChiTietSanPham7.getValueAt(row, 10).toString());
            cboHang.setSelectedItem(tblChiTietSanPham7.getValueAt(row, 7).toString());
            String value = (String) tblChiTietSanPham7.getValueAt(row, 6);
            setSelectedValue(cboChatLieu, value);
            String size = tblChiTietSanPham7.getValueAt(row, 5).toString();
            setSelectedSize(cboKichThuoc, size);
            String ms = (String) tblChiTietSanPham7.getValueAt(row, 4);
            setSelectedMS(cboMauSac, ms);
            String Tensp = (String) tblChiTietSanPham7.getValueAt(row, 3);
            setSelectedSP(cboTenSp, Tensp);
            String de = (String) tblChiTietSanPham7.getValueAt(row, 8);
            setSelectedDe(cboDe, de);
//            setSelectedDG(cboDe, de);
//            SanPham sp = (SanPham) listSP.get(row);
//            DeGiay dg = (DeGiay) listDG.get(row);
//            ChatLieu cl = (ChatLieu) listCL.get(row);
//            idSP.setText(sp.getId() + "");
//            IDChatLieu.setText(cl.getId() + "");
//            IDDeGiay.setText(dg.getId() + "");

        }
    }

    public static void setSelectedValue(JComboBox comboBox, String value) {
        ChatLieu item;
//        System.out.println("count" + comboBox.getItemCount() + "__-");
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            item = (ChatLieu) comboBox.getItemAt(i);
            if (item.getTenChatLieu().equalsIgnoreCase(value)) {
                comboBox.setSelectedIndex(i);
//                System.out.println("" + i + "__-");

                break;
            }
        }

    }

    public static void setSelectedSize(JComboBox comboBox, String value) {
        Size size;
        for (int j = 0; j < comboBox.getItemCount(); j++) {
            size = (Size) comboBox.getItemAt(j);
            if (size.getSize() == Integer.valueOf(value)) {
                comboBox.setSelectedIndex(j);
                break;
            }
        }
    }

    public static void setSelectedSP(JComboBox comboBox, String value) {
        SanPham sp;
        for (int j = 0; j < comboBox.getItemCount(); j++) {
            sp = (SanPham) comboBox.getItemAt(j);
            if (sp.getTen().equalsIgnoreCase(value)) {
                comboBox.setSelectedIndex(j);
                break;
            }
        }
    }

    public static void setSelectedDe(JComboBox comboBox, String value) {
        DeGiay dg;
        for (int j = 0; j < comboBox.getItemCount(); j++) {
            dg = (DeGiay) comboBox.getItemAt(j);
            if (dg.getTen().equalsIgnoreCase(value)) {
                comboBox.setSelectedIndex(j);
                break;
            }
        }
    }

    public static void setSelectedHang(JComboBox comboBox, String value) {
        HangGiay item;
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            item = (HangGiay) comboBox.getItemAt(i);
            if (item.getTen().equalsIgnoreCase(value)) {
                comboBox.setSelectedIndex(i);
                break;
            }
        }

    }

    public static void setSelectedDG(JComboBox comboBox, String value) {
        DeGiay item;
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            item = (DeGiay) comboBox.getItemAt(i);
            if (item.getTen().equalsIgnoreCase(value)) {
                comboBox.setSelectedIndex(i);
                break;
            }
        }

    }

    public static void setSelectedMS(JComboBox comboBox, String value) {
        MauSac ms;
//        System.out.println("count" + comboBox.getItemCount() + "__-");
        for (int k = 0; k < comboBox.getItemCount(); k++) {
            ms = (MauSac) comboBox.getItemAt(k);
            if (ms.getTenMau().equalsIgnoreCase(value)) {
                comboBox.setSelectedIndex(k);
//                System.out.println("" + i + "__-");

                break;
            }
        }
    }

    void insertSPCT() {
        SanPhamChiTiet spct = checkFormSPCT();

        if (spct != null) {
            int i = JOptionPane.showConfirmDialog(null, "Bạn có muốn thêm ?", "Trả lời", JOptionPane.YES_NO_OPTION);
            if (i == 0) {
                try {
                    spctd.insert(spct);

                    JOptionPane.showMessageDialog(null, "Thêm thành công!");
                    tab.setVisible(true);
                    AddUpdateSPCT.setVisible(false);
                    fillToTableALLSPCT(listSPCT);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Thêm thất bại!");
                }
            }
        }
    }

    SanPhamChiTiet checkFormSPCT() {
        SanPhamChiTiet spct = new SanPhamChiTiet();
//        row = tblDSSP.getSelectedRow();
//        String ma = tblDSSP.getValueAt(row, 1).toString();
//        
//        listSPCT = spctd.selectById(ma);
        SanPham tenSP = (SanPham) cboTenSp.getSelectedItem();
        MauSac ms = (MauSac) cboMauSac.getSelectedItem();
        Size kichThuoc = (Size) cboKichThuoc.getSelectedItem();
        HangGiay hang = (HangGiay) cboHang.getSelectedItem();
        ChatLieu chatLieu = (ChatLieu) cboChatLieu.getSelectedItem();
        DeGiay degiay = (DeGiay) cboDe.getSelectedItem();

        String maSPCT = txtMaCTSP.getText();
        String slTon = txtSlTon.getText();
        String gia = txtGiaBan.getText();
        String mota = txtMoTa.getText();
        if (slTon.isEmpty() || gia.isEmpty() || mota.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin");
            return null;
        }
        listSPCT = spctd.selectById(tenSP.getMaSP());
        for (SanPhamChiTiet spct2 : listSPCT) {
            if (spct2.getChatLieu().getId() == chatLieu.getId()
                    && spct2.getMauSac().getId() == ms.getId()
                    && spct2.getSize().getId() == kichThuoc.getId()
                    && spct2.getHang().getId() == hang.getId()
                    && spct2.getDeGiay().getId() == degiay.getId()) {
                JOptionPane.showMessageDialog(null, "Đã tốn tại !");
                return null;
            }
        }
        String check = "^[1-9]\\d*$";
        if (!slTon.matches(check) || !gia.matches(check)) {
            JOptionPane.showMessageDialog(null, "Vui lòng điền giá và số lượng số nguyên dương");
            return null;
        }
        spct.setMaCTSP(maSPCT);
        spct.setDeGiay(degiay);
        spct.setChatLieu(chatLieu);
        spct.setSp(tenSP);
        spct.setMauSac(ms);
        spct.setSize(kichThuoc);
        spct.setHang(hang);
        spct.setSoLuongTon(Integer.parseInt(slTon));
        spct.setGia(Integer.parseInt(gia));
        spct.setMoTa(mota);
        return spct;
    }

    SanPhamChiTiet checkUpdateSPCT() {
        SanPhamChiTiet spct = new SanPhamChiTiet();
//        row = tblDSSP.getSelectedRow();
//        String ma = tblDSSP.getValueAt(row, 1).toString();
//        listSPCT = spctd.selectById(ma);
//        MauSac ms = (MauSac) cboMauSac.getSelectedItem();
//        Size kichThuoc = (Size) cboKichThuoc.getSelectedItem();
//        HangGiay hang = (HangGiay) cboHang.getSelectedItem();
//        ChatLieu chatLieu = (ChatLieu) cboChatLieu.getSelectedItem();
//        DeGiay degiay = (DeGiay) cboDe.getSelectedItem();
//        String maSPCT = txtMaCTSP.getText();
//        SanPham sp = (SanPham) cboTenSp.getSelectedItem();
        String maSPCT = txtMaCTSP.getText();
        String slTon = txtSlTon.getText();
        String gia = txtGiaBan.getText();
        String mota = txtMoTa.getText();
        if (slTon.isEmpty() || gia.isEmpty() || mota.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin");
            return null;
        }
        String check = "^[1-9]\\d*$";
        if (!slTon.matches(check) || !gia.matches(check)) {
            JOptionPane.showMessageDialog(null, "Vui lòng điền giá và số lượng số nguyên dương");
            return null;
        }
        spct.setMaCTSP(maSPCT);
        spct.setSoLuongTon(Integer.parseInt(slTon));
        spct.setGia(Integer.parseInt(gia));
        spct.setMoTa(mota);
        return spct;
    }

    void updateSPCT() {
        SanPhamChiTiet spct = checkUpdateSPCT();
//        row = tblChiTietSanPham7.getSelectedRow();
        if (spct == null) {
            return;
        }
//        if (row == -1) {
//            return;
//        }
//        String ma = tblChiTietSanPham7.getValueAt(row, 2).toString();;
//        spct.setMaCTSP(ma);
        try {
            spctd.update(spct);

            JOptionPane.showMessageDialog(null, "Update thành công!");
            tab.setVisible(true);
            AddUpdateSPCT.setVisible(false);
            fillToTableSPCT();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "update thất bại!");
        }
    }

    public String getTenSP() {
        row = tblChiTietSanPham7.getSelectedRow();
        return tblChiTietSanPham7.getValueAt(row, 3).toString();
    }

    public String getMaSPCT() {
        row = tblChiTietSanPham7.getSelectedRow();
        return tblChiTietSanPham7.getValueAt(row, 2).toString();
    }

    public String getMS() {
        row = tblChiTietSanPham7.getSelectedRow();
        return tblChiTietSanPham7.getValueAt(row, 4).toString();
    }

    public String getKichThuoc() {
        row = tblChiTietSanPham7.getSelectedRow();
        return tblChiTietSanPham7.getValueAt(row, 5).toString();
    }

    public String getChatLieu() {
        row = tblChiTietSanPham7.getSelectedRow();
        return tblChiTietSanPham7.getValueAt(row, 6).toString();
    }

    public String getHang() {
        row = tblChiTietSanPham7.getSelectedRow();
        return tblChiTietSanPham7.getValueAt(row, 7).toString();
    }

    public String getDeGiay() {
        row = tblChiTietSanPham7.getSelectedRow();
        return tblChiTietSanPham7.getValueAt(row, 8).toString();
    }

    public String getGia() {
        row = tblChiTietSanPham7.getSelectedRow();
        return tblChiTietSanPham7.getValueAt(row, 9).toString();
    }

    public String getMoTa() {
        row = tblChiTietSanPham7.getSelectedRow();
        return tblChiTietSanPham7.getValueAt(row, 10).toString();
    }

    public String getSLTon() {
        row = tblChiTietSanPham7.getSelectedRow();
        return tblChiTietSanPham7.getValueAt(row, 11).toString();
    }

    public void tkMinMax() {
        listSPCT = spctd.selectAll();
        for (SanPhamChiTiet spct : listSPCT) {

        }
    }

    private void initWebcame() {
        Dimension size = WebcamResolution.QVGA.getSize();
        webcam = Webcam.getWebcams().get(0);
        if (webcam.isOpen()) {
            webcam.close();
            return;
        }
        webcam.setViewSize(size);
        panel = new WebcamPanel(webcam);
        panel.setPreferredSize(size);
        panel.setFPSDisplayed(true);
        jPanel3.add(panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 20, 160, 120));
        executor.execute(this);
        return;
    }

    @Override
    public void run() {
//        do {
//            try {
//                Thread.sleep(10);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            Result result = null;
//            BufferedImage image = null;
//            if (webcam.isOpen()) {
//                if ((image = webcam.getImage()) == null) {
//                    continue;
//                }
//            }
//            
//            LuminanceSource source = new BufferedImageLuminanceSource(image);
//            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
//            try {
//                result = new MultiFormatReader().decode(bitmap);
//            } catch (Exception e) {
//                //
//            }
//            if (result != null) {
//
////                txtmaSPCTQR.setText(result.getText());
//                XemSPCT xem = new XemSPCT(jFrame1, true);
//                ArrayList<SanPhamChiTiet> spct = spctd.selectAll();
//                for (SanPhamChiTiet sp : spct) {
//                    String ma = result.getText();
//                    if (ma.equals(sp.getMaCTSP())) {
//                        xem.setTenSP(sp.getSp().getTen());
//                        xem.setMaSPCT(sp.getMaCTSP());
//                        xem.setSLTon(sp.getSoLuongTon() + "");
//                        xem.setMS(sp.getMauSac().getTenMau());
//                        xem.setKichThuoc(sp.getSize().getSize() + "");
//                        xem.setChatLieu(sp.getChatLieu().getTenChatLieu());
//                        xem.setHang(sp.getHang().getTen());
//                        xem.setDeGiay(sp.getDeGiay().getTen());
//                        xem.setGia(sp.getGia() + "");
//                        xem.setMoTa(sp.getMoTa());
//                        xem.setVisible(true);
//                    }
//                }
//            }
//        } while (true);
//        boolean isXemSPCTShown = false;

//        Webcam webcam = Webcam.getDefault();
//        webcam.open();
//
//        boolean isXemSPCTShown = false;
//
//        do {
//            try {
//                Thread.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            Result result = null;
//            BufferedImage image = null;
//
//            if (webcam.isOpen() && !isXemSPCTShown) {
//                image = webcam.getImage();
//                if (image == null) {
//                    continue;
//                }
//
//                LuminanceSource source = new BufferedImageLuminanceSource(image);
//                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
//
//                try {
//                    result = new MultiFormatReader().decode(bitmap);
//                } catch (NotFoundException e) {
//                    // Không tìm thấy mã QR hoặc mã không hợp lệ
//                }
//
//                if (result != null) {
//                    String ma = result.getText();
//                    SwingUtilities.invokeLater(new Runnable() {
//                        public void run() {
//                            XemSPCT xem = new XemSPCT(jFrame1, true);
//                            ArrayList<SanPhamChiTiet> spct = spctd.selectAll();
//                            for (SanPhamChiTiet sp : spct) {
//                                if (ma.equals(sp.getMaCTSP())) {
//                                    xem.setTenSP(sp.getSp().getTen());
//                                    xem.setMaSPCT(sp.getMaCTSP());
//                                    xem.setSLTon(sp.getSoLuongTon() + "");
//                                    xem.setMS(sp.getMauSac().getTenMau());
//                                    xem.setKichThuoc(sp.getSize().getSize() + "");
//                                    xem.setChatLieu(sp.getChatLieu().getTenChatLieu());
//                                    xem.setHang(sp.getHang().getTen());
//                                    xem.setDeGiay(sp.getDeGiay().getTen());
//                                    xem.setGia(sp.getGia() + "");
//                                    xem.setMoTa(sp.getMoTa());
//
//                                    webcam.close(); // Vô hiệu hóa webcam
//
//                                    xem.addWindowListener(new WindowAdapter() {
//                                        @Override
//                                        public void windowClosed(WindowEvent e) {
//                                            initWebcame();
//                                            webcam.open(); // Khôi phục hoạt động của webcam khi form đóng lại
//                                        }
//                                    });
//
//                                    xem.setVisible(true);
//
//                                    break;
//                                }
//                            }
//                        }
//                    });
//                }
//            }
//        } while (true);
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, "My Thread");
        t.setDaemon(true);
        return t;
    }

    private void fillToTableALLSPCT(ArrayList<SanPhamChiTiet> listspct) {
        dtm1 = (DefaultTableModel) tblChiTietSanPham7.getModel();
        dtm1.setRowCount(0);
        listspct = spctd.selectAll();
        int size = listspct.size();
        for (int i = 0; i < size; i++) {
            SanPhamChiTiet spct = listspct.get(i);
            Object[] rowData = spct.toDataRow(i + 1);
            dtm1.addRow(rowData);
        }
        btnXoa7.setEnabled(true);
        btnKhoiPHuc7.setEnabled(false);

    }

    void tkSPTheoGia() {

        try {
            int min = Integer.parseInt(txtMin.getText());
            int max = Integer.parseInt(txtMax.getText());
            if (min < 0 || max < 0) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập số nguyên dương");
                return;
            }
            dtm1.setRowCount(0);
            listSPCT = spctd.selectByGia(min, max);

            if (listSPCT.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Không tìm thấy sản phẩm trong khoảng giá!");
            } else {
                int size = listSPCT.size();
                for (int i = 0; i < size; i++) {
                    SanPhamChiTiet spct = listSPCT.get(i);
                    Object[] rowData = spct.toDataRow(i + 1);
                    dtm1.addRow(rowData);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập số nguyên dương");
            return;
        }

    }

    void loadQR() {

        listSPCT = spctd.selectAllSPCTTheoMa(QuetQR.maSPCT);
        for (SanPhamChiTiet spct : listSPCT) {
            txtmaSPCTQR.setText(spct.getMaCTSP());
            System.out.println(spct.getMaCTSP());
        }

    }

    void checkAll() {
        int rowCount = dtm1.getRowCount(); // Số lượng hàng trong bảng
//        int columnCount = dtm1.getColumnCount(); // Số lượng cột trong bảng

        for (int i = 0; i < rowCount; i++) {
            dtm1.setValueAt(true, i, 0); // Đặt giá trị cột đầu tiên thành true tại hàng thứ i
        }
    }
}
