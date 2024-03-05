/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.edu.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import poly.edu.Model.TKHoaDon;
import poly.edu.Model.KhachHang;
import poly.edu.uitility.DBcontext;

/**
 *
 * @author Dell
 */
public class KhachHangServices {

    public ArrayList<KhachHang> AllThongTinKhachHang() {
        ArrayList<KhachHang> lst = new ArrayList<>();
        String sql = "SELECT KhachHang.ID, KhachHang.MaKH, KhachHang.Email, KhachHang.GioiTinh, KhachHang.NgaySinh, KhachHang.Sdt, KhachHang.TenKH, DiaChi.ThanhPho,\n"
                + "             DiaChi.DiaChiChiTiet,DiaChi.Xa_Phuong, DiaChi.Quan_Huyen, KhachHang.create_at\n"
                + "             FROM KhachHang left JOIN DiaChi ON KhachHang.ID = DiaChi.ID_KhachHang WHERE KhachHang.deleted = 0 ORDER BY create_at DESC";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setEmail(rs.getString("Email"));
                kh.setGioiTinh(rs.getBoolean("GioiTinh"));
                kh.setMaKH(rs.getString("MaKH"));
                kh.setNgaySinh(rs.getDate("NgaySinh"));
                kh.setSdt(rs.getString("Sdt"));
                kh.setTenKH(rs.getString("TenKH"));
                kh.setThanhPho(rs.getString("ThanhPho"));
                kh.setDiaChi(rs.getString("DiaChiChiTiet"));
                kh.setQuanHuyen(rs.getString("Quan_Huyen"));
                kh.setXaPhuong(rs.getString("Xa_Phuong"));
                //kh.setNguoiSua(rs.getString("update_at"));
                kh.setID(rs.getInt("ID"));
                lst.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lst;
    }

    public ArrayList<KhachHang> AllThongTinKhachHangKCoKhachBanLe() {
        ArrayList<KhachHang> lst = new ArrayList<>();
        String sql = "SELECT KhachHang.ID, KhachHang.MaKH, KhachHang.Email, KhachHang.GioiTinh, KhachHang.NgaySinh, KhachHang.Sdt, KhachHang.TenKH, DiaChi.ThanhPho,\n"
                + "             DiaChi.DiaChiChiTiet,DiaChi.Xa_Phuong, DiaChi.Quan_Huyen, KhachHang.create_at\n"
                + "             FROM KhachHang left JOIN DiaChi ON KhachHang.ID = DiaChi.ID_KhachHang  ORDER BY create_at DESC";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setEmail(rs.getString("Email"));
                kh.setGioiTinh(rs.getBoolean("GioiTinh"));
                kh.setMaKH(rs.getString("MaKH"));
                kh.setNgaySinh(rs.getDate("NgaySinh"));
                kh.setSdt(rs.getString("Sdt"));
                kh.setTenKH(rs.getString("TenKH"));
                kh.setThanhPho(rs.getString("ThanhPho"));
                kh.setDiaChi(rs.getString("DiaChiChiTiet"));
                kh.setQuanHuyen(rs.getString("Quan_Huyen"));
                kh.setXaPhuong(rs.getString("Xa_Phuong"));
                //kh.setNguoiSua(rs.getString("update_at"));
                kh.setID(rs.getInt("ID"));
                lst.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lst;
    }

    public ArrayList<KhachHang> AllThongTinKhachHangTheoMa(String maKH) {
        ArrayList<KhachHang> lst = new ArrayList<>();
        String sql = "SELECT KhachHang.ID, KhachHang.MaKH, KhachHang.Email, KhachHang.GioiTinh, KhachHang.NgaySinh, KhachHang.Sdt, KhachHang.TenKH, DiaChi.ThanhPho,\n"
                + "             DiaChi.DiaChiChiTiet,DiaChi.Xa_Phuong, DiaChi.Quan_Huyen, KhachHang.create_at\n"
                + "             FROM KhachHang left JOIN DiaChi ON KhachHang.ID = DiaChi.ID_KhachHang WHERE KhachHang.MaKH LIKE ?  ORDER BY create_at DESC ";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setObject(1, maKH);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setEmail(rs.getString("Email"));
                kh.setGioiTinh(rs.getBoolean("GioiTinh"));
                kh.setMaKH(rs.getString("MaKH"));
                kh.setNgaySinh(rs.getDate("NgaySinh"));
                kh.setSdt(rs.getString("Sdt"));
                kh.setTenKH(rs.getString("TenKH"));
                kh.setThanhPho(rs.getString("ThanhPho"));
                kh.setDiaChi(rs.getString("DiaChiChiTiet"));
                kh.setQuanHuyen(rs.getString("Quan_Huyen"));
                kh.setXaPhuong(rs.getString("Xa_Phuong"));
                //kh.setNguoiSua(rs.getString("update_at"));
                kh.setID(rs.getInt("ID"));
                lst.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lst;
    }

