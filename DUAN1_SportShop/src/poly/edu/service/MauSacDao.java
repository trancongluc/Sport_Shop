/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.edu.service;

import java.util.ArrayList;
import poly.edu.Model.MauSac;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import poly.edu.uitility.DBcontext;
/**
 *
 * @author Admin
 */
public class MauSacDao extends EduSysDAO<MauSac, String>{
    private Integer insert;
    private Integer update;
    private Integer delete;
    @Override
    public void insert(MauSac entity) {
         insert = 0;
        String sql = """
                   INSERT INTO MauSac
                                          (MaMau, TenMau, TrangThai, create_at, create_by, update_at, update_by, deleted)
                    VALUES    ('NOT',?,'Active',GETDATE(),'NV001',GETDATE(),'NV001',0)
                    """;
        try ( Connection cn = DBcontext.getConnection();  PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setObject(1, entity.getTenMau());
                             
            insert = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(MauSac entity) {
        update = 0;
        String sql = """
                    UPDATE    MauSac
                    SET             TenMau =?
                    WHERE ID = ?
                    """;
        try ( Connection cn = DBcontext.getConnection();  PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setObject(1, entity.getTenMau());
            ps.setObject(2, entity.getId());
            
            update = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String key) {
        delete = 0;
        String sql = """
                    UPDATE MauSac SET  TrangThai = 1 WHERE ID = ?
                    """;
        try ( Connection cn = DBcontext.getConnection();  PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setObject(1, key);

            delete = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<MauSac> selectAll() {
         ArrayList<MauSac> listMauSac = new ArrayList<>();
        String sql = "SELECT * FROM MauSac ORDER BY create_at DESC";

        try (Connection cn = DBcontext.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt(1);
                String maMau = rs.getString(2);
                String tenMau = rs.getString(3);
                String trangThai = rs.getString(4);
                Date createAt = rs.getDate(5);
                String createBy = rs.getString(6);
                Date updateAt = rs.getDate(7);
                String updateBy = rs.getString(8);
                boolean deleted = rs.getBoolean(9);

                MauSac mauSac = new MauSac(id, maMau, tenMau, trangThai, createAt, createBy, updateAt, updateBy, deleted);
                listMauSac.add(mauSac);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listMauSac;
    }

    @Override
    public ArrayList<MauSac> selectById(String key) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    protected ArrayList<MauSac> selectBySQL(String sql, Object... args) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

 
}
