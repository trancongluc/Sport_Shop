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
public class HoaDon {

    int id;
    int id_KH;
    int id_NV;
    int id_VC;
    private String maHoaDon;
    private Date ngayTao;
    private Date ngayThanhToan;
    private Date ngayXacNhan;
    private String tenKH;
    private int tongTien;
    private String trangThai;
    private String diaChi;
    private String sdt;
    private int soTienGiam;
    private String hinhThucThanhToan;
    private Date ngayShip;
    private Date ngayMuonNhan;
    private int phiShip;
    private String tenSP;
    private int soLuong;
    private String tenNV;
    private String maNV;
    private int tienGiamGia;
    private String maSP;
    private String thuongHieu;
    private int kichCo;
    String trangThaiThanhToan;
    private String nguoiTao;
    private String hang;
    private String mausac;
    private String chatLieu;
    private String DeGiay;
    private int size;
    private int giaSP;
    private int tienKhachDua;
    boolean deleted;
    int soluong;
    String sdtNGuoiShip;
    String tenNguoiShip;
    String tenVC;

    public HoaDon() {
    }

    public HoaDon(int id, int id_KH, int id_NV, String maHoaDon, Date ngayTao, Date ngayThanhToan, Date ngayXacNhan, String tenKH, int tongTien, String trangThai, String diaChi, String sdt, int soTienGiam, String hinhThucThanhToan, Date ngayShip, Date ngayMuonNhan, int phiShip, String tenSP, int soLuong, String tenNV, String maNV, int tienGiamGia, String maSP, String thuongHieu, int kichCo, String trangThaiThanhToan, String nguoiTao, String hang, String mausac, String chatLieu, String DeGiay, int size, int giaSP, int tienKhachDua, boolean deleted, int soluong, String sdtNGuoiShip, String tenNguoiShip) {
        this.id = id;
        this.id_KH = id_KH;
        this.id_NV = id_NV;
        this.maHoaDon = maHoaDon;
        this.ngayTao = ngayTao;
        this.ngayThanhToan = ngayThanhToan;
        this.ngayXacNhan = ngayXacNhan;
        this.tenKH = tenKH;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.soTienGiam = soTienGiam;
        this.hinhThucThanhToan = hinhThucThanhToan;
        this.ngayShip = ngayShip;
        this.ngayMuonNhan = ngayMuonNhan;
        this.phiShip = phiShip;
        this.tenSP = tenSP;
        this.soLuong = soLuong;
        this.tenNV = tenNV;
        this.maNV = maNV;
        this.tienGiamGia = tienGiamGia;
        this.maSP = maSP;
        this.thuongHieu = thuongHieu;
        this.kichCo = kichCo;
        this.trangThaiThanhToan = trangThaiThanhToan;
        this.nguoiTao = nguoiTao;
        this.hang = hang;
        this.mausac = mausac;
        this.chatLieu = chatLieu;
        this.DeGiay = DeGiay;
        this.size = size;
        this.giaSP = giaSP;
        this.tienKhachDua = tienKhachDua;
        this.deleted = deleted;
        this.soluong = soluong;
        this.sdtNGuoiShip = sdtNGuoiShip;
        this.tenNguoiShip = tenNguoiShip;
    }

