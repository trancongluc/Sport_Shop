/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.edu.Model;

/**
 *
 * @author Admin
 */
public class thongKe {

    private float doanhThu;
    private int thang;

    public thongKe() {
    }

    public thongKe(float doanhThu, int thang) {
        this.doanhThu = doanhThu;
        this.thang = thang;
    }

    public float getDoanhThu() {
        return doanhThu;
    }

    public void setDoanhThu(float doanhThu) {
        this.doanhThu = doanhThu;
    }

    public int getThang() {
        return thang;
    }

    public void setThang(int thang) {
        this.thang = thang;
    }
}
