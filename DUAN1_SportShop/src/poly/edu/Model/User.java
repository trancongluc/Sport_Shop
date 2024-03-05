/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.edu.Model;

/**
 *
 * @author Admin
 */
public class User {
    private String TenDangNhap;
    private String MatKhau;
    private String VaiTro;

    public User() {
    }

    public User(String TenDangNhap, String MatKhau, String VaiTro) {
        this.TenDangNhap = TenDangNhap;
        this.MatKhau = MatKhau;
        this.VaiTro = VaiTro;
    }

    public String getTenDangNhap() {
        return TenDangNhap;
    }

    public void setTenDangNhap(String TenDangNhap) {
        this.TenDangNhap = TenDangNhap;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String MatKhau) {
        this.MatKhau = MatKhau;
    }

    public String getVaiTro() {
        return VaiTro;
    }

    public void setVaiTro(String VaiTro) {
        this.VaiTro = VaiTro;
    }
}