    public HoaDon(int id, int id_KH, int id_NV, int id_VC, String maHoaDon, Date ngayTao, Date ngayThanhToan, Date ngayXacNhan, String tenKH, int tongTien, String trangThai, String diaChi, String sdt, int soTienGiam, String hinhThucThanhToan, Date ngayShip, Date ngayMuonNhan, int phiShip, String tenSP, int soLuong, String tenNV, String maNV, int tienGiamGia, String maSP, String thuongHieu, int kichCo, String trangThaiThanhToan, String nguoiTao, String hang, String mausac, String chatLieu, String DeGiay, int size, int giaSP, int tienKhachDua, boolean deleted, int soluong, String sdtNGuoiShip, String tenNguoiShip, String tenVC) {
        this.id = id;
        this.id_KH = id_KH;
        this.id_NV = id_NV;
        this.id_VC = id_VC;
        this.maHoaDon = maHoaDon;
        this.ngayTao = ngayTao;
        this.ngayThanhToan = ngayThanhToan;
        this.ngayXacNhan = ngayXacNhan;
        this.tenKH = tenKH;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.soTienGiam = soTienGiam;
        this.hinhThucThanhToan = hinhThucThanhToan;
        this.ngayShip = ngayShip;
        this.ngayMuonNhan = ngayMuonNhan;
        this.phiShip = phiShip;
        this.tenSP = tenSP;
        this.soLuong = soLuong;
        this.tenNV = tenNV;
        this.maNV = maNV;
        this.tienGiamGia = tienGiamGia;
        this.maSP = maSP;
        this.thuongHieu = thuongHieu;
        this.kichCo = kichCo;
        this.trangThaiThanhToan = trangThaiThanhToan;
        this.nguoiTao = nguoiTao;
        this.hang = hang;
        this.mausac = mausac;
        this.chatLieu = chatLieu;
        this.DeGiay = DeGiay;
        this.size = size;
        this.giaSP = giaSP;
        this.tienKhachDua = tienKhachDua;
        this.deleted = deleted;
        this.soluong = soluong;
        this.sdtNGuoiShip = sdtNGuoiShip;
        this.tenNguoiShip = tenNguoiShip;
        this.tenVC = tenVC;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_KH() {
        return id_KH;
    }

    public void setId_KH(int id_KH) {
        this.id_KH = id_KH;
    }

    public int getId_NV() {
        return id_NV;
    }

    public void setId_NV(int id_NV) {
        this.id_NV = id_NV;
    }

    public int getId_VC() {
        return id_VC;
    }

    public void setId_VC(int id_VC) {
        this.id_VC = id_VC;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public Date getNgayThanhToan() {
        return ngayThanhToan;
    }

    public void setNgayThanhToan(Date ngayThanhToan) {
        this.ngayThanhToan = ngayThanhToan;
    }

    public Date getNgayXacNhan() {
        return ngayXacNhan;
    }

    public void setNgayXacNhan(Date ngayXacNhan) {
        this.ngayXacNhan = ngayXacNhan;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public int getSoTienGiam() {
        return soTienGiam;
    }

    public void setSoTienGiam(int soTienGiam) {
        this.soTienGiam = soTienGiam;
    }

    public String getHinhThucThanhToan() {
        return hinhThucThanhToan;
    }

    public void setHinhThucThanhToan(String hinhThucThanhToan) {
        this.hinhThucThanhToan = hinhThucThanhToan;
    }

    public Date getNgayShip() {
        return ngayShip;
    }

    public void setNgayShip(Date ngayShip) {
        this.ngayShip = ngayShip;
    }

    public Date getNgayMuonNhan() {
        return ngayMuonNhan;
    }

    public void setNgayMuonNhan(Date ngayMuonNhan) {
        this.ngayMuonNhan = ngayMuonNhan;
    }

    public int getPhiShip() {
        return phiShip;
    }

    public void setPhiShip(int phiShip) {
        this.phiShip = phiShip;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public int getTienGiamGia() {
        return tienGiamGia;
    }

    public void setTienGiamGia(int tienGiamGia) {
        this.tienGiamGia = tienGiamGia;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getThuongHieu() {
        return thuongHieu;
    }

    public void setThuongHieu(String thuongHieu) {
        this.thuongHieu = thuongHieu;
    }

    public int getKichCo() {
        return kichCo;
    }

    public void setKichCo(int kichCo) {
        this.kichCo = kichCo;
    }

    public String getTrangThaiThanhToan() {
        return trangThaiThanhToan;
    }

    public void setTrangThaiThanhToan(String trangThaiThanhToan) {
        this.trangThaiThanhToan = trangThaiThanhToan;
    }

    public String getNguoiTao() {
        return nguoiTao;
    }

    public void setNguoiTao(String nguoiTao) {
        this.nguoiTao = nguoiTao;
    }

    public String getHang() {
        return hang;
    }

    public void setHang(String hang) {
        this.hang = hang;
    }

    public String getMausac() {
        return mausac;
    }

    public void setMausac(String mausac) {
        this.mausac = mausac;
    }

    public String getChatLieu() {
        return chatLieu;
    }

    public void setChatLieu(String chatLieu) {
        this.chatLieu = chatLieu;
    }

    public String getDeGiay() {
        return DeGiay;
    }

    public void setDeGiay(String DeGiay) {
        this.DeGiay = DeGiay;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getGiaSP() {
        return giaSP;
    }

    public void setGiaSP(int giaSP) {
        this.giaSP = giaSP;
    }

    public int getTienKhachDua() {
        return tienKhachDua;
    }

    public void setTienKhachDua(int tienKhachDua) {
        this.tienKhachDua = tienKhachDua;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getSdtNGuoiShip() {
        return sdtNGuoiShip;
    }

    public void setSdtNGuoiShip(String sdtNGuoiShip) {
        this.sdtNGuoiShip = sdtNGuoiShip;
    }

    public String getTenNguoiShip() {
        return tenNguoiShip;
    }

    public void setTenNguoiShip(String tenNguoiShip) {
        this.tenNguoiShip = tenNguoiShip;
    }

    public String getTenVC() {
        return tenVC;
    }

    public void setTenVC(String tenVC) {
        this.tenVC = tenVC;
    }

    
    
}
