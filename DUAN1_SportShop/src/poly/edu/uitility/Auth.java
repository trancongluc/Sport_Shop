/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.edu.uitility;

import poly.edu.Model.NhanVien;
import poly.edu.Model.User;

/**
 *
 * @author Admin
 */
public class Auth {
   public static User user = null;

    public static void clear() {
        Auth.user = null;
    }

    public static boolean isLogin() {
        return Auth.user != null;
    }

    public static boolean NhanVien() {
        return Auth.isLogin() && user.getVaiTro().equals("Nhân Viên");
    }
    public static boolean Admin() {
        return Auth.isLogin() && user.getVaiTro().equals("Admin");
    }
}
