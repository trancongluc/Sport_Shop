/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.edu.service;

import java.util.ArrayList;
import poly.edu.Model.SanPhamChiTiet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import poly.edu.Model.ChatLieu;
import poly.edu.Model.DeGiay;
import poly.edu.Model.HangGiay;
import poly.edu.Model.HoaDonChiTiet;
import poly.edu.Model.MauSac;
import poly.edu.Model.SanPham;
import poly.edu.Model.Size;
import poly.edu.uitility.DBcontext;

/**
 *
 * @author Admin
 */
public class SanPhamChiTietDao extends EduSysDAO<SanPhamChiTiet, String> {

    private Integer insert;
    private Integer update;
    private Integer delete;

    @Override
    public void insert(SanPhamChiTiet entity) {
        insert = 0;
        String sql = """
                        INSERT INTO SanPhamChiTiet
                                              (ID_SanPham, ID_MauSac, ID_Size, ID_Hang, ID_ChatLieu, ID_DeGiay, MoTa, Gia, SoLuongTon, TrangThai, create_at, create_by, update_at, update_by, deleted, MaSPCT)
                        VALUES (?, ?, ?, ?, ?, ?, ?,?, ?, 'Active', GETDATE(), 'NV001', GETDATE(), 'NV001', 0, ?)
                        """;
        try ( Connection cn = DBcontext.getConnection();  PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setObject(1, entity.getSp().getId());
            ps.setObject(2, entity.getMauSac().getId());
            ps.setObject(3, entity.getSize().getId());
            ps.setObject(4, entity.getHang().getId());
            ps.setObject(5, entity.getChatLieu().getId());
            ps.setObject(6, entity.getDeGiay().getId());
            ps.setObject(7, entity.getMoTa());
            ps.setObject(8, entity.getGia());
            ps.setObject(9, entity.getSoLuongTon());
            ps.setObject(10, entity.getMaCTSP());
            insert = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(SanPhamChiTiet entity) {

        update = 0;
        String sql = """
                        UPDATE    SanPhamChiTiet
                        SET       MoTa =?, Gia =?, SoLuongTon =?
                        WHERE MaSPCT = ?
                        """;
        try ( Connection cn = DBcontext.getConnection();  PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setObject(1, entity.getMoTa());
            ps.setObject(2, entity.getGia());
            ps.setObject(3, entity.getSoLuongTon());
            ps.setObject(4, entity.getMaCTSP());
            update = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String key) {
        delete = 0;
        String sql = """
                    UPDATE    SanPhamChiTiet
                                        SET             deleted =1
                                         WHERE MaSPCT LIKE ?
                    """;
        try ( Connection cn = DBcontext.getConnection();  PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setObject(1, key);

            delete = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void khoiPhuc(String key) {
        delete = 0;
        String sql = """
                    UPDATE    SanPhamChiTiet
                    SET             deleted =0
                     WHERE MaSPCT LIKE ?
                    """;
        try ( Connection cn = DBcontext.getConnection();  PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setObject(1, key);

            delete = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<SanPhamChiTiet> selectAll() {
        ArrayList<SanPhamChiTiet> listSPCT = new ArrayList<>();
        String sql = """
                    select spct.ID, spct.MaSPCT, spct.ID_SanPham, sp.Ten, spct.ID_MauSac,
                    	ms.TenMau,spct.ID_Size, s.Size, spct.ID_Hang, h.Ten, spct.ID_ChatLieu ,cl.TenChatLieu,spct.ID_DeGiay , dg.Ten,spct.SoLuongTon,spct.MoTa
                     ,spct.Gia,spct.TrangThai,spct.deleted,spct.create_at,spct.update_at
                    from SanPhamChiTiet spct join SanPham sp ON sp.ID = spct.ID_SanPham
                     join MauSac ms on ms.ID = spct.ID_MauSac
                     join Size s on s.ID = spct.ID_Size
                     join Hang h on h.ID = spct.ID_Hang
                     join ChatLieu cl on cl.ID = spct.ID_ChatLieu
                     join DeGiay dg on dg.ID = spct.ID_DeGiay     
                      WHERE spct.deleted = 0   and spct.SoLuongTon != 0      
                         ORDER BY create_at DESC
                    """;
        try ( Connection cn = DBcontext.getConnection();  PreparedStatement ps = cn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String maCTSP = rs.getString(2);
                int idSP = rs.getInt(3);
                String tenSP = rs.getString(4);
                int idms = rs.getInt(5);
                String tenMS = rs.getString(6);
                int idSize = rs.getInt(7);
                int Tensize = rs.getInt(8);
                int idHang = rs.getInt(9);
                String tenHang = rs.getString(10);
                int idCL = rs.getInt(11);
                String tenCL = rs.getString(12);
                int idDG = rs.getInt(13);
                String tenDe = rs.getString(14);
                int gia = rs.getInt("Gia");
                String mota = rs.getString("MoTa");
                int soLuongTon = rs.getInt("SoLuongTon");
                String trangThai = rs.getString(18);
                boolean deleted = rs.getBoolean(19);
                Date createAt = rs.getDate(20);
                Date updateAt = rs.getDate(21);

                SanPham sp = new SanPham();
                sp.setTen(tenSP);
//                    sp.setMaSP(maSP);
                MauSac ms = new MauSac();
                ms.setId(idms);
                ms.setTenMau(tenMS);
                Size size = new Size();
                size.setId(idSize);
                size.setSize(Tensize);
                HangGiay hg = new HangGiay();
                hg.setId(idHang);
                hg.setTen(tenHang);
                ChatLieu cl = new ChatLieu();
                cl.setId(idCL);
                cl.setTenChatLieu(tenCL);
                DeGiay dg = new DeGiay();
                dg.setId(idDG);
                dg.setTen(tenDe);
                SanPhamChiTiet spct = new SanPhamChiTiet(id, sp, ms, size, hg, cl, dg, maCTSP, gia, mota, soLuongTon, trangThai, createAt, updateAt, deleted);
//                SanPhamChiTiet spct = new SanPhamChiTiet(sp, ms, size, hg, cl, dg, maCTSP, gia, mota, soLuongTon);
                listSPCT.add(spct);
            }
            return listSPCT;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<SanPhamChiTiet> selectById(String key) {
        ArrayList<SanPhamChiTiet> listSPCT = new ArrayList<>();
        String sql = """
                    select spct.ID, spct.MaSPCT, spct.ID_SanPham, sp.Ten, spct.ID_MauSac,
                    	ms.TenMau,spct.ID_Size, s.Size, spct.ID_Hang, h.Ten, spct.ID_ChatLieu ,cl.TenChatLieu,spct.ID_DeGiay , dg.Ten,spct.SoLuongTon,spct.MoTa
                     ,spct.Gia,spct.TrangThai,spct.deleted,spct.create_at,spct.update_at
                    from SanPhamChiTiet spct join SanPham sp ON sp.ID = spct.ID_SanPham
                     join MauSac ms on ms.ID = spct.ID_MauSac
                     join Size s on s.ID = spct.ID_Size
                     join Hang h on h.ID = spct.ID_Hang
                     join ChatLieu cl on cl.ID = spct.ID_ChatLieu
                     join DeGiay dg on dg.ID = spct.ID_DeGiay
                                    WHERE sp.MaSP LIKE ?
                         ORDER BY create_at DESC
                    """;
        try ( Connection cn = DBcontext.getConnection();  PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setObject(1, key + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String maCTSP = rs.getString(2);
                int idSP = rs.getInt(3);
                String tenSP = rs.getString(4);
                int idms = rs.getInt(5);
                String tenMS = rs.getString(6);
                int idSize = rs.getInt(7);
                int Tensize = rs.getInt(8);
                int idHang = rs.getInt(9);
                String tenHang = rs.getString(10);
                int idCL = rs.getInt(11);
                String tenCL = rs.getString(12);
                int idDG = rs.getInt(13);
                String tenDe = rs.getString(14);
                int gia = rs.getInt("Gia");
                String mota = rs.getString("MoTa");
                int soLuongTon = rs.getInt("SoLuongTon");
                String trangThai = rs.getString(18);
                boolean deleted = rs.getBoolean(19);
                Date createAt = rs.getDate(20);
                Date updateAt = rs.getDate(21);

                SanPham sp = new SanPham();
                sp.setTen(tenSP);
//                    sp.setMaSP(maSP);
                MauSac ms = new MauSac();
                ms.setId(idms);
                ms.setTenMau(tenMS);
                Size size = new Size();
                size.setId(idSize);
                size.setSize(Tensize);
                HangGiay hg = new HangGiay();
                hg.setId(idHang);
                hg.setTen(tenHang);
                ChatLieu cl = new ChatLieu();
                cl.setId(idCL);
                cl.setTenChatLieu(tenCL);
                DeGiay dg = new DeGiay();
                dg.setId(idDG);
                dg.setTen(tenDe);
                SanPhamChiTiet spct = new SanPhamChiTiet(id, sp, ms, size, hg, cl, dg, maCTSP, gia, mota, soLuongTon, trangThai, createAt, updateAt, deleted);
//                SanPhamChiTiet spct = new SanPhamChiTiet(sp, ms, size, hg, cl, dg, maCTSP, gia, mota, soLuongTon);
                listSPCT.add(spct);

            }

            return listSPCT;

        } catch (Exception e) {
            System.out.println("catch " + key);
            e.printStackTrace();
        }
        return null;

    }

    @Override
    protected ArrayList<SanPhamChiTiet> selectBySQL(String sql, Object... args) {

        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Integer UpdateSPCT(SanPhamChiTiet spct) {
        Integer row = null;
        String sql = """
                        UPDATE SanPhamChiTiet
                        Set SoLuongTon = ?, deleted = ?, TrangThai =?
                        WHERE MaSPCT = ?         
                     """;
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);
//            pstm.setInt(1, idHD);
            pstm.setInt(1, spct.getSoLuongTon());
            pstm.setObject(3, spct.getTrangThai());
            pstm.setObject(2, spct.isDeleted());
            pstm.setString(4, spct.getMaCTSP());
            row = pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    public ArrayList<SanPhamChiTiet> selectAllSPCTTheoMa(String maSPCT) {
        ArrayList<SanPhamChiTiet> listSPCT = new ArrayList<>();
        String sql = """
                    select spct.ID, spct.MaSPCT, spct.ID_SanPham, sp.Ten, spct.ID_MauSac,
                    	ms.TenMau,spct.ID_Size, s.Size, spct.ID_Hang, h.Ten, spct.ID_ChatLieu ,cl.TenChatLieu,spct.ID_DeGiay , dg.Ten,spct.SoLuongTon,spct.MoTa
                     ,spct.Gia,spct.TrangThai,spct.deleted,spct.create_at,spct.update_at
                    from SanPhamChiTiet spct join SanPham sp ON sp.ID = spct.ID_SanPham
                     join MauSac ms on ms.ID = spct.ID_MauSac
                     join Size s on s.ID = spct.ID_Size
                     join Hang h on h.ID = spct.ID_Hang
                     join ChatLieu cl on cl.ID = spct.ID_ChatLieu
                     join DeGiay dg on dg.ID = spct.ID_DeGiay   
                     WHERE MaSPCT LIKE ?           
                         ORDER BY create_at DESC
                     
                    """;
        try ( Connection cn = DBcontext.getConnection();  PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setObject(1, maSPCT);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String maCTSP = rs.getString(2);
                int idSP = rs.getInt(3);
                String tenSP = rs.getString(4);
                int idms = rs.getInt(5);
                String tenMS = rs.getString(6);
                int idSize = rs.getInt(7);
                int Tensize = rs.getInt(8);
                int idHang = rs.getInt(9);
                String tenHang = rs.getString(10);
                int idCL = rs.getInt(11);
                String tenCL = rs.getString(12);
                int idDG = rs.getInt(13);
                String tenDe = rs.getString(14);
                int gia = rs.getInt("Gia");
                String mota = rs.getString("MoTa");
                int soLuongTon = rs.getInt("SoLuongTon");
                String trangThai = rs.getString(18);
                boolean deleted = rs.getBoolean(19);
                Date createAt = rs.getDate(20);
                Date updateAt = rs.getDate(21);

                SanPham sp = new SanPham();
                sp.setTen(tenSP);
//                    sp.setMaSP(maSP);
                MauSac ms = new MauSac();
                ms.setId(idms);
                ms.setTenMau(tenMS);
                Size size = new Size();
                size.setId(idSize);
                size.setSize(Tensize);
                HangGiay hg = new HangGiay();
                hg.setId(idHang);
                hg.setTen(tenHang);
                ChatLieu cl = new ChatLieu();
                cl.setId(idCL);
                cl.setTenChatLieu(tenCL);
                DeGiay dg = new DeGiay();
                dg.setId(idDG);
                dg.setTen(tenDe);
                SanPhamChiTiet spct = new SanPhamChiTiet(id, sp, ms, size, hg, cl, dg, maCTSP, gia, mota, soLuongTon, trangThai, createAt, updateAt, deleted);
//                SanPhamChiTiet spct = new SanPhamChiTiet(sp, ms, size, hg, cl, dg, maCTSP, gia, mota, soLuongTon);
                listSPCT.add(spct);
            }
            return listSPCT;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer UpdateSPCTSLT(SanPhamChiTiet spct) {
        Integer row = null;
        String sql = """
                        UPDATE SanPhamChiTiet
                        Set SoLuongTon = ?, deleted = 0
                        WHERE MaSPCT = ?         
                     """;
        Connection cn = DBcontext.getConnection();
        try {
            PreparedStatement pstm = cn.prepareStatement(sql);
//            pstm.setInt(1, idHD);
            pstm.setInt(1, spct.getSoLuongTon());
            pstm.setString(2, spct.getMaCTSP());
            row = pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    public ArrayList<SanPhamChiTiet> selectByGia(int min, int max) {
        ArrayList<SanPhamChiTiet> listSPCT = new ArrayList<>();
        String sql = """
                    select spct.ID, spct.MaSPCT, spct.ID_SanPham, sp.Ten, spct.ID_MauSac,
                    	ms.TenMau,spct.ID_Size, s.Size, spct.ID_Hang, h.Ten, spct.ID_ChatLieu ,cl.TenChatLieu,spct.ID_DeGiay , dg.Ten,spct.SoLuongTon,spct.MoTa
                     ,spct.Gia,spct.TrangThai,spct.deleted,spct.create_at,spct.update_at
                    from SanPhamChiTiet spct join SanPham sp ON sp.ID = spct.ID_SanPham
                     join MauSac ms on ms.ID = spct.ID_MauSac
                     join Size s on s.ID = spct.ID_Size
                     join Hang h on h.ID = spct.ID_Hang
                     join ChatLieu cl on cl.ID = spct.ID_ChatLieu
                     join DeGiay dg on dg.ID = spct.ID_DeGiay
                                    WHERE Gia BETWEEN ? AND ? AND SoLuongTon >0
                        
                    """;
        try ( Connection cn = DBcontext.getConnection();  PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setObject(1, min);
            ps.setObject(2, max);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String maCTSP = rs.getString(2);
                int idSP = rs.getInt(3);
                String tenSP = rs.getString(4);
                int idms = rs.getInt(5);
                String tenMS = rs.getString(6);
                int idSize = rs.getInt(7);
                int Tensize = rs.getInt(8);
                int idHang = rs.getInt(9);
                String tenHang = rs.getString(10);
                int idCL = rs.getInt(11);
                String tenCL = rs.getString(12);
                int idDG = rs.getInt(13);
                String tenDe = rs.getString(14);
                int gia = rs.getInt("Gia");
                String mota = rs.getString("MoTa");
                int soLuongTon = rs.getInt("SoLuongTon");
                String trangThai = rs.getString(18);
                boolean deleted = rs.getBoolean(19);
                Date createAt = rs.getDate(20);
                Date updateAt = rs.getDate(21);

                SanPham sp = new SanPham();
                sp.setTen(tenSP);
//                    sp.setMaSP(maSP);
                MauSac ms = new MauSac();
                ms.setId(idms);
                ms.setTenMau(tenMS);
                Size size = new Size();
                size.setId(idSize);
                size.setSize(Tensize);
                HangGiay hg = new HangGiay();
                hg.setId(idHang);
                hg.setTen(tenHang);
                ChatLieu cl = new ChatLieu();
                cl.setId(idCL);
                cl.setTenChatLieu(tenCL);
                DeGiay dg = new DeGiay();
                dg.setId(idDG);
                dg.setTen(tenDe);
                SanPhamChiTiet spct = new SanPhamChiTiet(id, sp, ms, size, hg, cl, dg, maCTSP, gia, mota, soLuongTon, trangThai, createAt, updateAt, deleted);
//                SanPhamChiTiet spct = new SanPhamChiTiet(sp, ms, size, hg, cl, dg, maCTSP, gia, mota, soLuongTon);
                listSPCT.add(spct);

            }

            return listSPCT;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public ArrayList<SanPhamChiTiet> selectAllSPCTDaXoa() {
        ArrayList<SanPhamChiTiet> listSPCT = new ArrayList<>();
        String sql = """
                    select spct.ID, spct.MaSPCT, spct.ID_SanPham, sp.Ten, spct.ID_MauSac,
                    	ms.TenMau,spct.ID_Size, s.Size, spct.ID_Hang, h.Ten, spct.ID_ChatLieu ,cl.TenChatLieu,spct.ID_DeGiay , dg.Ten,spct.SoLuongTon,spct.MoTa
                     ,spct.Gia,spct.TrangThai,spct.deleted,spct.create_at,spct.update_at
                    from SanPhamChiTiet spct join SanPham sp ON sp.ID = spct.ID_SanPham
                     join MauSac ms on ms.ID = spct.ID_MauSac
                     join Size s on s.ID = spct.ID_Size
                     join Hang h on h.ID = spct.ID_Hang
                     join ChatLieu cl on cl.ID = spct.ID_ChatLieu
                     join DeGiay dg on dg.ID = spct.ID_DeGiay     
                      WHERE spct.deleted = 1      
                         ORDER BY create_at DESC
                    """;
        try ( Connection cn = DBcontext.getConnection();  PreparedStatement ps = cn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String maCTSP = rs.getString(2);
                int idSP = rs.getInt(3);
                String tenSP = rs.getString(4);
                int idms = rs.getInt(5);
                String tenMS = rs.getString(6);
                int idSize = rs.getInt(7);
                int Tensize = rs.getInt(8);
                int idHang = rs.getInt(9);
                String tenHang = rs.getString(10);
                int idCL = rs.getInt(11);
                String tenCL = rs.getString(12);
                int idDG = rs.getInt(13);
                String tenDe = rs.getString(14);
                int gia = rs.getInt("Gia");
                String mota = rs.getString("MoTa");
                int soLuongTon = rs.getInt("SoLuongTon");
                String trangThai = rs.getString(18);
                boolean deleted = rs.getBoolean(19);
                Date createAt = rs.getDate(20);
                Date updateAt = rs.getDate(21);

                SanPham sp = new SanPham();
                sp.setTen(tenSP);
//                    sp.setMaSP(maSP);
                MauSac ms = new MauSac();
                ms.setId(idms);
                ms.setTenMau(tenMS);
                Size size = new Size();
                size.setId(idSize);
                size.setSize(Tensize);
                HangGiay hg = new HangGiay();
                hg.setId(idHang);
                hg.setTen(tenHang);
                ChatLieu cl = new ChatLieu();
                cl.setId(idCL);
                cl.setTenChatLieu(tenCL);
                DeGiay dg = new DeGiay();
                dg.setId(idDG);
                dg.setTen(tenDe);
                SanPhamChiTiet spct = new SanPhamChiTiet(id, sp, ms, size, hg, cl, dg, maCTSP, gia, mota, soLuongTon, trangThai, createAt, updateAt, deleted);
//                SanPhamChiTiet spct = new SanPhamChiTiet(sp, ms, size, hg, cl, dg, maCTSP, gia, mota, soLuongTon);
                listSPCT.add(spct);
            }
            return listSPCT;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<SanPhamChiTiet> selectAllKCoKM() {
        ArrayList<SanPhamChiTiet> listSPCT = new ArrayList<>();
        String sql = """
                     select spct.ID, spct.MaSPCT, spct.ID_SanPham, sp.Ten, spct.ID_MauSac,
                                        	ms.TenMau,spct.ID_Size, s.Size, spct.ID_Hang, h.Ten, spct.ID_ChatLieu ,cl.TenChatLieu,spct.ID_DeGiay , dg.Ten,spct.SoLuongTon,spct.MoTa
                                         ,spct.Gia,spct.TrangThai,spct.deleted,spct.create_at,spct.update_at
                                        from SanPhamChiTiet spct join SanPham sp ON sp.ID = spct.ID_SanPham
                                         join MauSac ms on ms.ID = spct.ID_MauSac
                                         join Size s on s.ID = spct.ID_Size
                                         join Hang h on h.ID = spct.ID_Hang
                                         join ChatLieu cl on cl.ID = spct.ID_ChatLieu
                                         join DeGiay dg on dg.ID = spct.ID_DeGiay     
                                          WHERE spct.deleted = 0   and spct.SoLuongTon != 0      
                    					  AND NOT EXISTS (
                                           SELECT 1
                                           FROM GiamGia_SPCT gs
                                           WHERE gs.id_SPCT = spct.ID 
                                         )
                                             ORDER BY create_at DESC
                    """;
        try ( Connection cn = DBcontext.getConnection();  PreparedStatement ps = cn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String maCTSP = rs.getString(2);
                int idSP = rs.getInt(3);
                String tenSP = rs.getString(4);
                int idms = rs.getInt(5);
                String tenMS = rs.getString(6);
                int idSize = rs.getInt(7);
                int Tensize = rs.getInt(8);
                int idHang = rs.getInt(9);
                String tenHang = rs.getString(10);
                int idCL = rs.getInt(11);
                String tenCL = rs.getString(12);
                int idDG = rs.getInt(13);
                String tenDe = rs.getString(14);
                int gia = rs.getInt("Gia");
                String mota = rs.getString("MoTa");
                int soLuongTon = rs.getInt("SoLuongTon");
                String trangThai = rs.getString(18);
                boolean deleted = rs.getBoolean(19);
                Date createAt = rs.getDate(20);
                Date updateAt = rs.getDate(21);

                SanPham sp = new SanPham();
                sp.setTen(tenSP);
//                    sp.setMaSP(maSP);
                MauSac ms = new MauSac();
                ms.setId(idms);
                ms.setTenMau(tenMS);
                Size size = new Size();
                size.setId(idSize);
                size.setSize(Tensize);
                HangGiay hg = new HangGiay();
                hg.setId(idHang);
                hg.setTen(tenHang);
                ChatLieu cl = new ChatLieu();
                cl.setId(idCL);
                cl.setTenChatLieu(tenCL);
                DeGiay dg = new DeGiay();
                dg.setId(idDG);
                dg.setTen(tenDe);
                SanPhamChiTiet spct = new SanPhamChiTiet(id, sp, ms, size, hg, cl, dg, maCTSP, gia, mota, soLuongTon, trangThai, createAt, updateAt, deleted);
//                SanPhamChiTiet spct = new SanPhamChiTiet(sp, ms, size, hg, cl, dg, maCTSP, gia, mota, soLuongTon);
                listSPCT.add(spct);
            }
            return listSPCT;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public SanPhamChiTiet selectByIdThuocTinh(int idSP, int idMS, int idSize, int idDeGiay, int idHang, int idChatLieu) {
        ArrayList<SanPhamChiTiet> listSPCT = new ArrayList<>();
        String sql = """
                    select spct.ID, spct.ID_SanPham, spct.ID_MauSac,
                     spct.ID_Size, spct.ID_Hang, spct.ID_ChatLieu ,spct.ID_DeGiay
                   from SanPhamChiTiet spct 
                     WHERE spct.ID_ChatLieu = ? AND spct.ID_DeGiay = ? AND spct.ID_Hang =? AND spct.ID_MauSac = ?
                     AND spct.ID_SanPham =?
                    AND spct.ID_Size = ?
                    """;
        try ( Connection cn = DBcontext.getConnection();  PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setObject(1, idChatLieu);
            ps.setObject(2, idDeGiay);
            ps.setObject(3, idHang);
            ps.setObject(4, idMS);
            ps.setObject(5, idSP);
            ps.setObject(6, idSize);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                SanPhamChiTiet spct = new SanPhamChiTiet();
                spct.setId(rs.getInt(1));
                spct.getSp().setId(rs.getInt(2));
                spct.getSize().setId(rs.getInt(3));
                spct.getMauSac().setId(rs.getInt(4));
                spct.getHang().setId(rs.getInt(5));
                spct.getChatLieu().setId(rs.getInt(6));
                spct.getDeGiay().setId(rs.getInt(7));
                return spct;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

}
