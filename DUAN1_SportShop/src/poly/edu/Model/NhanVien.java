/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.edu.Model;

import java.util.Date;

/**
 *
 * @author panha
 */
public class NhanVien {

    private int ID;
    private int vaiTro;
    private String CCCD;
    private String ten;
    private int tuoi;
    private String email;
    private String SDT;
    private String diaChi;
    private String mk;
    private String maNV;
    private String create;

    public NhanVien() {
    }

    public NhanVien(int ID, int vaiTro, String CCCD, String ten, int tuoi, String email, String SDT, String diaChi, String mk, String maNV, String create) {
        this.ID = ID;
        this.vaiTro = vaiTro;
        this.CCCD = CCCD;
        this.ten = ten;
        this.tuoi = tuoi;
        this.email = email;
        this.SDT = SDT;
        this.diaChi = diaChi;
        this.mk = mk;
        this.maNV = maNV;
        this.create = create;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(int vaiTro) {
        this.vaiTro = vaiTro;
    }

    public String getCCCD() {
        return CCCD;
    }

    public void setCCCD(String CCCD) {
        this.CCCD = CCCD;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getTuoi() {
        return tuoi;
    }

    public void setTuoi(int tuoi) {
        this.tuoi = tuoi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getMk() {
        return mk;
    }

    public void setMk(String mk) {
        this.mk = mk;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getCreate() {
        return create;
    }

    public void setCreate(String create) {
        this.create = create;
    }

    public String vaitro(int vt) {
        if (vt == 2) {
            return "Nhan vien";
        } else {
            return "Admin";
        }
    }

}
