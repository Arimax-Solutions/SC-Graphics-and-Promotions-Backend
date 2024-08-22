package com.sc_graghics.sc_graphic.service;

import java.util.List;

/**
 * @author : Chanuka Weerakkody
 * @since : 20.1.1
 **/

public interface SuperService <T,ID>{
    void save(T data);
    void update(T data);
    void delete(ID id);
    T findById(ID id);
    List<T> findAll();
}
