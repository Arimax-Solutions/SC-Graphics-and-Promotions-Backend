package com.sc_graghics.sc_graphic.dto;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sc_graghics.sc_graphic.util.Base64ImageDeserializer;
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

    @JsonDeserialize(using = Base64ImageDeserializer.class)
    private byte[] img;
    private String category;
    private String subcategory;
    private Double price;
    private String description;
    private List<ProductDetailsDto> details;
}
