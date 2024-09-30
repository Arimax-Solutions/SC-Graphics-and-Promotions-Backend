package com.sc_graghics.sc_graphic.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.BitSet;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String img;
    private String category;
    private String subcategory;
    private Double price;
    private String description;
    private String darazLink;
    @ElementCollection
    private List<ProductDetails> details;


}
