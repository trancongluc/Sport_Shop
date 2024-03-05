/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.edu.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import poly.edu.Model.NhanVien;
import poly.edu.uitility.DBcontext;

/**
 *
 * @author panha
 */
public class NhanVienService {

    public ArrayList<NhanVien> getAllEm() {
        ArrayList<NhanVien> lst = new ArrayList<>();

        String sql = "select ID, ID_VaiTro, MaCCCD, TenNV, Tuoi, Email, SDT, DiaChi, MatKhau, MaNV, create_at from NhanVien order by NhanVien.create_at desc";

        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                NhanVien nv = new NhanVien(rs.getInt(1), rs.getInt(2), rs.getString(3)
                        , rs.getString(4), rs.getInt(5), rs.getString(6), rs.getString(7), rs.getString(8)
                        , rs.getString(9), rs.getString(10), rs.getString(11));
                lst.add(nv);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return lst;

        /**
         *
         * @param st
         * @return
         */
    }
    public ArrayList<NhanVien> getAllEmTheoMa(String maNV) {
        ArrayList<NhanVien> lst = new ArrayList<>();

        String sql = "select ID, ID_VaiTro, MaCCCD, TenNV, Tuoi, Email, SDT, DiaChi, MatKhau, MaNV, create_at from NhanVien WHERE MaNV = ? order by NhanVien.create_at desc ";

        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setObject(1, maNV);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                NhanVien nv = new NhanVien(rs.getInt(1), rs.getInt(2), rs.getString(3)
                        , rs.getString(4), rs.getInt(5), rs.getString(6), rs.getString(7), rs.getString(8)
                        , rs.getString(9), rs.getString(10), rs.getString(11));
                lst.add(nv);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return lst;

        /**
         *
         * @param st
         * @return
         */
    }
    public ArrayList<NhanVien> searchEm(String ten, String mail, String nvien) {
        ArrayList<NhanVien> lst = new ArrayList<>();

        String sql = "select ID, ID_VaiTro, MaCCCD, TenNV, Tuoi, Email, SDT, DiaChi, MatKhau, MaNV, create_at from NhanVien where TenNV Like ? or Email like ? or MaNV like ? ";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setString(1, ten);
            pstm.setString(2, mail);
            pstm.setString(3, nvien);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                NhanVien nv = new NhanVien(rs.getInt(1), rs.getInt(2), rs.getString(3)
                        , rs.getString(4), rs.getInt(5), rs.getString(6), rs.getString(7), rs.getString(8)
                        , rs.getString(9), rs.getString(10), rs.getString(11));
                lst.add(nv);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return lst;
    }

    public Integer AddEm(NhanVien st) {
        Integer row = null;
        String sql = "insert into NhanVien (ID_VaiTro, MaCCCD, TenNV, Tuoi, Email, SDT, DiaChi"
                + ", create_at, create_by, update_at, update_by, deleted, MatKhau, MaNV)"
                + " values (?,?,?,?,?,?,?,getdate(), N'NV001', CAST(N'2023-11-13' AS Date), N'NV001', 0,?,?)";

        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setInt(1, st.getVaiTro());
            pstm.setString(2, st.getCCCD());
            pstm.setString(3, st.getTen());
            pstm.setInt(4, st.getTuoi());
            pstm.setString(5, st.getEmail());
            pstm.setString(6, st.getSDT());
            pstm.setString(7, st.getDiaChi());
            pstm.setString(8, st.getMk());
            pstm.setString(9, st.getMaNV());


            row = pstm.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
        return row;
    }

    public Integer AddVT(String vaitro) {
        Integer row = null;
        String sql = "insert into VaiTro ( tenVaiTro, create_at, create_by, update_at, update_by, deleted)"
                + " values ( ?, CAST(N'2023-11-13' AS Date), N'NV001', CAST(N'2023-11-13' AS Date), N'NV001', 0)";

        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setString(1, vaitro);

            row = pstm.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
        return row;
    }

    public Integer DeleteEm(int maNV) {
        Integer row = null;
        String sql = "DELETE FROM NhanVien WHERE ID LIKE ? ";

        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setInt(1, maNV);
            row = pstm.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
        return row;
    }

    public Integer DeleteVT(int maID) {
        Integer row = null;
        String sql = "DELETE FROM VaiTro WHERE ID LIKE ? ";

        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setInt(1, maID);
            row = pstm.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
        return row;
    }

    public Integer UpdateEm(NhanVien st, int ID) {
        Integer row = null;
        String sql = "UPDATE NhanVien\n"
                + "SET ID_VaiTro = ?, MaCCCD = ?, TenNV = ?, Tuoi = ?, Email = ?, SDT = ?, DiaChi = ?, MatKhau = ?, MaNV = ?\n"
                + "WHERE ID = ?";

        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setInt(1, st.getVaiTro());
            pstm.setString(2, st.getCCCD());
            pstm.setString(3, st.getTen());
            pstm.setInt(4, st.getTuoi());
            pstm.setString(5, st.getEmail());
            pstm.setString(6, st.getSDT());
            pstm.setString(7, st.getDiaChi());
            pstm.setString(8, st.getMk());
            pstm.setString(9, st.getMaNV());
            pstm.setInt(10, ID);

            row = pstm.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
        return row;
    }

    public Integer UpdateVT(int ID, String vt) {
        Integer row = null;
        String sql = "UPDATE VaiTro\n"
                + "SET tenVaiTro = ?\n"
                + "WHERE ID = ?";

        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setInt(2, ID);
            pstm.setString(1, vt);

            row = pstm.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
        return row;
    }

}
