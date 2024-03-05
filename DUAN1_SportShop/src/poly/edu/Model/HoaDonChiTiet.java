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
public class HoaDonChiTiet {

    int id;
    int ID_spct;
    int ID_hd;
    int sl;
    int gia;
    String maSPCT;
    String tenSP;
    Date createAt;
    String createBy;
    Date updateAt;
    String updateBy;
    boolean delete;

    public HoaDonChiTiet() {
    }

    public HoaDonChiTiet(int id, int ID_spct, int ID_hd, int sl, int gia, String maSPCT, String tenSP, Date createAt, String createBy, Date updateAt, String updateBy, boolean delete) {
        this.id = id;
        this.ID_spct = ID_spct;
        this.ID_hd = ID_hd;
        this.sl = sl;
        this.gia = gia;
        this.maSPCT = maSPCT;
        this.tenSP = tenSP;
        this.createAt = createAt;
        this.createBy = createBy;
        this.updateAt = updateAt;
        this.updateBy = updateBy;
        this.delete = delete;
    }

    public HoaDonChiTiet(int id, int ID_spct, int ID_hd, int sl, int gia, String maSPCT, String tenSP, boolean delete) {
        this.id = id;
        this.ID_spct = ID_spct;
        this.ID_hd = ID_hd;
        this.sl = sl;
        this.gia = gia;
        this.maSPCT = maSPCT;
        this.tenSP = tenSP;
        this.delete = delete;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getID_spct() {
        return ID_spct;
    }

    public void setID_spct(int ID_spct) {
        this.ID_spct = ID_spct;
    }

    public int getID_hd() {
        return ID_hd;
    }

    public void setID_hd(int ID_hd) {
        this.ID_hd = ID_hd;
    }

    public int getSl() {
        return sl;
    }

    public void setSl(int sl) {
        this.sl = sl;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public String getMaSPCT() {
        return maSPCT;
    }

    public void setMaSPCT(String maSPCT) {
        this.maSPCT = maSPCT;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

}
