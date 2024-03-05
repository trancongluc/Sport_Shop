/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.edu.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import poly.edu.Model.HoaDon;
import poly.edu.uitility.DBcontext;

/**
 *
 * @author Admin
 */
public class HoaDonService {

    //private ArrayList<HoaDon> list = new ArrayList<>();
    public ArrayList<HoaDon> getAllHoaDonChoThanhToan() {

        ArrayList<HoaDon> lst = new ArrayList<>();
        String sql = """
              SELECT HoaDon.ID, MaHD, NgayGiaoDich,NhanVien.TenNV,TenKH, SUM(hdct.soLuong) AS TongSoLuongSanPham,HinhThucThanhToan,trangThai,
              HoaDon.create_at, HoaDon.update_at, ngayNhan, ngayXacNhan,tongTien,PhiShip,MaNV,TenNV,HoaDon.create_by
               FROM HoaDon
              JOIN NhanVien ON HoaDon.ID_NhanVien = NhanVien.id
              left JOIN HoaDonChiTiet hdct ON hdct.ID_HoaDon = HoaDon.ID
              WHERE HoaDon.trangThai LIKE N'%Chờ Thanh Toán%'
              GROUP BY HoaDon.ID, MaHD, NgayGiaoDich,NhanVien.TenNV,TenKH,HinhThucThanhToan,trangThai,HoaDon.create_at, HoaDon.update_at, ngayNhan
              , ngayXacNhan,tongTien,PhiShip,MaNV,TenNV,HoaDon.create_by
                     ORDER BY HoaDon.create_at DESC
                     """;
        Connection cn = (Connection) DBcontext.getConnection();
        try {
            //tao Statement
            PreparedStatement pstm = cn.prepareStatement(sql);
            //thi hanh Statement => dung Resultset nhan kq
            ResultSet rs = pstm.executeQuery();
            //xu ly kq => do du lieu vao lst
            while (rs.next()) {
                HoaDon h = new HoaDon();
                //h.setMaHoaDon(rs.getString("maHoaDon"));
//                n.setTrangThai(rs.getString("tenTrangThai")); 
                h.setId(rs.getInt(1));
                h.setMaHoaDon(rs.getString("maHD"));
                h.setTenKH(rs.getString("TenKH"));
                h.setNgayTao(rs.getDate("create_at"));
                h.setNgayThanhToan(rs.getDate("ngayNhan"));
                h.setTenNV(rs.getString("TenNV"));
                h.setNgayXacNhan(rs.getDate("ngayXacNhan"));
                h.setSoLuong(rs.getInt(6));
                h.setTongTien(rs.getInt("tongTien"));
                h.setHinhThucThanhToan(rs.getString("HinhThucThanhToan"));
                h.setTrangThai(rs.getString("trangThai"));
                h.setMaNV(rs.getString("MaNV"));
                h.setNguoiTao(rs.getString("create_by"));
//                h.setKichCo(rs.getInt("size"));
//                h.setThuongHieu(rs.getString("Ten"));
//                h.setMaSP(rs.getString("MaSPCT"));
                lst.add(h);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return lst;

    }

    public ArrayList<HoaDon> HoaDonChiTiet(String MaHD) {
        ArrayList<HoaDon> lst = new ArrayList<>();
        String sql = """
                     SELECT *
                                          FROM HoaDon
                                          JOIN KhachHang ON HoaDon.ID_KhachHang = KhachHang.ID 
                                          JOIN HoaDonChiTiet ON HoaDon.ID = HoaDonChiTiet.ID_HoaDon
                                          JOIN SanPhamChiTiet ON HoaDonChiTiet.ID_SanPhamChiTiet = SanPhamChiTiet.ID
                                          JOIN SanPham ON SanPhamChiTiet.ID_SanPham = SanPham.ID
                                          join NhanVien on HoaDon.ID_NhanVien = NhanVien.ID
                                          join MauSac ms on ms.ID = SanPhamChiTiet.ID_MauSac
                                          join Size s on s.ID = SanPhamChiTiet.ID_Size
                                          join Hang h on h.ID = SanPhamChiTiet.ID_Hang
                                          join ChatLieu cl on cl.ID = SanPhamChiTiet.ID_ChatLieu
                                          join DeGiay dg on dg.ID = SanPhamChiTiet.ID_DeGiay
                     where MaHD = ?
                     """;

        try ( Connection cn = DBcontext.getConnection();  PreparedStatement pstm = cn.prepareStatement(sql)) {

            pstm.setString(1, MaHD);

            try ( ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    HoaDon hoaDon = new HoaDon();
                    hoaDon.setMaHoaDon(rs.getString("maHD"));
                    hoaDon.setMaNV(rs.getString("maNV"));
                    // hoaDon.setNgayGiaoDich(rs.getDate("NgayGiaoDich"));
                    hoaDon.setTongTien(rs.getInt("tongTien"));
                    hoaDon.setTrangThai(rs.getString("trangThai"));
                    hoaDon.setTenSP(rs.getString(65));
                    hoaDon.setHang(rs.getString(106));
                    hoaDon.setChatLieu(rs.getString("TenChatLieu"));
                    hoaDon.setDeGiay(rs.getString(122));
                    hoaDon.setGiaSP(rs.getInt("gia"));
                    hoaDon.setKichCo(rs.getInt("Size"));
                    hoaDon.setMausac(rs.getString("TenMau"));
                    hoaDon.setSoLuong(rs.getInt("soLuong"));
                    hoaDon.setNgayXacNhan(rs.getDate("ngayXacNhan"));
                    hoaDon.setTenNV(rs.getString("tenNV"));
                    lst.add(hoaDon);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lst;
    }

    public Integer updateHoaDon(String maHD) {
        Integer row = null;
        String sql = """
                        update HoaDon
                        set deleted = 1
                        where MaHD =?
                     """;
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setString(1, maHD);

            row = pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    public ArrayList<HoaDon> getAllSanPham() {

        ArrayList<HoaDon> lst = new ArrayList<>();
        String sql = """
                   select * from HoaDon join NhanVien on HoaDon.ID_NhanVien = NhanVien.ID
                                      					join HoaDonChiTiet on HoaDon.ID = HoaDonChiTiet.ID_HoaDon
                                      					join SanPhamChiTiet on HoaDonChiTiet.ID_SanPhamChiTiet = SanPhamChiTiet.ID
                                      					join Hang on SanPhamChiTiet.ID_Hang = Hang.ID
                                      					join Size on SanPhamChiTiet.ID_Size = SanPhamChiTiet.ID_Size where HoaDon.deleted=0
                     """;
        Connection cn = (Connection) DBcontext.getConnection();
        try {
            //tao Statement
            PreparedStatement pstm = cn.prepareStatement(sql);
            //thi hanh Statement => dung Resultset nhan kq
            ResultSet rs = pstm.executeQuery();
            //xu ly kq => do du lieu vao lst
            while (rs.next()) {
                HoaDon h = new HoaDon();
                h.setMaHoaDon(rs.getString("maHD"));
                h.setTenKH(rs.getString("TenKH"));
                h.setNgayTao(rs.getDate("create_at"));
                h.setNgayThanhToan(rs.getDate("ngayNhan"));
                h.setNgayXacNhan(rs.getDate("ngayXacNhan"));
                h.setTongTien(rs.getInt("tongTien"));
                h.setHinhThucThanhToan(rs.getString("HinhThucThanhToan"));
                h.setTrangThai(rs.getString("trangThai"));
                h.setMaNV(rs.getString("MaNV"));
                h.setNguoiTao(rs.getString("create_by"));
                h.setKichCo(rs.getInt("size"));
                h.setThuongHieu(rs.getString("Ten"));
                h.setMaSP(rs.getString("MaSPCT"));
                lst.add(h);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return lst;

    }

    public ArrayList<HoaDon> getToanSanPham(String MaHD) {

        ArrayList<HoaDon> lst = new ArrayList<>();
        String sql = """
                   select * from HoaDon
                                            join HoaDonChiTiet on HoaDon.ID = HoaDonChiTiet.ID_HoaDon
                                            join SanPhamChiTiet on HoaDonChiTiet.ID_SanPhamChiTiet = SanPhamChiTiet.ID
                                            join Hang on SanPhamChiTiet.ID_Hang = Hang.ID
                                            join Size on SanPhamChiTiet.ID_Size = Size.ID and HoaDon.maHD = ?
                     """;
        Connection cn = (Connection) DBcontext.getConnection();
        try {
            //tao Statement

            PreparedStatement pstm = cn.prepareStatement(sql);
            //thi hanh Statement => dung Resultset nhan kq
            pstm.setString(1, MaHD);
            ResultSet rs = pstm.executeQuery();
            //xu ly kq => do du lieu vao lst
            while (rs.next()) {
                HoaDon h = new HoaDon();
                h.setMaHoaDon(rs.getString("MaHD"));
                h.setTenKH(rs.getString("TenKH"));
                h.setNgayTao(rs.getDate("create_at"));
                h.setNgayThanhToan(rs.getDate("ngayNhan"));
                h.setNgayXacNhan(rs.getDate("ngayXacNhan"));
                h.setTongTien(rs.getInt("tongTien"));
                h.setHinhThucThanhToan(rs.getString("HinhThucThanhToan"));
                h.setTrangThai(rs.getString("trangThai"));
                // h.setMaNV(rs.getString("MaNV"));
                h.setNguoiTao(rs.getString("create_by"));
                h.setKichCo(rs.getInt("size"));
                h.setThuongHieu(rs.getString("Ten"));
                h.setMaSP(rs.getString("MaSPCT"));
                h.setSoLuong(rs.getInt("soLuong"));
                lst.add(h);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return lst;

    }

    public Integer updateSanPham(String maHD) {
        Integer row = null;
        String sql = """
                        UPDATE HoaDon
                        JOIN HoaDonChiTiet ON HoaDon.ID = HoaDonChiTiet.ID_HoaDon
                        JOIN SanPhamChiTiet ON HoaDonChiTiet.ID_SanPhamChiTiet = SanPhamChiTiet.ID
                        JOIN Hang ON SanPhamChiTiet.ID_Hang = Hang.ID
                        JOIN Size ON SanPhamChiTiet.ID_Size = Size.ID
                        JOIN KhachHang ON HoaDon.ID_KhachHang = KhachHang.id
                        JOIN DiaChi ON KhachHang.ID = DiaChi.ID_KhachHang
                        SET KhachHang.SDT = ?, DiaChi.diachi = ? 
                        where MaHD =?
                     """;
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setString(1, maHD);

            row = pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    public void insertHoaDon(HoaDon hd) {
        Integer row = null;
        String sql = """
                        INSERT INTO HoaDon
                                              (ID_KhachHang, ID_NhanVien, TenKH, sdt, tongTien, trangThai,
                     diaChi, ngayShip, ngayNhan, ngayXacNhan, ngayMuonNhan, soTienGiam, PhiShip, HinhThucThanhToan,
                     TrangThaiThanhT, create_at, create_by, 
                                              update_at, update_by, deleted, MaHD, NgayGiaoDich)
                        VALUES    (?,3,?,?,0,?,?,?,?,?,?,?,?,?,'Chờ Thanh Toán',GETDATE(),'NV001',GETDATE(),'NV001',1,?,?)
                    
                     """;
        Connection cn = DBcontext.getConnection();
        try {
            LocalDate ngayHomNay = LocalDate.now();
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setObject(1, hd.getId_KH());
            pstm.setObject(2, hd.getTenKH());
            pstm.setObject(3, hd.getSdt());
//            pstm.setObject(4, hd.getTongTien());
            pstm.setObject(4, hd.getTrangThai());
            pstm.setObject(5, hd.getDiaChi());
            pstm.setObject(6, hd.getNgayShip());
            pstm.setObject(7, hd.getNgayMuonNhan());
            pstm.setObject(8, hd.getNgayXacNhan());
            pstm.setObject(9, hd.getNgayMuonNhan());
            pstm.setObject(10, hd.getSoTienGiam());
            pstm.setObject(11, hd.getPhiShip());
            pstm.setObject(12, hd.getHinhThucThanhToan());

            pstm.setObject(13, hd.getMaHoaDon());
            pstm.setObject(14, hd.getNgayThanhToan());
            row = pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Integer updateHDBH(HoaDon hd) {
        Integer row = null;
        String sql = """
                     	update HoaDon
                        set trangThai = N'Thành công' , deleted = 0, HinhThucThanhToan =?, 
                        tongTien =? , ngayNhan=GETDATE(),ngayXacNhan = GETDATE(), TrangThaiThanhT =?, ID_Voucher = ?, ID_KhachHang = ?, TenKH =?
                        WHERE MaHD = ?                   
                     """;
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setObject(1, hd.getHinhThucThanhToan());
            pstm.setObject(2, hd.getTongTien());
            pstm.setObject(3, hd.getTrangThaiThanhToan());
            pstm.setObject(4, hd.getId_VC());
            pstm.setObject(5, hd.getId_KH());
            pstm.setObject(6, hd.getTenKH());
            pstm.setString(7, hd.getMaHoaDon());
            row = pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    public Integer updateDatHang(HoaDon hd) {
        Integer row = null;
        String sql = """
                        update HoaDon
                        set trangThai = ?, PhiShip =?, ngayMuonNhan =? , TrangThaiThanhT=? 
                        , HinhThucThanhToan = ? , TenKH =?, sdt =?,diaChi =? , tongTien =?, ngayShip =? , 
                     ngayNhan =?, ngayXacNhan =?, ID_KhachHang = ?, ID_Voucher = ?, ID_NhanVien = ?
                         WHERE MaHD = ?                
                     """;
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);

            pstm.setString(1, hd.getTrangThai());
            pstm.setDouble(2, hd.getPhiShip());
            pstm.setObject(3, hd.getNgayMuonNhan());
            pstm.setObject(4, hd.getTrangThaiThanhToan());
            pstm.setObject(5, hd.getHinhThucThanhToan());
            pstm.setObject(6, hd.getTenKH());
            pstm.setObject(7, hd.getSdt());
            pstm.setString(8, hd.getDiaChi());
            pstm.setObject(9, hd.getTongTien());
            pstm.setObject(10, hd.getNgayShip());
            pstm.setObject(11, hd.getNgayThanhToan());
            pstm.setObject(12, hd.getNgayXacNhan());
            pstm.setObject(13, hd.getId_KH());
            pstm.setObject(14, hd.getId_VC());
            pstm.setObject(15, hd.getId_NV());
            pstm.setString(16, hd.getMaHoaDon());

            row = pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    public ArrayList<HoaDon> getAllHoaDonDaThanhToan() {

        ArrayList<HoaDon> lst = new ArrayList<>();
        String sql = """
                      SELECT HoaDon.ID, MaHD, NgayGiaoDich,NhanVien.TenNV,TenKH, COUNT(hdct.soLuong) AS TongSoLuongSanPham,HinhThucThanhToan,trangThai,
                                                                                                  HoaDon.create_at, HoaDon.update_at, ngayNhan, ngayXacNhan,tongTien,PhiShip,MaNV,TenNV,HoaDon.create_by
                                                                                                   FROM HoaDon
                                                                                                  JOIN NhanVien ON HoaDon.ID_NhanVien = NhanVien.id
                                                                                                  left JOIN HoaDonChiTiet hdct ON hdct.ID_HoaDon = HoaDon.ID
                                                                                                  WHERE HoaDon.trangThai LIKE N'%Đã thanh toán%' AND HoaDon.deleted = 1
                                                                                                  GROUP BY HoaDon.ID, MaHD, NgayGiaoDich,NhanVien.TenNV,TenKH,HinhThucThanhToan,trangThai,HoaDon.create_at, HoaDon.update_at, ngayNhan
                                                                                                  , ngayXacNhan,tongTien,PhiShip,MaNV,TenNV,HoaDon.create_by
                                                                                   ORDER BY HoaDon.create_at DESC
                     
                     """;
        Connection cn = (Connection) DBcontext.getConnection();
        try {
            //tao Statement
            PreparedStatement pstm = cn.prepareStatement(sql);
            //thi hanh Statement => dung Resultset nhan kq
            ResultSet rs = pstm.executeQuery();
            //xu ly kq => do du lieu vao lst
            while (rs.next()) {
                HoaDon h = new HoaDon();
                //h.setMaHoaDon(rs.getString("maHoaDon"));
//                n.setTrangThai(rs.getString("tenTrangThai")); 
                h.setId(rs.getInt(1));
                h.setMaHoaDon(rs.getString("maHD"));
                h.setTenKH(rs.getString("TenKH"));
                h.setNgayTao(rs.getDate("create_at"));
                h.setNgayThanhToan(rs.getDate("ngayNhan"));
                h.setSoLuong(rs.getInt(6));
                h.setTenNV(rs.getString("TenNV"));
                h.setNgayXacNhan(rs.getDate("ngayXacNhan"));
                h.setTongTien(rs.getInt("tongTien"));
                h.setHinhThucThanhToan(rs.getString("HinhThucThanhToan"));
                h.setTrangThai(rs.getString("trangThai"));
                h.setMaNV(rs.getString("MaNV"));
                h.setNguoiTao(rs.getString("create_by"));
//                h.setKichCo(rs.getInt("size"));
//                h.setThuongHieu(rs.getString("Ten"));
//                h.setMaSP(rs.getString("MaSPCT"));
                lst.add(h);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return lst;

    }

    public ArrayList<HoaDon> getAllHoaDonTaiQuay() {

        ArrayList<HoaDon> lst = new ArrayList<>();
        String sql = """
                     SELECT HoaDon.ID, MaHD, NgayGiaoDich,NhanVien.TenNV,TenKH, COUNT(hdct.soLuong) AS TongSoLuongSanPham,HinhThucThanhToan,trangThai,
                                   HoaDon.create_at, HoaDon.update_at, ngayNhan, ngayXacNhan,tongTien,PhiShip,MaNV,TenNV,HoaDon.create_by
                                    FROM HoaDon
                                   JOIN NhanVien ON HoaDon.ID_NhanVien = NhanVien.id
                                   left JOIN HoaDonChiTiet hdct ON hdct.ID_HoaDon = HoaDon.ID
                                   WHERE HoaDon.trangThai LIKE N'%Đã Thanh Toán%' AND HoaDon.deleted = 0
                                   GROUP BY HoaDon.ID, MaHD, NgayGiaoDich,NhanVien.TenNV,TenKH,HinhThucThanhToan,trangThai,HoaDon.create_at, HoaDon.update_at, ngayNhan
                                   , ngayXacNhan,tongTien,PhiShip,MaNV,TenNV,HoaDon.create_by
                    ORDER BY HoaDon.create_at DESC
                     
                     """;
        Connection cn = (Connection) DBcontext.getConnection();
        try {
            //tao Statement
            PreparedStatement pstm = cn.prepareStatement(sql);
            //thi hanh Statement => dung Resultset nhan kq
            ResultSet rs = pstm.executeQuery();
            //xu ly kq => do du lieu vao lst
            while (rs.next()) {
                HoaDon h = new HoaDon();
                //h.setMaHoaDon(rs.getString("maHoaDon"));
//                n.setTrangThai(rs.getString("tenTrangThai")); 
                h.setId(rs.getInt(1));
                h.setMaHoaDon(rs.getString("maHD"));
                h.setTenKH(rs.getString("TenKH"));
                h.setNgayTao(rs.getDate("create_at"));
                h.setNgayThanhToan(rs.getDate("ngayNhan"));
                h.setTenNV(rs.getString("TenNV"));
                h.setSoLuong(rs.getInt(6));
                h.setNgayXacNhan(rs.getDate("ngayXacNhan"));
                h.setTongTien(rs.getInt("tongTien"));
                h.setHinhThucThanhToan(rs.getString("HinhThucThanhToan"));
                h.setTrangThai(rs.getString("trangThai"));
                h.setMaNV(rs.getString("MaNV"));
                h.setNguoiTao(rs.getString("create_by"));
//                h.setKichCo(rs.getInt("size"));
//                h.setThuongHieu(rs.getString("Ten"));
//                h.setMaSP(rs.getString("MaSPCT"));
                lst.add(h);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return lst;

    }

    public ArrayList<HoaDon> getAllHoaDonChoGiaoHang() {

        ArrayList<HoaDon> lst = new ArrayList<>();
        String sql = """
                     SELECT HoaDon.ID, MaHD, NgayGiaoDich,NhanVien.TenNV,TenKH, COUNT(hdct.soLuong) AS TongSoLuongSanPham,HinhThucThanhToan,trangThai,
                                                        HoaDon.create_at, HoaDon.update_at, ngayNhan, ngayXacNhan,tongTien,PhiShip,MaNV,TenNV,HoaDon.create_by
                                                         FROM HoaDon
                                                        JOIN NhanVien ON HoaDon.ID_NhanVien = NhanVien.id
                                                        left JOIN HoaDonChiTiet hdct ON hdct.ID_HoaDon = HoaDon.ID
                                                        WHERE HoaDon.trangThai LIKE N'%Chờ Giao Hàng%' AND HoaDon.deleted = 1
                                                        GROUP BY HoaDon.ID, MaHD, NgayGiaoDich,NhanVien.TenNV,TenKH,HinhThucThanhToan,trangThai,HoaDon.create_at, HoaDon.update_at, ngayNhan
                                                        , ngayXacNhan,tongTien,PhiShip,MaNV,TenNV,HoaDon.create_by
                                         ORDER BY HoaDon.create_at DESC
                    
                     
                     """;
        Connection cn = (Connection) DBcontext.getConnection();
        try {
            //tao Statement
            PreparedStatement pstm = cn.prepareStatement(sql);
            //thi hanh Statement => dung Resultset nhan kq
            ResultSet rs = pstm.executeQuery();
            //xu ly kq => do du lieu vao lst
            while (rs.next()) {
                HoaDon h = new HoaDon();
                //h.setMaHoaDon(rs.getString("maHoaDon"));
//                n.setTrangThai(rs.getString("tenTrangThai")); 
                h.setId(rs.getInt(1));
                h.setMaHoaDon(rs.getString("maHD"));
                h.setTenKH(rs.getString("TenKH"));
                h.setNgayTao(rs.getDate("create_at"));
                h.setNgayThanhToan(rs.getDate("ngayNhan"));
                h.setTenNV(rs.getString("TenNV"));
                h.setSoLuong(rs.getInt(6));
                h.setNgayXacNhan(rs.getDate("ngayXacNhan"));
                h.setTongTien(rs.getInt("tongTien"));
                h.setHinhThucThanhToan(rs.getString("HinhThucThanhToan"));
                h.setTrangThai(rs.getString("trangThai"));
                h.setMaNV(rs.getString("MaNV"));
                h.setNguoiTao(rs.getString("create_by"));
//                h.setKichCo(rs.getInt("size"));
//                h.setThuongHieu(rs.getString("Ten"));
//                h.setMaSP(rs.getString("MaSPCT"));
                lst.add(h);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return lst;

    }

    public ArrayList<HoaDon> getAllHoaDonDangGiao() {

        ArrayList<HoaDon> lst = new ArrayList<>();
        String sql = """
                     SELECT HoaDon.ID, MaHD, NgayGiaoDich,NhanVien.TenNV,TenKH, COUNT(hdct.soLuong) AS TongSoLuongSanPham,HinhThucThanhToan,trangThai,
                                                                             HoaDon.create_at, HoaDon.update_at, ngayNhan, ngayXacNhan,tongTien,PhiShip,MaNV,TenNV,HoaDon.create_by
                                                                              FROM HoaDon
                                                                             JOIN NhanVien ON HoaDon.ID_NhanVien = NhanVien.id
                                                                             left JOIN HoaDonChiTiet hdct ON hdct.ID_HoaDon = HoaDon.ID
                                                                             WHERE HoaDon.trangThai LIKE N'%Đang vận chuyển%' AND HoaDon.deleted = 1
                                                                             GROUP BY HoaDon.ID, MaHD, NgayGiaoDich,NhanVien.TenNV,TenKH,HinhThucThanhToan,trangThai,HoaDon.create_at, HoaDon.update_at, ngayNhan
                                                                             , ngayXacNhan,tongTien,PhiShip,MaNV,TenNV,HoaDon.create_by
                                                              ORDER BY HoaDon.create_at DESC
                    
                     
                     """;
        Connection cn = (Connection) DBcontext.getConnection();
        try {
            //tao Statement
            PreparedStatement pstm = cn.prepareStatement(sql);
            //thi hanh Statement => dung Resultset nhan kq
            ResultSet rs = pstm.executeQuery();
            //xu ly kq => do du lieu vao lst
            while (rs.next()) {
                HoaDon h = new HoaDon();
                //h.setMaHoaDon(rs.getString("maHoaDon"));
//                n.setTrangThai(rs.getString("tenTrangThai")); 
                h.setId(rs.getInt(1));
                h.setMaHoaDon(rs.getString("maHD"));
                h.setTenKH(rs.getString("TenKH"));
                h.setNgayTao(rs.getDate("create_at"));
                h.setNgayThanhToan(rs.getDate("ngayNhan"));
                h.setTenNV(rs.getString("TenNV"));
                h.setSoLuong(rs.getInt(6));
                h.setNgayXacNhan(rs.getDate("ngayXacNhan"));
                h.setTongTien(rs.getInt("tongTien"));
                h.setHinhThucThanhToan(rs.getString("HinhThucThanhToan"));
                h.setTrangThai(rs.getString("trangThai"));
                h.setMaNV(rs.getString("MaNV"));
                h.setNguoiTao(rs.getString("create_by"));
//                h.setKichCo(rs.getInt("size"));
//                h.setThuongHieu(rs.getString("Ten"));
//                h.setMaSP(rs.getString("MaSPCT"));
                lst.add(h);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return lst;

    }

    public ArrayList<HoaDon> getAllHoaDonGiaoLai() {

        ArrayList<HoaDon> lst = new ArrayList<>();
        String sql = """
                      SELECT HoaDon.ID, MaHD, NgayGiaoDich,NhanVien.TenNV,TenKH, COUNT(hdct.soLuong) AS TongSoLuongSanPham,HinhThucThanhToan,trangThai,
                                                                                                  HoaDon.create_at, HoaDon.update_at, ngayNhan, ngayXacNhan,tongTien,PhiShip,MaNV,TenNV,HoaDon.create_by
                                                                                                   FROM HoaDon
                                                                                                  JOIN NhanVien ON HoaDon.ID_NhanVien = NhanVien.id
                                                                                                  left JOIN HoaDonChiTiet hdct ON hdct.ID_HoaDon = HoaDon.ID
                                                                                                  WHERE HoaDon.trangThai LIKE N'%Giao lại%' AND HoaDon.deleted = 1
                                                                                                  GROUP BY HoaDon.ID, MaHD, NgayGiaoDich,NhanVien.TenNV,TenKH,HinhThucThanhToan,trangThai,HoaDon.create_at, HoaDon.update_at, ngayNhan
                                                                                                  , ngayXacNhan,tongTien,PhiShip,MaNV,TenNV,HoaDon.create_by
                                                                                   ORDER BY HoaDon.create_at DESC
                     
                     """;
        Connection cn = (Connection) DBcontext.getConnection();
        try {
            //tao Statement
            PreparedStatement pstm = cn.prepareStatement(sql);
            //thi hanh Statement => dung Resultset nhan kq
            ResultSet rs = pstm.executeQuery();
            //xu ly kq => do du lieu vao lst
            while (rs.next()) {
                HoaDon h = new HoaDon();
                //h.setMaHoaDon(rs.getString("maHoaDon"));
//                n.setTrangThai(rs.getString("tenTrangThai")); 
                h.setId(rs.getInt(1));
                h.setMaHoaDon(rs.getString("maHD"));
                h.setTenKH(rs.getString("TenKH"));
                h.setNgayTao(rs.getDate("create_at"));
                h.setNgayThanhToan(rs.getDate("ngayNhan"));
                h.setTenNV(rs.getString("TenNV"));
                h.setSoLuong(rs.getInt(6));
                h.setNgayXacNhan(rs.getDate("ngayXacNhan"));
                h.setTongTien(rs.getInt("tongTien"));
                h.setHinhThucThanhToan(rs.getString("HinhThucThanhToan"));
                h.setTrangThai(rs.getString("trangThai"));
                h.setMaNV(rs.getString("MaNV"));
                h.setNguoiTao(rs.getString("create_by"));
//                h.setKichCo(rs.getInt("size"));
//                h.setThuongHieu(rs.getString("Ten"));
//                h.setMaSP(rs.getString("MaSPCT"));
                lst.add(h);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return lst;

    }

    public ArrayList<HoaDon> getAllHoaDonDaGiao() {

        ArrayList<HoaDon> lst = new ArrayList<>();
        String sql = """
                     SELECT HoaDon.ID, MaHD, NgayGiaoDich,NhanVien.TenNV,TenKH, COUNT(hdct.soLuong) AS TongSoLuongSanPham,HinhThucThanhToan,trangThai,
                                                                                                  HoaDon.create_at, HoaDon.update_at, ngayNhan, ngayXacNhan,tongTien,PhiShip,MaNV,TenNV,HoaDon.create_by
                                                                                                   FROM HoaDon
                                                                                                  JOIN NhanVien ON HoaDon.ID_NhanVien = NhanVien.id
                                                                                                  left JOIN HoaDonChiTiet hdct ON hdct.ID_HoaDon = HoaDon.ID
                                                                                                  WHERE HoaDon.trangThai LIKE N'%Đã giao%' AND HoaDon.deleted = 1
                                                                                                  GROUP BY HoaDon.ID, MaHD, NgayGiaoDich,NhanVien.TenNV,TenKH,HinhThucThanhToan,trangThai,HoaDon.create_at, HoaDon.update_at, ngayNhan
                                                                                                  , ngayXacNhan,tongTien,PhiShip,MaNV,TenNV,HoaDon.create_by
                                                                                   ORDER BY HoaDon.create_at DESC
                     
                     """;
        Connection cn = (Connection) DBcontext.getConnection();
        try {
            //tao Statement
            PreparedStatement pstm = cn.prepareStatement(sql);
            //thi hanh Statement => dung Resultset nhan kq
            ResultSet rs = pstm.executeQuery();
            //xu ly kq => do du lieu vao lst
            while (rs.next()) {
                HoaDon h = new HoaDon();
                //h.setMaHoaDon(rs.getString("maHoaDon"));
//                n.setTrangThai(rs.getString("tenTrangThai")); 
                h.setId(rs.getInt(1));
                h.setMaHoaDon(rs.getString("maHD"));
                h.setTenKH(rs.getString("TenKH"));
                h.setNgayTao(rs.getDate("create_at"));
                h.setNgayThanhToan(rs.getDate("ngayNhan"));
                h.setTenNV(rs.getString("TenNV"));
                h.setSoLuong(rs.getInt(6));
                h.setNgayXacNhan(rs.getDate("ngayXacNhan"));
                h.setTongTien(rs.getInt("tongTien"));
                h.setHinhThucThanhToan(rs.getString("HinhThucThanhToan"));
                h.setTrangThai(rs.getString("trangThai"));
                h.setMaNV(rs.getString("MaNV"));
                h.setNguoiTao(rs.getString("create_by"));
//                h.setKichCo(rs.getInt("size"));
//                h.setThuongHieu(rs.getString("Ten"));
//                h.setMaSP(rs.getString("MaSPCT"));
                lst.add(h);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return lst;

    }

    public ArrayList<HoaDon> getAllHoaDon() {

        ArrayList<HoaDon> lst = new ArrayList<>();
        String sql = """
                     select* from HoaDon join NhanVien on HoaDon.ID_NhanVien = NhanVien.id where HoaDon.deleted =0 ORDER BY ngayXacNhan DESC
                     
                     """;
        Connection cn = (Connection) DBcontext.getConnection();
        try {
            //tao Statement
            PreparedStatement pstm = cn.prepareStatement(sql);
            //thi hanh Statement => dung Resultset nhan kq
            ResultSet rs = pstm.executeQuery();
            //xu ly kq => do du lieu vao lst
            while (rs.next()) {
                HoaDon h = new HoaDon();
                //h.setMaHoaDon(rs.getString("maHoaDon"));
//                n.setTrangThai(rs.getString("tenTrangThai")); 
                h.setId(rs.getInt(1));
                h.setMaHoaDon(rs.getString("maHD"));
                h.setTenKH(rs.getString("TenKH"));
                h.setNgayTao(rs.getDate("create_at"));
                h.setNgayThanhToan(rs.getDate("ngayNhan"));
                h.setTenNV(rs.getString("TenNV"));
                h.setNgayXacNhan(rs.getDate("ngayXacNhan"));
                h.setTongTien(rs.getInt("tongTien"));
                h.setHinhThucThanhToan(rs.getString("HinhThucThanhToan"));
                h.setTrangThai(rs.getString("trangThai"));
                h.setMaNV(rs.getString("MaNV"));
                h.setNguoiTao(rs.getString("create_by"));
                h.setTrangThaiThanhToan(rs.getString("TrangThaiThanhT"));
//                h.setKichCo(rs.getInt("size"));
//                h.setThuongHieu(rs.getString("Ten"));
//                h.setMaSP(rs.getString("MaSPCT"));
                lst.add(h);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return lst;

    }

    public ArrayList<HoaDon> getAllHoaDonTheoMa(String maHD) {

        ArrayList<HoaDon> lst = new ArrayList<>();
        String sql = """
                     select* from HoaDon join NhanVien on HoaDon.ID_NhanVien = NhanVien.id 
                     	join Voucher on Voucher.ID = HoaDon.ID_Voucher
                                          WHERE MaHD = ?
                     """;
        Connection cn = (Connection) DBcontext.getConnection();
        try {
            //tao Statement
            PreparedStatement pstm = cn.prepareStatement(sql);
            //thi hanh Statement => dung Resultset nhan kq
            pstm.setObject(1, maHD);
            ResultSet rs = pstm.executeQuery();
            //xu ly kq => do du lieu vao lst
            while (rs.next()) {
                HoaDon h = new HoaDon();
                //h.setMaHoaDon(rs.getString("maHoaDon"));
//                n.setTrangThai(rs.getString("tenTrangThai")); 
                h.setId(rs.getInt(1));
                h.setMaHoaDon(rs.getString("maHD"));
                h.setTenKH(rs.getString("TenKH"));
                h.setSdt(rs.getString("sdt"));
                h.setNgayMuonNhan(rs.getDate("ngayMuonNhan"));
                h.setDiaChi(rs.getString("diaChi"));
                h.setNgayTao(rs.getDate("create_at"));
                h.setNgayThanhToan(rs.getDate("ngayNhan"));
                h.setTenNV(rs.getString("TenNV"));
                h.setNgayXacNhan(rs.getDate("ngayXacNhan"));
                h.setTongTien(rs.getInt("tongTien"));
                h.setPhiShip(rs.getInt("PhiShip"));
                h.setHinhThucThanhToan(rs.getString("HinhThucThanhToan"));
                h.setTrangThaiThanhToan(rs.getString("TrangThaiThanhT"));
                h.setTrangThai(rs.getString("trangThai"));
                h.setMaNV(rs.getString("MaNV"));
                h.setNguoiTao(rs.getString("create_by"));
                h.setSdtNGuoiShip(rs.getString("SDT"));
                h.setTenVC(rs.getString("TenVC"));
                h.setSoTienGiam(Integer.parseInt(rs.getString("mucGiamGia")));
//                h.setKichCo(rs.getInt("size"));
//                h.setThuongHieu(rs.getString("Ten"));
//                h.setMaSP(rs.getString("MaSPCT"));
                lst.add(h);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return lst;

    }

    public Integer updateTrangThai(HoaDon hd) {
        Integer row = null;
        String sql = """
                        update HoaDon
                        					 set trangThai = ? 
                        					 WHERE MaHD = ?             
                     """;
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);

            pstm.setString(1, hd.getTrangThai());
            pstm.setString(2, hd.getMaHoaDon());

            row = pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    public Integer hoanThanhGiaoHang(HoaDon hd) {
        Integer row = null;
        String sql = """
                        update HoaDon
                       set trangThai = ? ,deleted =0 ,ngayXacNhan = GETDATE(), ngayNhan  =GETDATE()
                        WHERE MaHD = ?             
                     """;
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);

            pstm.setString(1, hd.getTrangThai());
            pstm.setString(2, hd.getMaHoaDon());

            row = pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    public Integer ngayShip(HoaDon hd) {
        Integer row = null;
        String sql = """
                        update HoaDon
                                                set trangThai = ? ,ngayShip = GETDATE()
                                                WHERE MaHD = ?            
                     """;
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);

            pstm.setString(1, hd.getTrangThai());
            pstm.setString(2, hd.getMaHoaDon());

            row = pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

}
