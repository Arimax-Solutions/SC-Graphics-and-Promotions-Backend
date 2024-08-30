package com.sc_graghics.sc_graphic.service.custom.impl;

import com.sc_graghics.sc_graphic.dto.ProductDto;
import com.sc_graghics.sc_graphic.entity.Product;
import com.sc_graghics.sc_graphic.repo.ProductRepository;
import com.sc_graghics.sc_graphic.service.custom.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceIMPL implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private final ModelMapper modelMapper;

    public ProductServiceIMPL(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public void save(ProductDto productDto) {
        productRepository.save(modelMapper.map(productDto, Product.class));
    }

    @Override
    public void update(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        Optional<Product> byId = productRepository.findById(product.getId());
        if (byId.isPresent()) {
            Product existingProduct = byId.get();
            existingProduct.setTitle(product.getTitle());
            existingProduct.setImgBase64(product.getImgBase64());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setDetails(product.getDetails());
            productRepository.save(existingProduct);
        }
    }

    @Override
    public void delete(Integer id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        }
    }

    @Override
    public ProductDto findById(Integer id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(value -> modelMapper.map(value, ProductDto.class)).orElse(null);
    }

    @Override
    public List<ProductDto> findAll() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(product -> modelMapper.map(products,ProductDto.class)).collect(Collectors.toList());
    }

}
