package com.sc_graghics.sc_graphic.service.custom.impl;

import com.sc_graghics.sc_graphic.dto.ProductDto;
import com.sc_graghics.sc_graphic.entity.Product;
import com.sc_graghics.sc_graphic.repo.ProductRepository;
import com.sc_graghics.sc_graphic.service.custom.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

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
//        productRepository.save(modelMapper.map(productDto, Product.class));
    }

    @Override
    public void update(ProductDto productDto) {
        System.out.println(productDto);
        Product product = modelMapper.map(productDto, Product.class);
        Optional<Product> byId = productRepository.findById(product.getId());
        if (byId.isPresent()) {
            Product existingProduct = byId.get();
            existingProduct.setTitle(product.getTitle());
            existingProduct.setImg(product.getImg());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setDetails(product.getDetails());
            System.out.println("\n\n\n\n" + existingProduct);
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
//       return productRepository.findAll();
        return null;
    }

    @Override
    public List<Product> findAllProduct() {
        return productRepository.findAll();

    }

    @Override
    public Product saveProduct(ProductDto productDto) {
        if (productDto.getImageData() != null && productDto.getImageData().startsWith("data:image")) {
            // Strip the base64 image prefix and decode the image
            String base64Image = productDto.getImageData().substring(productDto.getImageData().indexOf(",") + 1);
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
            productDto.setImg(imageBytes);
        } else if (productDto.getImg() != null) {
            // Handle if `img` is directly passed as a byte array
            productDto.setImg(productDto.getImg());
        }
        System.out.println("\n\n\n\n"+productDto);
        Product product = modelMapper.map(productDto, Product.class);
        System.out.println("\n\n\n\n"+product);
        return productRepository.save(product);
    }
}
