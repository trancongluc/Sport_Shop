/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.edu.service;

import poly.edu.Model.User;
import java.sql.*;
import poly.edu.uitility.DBcontext;
/**
 *
 * @author Admin
 */
public class UserService {
    public User AllUser(String MaNV) {
        User u = null;
        String sql = "Select * FROM NhanVien Join VaiTro ON VaiTro.ID = NhanVien.ID_VaiTro WHERE MaNV = ? ";
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);
            pstm.setString(1, MaNV);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                u = new User();
                u.setTenDangNhap(rs.getString("MaNV"));
                u.setMatKhau(rs.getString("MatKhau"));
                u.setVaiTro(rs.getString("tenVaiTro"));
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return u;
    }
}
