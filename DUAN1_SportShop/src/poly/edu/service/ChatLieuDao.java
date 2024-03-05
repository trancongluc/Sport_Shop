/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.edu.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import poly.edu.Model.ChatLieu;
import poly.edu.uitility.DBcontext;

/**
 *
 * @author Admin
 */
public class ChatLieuDao extends EduSysDAO<ChatLieu, String> {

    private Integer insert;
    private Integer update;
    private Integer delete;

    @Override
    public void insert(ChatLieu entity) {
        insert = 0;
        String sql = """
                    INSERT INTO ChatLieu
                                          ( TrangThai, TenChatLieu, create_at, create_by, update_at, update_by, deleted)
                    VALUES    ('Active',?,GETDATE(),'NV001',GETDATE(),'NV001',0)
                    """;
        try ( Connection cn = DBcontext.getConnection();  PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setObject(1, entity.getTenChatLieu());
                                
            insert = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(ChatLieu entity) {
        update = 0;
        String sql = """
                    UPDATE    ChatLieu
                    SET             TenChatLieu =?
                    WHERE ID = ?
                    """;
        try ( Connection cn = DBcontext.getConnection();  PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setObject(1, entity.getTenChatLieu());
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
                    UPDATE ChatLieu SET  TrangThai = 1 WHERE ID = ?
                    """;
        try ( Connection cn = DBcontext.getConnection();  PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setObject(1, key);

            delete = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<ChatLieu> selectAll() {
        ArrayList<ChatLieu> listCD = new ArrayList<>();
        String sql = """
                    select * from ChatLieu ORDER BY create_at DESC
                    """;
        try ( Connection cn = DBcontext.getConnection();  PreparedStatement ps = cn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String tenChatLieu = rs.getString(2);
                String trangThai = rs.getString(3);
                Date createAt = rs.getDate(4);
                String createBy = rs.getString(5);
                Date updateAt = rs.getDate(6);
                String updateBy = rs.getString(7);
                boolean deleted = rs.getBoolean(8);

                ChatLieu chatLieu = new ChatLieu(id, tenChatLieu, trangThai, createAt, createBy, updateAt, updateBy, deleted);
                listCD.add(chatLieu);
            }
            return listCD;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<ChatLieu> selectById(String key) {
        ChatLieu cl =null;
        String sql = "SELECT * FROM ChatLieu WHERE ID = ?";
    try (Connection cn = DBcontext.getConnection();
         PreparedStatement ps = cn.prepareStatement(sql)) {
        ps.setString(1, key);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int id = rs.getInt(1);
            String tenChatLieu = rs.getString(2);
            String trangThai = rs.getString(3);
            Date createAt = rs.getDate(4);
            String createBy = rs.getString(5);
            Date updateAt = rs.getDate(6);
            String updateBy = rs.getString(7);
            boolean deleted = rs.getBoolean(8);

            cl = new ChatLieu(id, tenChatLieu, trangThai, createAt, createBy, updateAt, updateBy, deleted);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
    }

   
    @Override
    protected ArrayList<ChatLieu> selectBySQL(String sql, Object... args) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public ArrayList<ChatLieu> selectAll(int offset, int limit) {
        ArrayList<ChatLieu> listCD = new ArrayList<>();
        String sql = """
                    select * from ChatLieu ORDER BY create_at DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
                    """;
        try ( Connection cn = DBcontext.getConnection();  PreparedStatement ps = cn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String tenChatLieu = rs.getString(2);
                String trangThai = rs.getString(3);
                Date createAt = rs.getDate(4);
                String createBy = rs.getString(5);
                Date updateAt = rs.getDate(6);
                String updateBy = rs.getString(7);
                boolean deleted = rs.getBoolean(8);
                ChatLieu chatLieu = new ChatLieu(id, tenChatLieu, trangThai, createAt, createBy, updateAt, updateBy, deleted);
                listCD.add(chatLieu);
            }
            return listCD;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public int countChatLieu(){
        String sql ="""
                    Select count(*) From ChatLieu
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
