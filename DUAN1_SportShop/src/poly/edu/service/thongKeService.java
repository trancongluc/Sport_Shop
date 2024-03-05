/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.edu.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import poly.edu.Model.thongKe;
import poly.edu.uitility.DBcontext;

/**
 *
 * @author panha
 */
public class thongKeService {

     public int countKH() {
        int sl = 0;

        String sql = "select count(*) from KhachHang";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                sl = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return sl;
    }
//
//    public int countKHby(int nam) {
//        int sl = 0;
//
//        String sql = "select count(*) from KhachHang where year(create_at)=?";
//        Connection cn = DBcontext.getConnection();
//        try {
//            PreparedStatement pstm = cn.prepareStatement(sql);
//            pstm.setInt(1, nam);
//            ResultSet rs = pstm.executeQuery();
//            while (rs.next()) {
//                sl = rs.getInt(1);
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        return sl;
//    }

    public int countTCbm(int thg) {
        int sl = 0;

        String sql = "select count(*) from HoaDon "
                + "where (trangThai like N'Thành công')"
                + " AND deleted = 0 and  month(create_at)=?";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setInt(1, thg);

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                sl = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return sl;
    }

    public int countTCby(int nam) {
        int sl = 0;

        String sql = "select count(*) from HoaDon "
                + "where (trangThai like N'Thành công')"
                + " AND deleted = 0 and year(create_at)=?";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setInt(1, nam);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                sl = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return sl;
    }

    public int countDTbm(int thg) {
        int sl = 0;

        String sql = "SELECT SUM(tongTien) FROM HoaDon "
                + "WHERE MONTH(ngayXacNhan) = ? "
                + "and (trangThai like N'Thành công') AND deleted = 0";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setInt(1, thg);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                sl = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return sl;
    }

    public int countDTby(int nam) {
        int sl = 0;

        String sql = "SELECT SUM(tongTien) FROM HoaDon "
                + "WHERE YEAR(ngayXacNhan) = ? "
                + "and (trangThai like N'Thành công') AND deleted = 0";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setInt(1, nam);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                sl = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return sl;
    }
//

    public int countHuybm(int thg) {
        int sl = 0;

        String sql = "SELECT count(*) FROM HoaDon WHERE MONTH(ngayXacNhan) = ? and deleted = 1";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setInt(1, thg);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                sl = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return sl;
    }

    public int countHuyby(int nam) {
        int sl = 0;

        String sql = "SELECT count(*) FROM HoaDon WHERE YEAR(ngayXacNhan) = ? and deleted = 1";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setInt(1, nam);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                sl = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return sl;
    }

    public int countHuybt(String start, String end) {
        int sl = 0;

        String sql = "SELECT count(*) FROM HoaDon WHERE ngayXacNhan BETWEEN ? AND ? and deleted = 1";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setString(1, start);
            pstm.setString(2, end);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                sl = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return sl;
    }

//    public int countKHbt(String start, String end) {
//        int sl = 0;
//
//        String sql = "select count(*) from KhachHang where create_at BETWEEN ? AND ? ";
//        Connection cn = DBcontext.getConnection();
//        try {
//            PreparedStatement pstm = cn.prepareStatement(sql);
//            pstm.setString(1, start);
//            pstm.setString(2, end);
//            ResultSet rs = pstm.executeQuery();
//            while (rs.next()) {
//                sl = rs.getInt(1);
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        return sl;
//    }
    public int countDTbt(String start, String end) {
        int sl = 0;

        String sql = "SELECT SUM(tongTien) FROM HoaDon "
                + "WHERE ngayXacNhan BETWEEN ? AND ? "
                + "and (trangThai like N'Thành công') AND deleted = 0";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setString(1, start);
            pstm.setString(2, end);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                sl = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return sl;
    }

    public int countTCbt(String start, String end) {
        int sl = 0;

        String sql = "select count(*) from HoaDon "
                + "where (trangThai like N'Thành công') "
                + "AND deleted = 0 and create_at BETWEEN ? AND ?";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setString(1, start);
            pstm.setString(2, end);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                sl = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return sl;
    }

    public ArrayList<thongKe> BarChartTest(int nam) {
        ArrayList<thongKe> tk = new ArrayList<>();
        String sql = "SELECT SUM(tongTien), month(ngayXacNhan) FROM HoaDon\n"
                + "               WHERE Year(ngayXacNhan) = ? AND (trangThai like N'Thành công') AND deleted = 0\n"
                + "               GROUP BY Month(ngayXacNhan)\n"
                + "               ORDER BY Month(ngayXacNhan)";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setInt(1, nam);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                thongKe ntk = new thongKe(rs.getFloat(1), rs.getInt(2));
                tk.add(ntk);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return tk;
    }

    public ArrayList<thongKe> BarChartTest2(int thg) {
        ArrayList<thongKe> tk = new ArrayList<>();
        String sql = "SELECT SUM(tongTien), Day(ngayXacNhan) FROM HoaDon\n"
                + "               WHERE Month(ngayXacNhan) = ? AND (trangThai like N'Thành công') AND deleted = 0\n"
                + "               GROUP BY Day(ngayXacNhan), Month(ngayXacNhan)\n"
                + "               ORDER BY Day(ngayXacNhan)";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setInt(1, thg);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                thongKe ntk = new thongKe(rs.getFloat(1), rs.getInt(2));
                tk.add(ntk);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return tk;
    }

    public ArrayList<thongKe> BarChartTest3(String Start, String End) {
        ArrayList<thongKe> tk = new ArrayList<>();
        String sql = "				SELECT SUM(tongTien), day(ngayXacNhan) FROM HoaDon \n"
                + "                WHERE ngayXacNhan BETWEEN ? AND ?\n"
                + "                 AND (trangThai like N'Thành công') AND deleted = 0 \n"
                + "                GROUP BY day(ngayXacNhan)";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setString(1, Start);
            pstm.setString(2, End);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                thongKe ntk = new thongKe(rs.getFloat(1), rs.getInt(2));
                tk.add(ntk);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return tk;
    }
}
