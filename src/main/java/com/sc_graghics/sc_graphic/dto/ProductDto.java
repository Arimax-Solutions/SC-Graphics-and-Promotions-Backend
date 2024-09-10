package com.sc_graghics.sc_graphic.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.BitSet;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDto {

    private Integer id;
    private String title;
    private String imageData;
    private byte[] img;
    private Double price;
    private String description;
    private List<ProductDetailsDto> details;
}
