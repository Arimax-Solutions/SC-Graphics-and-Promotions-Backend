package com.sc_graghics.sc_graphic.repo;
import com.sc_graghics.sc_graphic.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
