/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.edu.Model;

import java.util.Date;

/**
 *
 * @author Dell
 */
public class KhachHang {

    private String tenKH;
    private Date NgaySinh;
    private boolean GioiTinh;
    private String MaKH;
    private String Email;
    private String sdt;
    private String DiaChi;
    private String ThanhPho;
    private String QuanHuyen;
    private String XaPhuong;
    private String TrangThai;
    private int ID;
    private int IDKH;

    public KhachHang() {
    }

    public KhachHang(String tenKH, Date NgaySinh, boolean GioiTinh, String MaKH, String Email, String sdt, String DiaChi, String ThanhPho, String QuanHuyen, String XaPhuong, String TrangThai, int ID, int IDKH) {
        this.tenKH = tenKH;
        this.NgaySinh = NgaySinh;
        this.GioiTinh = GioiTinh;
        this.MaKH = MaKH;
        this.Email = Email;
        this.sdt = sdt;
        this.DiaChi = DiaChi;
        this.ThanhPho = ThanhPho;
        this.QuanHuyen = QuanHuyen;
        this.XaPhuong = XaPhuong;
        this.TrangThai = TrangThai;
        this.ID = ID;
        this.IDKH = IDKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public Date getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(Date NgaySinh) {
        this.NgaySinh = NgaySinh;
    }

    public boolean isGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(boolean GioiTinh) {
        this.GioiTinh = GioiTinh;
    }

    public String getMaKH() {
        return MaKH;
    }

    public void setMaKH(String MaKH) {
        this.MaKH = MaKH;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public String getThanhPho() {
        return ThanhPho;
    }

    public void setThanhPho(String ThanhPho) {
        this.ThanhPho = ThanhPho;
    }

    public String getQuanHuyen() {
        return QuanHuyen;
    }

    public void setQuanHuyen(String QuanHuyen) {
        this.QuanHuyen = QuanHuyen;
    }

    public String getXaPhuong() {
        return XaPhuong;
    }

    public void setXaPhuong(String XaPhuong) {
        this.XaPhuong = XaPhuong;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String TrangThai) {
        this.TrangThai = TrangThai;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getIDKH() {
        return IDKH;
    }

    public void setIDKH(int IDKH) {
        this.IDKH = IDKH;
    }

    

}
