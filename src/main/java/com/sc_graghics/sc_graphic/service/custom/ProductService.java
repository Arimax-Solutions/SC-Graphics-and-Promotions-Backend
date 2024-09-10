package com.sc_graghics.sc_graphic.service.custom;

import com.sc_graghics.sc_graphic.dto.ProductDto;
import com.sc_graghics.sc_graphic.entity.Product;
import com.sc_graghics.sc_graphic.entity.User;
import com.sc_graghics.sc_graphic.service.SuperService;

import java.util.List;

public interface ProductService extends SuperService<ProductDto,Integer> {
    Product saveProduct(ProductDto productDto);
    List<Product> findAllProduct();
}
