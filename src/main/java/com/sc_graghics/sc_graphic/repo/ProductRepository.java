package com.sc_graghics.sc_graphic.repo;
import com.sc_graghics.sc_graphic.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    // Increment the click count for a specific product
    @Modifying
    @Query("UPDATE Product p SET p.clickCount = p.clickCount + 1 WHERE p.id = :productId")
    void incrementClickCount(@Param("productId") Integer productId);

    // Fetch the top 12 products ordered by click count
    @Query("SELECT p FROM Product p ORDER BY p.clickCount DESC")
    List<Product> findTop16ByOrderByClickCountDesc();
}
