/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.edu.Model;

/**
 *
 * @author Dell
 */
public class TKHoaDon {
    private String MaHoaDon;
    private String NgayGiaoDich;
    private String TongTien;
    private String TrangThai;

    public TKHoaDon() {
    }

    public TKHoaDon(String MaHoaDon, String NgayGiaoDich, String TongTien, String TrangThai) {
        this.MaHoaDon = MaHoaDon;
        this.NgayGiaoDich = NgayGiaoDich;
        this.TongTien = TongTien;
        this.TrangThai = TrangThai;
    }

    public String getMaHoaDon() {
        return MaHoaDon;
    }

    public void setMaHoaDon(String MaHoaDon) {
        this.MaHoaDon = MaHoaDon;
    }

    public String getNgayGiaoDich() {
        return NgayGiaoDich;
    }

    public void setNgayGiaoDich(String NgayGiaoDich) {
        this.NgayGiaoDich = NgayGiaoDich;
    }

    public String getTongTien() {
        return TongTien;
    }

    public void setTongTien(String TongTien) {
        this.TongTien = TongTien;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String TrangThai) {
        this.TrangThai = TrangThai;
    }
    
}
