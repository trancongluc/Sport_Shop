/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.edu.service;

import java.util.ArrayList;
import java.util.Date;
import poly.edu.Model.SanPham;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;
import poly.edu.Panels.SanPhamForm;
import poly.edu.uitility.DBcontext;

/**
 *
 * @author Admin
 */
public class SanPhamDao extends EduSysDAO<SanPham, String> {

    private Integer insert;
    private Integer update;
    private Integer delete;

    @Override
    public void insert(SanPham entity) {
        insert = 0;
        String sql = """
                    INSERT INTO SanPham
                                          (Ten, TrangThai, create_at, create_by, update_at, update_by, deleted, MaSP)
                    VALUES    (?,N'Hết hàng',GETDATE(),'NV001',GETDATE(),'NV001',0,?);
                    """;
        try ( Connection cn = DBcontext.getConnection();  PreparedStatement ps = cn.prepareStatement(sql)) {
            Random random = new Random();
            int IntMaSP = random.nextInt(100000); // Số ngẫu nhiên từ 0 đến 99999
            String maSP = "SP" + IntMaSP;
            ps.setObject(1, entity.getTen());
//            ps.setObject(2, entity.getCreateAt());
//            ps.setObject(3, entity.getUpdateAt());
            ps.setObject(2, maSP);

            insert = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(SanPham entity) {
        update = 0;
        String sql = """
                    UPDATE    SanPham
                    SET             Ten =?
                     WHERE MaSP =?
                    """;
        try ( Connection cn = DBcontext.getConnection();  PreparedStatement ps = cn.prepareStatement(sql)) {
            
            ps.setObject(1, entity.getTen());
            ps.setObject(2, entity.getMaSP());

            update = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<SanPham> selectAll() {
        ArrayList<SanPham> listSanPham = new ArrayList<>();
        String sql = """
                     select SanPham.ID, SanPham.MaSP, SanPham.Ten, SanPham.TrangThai,SanPham.create_at,
                     SanPham.create_by,SanPham.update_at,SanPham.update_by,SanPham.deleted,sumSoLuong
                     	from SanPham 
                     	left join (select ID_SanPham, SUM(SoLuongTon)as sumSoLuong
                     	from SanPhamChiTiet group by ID_SanPham)as sub on SanPham.ID = sub.ID_SanPham ORDER BY create_at DESC
                     """;
        try ( Connection cn = DBcontext.getConnection();  PreparedStatement ps = cn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt(1);
                String maSP = rs.getString(2);
                String ten = rs.getString(3);
                String trangThai = rs.getString(4);
                Date createAt = rs.getDate(5);
                String createBy = rs.getString(6);
                Date updateAt = rs.getDate(7);
                String updateBy = rs.getString(8);
                boolean deleted = rs.getBoolean(9);
                int soLuong = rs.getInt(10);
                SanPham sanPham = new SanPham(id, maSP, ten, trangThai, createAt, createBy, updateAt, updateBy, deleted, soLuong);
                listSanPham.add(sanPham);
            }
            ps.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSanPham;

    }

    @Override
    public ArrayList<SanPham> selectById(String key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected ArrayList<SanPham> selectBySQL(String sql, Object... args) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int tongSanPham(int page) {
        try {
            int litmit = 3;
            String sql = """
                    select COUNT(*) from SanPham
                    """;
            String sqlPT = """
                     select SanPham.ID, SanPham.MaSP, SanPham.Ten, SanPham.TrangThai,SanPham.create_at,SanPham.create_by,SanPham.update_at,SanPham.update_by,SanPham.deleted, sum(SoLuongTon) AS 'SoLuong' from SanPhamChiTiet join SanPham on SanPhamChiTiet.ID_SanPham=SanPham.ID 
                     group by MaSP,SanPham.Ten,SanPham.TrangThai,SanPham.ID,SanPham.create_at,SanPham.create_by,SanPham.update_at,SanPham.update_by,SanPham.deleted 
                     """ + (page - 1) * litmit + "," + litmit;
            Connection cn = DBcontext.getConnection();
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
            rs.close();
            ps.close();
            PreparedStatement psPT = cn.prepareStatement(sql);
            ResultSet rsPT = ps.executeQuery();
            SanPhamForm spf = new SanPhamForm();
//            spf.phanTrangSP(page);
            while (rsPT.next()) {
                int id = rsPT.getInt(1);
                String maSP = rsPT.getString(2);
                String ten = rsPT.getString(3);
                String trangThai = rsPT.getString(4);
                Date createAt = rsPT.getDate(5);
                String createBy = rsPT.getString(6);
                Date updateAt = rsPT.getDate(7);
                String updateBy = rsPT.getString(8);
                boolean deleted = rsPT.getBoolean(9);
                int soLuong = rsPT.getInt(10);
                SanPham sanPham = new SanPham(id, maSP, ten, trangThai, createAt, createBy, updateAt, updateBy, deleted, soLuong);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<SanPham> selectAll(int offset, int limit) {
        ArrayList<SanPham> listSanPham = new ArrayList<>();
        String sql = """
                     select SanPham.ID, SanPham.MaSP, SanPham.Ten, SanPham.TrangThai,SanPham.create_at,SanPham.create_by,SanPham.update_at,SanPham.update_by,SanPham.deleted,sumSoLuong
                    from SanPham 
                    left join (select ID_SanPham, SUM(SoLuongTon)as sumSoLuong
                    from SanPhamChiTiet group by ID_SanPham)as sub on SanPham.ID = sub.ID_SanPham ORDER BY create_at DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
                     """;
        try ( Connection cn = DBcontext.getConnection();  PreparedStatement ps = cn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt(1);
                String maSP = rs.getString(2);
                String ten = rs.getString(3);
                String trangThai = rs.getString(4);
                Date createAt = rs.getDate(5);
                String createBy = rs.getString(6);
                Date updateAt = rs.getDate(7);
                String updateBy = rs.getString(8);
                boolean deleted = rs.getBoolean(9);
                int soLuong = rs.getInt(10);
                SanPham sanPham = new SanPham(id, maSP, ten, trangThai, createAt, createBy, updateAt, updateBy, deleted, soLuong);

                listSanPham.add(sanPham);
            }
            ps.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSanPham;

    }
    public int countSP(){
        String sql ="""
                    Select count(*) From SanPham
                    """;
         try ( Connection cn = DBcontext.getConnection();  PreparedStatement ps = cn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
               return rs.getInt(1);         
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
         return 0;
    }
}
