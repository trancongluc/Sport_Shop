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
public class SanPhamChiTiet {
    int id;  
    SanPham sp;
    MauSac mauSac;
    Size size;
    HangGiay hang;
    ChatLieu chatLieu;
    DeGiay deGiay;
    String maCTSP;
    int gia;
    String moTa;
    int SoLuongTon;
    String trangThai;
    Date createAt;
    String createBy;
    Date updateAt;
    String updateBy;
    boolean deleted;

    public SanPhamChiTiet() {
    }

    public SanPhamChiTiet(SanPham sp, MauSac mauSac, HangGiay hang, DeGiay deGiay, String maCTSP, int gia, String moTa, int SoLuongTon, String trangThai, Date createAt, Date updateAt, boolean deleted) {
        this.sp = sp;
        this.mauSac = mauSac;
        this.hang = hang;
        this.deGiay = deGiay;
        this.maCTSP = maCTSP;
        this.gia = gia;
        this.moTa = moTa;
        this.SoLuongTon = SoLuongTon;
        this.trangThai = trangThai;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.deleted = deleted;
    }

    public SanPhamChiTiet(int id, SanPham sp, MauSac mauSac, Size size, HangGiay hang, ChatLieu chatLieu, DeGiay deGiay, String maCTSP, int gia, String moTa, int SoLuongTon, String trangThai, Date createAt, Date updateAt, boolean deleted) {
        this.id = id;
        this.sp = sp;
        this.mauSac = mauSac;
        this.size = size;
        this.hang = hang;
        this.chatLieu = chatLieu;
        this.deGiay = deGiay;
        this.maCTSP = maCTSP;
        this.gia = gia;
        this.moTa = moTa;
        this.SoLuongTon = SoLuongTon;
        this.trangThai = trangThai;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.deleted = deleted;
    }



    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SanPham getSp() {
        return sp;
    }

    public void setSp(SanPham sp) {
        this.sp = sp;
    }

    public MauSac getMauSac() {
        return mauSac;
    }

    public void setMauSac(MauSac mauSac) {
        this.mauSac = mauSac;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public HangGiay getHang() {
        return hang;
    }

    public void setHang(HangGiay hang) {
        this.hang = hang;
    }

    public ChatLieu getChatLieu() {
        return chatLieu;
    }

    public void setChatLieu(ChatLieu chatLieu) {
        this.chatLieu = chatLieu;
    }

    public DeGiay getDeGiay() {
        return deGiay;
    }

    public void setDeGiay(DeGiay deGiay) {
        this.deGiay = deGiay;
    }

    public String getMaCTSP() {
        return maCTSP;
    }

    public void setMaCTSP(String maCTSP) {
        this.maCTSP = maCTSP;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getSoLuongTon() {
        return SoLuongTon;
    }

    public void setSoLuongTon(int SoLuongTon) {
        this.SoLuongTon = SoLuongTon;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "SanPhamChiTiet{" + "id=" + id + ", sp=" + sp + ", mauSac=" + mauSac + ", size=" + size + ", hang=" + hang + ", chatLieu=" + chatLieu + ", deGiay=" + deGiay + ", maCTSP=" + maCTSP + ", gia=" + gia + ", moTa=" + moTa + ", SoLuongTon=" + SoLuongTon + ", trangThai=" + trangThai + ", createAt=" + createAt + ", createBy=" + createBy + ", updateAt=" + updateAt + ", updateBy=" + updateBy + ", deleted=" + deleted + '}';
    }

    

    public Object[] toDataRow(int index){
        return new Object[]{isDeleted(),index,maCTSP,sp.getTen(),mauSac.getTenMau(),size.getSize(),chatLieu.getTenChatLieu(),hang.getTen(),deGiay.getTen(),gia,moTa,SoLuongTon,isDeleted()== false ?"Hoạt đông":"Không hoạt động"};
    }
    public Object[] toDataRowBanHang(int index){
        return new Object[]{index,maCTSP,sp.getTen(),mauSac.getTenMau(),size.getSize(),chatLieu.getTenChatLieu(),hang.getTen(),deGiay.getTen(),gia,SoLuongTon};
    }
    public Object[] gioHang(int index){
        return new Object[]{index,maCTSP,sp.getTen(),gia};
    }
    public Object[] toDataRowKM(int index){
        return new Object[]{isDeleted(),index,maCTSP,sp.getTen(),gia,hang.getTen(),mauSac.getTenMau(),size.getSize(),chatLieu.getTenChatLieu(),deGiay.getTen(),SoLuongTon};
    }
}
