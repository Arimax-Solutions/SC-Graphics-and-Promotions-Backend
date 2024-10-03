package com.sc_graghics.sc_graphic.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sc_graghics.sc_graphic.dto.ProductDto;
import com.sc_graghics.sc_graphic.entity.Product;
import com.sc_graghics.sc_graphic.service.custom.ProductService;
import com.sc_graghics.sc_graphic.service.custom.impl.ProductServiceIMPL;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
@CrossOrigin("*")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductServiceIMPL productServiceIMPL;

    @Autowired
    private final ModelMapper modelMapper;

    public ProductController(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductDto> createProduct(
            @RequestPart("product") String productJson,
            @RequestPart("img") MultipartFile imageFile) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDto productDto = objectMapper.readValue(productJson, ProductDto.class);
        Product product = productService.saveProduct(productDto, imageFile);
        ProductDto responseDto = modelMapper.map(product, ProductDto.class);
        return ResponseEntity.ok(responseDto);
    }


    @GetMapping()
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> all = productService.findAll();
        List<ProductDto> productDtos = all.stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .toList();
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Integer id) {
        ProductDto productDto = productService.findById(id);
        return productDto != null
                ? ResponseEntity.ok(productDto)
                : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable Integer id,
            @RequestPart("product") String productJson,
            @RequestPart(value = "img", required = false) MultipartFile imageFile) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDto productDto = objectMapper.readValue(productJson, ProductDto.class);
        System.out.println(productDto);
        productDto.setId(id);
        productService.update(productDto, imageFile);
        return ResponseEntity.ok(productDto);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("click/count/{id}")
    public ResponseEntity<Void> recordClick(@PathVariable Integer id) {
        try {
            // Call the service method to increment the click count
            productServiceIMPL.incrementProductClickCount(id);
            System.out.println("Click recorded for product ID: " + id);
            return ResponseEntity.ok().build(); // Respond with 200 OK
        } catch (Exception e) {
            System.err.println("Error recording click for product ID: " + id + " - " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // API to get the most popular products
    @GetMapping("/popular")
    public ResponseEntity<List<ProductDto>> getPopularProducts() {
        List<ProductDto> popularProducts = productServiceIMPL.getMostPopularProducts();
        return ResponseEntity.ok(popularProducts);
    }
}
