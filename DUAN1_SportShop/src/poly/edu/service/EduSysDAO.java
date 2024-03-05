/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.edu.service;

import java.awt.List;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public abstract class EduSysDAO<E,K> {
    abstract public void insert(E entity);
    abstract public void update(E entity);
    abstract public void delete(K key);
    abstract public ArrayList<E> selectAll();
    abstract public ArrayList<E> selectById(K key);

    abstract protected ArrayList<E> selectBySQL(String sql, Object ... args);
}
