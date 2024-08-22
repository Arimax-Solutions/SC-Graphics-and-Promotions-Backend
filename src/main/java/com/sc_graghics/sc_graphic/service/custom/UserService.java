package com.sc_graghics.sc_graphic.service.custom;


import com.sc_graghics.sc_graphic.entity.User;
import com.sc_graghics.sc_graphic.service.SuperService;

/**
 * @author : Chanuka Weerakkody
 * @since : 20.1.1
 **/

public interface UserService extends SuperService<User,Integer> {
    Boolean existsByUsername(String username);
}