    public Integer ADDKhachHang(KhachHang kh) {
        Integer row = null;
        String sqlKhachHang = "INSERT INTO KhachHang VALUES (?, ?, ?, GETDATE(), 'NV001', GETDATE(),'NV001', 0, ?, ?, ?)";
        String sqlDiaChi = "INSERT INTO DiaChi VALUES (?, GETDATE(), 'NhanVien1', GETDATE(), 'NhanVien1', 0, ?,?,?,?)";

        Connection cn = DBcontext.getConnection();
        try {
            // Insert KhachHang
            PreparedStatement pstmKhachHang = cn.prepareStatement(sqlKhachHang, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmKhachHang.setString(1, kh.getTenKH());
            pstmKhachHang.setString(3, kh.getEmail());
            pstmKhachHang.setBoolean(4, kh.isGioiTinh());
            pstmKhachHang.setString(5, kh.getMaKH());
            pstmKhachHang.setDate(6, new java.sql.Date(kh.getNgaySinh().getTime()));
            pstmKhachHang.setString(2, kh.getSdt());

            // Execute the insert statement and get the generated keys
            row = pstmKhachHang.executeUpdate();

            // Check if the KhachHang record was inserted successfully
            if (row != null && row > 0) {
                // Get the generated key (ID) of the inserted KhachHang record
                ResultSet generatedKeys = pstmKhachHang.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int khachHangID = generatedKeys.getInt(1);

                    // Insert DiaChi with the generated KhachHang ID
                    PreparedStatement pstmDiaChi = cn.prepareStatement(sqlDiaChi);
                    pstmDiaChi.setInt(1, khachHangID);
                    pstmDiaChi.setString(2, kh.getThanhPho());
                    pstmDiaChi.setString(4, kh.getXaPhuong());
                    pstmDiaChi.setString(3, kh.getQuanHuyen());
                    pstmDiaChi.setString(5, kh.getDiaChi());

                    // Execute the DiaChi insert statement
                    int diaChiRow = pstmDiaChi.executeUpdate();
                } else {
                    // Handle the case where no generated keys are returned
                    System.out.println("Failed to retrieve generated keys for KhachHang.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return row;
    }

    public Integer UpdateKhachHang(KhachHang kh) {
        Integer row = null;
        String sqlKhachHang = "UPDATE KhachHang Set tenKH = ?, GioiTinh = ?, NgaySinh = ?, Sdt = ?, Email = ?, update_by = 'NV001', update_at = GETDATE()  WHERE ID = ?";
        String sqlDiaChi = "UPDATE DiaChi Set update_by = 'NV001', update_at = GETDATE(), ThanhPho = ?, Quan_Huyen =?,Xa_Phuong = ?, DiaChiChiTiet = ? WHERE ID_KhachHang = ?";

        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sqlKhachHang);
            pstm.setString(1, kh.getTenKH());
            pstm.setBoolean(2, kh.isGioiTinh());
            pstm.setDate(3, new java.sql.Date(kh.getNgaySinh().getTime()));
            pstm.setString(4, kh.getSdt());
            pstm.setString(5, kh.getEmail());
            pstm.setInt(6, kh.getID());

            row = pstm.executeUpdate();
            if (row != null && row > 0) {
                PreparedStatement pstmDiaChi = cn.prepareStatement(sqlDiaChi);
                pstmDiaChi.setString(4, kh.getDiaChi());
                pstmDiaChi.setString(1, kh.getThanhPho());
                pstmDiaChi.setString(2, kh.getQuanHuyen());
                pstmDiaChi.setString(3, kh.getXaPhuong());
                pstmDiaChi.setInt(5, kh.getID());
                int rowDiaChi = pstmDiaChi.executeUpdate();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return row;
    }

//    public ArrayList<KhachHang> ThongTinGiaoDich() {
//        ArrayList<KhachHang> lst = new ArrayList<>();
//        String sql = "SELECT TKHoaDon.MaHD, TKHoaDon.NgayGiaoDich, TKHoaDon.TongTien, TKHoaDon.TrangThai, TKHoaDon.ID_KhachHang"
//                + " FROM KhachHang JOIN TKHoaDon ON KhachHang.ID = TKHoaDon.ID_KhachHang WHERE TKHoaDon.deleted = 0";
//
//        try ( Connection cn = DBcontext.getConnection();  PreparedStatement pstm = cn.prepareStatement(sql);  ResultSet rs = pstm.executeQuery()) {
//
//            while (rs.next()) {
//                KhachHang kh = new KhachHang();
//                kh.setMaHoaDon(rs.getString("MaHD"));
//                kh.setNgayGiaoDich(rs.getString("NgayGiaoDich"));
//                kh.setTongTien(rs.getString("TongTien"));
//                kh.setTrangThai(rs.getString("TrangThai"));
//                kh.setIDKH(rs.getInt("ID_KhachHang"));
//                lst.add(kh);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return lst;
//    }
    public ArrayList<TKHoaDon> ThongTinGiaoDich(int khachHangID) {
        ArrayList<TKHoaDon> lst = new ArrayList<>();
        String sql = "SELECT MaHD, NgayGiaoDich, TongTien, TrangThai FROM HoaDon WHERE ID_KhachHang = ? AND deleted = 0";

        try ( Connection cn = DBcontext.getConnection();  PreparedStatement pstm = cn.prepareStatement(sql)) {

            pstm.setInt(1, khachHangID);

            try ( ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    TKHoaDon hoaDon = new TKHoaDon();
                    hoaDon.setMaHoaDon(rs.getString("MaHD"));
                    hoaDon.setNgayGiaoDich(rs.getString("NgayGiaoDich"));
                    hoaDon.setTongTien(rs.getString("TongTien"));
                    hoaDon.setTrangThai(rs.getString("TrangThai"));
                    lst.add(hoaDon);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lst;
    }

    public ArrayList<KhachHang> TimKiemThongTinGiaoDich(Integer selectedID) {
        Integer row = null;
        ArrayList<KhachHang> lst = new ArrayList<>();
        String sql = "SELECT HoaDon.MaHD, HoaDon.NgayGiaoDich, HoaDon.TongTien, HoaDon.TrangThai, HoaDon.ID_KhachHang"
                + " FROM KhachHang JOIN HoaDon ON KhachHang.ID = HoaDon.ID_KhachHang WHERE HoaDon.ID_KhachHang = 0";

        try ( Connection cn = DBcontext.getConnection();  PreparedStatement pstm = cn.prepareStatement(sql);  ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {

                pstm.setInt(1, selectedID);
                row = pstm.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lst;
    }

    public ArrayList<KhachHang> SelectByKeyWork(String TenKH) {
        ArrayList<KhachHang> lst = new ArrayList<>();
        String sql = "SELECT KhachHang.ID, KhachHang.MaKH, KhachHang.Email, KhachHang.GioiTinh, KhachHang.NgaySinh, KhachHang.Sdt, KhachHang.TenKH, DiaChi.ThanhPho\n"
                + "FROM KhachHang\n"
                + "JOIN DiaChi ON KhachHang.ID = DiaChi.ID_KhachHang\n"
                + "WHERE KhachHang.deleted = 0 AND KhachHang.TenKH LIKE ?";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setString(1, "%" + TenKH + "%");
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setEmail(rs.getString("Email"));
                kh.setGioiTinh(rs.getBoolean("GioiTinh"));
                kh.setMaKH(rs.getString("MaKH"));
                kh.setNgaySinh(rs.getDate("NgaySinh"));
                kh.setSdt(rs.getString("Sdt"));
                kh.setTenKH(rs.getString("TenKH"));
                kh.setDiaChi(rs.getString("ThanhPho"));
                //kh.setNguoiSua(rs.getString("update_at"));
                kh.setID(rs.getInt("ID"));
                lst.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lst;
    }

    public Integer DeletedMem(String MaKH) {
        Integer row = null;
        String sql = "UPDATE KhachHang SET deleted = 1 WHERE MaKH = ?";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setString(1, MaKH);
            row = pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return row;
    }
}
