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
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import poly.edu.Model.HoaDon;
import poly.edu.Model.HoaDonChiTiet;
import poly.edu.Model.SanPham;
import poly.edu.Model.SanPhamChiTiet;
import poly.edu.uitility.DBcontext;

/**
 *
 * @author Admin
 */
public class HoaDonChiTietService {

    public ArrayList<HoaDonChiTiet> getAllHoaDonCTMaCTSP(String key) {

        ArrayList<HoaDonChiTiet> lst = new ArrayList<>();
        String sql = """
   select HoaDon.ID, HoaDonChiTiet.ID_HoaDon, HoaDon.MaHD, HoaDonChiTiet.ID_SanPhamChiTiet,
                        SanPhamChiTiet.Gia, SanPhamChiTiet.MaSPCT, sp.Ten, HoaDonChiTiet.deleted
                       from HoaDonChiTiet join HoaDon on HoaDon.ID = HoaDonChiTiet.ID_HoaDon 
                    join SanPhamChiTiet on HoaDonChiTiet.ID_SanPhamChiTiet = SanPhamChiTiet.ID 
     join SanPham sp ON sp.ID = SanPhamChiTiet.ID_SanPham
                        where SanPhamChiTiet.MaSPCT LIKE ?
                     """;
        Connection cn = (Connection) DBcontext.getConnection();
        try {
            //tao Statement
            PreparedStatement pstm = cn.prepareStatement(sql);
            //thi hanh Statement => dung Resultset nhan kq
            ResultSet rs = pstm.executeQuery();
            pstm.setObject(1, key + "%");
            while (rs.next()) {
                int id = rs.getInt(1);
                int idHD = rs.getInt(2);
                String maHD = rs.getString(3);
                int sl = rs.getInt(9);
                int idSPCT = rs.getInt(4);
                int gia = rs.getInt(5);
                String maSPCT = rs.getString(6);
                String tenspct = rs.getString(7);
                boolean delete = rs.getBoolean(8);
//                HoaDon hd = new HoaDon();
//                hd.setMaHoaDon(maHD);
//                SanPhamChiTiet spct = new SanPhamChiTiet();
//                spct.setMaCTSP(maSPCT);
//                spct.setId(idSPCT);
//                SanPham sp = new SanPham();
//                sp.setTen(tenspct);
//                spct.setSp(sp);

                HoaDonChiTiet hdct = new HoaDonChiTiet(id, idSPCT, idHD, sl, gia, maSPCT, tenspct, delete);
                lst.add(hdct);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return lst;

    }

    public void insertHoaDonCT(HoaDonChiTiet hdct) {
        Integer row = null;
        String sql = """
                        INSERT INTO HoaDonChiTiet
                                              (ID_SanPhamChiTiet, ID_HoaDon, soLuong, gia, create_at, create_by, update_at, update_by, deleted)
                        VALUES    (?,?,?,?,GETDATE(),'NV001',GETDATE(),'NV001',0)
                     
                     """;
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setObject(1, hdct.getID_spct());
            pstm.setObject(2, hdct.getID_hd());
            pstm.setObject(3, hdct.getSl());
            pstm.setObject(4, hdct.getGia());
            row = pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public ArrayList<HoaDonChiTiet> getAllHoaDonChiTietMaHD(String MaHD) {
        ArrayList<HoaDonChiTiet> lst = new ArrayList<>();
        String sql = """
                     SELECT *
                    FROM HoaDonChiTiet
                    JOIN HoaDon ON HoaDon.ID = HoaDonChiTiet.ID_HoaDon
                    JOIN KhachHang ON HoaDon.ID_KhachHang = KhachHang.ID  
                    JOIN SanPhamChiTiet ON HoaDonChiTiet.ID_SanPhamChiTiet = SanPhamChiTiet.ID
                    JOIN SanPham ON SanPhamChiTiet.ID_SanPham = SanPham.ID
                    join NhanVien on HoaDon.ID_NhanVien = NhanVien.ID
                    where MaHD LIKE ? 
                     """;

        try ( Connection cn = DBcontext.getConnection();  PreparedStatement pstm = cn.prepareStatement(sql)) {

            pstm.setString(1, MaHD);

            try ( ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt(1);
                    int idspct = rs.getInt("ID_SanPhamChiTiet");
                    int idHD = rs.getInt("ID_HoaDon");
                    String maspct = rs.getString("MaSPCT");
                    int gia = rs.getInt("Gia");
                    int sl = rs.getInt("soLuong");
                    String tenSP = rs.getString("ten");
                    boolean deleted = rs.getBoolean(10);
//                    SanPham sp = new SanPham();
//                    sp.setTen(tenSP);
//                    SanPhamChiTiet spct = new SanPhamChiTiet();
//                    spct.setMaCTSP(maspct);
//                    spct.setId(idspct);
//                    spct.setSp(sp);
//                    HoaDon hd = new HoaDon();
//                    hd.setId(idHD);
                    HoaDonChiTiet hdct = new HoaDonChiTiet(id, idspct, idHD, sl, gia, maspct, tenSP, deleted);
                    lst.add(hdct);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lst;
    }

    public Integer deleteSPInGioHang(int idHD) {
        Integer row = null;
        String sql = """
                        delete FROM HoaDonChiTiet
                        WHERE ID_HoaDon = ?             
                     """;
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setInt(1, idHD);
            row = pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    public Integer deleteSPInGioHangSP(int idSPCT) {
        Integer row = null;
        String sql = """
                        delete FROM HoaDonChiTiet
                        WHERE ID_SanPhamChiTiet =?         
                     """;
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);
//            pstm.setInt(1, idHD);
            pstm.setInt(1, idSPCT);
            row = pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    public Integer updateSLHDCT(HoaDonChiTiet hdct) {
        Integer row = null;
        String sql = """
                       UPDATE HoaDonChiTiet
                        SET soLuong = ? + HoaDonChiTiet.soLuong
                        WHERE ID_SanPhamChiTiet = ?
                           
                     """;
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);
//            pstm.setInt(1, idHD);
            SanPhamChiTiet spct = new SanPhamChiTiet();

            pstm.setInt(1, hdct.getSl());
            pstm.setInt(2, hdct.getID_spct());

            row = pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    public Integer updateSLHDCTKhiXoa(HoaDonChiTiet hdct) {
        Integer row = null;
        String sql = """
                        UPDATE HoaDonChiTiet
                                               SET soLuong = ?
                                               WHERE ID_SanPhamChiTiet = ?
                           
                     """;
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);
//            pstm.setInt(1, idHD);
            SanPhamChiTiet spct = new SanPhamChiTiet();

            pstm.setInt(1, hdct.getSl());
            pstm.setInt(2, hdct.getID_spct());
            row = pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }
}
