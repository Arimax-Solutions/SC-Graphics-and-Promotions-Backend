package com.sc_graghics.sc_graphic.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
    @Size(max = 65535)
    private String description;
    @Size(max = 65535)
    private String darazLink;
    private int clickCount;
    @ElementCollection
    private List<ProductDetails> details;


}
