/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.edu.Model;

import java.util.Date;

/**
 *
 * @author Admin
 */
public class KhuyenMai {

    int id;
    int id_Voucher;
    int id_SPCT;
    String MaKM;
    String tenVC;
    int soLuong;
    int soTienGiam;
    String hinhThucGiamGia;
    String mucGiamGia;
    Date ngayBatDau;
    Date ngayKetThuc;
    Date ngayTao;
    String nguoiTao;
    String nguoiSua;
    Date ngaySua;
    String tenSP;
    String maSPCT;
    int giaBan;
    String tenHang;
    String mauSac;
    String chatLieu;
    int Size;
    String DeGiay;
    boolean deleted;
    String trangThai;
    int giaTriDHToiThieu;

    public KhuyenMai() {
    }

    public KhuyenMai(int id, int id_Voucher, int id_SPCT, String MaKM, String tenVC, int soLuong, int soTienGiam, String hinhThucGiamGia, String mucGiamGia, Date ngayBatDau, Date ngayKetThuc, Date ngayTao, String nguoiTao, String nguoiSua, Date ngaySua, String tenSP, String maSPCT, int giaBan, String tenHang, String mauSac, String chatLieu, int Size, String DeGiay, boolean deleted, String trangThai, int giaTriDHToiThieu) {
        this.id = id;
        this.id_Voucher = id_Voucher;
        this.id_SPCT = id_SPCT;
        this.MaKM = MaKM;
        this.tenVC = tenVC;
        this.soLuong = soLuong;
        this.soTienGiam = soTienGiam;
        this.hinhThucGiamGia = hinhThucGiamGia;
        this.mucGiamGia = mucGiamGia;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.ngayTao = ngayTao;
        this.nguoiTao = nguoiTao;
        this.nguoiSua = nguoiSua;
        this.ngaySua = ngaySua;
        this.tenSP = tenSP;
        this.maSPCT = maSPCT;
        this.giaBan = giaBan;
        this.tenHang = tenHang;
        this.mauSac = mauSac;
        this.chatLieu = chatLieu;
        this.Size = Size;
        this.DeGiay = DeGiay;
        this.deleted = deleted;
        this.trangThai = trangThai;
        this.giaTriDHToiThieu = giaTriDHToiThieu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_Voucher() {
        return id_Voucher;
    }

    public void setId_Voucher(int id_Voucher) {
        this.id_Voucher = id_Voucher;
    }

    public int getId_SPCT() {
        return id_SPCT;
    }

    public void setId_SPCT(int id_SPCT) {
        this.id_SPCT = id_SPCT;
    }

    public String getMaKM() {
        return MaKM;
    }

    public void setMaKM(String MaKM) {
        this.MaKM = MaKM;
    }

    public String getTenVC() {
        return tenVC;
    }

    public void setTenVC(String tenVC) {
        this.tenVC = tenVC;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getSoTienGiam() {
        return soTienGiam;
    }

    public void setSoTienGiam(int soTienGiam) {
        this.soTienGiam = soTienGiam;
    }

    public String getHinhThucGiamGia() {
        return hinhThucGiamGia;
    }

    public void setHinhThucGiamGia(String hinhThucGiamGia) {
        this.hinhThucGiamGia = hinhThucGiamGia;
    }

    public String getMucGiamGia() {
        return mucGiamGia;
    }

    public void setMucGiamGia(String mucGiamGia) {
        this.mucGiamGia = mucGiamGia;
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getNguoiTao() {
        return nguoiTao;
    }

    public void setNguoiTao(String nguoiTao) {
        this.nguoiTao = nguoiTao;
    }

    public String getNguoiSua() {
        return nguoiSua;
    }

    public void setNguoiSua(String nguoiSua) {
        this.nguoiSua = nguoiSua;
    }

    public Date getNgaySua() {
        return ngaySua;
    }

    public void setNgaySua(Date ngaySua) {
        this.ngaySua = ngaySua;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getMaSPCT() {
        return maSPCT;
    }

    public void setMaSPCT(String maSPCT) {
        this.maSPCT = maSPCT;
    }

    public int getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(int giaBan) {
        this.giaBan = giaBan;
    }

    public String getTenHang() {
        return tenHang;
    }

    public void setTenHang(String tenHang) {
        this.tenHang = tenHang;
    }

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }

    public String getChatLieu() {
        return chatLieu;
    }

    public void setChatLieu(String chatLieu) {
        this.chatLieu = chatLieu;
    }

    public int getSize() {
        return Size;
    }

    public void setSize(int Size) {
        this.Size = Size;
    }

    public String getDeGiay() {
        return DeGiay;
    }

    public void setDeGiay(String DeGiay) {
        this.DeGiay = DeGiay;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public int getGiaTriDHToiThieu() {
        return giaTriDHToiThieu;
    }

    public void setGiaTriDHToiThieu(int giaTriDHToiThieu) {
        this.giaTriDHToiThieu = giaTriDHToiThieu;
    }

    @Override
    public String toString() {
        return tenVC;
    }

   
}
