/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.edu.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import poly.edu.Model.KhuyenMai;
import poly.edu.Model.SanPhamChiTiet;
import poly.edu.uitility.DBcontext;

/**
 *
 * @author Admin
 */
public class VoucherService {

    public ArrayList<KhuyenMai> getAllKhuyenMai() {

        ArrayList<KhuyenMai> lst = new ArrayList<>();
        String sql = """
                   select * from Voucher where deleted =0 ORDER BY create_at DESC
                     """;
        Connection cn = (Connection) DBcontext.getConnection();
        try {
            //tao Statement
            PreparedStatement pstm = cn.prepareStatement(sql);
            //thi hanh Statement => dung Resultset nhan kq
            ResultSet rs = pstm.executeQuery();
            //xu ly kq => do du lieu vao lst
            while (rs.next()) {
                KhuyenMai k = new KhuyenMai();
                //h.setMaHoaDon(rs.getString("maHoaDon"));
//                n.setTrangThai(rs.getString("tenTrangThai"));  
                k.setId(rs.getInt("ID"));
                k.setMaKM(rs.getString("MaKM"));
                k.setTenVC(rs.getString("TenVC"));
                k.setHinhThucGiamGia(rs.getString("hinhThucGiamGia"));
                k.setMucGiamGia(rs.getString("mucGiamGia"));
                k.setSoLuong(rs.getInt("soLuong"));
                k.setNgayBatDau(rs.getDate("ngayBatDau"));
                k.setNgayKetThuc(rs.getDate("ngayKetThuc"));
                k.setNgayTao(rs.getDate("create_at"));
                k.setNgaySua(rs.getDate("update_at"));
                k.setNguoiSua(rs.getString("update_by"));
                lst.add(k);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return lst;
    }
public ArrayList<KhuyenMai> getAllKhuyenMaiDangDienRa() {

        ArrayList<KhuyenMai> lst = new ArrayList<>();
        String sql = """
                   select * from Voucher where deleted =0 AND trangThai LIKE N'Đang diễn ra' ORDER BY create_at DESC
                     """;
        Connection cn = (Connection) DBcontext.getConnection();
        try {
            //tao Statement
            PreparedStatement pstm = cn.prepareStatement(sql);
            //thi hanh Statement => dung Resultset nhan kq
            ResultSet rs = pstm.executeQuery();
            //xu ly kq => do du lieu vao lst
            while (rs.next()) {
                KhuyenMai k = new KhuyenMai();
                //h.setMaHoaDon(rs.getString("maHoaDon"));
//                n.setTrangThai(rs.getString("tenTrangThai"));  
                k.setId(rs.getInt("ID"));
                k.setMaKM(rs.getString("MaKM"));
                k.setTenVC(rs.getString("TenVC"));
                k.setHinhThucGiamGia(rs.getString("hinhThucGiamGia"));
                k.setMucGiamGia(rs.getString("mucGiamGia"));
                k.setSoLuong(rs.getInt("soLuong"));
                k.setNgayBatDau(rs.getDate("ngayBatDau"));
                k.setNgayKetThuc(rs.getDate("ngayKetThuc"));
                k.setNgayTao(rs.getDate("create_at"));
                k.setNgaySua(rs.getDate("update_at"));
                k.setNguoiSua(rs.getString("update_by"));
                lst.add(k);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return lst;
    }

    public ArrayList<KhuyenMai> getAllVoucherBanHang(int giaTriDH) {

        ArrayList<KhuyenMai> lst = new ArrayList<>();
        String sql = """
                   select * from Voucher where deleted =0 AND trangThai LIKE N'Đang diễn ra' AND ? >= giaTriDonHangToiThieu ORDER BY create_at DESC
                     """;
        Connection cn = (Connection) DBcontext.getConnection();
        try {
            //tao Statement
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setObject(1, giaTriDH);
            //thi hanh Statement => dung Resultset nhan kq
            ResultSet rs = pstm.executeQuery();
            //xu ly kq => do du lieu vao lst
            while (rs.next()) {
                KhuyenMai k = new KhuyenMai();
                //h.setMaHoaDon(rs.getString("maHoaDon"));
//                n.setTrangThai(rs.getString("tenTrangThai"));  
                k.setId(rs.getInt("ID"));
                k.setMaKM(rs.getString("MaKM"));
                k.setTenVC(rs.getString("TenVC"));
                k.setHinhThucGiamGia(rs.getString("hinhThucGiamGia"));
                k.setMucGiamGia(rs.getString("mucGiamGia"));
                k.setSoLuong(rs.getInt("soLuong"));
                k.setNgayBatDau(rs.getDate("ngayBatDau"));
                k.setNgayKetThuc(rs.getDate("ngayKetThuc"));
                k.setNgayTao(rs.getDate("create_at"));
                k.setNgaySua(rs.getDate("update_at"));
                k.setNguoiSua(rs.getString("update_by"));
                lst.add(k);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return lst;
    }

    public ArrayList<KhuyenMai> getAllSPCTmaKM(String maKM) {

        ArrayList<KhuyenMai> lst = new ArrayList<>();
        String sql = """
                   					 select GiamGia_SPCT.deleted,spct.MaSPCT, sp.Ten,h.Ten,ms.TenMau,s.Size,cl.TenChatLieu,dg.Ten,spct.Gia
                                        ,spct.ID,v.ID from GiamGia_SPCT
                                      join Voucher v on v.ID = GiamGia_SPCT.id_Voucher
                                      join SanPhamChiTiet spct on GiamGia_SPCT.id_SPCT = spct.ID
                                      join SanPham sp ON sp.ID = spct.ID_SanPham
                                                           join MauSac ms on ms.ID = spct.ID_MauSac
                                                           join Size s on s.ID = spct.ID_Size
                                                           join Hang h on h.ID = spct.ID_Hang
                                                           join ChatLieu cl on cl.ID = spct.ID_ChatLieu
                                                           join DeGiay dg on dg.ID = spct.ID_DeGiay  
                                      					 where v.maKM = ? AND GiamGia_SPCT.deleted = 1
                     """;
        Connection cn = (Connection) DBcontext.getConnection();
        try {
            //tao Statement
            PreparedStatement pstm = cn.prepareStatement(sql);
            //thi hanh Statement => dung Resultset nhan kq
            pstm.setObject(1, maKM);
            ResultSet rs = pstm.executeQuery();
            //xu ly kq => do du lieu vao lst
            while (rs.next()) {
                KhuyenMai k = new KhuyenMai();
                //h.setMaHoaDon(rs.getString("maHoaDon"));
//                n.setTrangThai(rs.getString("tenTrangThai"));  
                k.setDeleted(rs.getBoolean(1));
                k.setMaSPCT(rs.getString("MaSPCT"));
                k.setTenSP(rs.getString(3));
                k.setGiaBan(rs.getInt("Gia"));
                k.setTenHang(rs.getString(4));
                k.setMauSac(rs.getString(5));
                k.setSize(rs.getInt(6));
                k.setChatLieu(rs.getString("TenChatLieu"));
                k.setDeGiay(rs.getString(8));
                k.setId_SPCT(rs.getInt(9));
                k.setId_Voucher(10);
                lst.add(k);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return lst;
    }

    public ArrayList<KhuyenMai> getAllKhuyenMaiSP() {

        ArrayList<KhuyenMai> lst = new ArrayList<>();
        String sql = """
                   select SanPhamChiTiet.MaSPCT, SanPham.Ten, HoaDonChiTiet.gia,Hang.Ten,MauSac.TenMau, Size.Size,ChatLieu.TenChatLieu, DeGiay.Ten, SanPhamChiTiet.deleted
                                      from SanPhamChiTiet join HoaDonChiTiet on SanPhamChiTiet.ID = HoaDonChiTiet.ID_SanPhamChiTiet
                                      							join SanPham on SanPhamChiTiet.ID_SanPham = SanPham. id
                                      							join Hang on SanPhamChiTiet.ID_Hang = Hang.ID
                                      							join MauSac on SanPhamChiTiet.ID_MauSac = MauSac.ID
                                      							join ChatLieu on SanPhamChiTiet.ID_ChatLieu = ChatLieu.ID
                                      							join Size on SanPhamChiTiet.ID_Size = Size.ID
                   											join DeGiay on SanPhamChiTiet.ID_DeGiay = DeGiay.ID
                     """;
        Connection cn = (Connection) DBcontext.getConnection();
        try {
            //tao Statement
            PreparedStatement pstm = cn.prepareStatement(sql);
            //thi hanh Statement => dung Resultset nhan kq
            ResultSet rs = pstm.executeQuery();
            //xu ly kq => do du lieu vao lst
            while (rs.next()) {
                KhuyenMai k = new KhuyenMai();
                //h.setMaHoaDon(rs.getString("maHoaDon"));
//                n.setTrangThai(rs.getString("tenTrangThai"));  
                k.setMaSPCT(rs.getString("MaSPCT"));
                k.setTenSP(rs.getString("Ten"));
                k.setGiaBan(rs.getInt("gia"));
                k.setTenHang(rs.getString("Ten"));
                k.setMauSac(rs.getString("TenMau"));
                k.setSize(rs.getInt("Size"));
                k.setChatLieu(rs.getString("TenChatLieu"));
                k.setDeGiay(rs.getString(8));
                k.setDeleted(rs.getBoolean(9));
                lst.add(k);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return lst;
    }

    public void updateKM(KhuyenMai km) {
        int row = 0;
        String sql = """
                     UPDATE    GiamGia_SPCT
                     SET             deleted = 1
                    
                     """;
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setObject(1, km.getTenVC());
            pstm.setObject(2, km.getHinhThucGiamGia());
            pstm.setObject(3, km.getMucGiamGia());
            pstm.setObject(4, km.getNgayBatDau());
            pstm.setObject(5, km.getNgayKetThuc());
            pstm.setObject(6, km.getNguoiTao());
            pstm.setObject(7, km.getNguoiSua());
            pstm.setObject(8, km.getMaKM());
            row = pstm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void insertKM(KhuyenMai km) {
        String voucherInsertSql = """
    INSERT INTO Voucher
    (maKM, ID_HoaDon, TenVC, hinhThucGiamGia, mucGiamGia, soLuong, NgayBatDau, NgayKetThuc, create_at, create_by, update_at, update_by, deleted)
    VALUES (?, 0, ?, ?, ?, 17, ?, ?, GETDATE(), ?, GETDATE(), ?, 0)
""";
        String giamGiaInsertSql = """
    INSERT INTO GiamGia_SPCT
    (id_SPCT, id_Voucher, soTienGiam, trangThai, create_At, create_By, update_at, update_By, deleted)
    VALUES (?, ?, ?, ?, GETDATE(), ?, GETDATE(), ?, ?)
                                 
""";

        Connection connection = DBcontext.getConnection();

        try {
            // Insert into Voucher table
            PreparedStatement voucherInsertStatement = connection.prepareStatement(voucherInsertSql, PreparedStatement.RETURN_GENERATED_KEYS);
            voucherInsertStatement.setObject(1, km.getMaKM());
            voucherInsertStatement.setObject(2, km.getTenVC());
            voucherInsertStatement.setObject(3, km.getHinhThucGiamGia());
            voucherInsertStatement.setObject(4, km.getMucGiamGia());
            voucherInsertStatement.setObject(5, km.getNgayBatDau());
            voucherInsertStatement.setObject(6, km.getNgayKetThuc());
            voucherInsertStatement.setObject(7, km.getNguoiTao());
            voucherInsertStatement.setObject(8, km.getNguoiSua());

            int voucherInsertRowCount = voucherInsertStatement.executeUpdate();

            // Get the generated ID from Voucher table
            if (voucherInsertRowCount > 0) {
                ResultSet generatedKeys = voucherInsertStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);

                    // Insert into GiamGia_SPCT table using the generated ID
                    PreparedStatement giamGiaInsertStatement = connection.prepareStatement(giamGiaInsertSql);
                    SanPhamChiTiet spct = new SanPhamChiTiet();
                    giamGiaInsertStatement.setObject(1, spct.getId());
                    giamGiaInsertStatement.setObject(2, generatedId); // Use the generated ID
                    giamGiaInsertStatement.setObject(3, km.getSoTienGiam());
                    giamGiaInsertStatement.setObject(4, km.getTrangThai());
                    giamGiaInsertStatement.setObject(5, km.getNguoiTao());
                    giamGiaInsertStatement.setObject(6, km.getNguoiSua());
                    giamGiaInsertStatement.setObject(7, km.isDeleted());
                    int giamGiaInsertRowCount = giamGiaInsertStatement.executeUpdate();

                    // Handle the result of the GiamGia_SPCT insert statement if needed
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void insertKM_SPCT(KhuyenMai km) {

        String giamGiaInsertSql = """
    INSERT INTO GiamGia_SPCT
    (id_SPCT, id_Voucher, soTienGiam, trangThai, create_At, create_By, update_at, update_By, deleted)
    VALUES (?, ?, ?, ?, GETDATE(), ?, GETDATE(), ?, ?)
""";

        Connection connection = DBcontext.getConnection();
        try {

            PreparedStatement giamGiaInsertStatement = connection.prepareStatement(giamGiaInsertSql);
            giamGiaInsertStatement.setObject(1, km.getId_SPCT());
            giamGiaInsertStatement.setObject(2, km.getId_Voucher()); // Use the generated ID
            giamGiaInsertStatement.setObject(3, km.getSoTienGiam());
            giamGiaInsertStatement.setObject(4, km.getTrangThai());
            giamGiaInsertStatement.setObject(5, km.getNguoiTao());
            giamGiaInsertStatement.setObject(6, km.getNguoiSua());
            giamGiaInsertStatement.setObject(7, km.isDeleted());
            int giamGiaInsertRowCount = giamGiaInsertStatement.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updateSPApKM(KhuyenMai km) {
        int row = 0;
        String sql = """
                     UPDATE    GiamGia_SPCT
                     SET            deleted = ?
                     WHERE id_SPCT = ? 
                     """;
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setObject(1, km.isDeleted());
            pstm.setObject(2, km.getId_SPCT());
            row = pstm.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<KhuyenMai> getAllSPCTKm() {

        ArrayList<KhuyenMai> lst = new ArrayList<>();
        String sql = """
                   SELECT spct.deleted, spct.MaSPCT, sp.Ten, h.Ten, ms.TenMau, s.Size, cl.TenChatLieu, dg.Ten, spct.Gia,
                          spct.ID, NULL AS VoucherID, spct.create_at
                   FROM SanPhamChiTiet spct
                   JOIN SanPham sp ON sp.ID = spct.ID_SanPham
                   JOIN MauSac ms ON ms.ID = spct.ID_MauSac
                   JOIN Size s ON s.ID = spct.ID_Size
                   JOIN Hang h ON h.ID = spct.ID_Hang
                   JOIN ChatLieu cl ON cl.ID = spct.ID_ChatLieu
                   JOIN DeGiay dg ON dg.ID = spct.ID_DeGiay
                   WHERE spct.deleted = 0 AND spct.SoLuongTon != 0
                     AND NOT EXISTS (
                       SELECT 1
                       FROM GiamGia_SPCT gs
                       WHERE gs.id_SPCT = spct.ID
                     )
                   ORDER BY spct.create_at DESC, spct.ID
                     """;
        Connection cn = (Connection) DBcontext.getConnection();
        try {
            //tao Statement
            PreparedStatement pstm = cn.prepareStatement(sql);
            //thi hanh Statement => dung Resultset nhan kq        
            ResultSet rs = pstm.executeQuery();
            //xu ly kq => do du lieu vao lst
            while (rs.next()) {
                KhuyenMai k = new KhuyenMai();
                //h.setMaHoaDon(rs.getString("maHoaDon"));
//                n.setTrangThai(rs.getString("tenTrangThai"));  
                k.setDeleted(rs.getBoolean(1));
                k.setMaSPCT(rs.getString("MaSPCT"));
                k.setTenSP(rs.getString(3));
                k.setGiaBan(rs.getInt("Gia"));
                k.setTenHang(rs.getString(4));
                k.setMauSac(rs.getString(5));
                k.setSize(rs.getInt(6));
                k.setChatLieu(rs.getString("TenChatLieu"));
                k.setDeGiay(rs.getString(8));
                k.setId_SPCT(rs.getInt(9));
                k.setId_Voucher(10);
                lst.add(k);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return lst;
    }

    public ArrayList<KhuyenMai> getAllSPCTDuocApDungKM() {

        ArrayList<KhuyenMai> lst = new ArrayList<>();
        String sql = """
                   SELECT spct.deleted, spct.MaSPCT, sp.Ten, h.Ten, ms.TenMau, s.Size, cl.TenChatLieu, dg.Ten, spct.Gia,
                          spct.ID, NULL AS VoucherID, spct.create_at
                   FROM SanPhamChiTiet spct
                   JOIN SanPham sp ON sp.ID = spct.ID_SanPham
                   JOIN MauSac ms ON ms.ID = spct.ID_MauSac
                   JOIN Size s ON s.ID = spct.ID_Size
                   JOIN Hang h ON h.ID = spct.ID_Hang
                   JOIN ChatLieu cl ON cl.ID = spct.ID_ChatLieu
                   JOIN DeGiay dg ON dg.ID = spct.ID_DeGiay
                   WHERE spct.deleted = 1 AND spct.SoLuongTon != 0
                     AND NOT EXISTS (
                       SELECT 1
                       FROM GiamGia_SPCT gs
                       WHERE gs.id_SPCT = spct.ID
                     )
                   ORDER BY spct.create_at DESC, spct.ID
                     """;
        Connection cn = (Connection) DBcontext.getConnection();
        try {
            //tao Statement
            PreparedStatement pstm = cn.prepareStatement(sql);
            //thi hanh Statement => dung Resultset nhan kq        
            ResultSet rs = pstm.executeQuery();
            //xu ly kq => do du lieu vao lst
            while (rs.next()) {
                KhuyenMai k = new KhuyenMai();
                //h.setMaHoaDon(rs.getString("maHoaDon"));
//                n.setTrangThai(rs.getString("tenTrangThai"));  
                k.setDeleted(rs.getBoolean(1));
                k.setMaSPCT(rs.getString("MaSPCT"));
                k.setTenSP(rs.getString(3));
                k.setGiaBan(rs.getInt("Gia"));
                k.setTenHang(rs.getString(4));
                k.setMauSac(rs.getString(5));
                k.setSize(rs.getInt(6));
                k.setChatLieu(rs.getString("TenChatLieu"));
                k.setDeGiay(rs.getString(8));
                k.setId_SPCT(rs.getInt(9));
                k.setId_Voucher(10);
                lst.add(k);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return lst;
    }

    public ArrayList<KhuyenMai> getAllSPCTKmTheoID(int id_spct) {

        ArrayList<KhuyenMai> lst = new ArrayList<>();
        String sql = """
                   SELECT spct.deleted, spct.MaSPCT, sp.Ten, h.Ten, ms.TenMau, s.Size, cl.TenChatLieu, dg.Ten, spct.Gia,
                          spct.ID, NULL AS VoucherID, spct.create_at
                   FROM SanPhamChiTiet spct
                   JOIN SanPham sp ON sp.ID = spct.ID_SanPham
                   JOIN MauSac ms ON ms.ID = spct.ID_MauSac
                   JOIN Size s ON s.ID = spct.ID_Size
                   JOIN Hang h ON h.ID = spct.ID_Hang
                   JOIN ChatLieu cl ON cl.ID = spct.ID_ChatLieu
                   JOIN DeGiay dg ON dg.ID = spct.ID_DeGiay
                   WHERE spct.deleted = 0 AND spct.SoLuongTon != 0 AND spct.ID = ?
                     AND NOT EXISTS (
                       SELECT 1
                       FROM GiamGia_SPCT gs
                       WHERE gs.id_SPCT = spct.ID
                     )
                   ORDER BY spct.create_at DESC, spct.ID
                     """;
        Connection cn = (Connection) DBcontext.getConnection();
        try {
            //tao Statement
            PreparedStatement pstm = cn.prepareStatement(sql);
            //thi hanh Statement => dung Resultset nhan kq     
            pstm.setObject(1, id_spct);
            ResultSet rs = pstm.executeQuery();
            //xu ly kq => do du lieu vao lst
            while (rs.next()) {
                KhuyenMai k = new KhuyenMai();
                //h.setMaHoaDon(rs.getString("maHoaDon"));
//                n.setTrangThai(rs.getString("tenTrangThai"));  
                k.setDeleted(rs.getBoolean(1));
                k.setMaSPCT(rs.getString("MaSPCT"));
                k.setTenSP(rs.getString(3));
                k.setGiaBan(rs.getInt("Gia"));
                k.setTenHang(rs.getString(4));
                k.setMauSac(rs.getString(5));
                k.setSize(rs.getInt(6));
                k.setChatLieu(rs.getString("TenChatLieu"));
                k.setDeGiay(rs.getString(8));
                k.setId_SPCT(rs.getInt(9));
                k.setId_Voucher(10);
                lst.add(k);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return lst;
    }

    public ArrayList<KhuyenMai> getAllVoucher() {

        ArrayList<KhuyenMai> lst = new ArrayList<>();
        String sql = """
                   select * from Voucher where deleted = 0   ORDER BY create_at DESC 
                     """;
        Connection cn = (Connection) DBcontext.getConnection();
        try {
            //tao Statement
            PreparedStatement pstm = cn.prepareStatement(sql);
            //thi hanh Statement => dung Resultset nhan kq        
            ResultSet rs = pstm.executeQuery();
            //xu ly kq => do du lieu vao lst
            while (rs.next()) {
                KhuyenMai k = new KhuyenMai();
                //h.setMaHoaDon(rs.getString("maHoaDon"));
//                n.setTrangThai(rs.getString("tenTrangThai"));  
                k.setId_Voucher(rs.getInt(1));
                k.setMaKM(rs.getString(2));
                k.setTenVC(rs.getString(3));
                k.setHinhThucGiamGia(rs.getString(4));
                k.setMucGiamGia(rs.getString(5));
                k.setGiaTriDHToiThieu(rs.getInt(6));
                k.setSoLuong(rs.getInt(7));
                k.setNgayBatDau(rs.getDate(8));
                k.setNgayKetThuc(rs.getDate(9));
                k.setDeleted(rs.getBoolean(14));
                k.setTrangThai(rs.getString("trangThai"));
                lst.add(k);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return lst;
    }

    public ArrayList<KhuyenMai> getAllVoucherDaXoa() {

        ArrayList<KhuyenMai> lst = new ArrayList<>();
        String sql = """
                   select * from Voucher where deleted = 1   ORDER BY create_at DESC 
                     """;
        Connection cn = (Connection) DBcontext.getConnection();
        try {
            //tao Statement
            PreparedStatement pstm = cn.prepareStatement(sql);
            //thi hanh Statement => dung Resultset nhan kq        
            ResultSet rs = pstm.executeQuery();
            //xu ly kq => do du lieu vao lst
            while (rs.next()) {
                KhuyenMai k = new KhuyenMai();
                //h.setMaHoaDon(rs.getString("maHoaDon"));
//                n.setTrangThai(rs.getString("tenTrangThai"));  
                k.setId_Voucher(rs.getInt(1));
                k.setMaKM(rs.getString(2));
                k.setTenVC(rs.getString(3));
                k.setHinhThucGiamGia(rs.getString(4));
                k.setMucGiamGia(rs.getString(5));
                k.setGiaTriDHToiThieu(rs.getInt(6));
                k.setSoLuong(rs.getInt(7));
                k.setNgayBatDau(rs.getDate(8));
                k.setNgayKetThuc(rs.getDate(9));
                k.setDeleted(rs.getBoolean(14));
                k.setTrangThai(rs.getString("trangThai"));
                lst.add(k);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return lst;
    }

    public void insertVoucher(KhuyenMai km) {

        String giamGiaInsertSql = """
                                    INSERT INTO Voucher
                                   (maKM, TenVC, hinhThucGiamGia, mucGiamGia, giaTriDonHangToiThieu, soLuong, NgayBatDau,
                                   NgayKetThuc, create_at, create_by, update_at, update_by, deleted, trangThai)
                                    VALUES       (?,?,?,?,?,?,?,?,GETDATE(),?,GETDATE(),?,0,?)
""";

        Connection connection = DBcontext.getConnection();
        try {

            PreparedStatement ps = connection.prepareStatement(giamGiaInsertSql);
            ps.setObject(1, km.getMaKM());
            ps.setObject(2, km.getTenVC()); // Use the generated ID
            ps.setObject(3, km.getHinhThucGiamGia());
            ps.setObject(4, km.getMucGiamGia());
            ps.setObject(5, km.getGiaTriDHToiThieu());
            ps.setObject(6, km.getSoLuong());
            ps.setObject(7, km.getNgayBatDau());
            ps.setObject(8, km.getNgayKetThuc());
            ps.setObject(9, km.getNguoiTao());
            ps.setObject(10, km.getNguoiSua());
            ps.setObject(11, km.getTrangThai());
            int row = ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updateVoucher(KhuyenMai km) {
        int row = 0;
        String sql = """
                     UPDATE    Voucher
                     SET             TenVC =?, hinhThucGiamGia =?, mucGiamGia =?, giaTriDonHangToiThieu =?, 
                     soLuong =?, NgayBatDau =?, NgayKetThuc =?, update_at = GETDATE(), update_by =?, deleted =?, trangThai =?
                     WHERE maKM = ?
                     """;
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setObject(11, km.getMaKM());
            ps.setObject(1, km.getTenVC()); // Use the generated ID
            ps.setObject(2, km.getHinhThucGiamGia());
            ps.setObject(3, km.getMucGiamGia());
            ps.setObject(4, km.getGiaTriDHToiThieu());
            ps.setObject(5, km.getSoLuong());
            ps.setObject(6, km.getNgayBatDau());
            ps.setObject(7, km.getNgayKetThuc());
            ps.setObject(8, km.getNguoiSua());
            ps.setObject(9, km.isDeleted());
            ps.setObject(10, km.getTrangThai());
            row = ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteVoucher(KhuyenMai km) {
        int row = 0;
        String sql = """
                     UPDATE    Voucher
                     SET      deleted =1 , trangThai = ?
                     WHERE maKM = ?
                     """;
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setObject(2, km.getMaKM());
            ps.setObject(1, km.getTrangThai());
            row = ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
