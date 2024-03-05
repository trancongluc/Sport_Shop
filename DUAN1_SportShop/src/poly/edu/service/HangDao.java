/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.edu.service;

import java.util.ArrayList;
import poly.edu.Model.HangGiay;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import poly.edu.uitility.DBcontext;
/**
 *
 * @author Admin
 */
public class HangDao extends EduSysDAO<HangGiay, String>{
    private Integer insert;
    private Integer update;
    private Integer delete;
    @Override
    public void insert(HangGiay entity) {
           insert = 0;
        String sql = """
                    INSERT INTO Hang
                                          (Ten, TrangThai, create_at, create_by, update_at, update_by, deleted)
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
    public void update(HangGiay entity) {
        update = 0;
        String sql = """
                    UPDATE    Hang
                    SET             Ten =?
                    WHERE ID = ?
                    """;
        try ( Connection cn = DBcontext.getConnection();  PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setObject(1,entity.getTen());
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
                    UPDATE Hang SET  TrangThai = 1 WHERE ID = ?
                    """;
        try ( Connection cn = DBcontext.getConnection();  PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setObject(1, key);

            delete = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<HangGiay> selectAll() {
        ArrayList<HangGiay> listHangGiay = new ArrayList<>();
        String sql = "SELECT * FROM Hang ORDER BY create_at DESC";

        try (Connection cn = DBcontext.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt(1);
                String ten = rs.getString(2);
                String trangThai = rs.getString(3);
                Date createAt = rs.getDate(4);
                String createBy = rs.getString(5);
                Date updateAt = rs.getDate(6);
                String updateBy = rs.getString(7);
                boolean deleted = rs.getBoolean(8);

                HangGiay hangGiay = new HangGiay(id, ten, trangThai, createAt, createBy, updateAt, updateBy, deleted);
                listHangGiay.add(hangGiay);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listHangGiay;
    }

    @Override
    public ArrayList<HangGiay> selectById(String key) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    protected ArrayList<HangGiay> selectBySQL(String sql, Object... args) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

   
    
}
