package com.sc_graghics.sc_graphic.repo;

import com.sc_graghics.sc_graphic.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author : Chanuka Weerakkody
 * @since : 20.1.1
 **/
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
}
