package com.sc_graghics.sc_graphic.service.custom;

import com.sc_graghics.sc_graphic.dto.ProductDto;
import com.sc_graghics.sc_graphic.entity.Product;
import com.sc_graghics.sc_graphic.entity.User;
import com.sc_graghics.sc_graphic.service.SuperService;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface ProductService {
    Product saveProduct(ProductDto productDto, MultipartFile imageFile);
    void update(ProductDto productDto, MultipartFile imageFile);
    String uploadImageToS3(MultipartFile file);
    void delete(Integer id);
    ProductDto findById(Integer id);

    List<ProductDto> findAll();

}
