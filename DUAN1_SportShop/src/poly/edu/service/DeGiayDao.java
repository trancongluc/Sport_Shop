/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.edu.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import poly.edu.Model.DeGiay;
import poly.edu.uitility.DBcontext;
import java.sql.ResultSet;

/**
 *
 * @author Admin
 */
public class DeGiayDao extends EduSysDAO<DeGiay, String> {

    private Integer insert;
    private Integer update;
    private Integer delete;

    @Override
    public void insert(DeGiay entity) {
        insert = 0;
        String sql = """
                    INSERT INTO DeGiay
                                          (Ten, TrangThai, create_at, create_by, update_at, update_by,deleted)
                    VALUES    (?,'Active',GETDATE(),'NV001',GETDATE(),'NV001',0)
                    """;
        try ( Connection cn = DBcontext.getConnection();  PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setObject(1, entity.getTen());

            insert = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(DeGiay entity) {
        update = 0;
        String sql = """
                    UPDATE    DeGiay
                    SET             Ten =?
                    WHERE ID = ?
                    """;
        try ( Connection cn = DBcontext.getConnection();  PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setObject(1, entity.getTen());
            ps.setObject(2, entity.getId());

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
    public ArrayList<DeGiay> selectAll() {
        ArrayList<DeGiay> listDeGiay = new ArrayList<>();
        String sql = "SELECT * FROM DeGiay ORDER BY create_at DESC";

        try ( Connection cn = DBcontext.getConnection();  PreparedStatement ps = cn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt(1);
                String ten = rs.getString(2);
                String trangThai = rs.getString(3);
                Date createAt = rs.getDate(4);
                String createBy = rs.getString(5);
                Date updateAt = rs.getDate(6);
                String updateBy = rs.getString(7);
                boolean deleted = rs.getBoolean(8);

                DeGiay deGiay = new DeGiay(id, ten, trangThai, createAt, createBy, updateAt, updateBy, deleted);
                listDeGiay.add(deGiay);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listDeGiay;
    }

    @Override
    public ArrayList<DeGiay> selectById(String key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected ArrayList<DeGiay> selectBySQL(String sql, Object... args) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ArrayList<DeGiay> selectAll(int offset, int limit) {
        ArrayList<DeGiay> listDeGiay = new ArrayList<>();
        String sql = "SELECT * FROM DeGiay ORDER BY create_at DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try ( Connection cn = DBcontext.getConnection();  PreparedStatement ps = cn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt(1);
                String ten = rs.getString(2);
                String trangThai = rs.getString(3);
                Date createAt = rs.getDate(4);
                String createBy = rs.getString(5);
                Date updateAt = rs.getDate(6);
                String updateBy = rs.getString(7);
                boolean deleted = rs.getBoolean(8);

                DeGiay deGiay = new DeGiay(id, ten, trangThai, createAt, createBy, updateAt, updateBy, deleted);
                listDeGiay.add(deGiay);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listDeGiay;
    }
}
