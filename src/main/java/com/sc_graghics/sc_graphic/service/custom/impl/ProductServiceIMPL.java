package com.sc_graghics.sc_graphic.service.custom.impl;

import com.sc_graghics.sc_graphic.dto.ProductDto;
import com.sc_graghics.sc_graphic.entity.Product;
import com.sc_graghics.sc_graphic.repo.ProductRepository;
import com.sc_graghics.sc_graphic.service.custom.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceIMPL implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private final ModelMapper modelMapper;

    @Autowired
    private S3Client s3Client;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    public ProductServiceIMPL(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    // Save product with the image URL
    @Override
    public Product saveProduct(ProductDto productDto, MultipartFile imageFile) {
        String imageUrl = uploadImageToS3(imageFile);
        productDto.setImg(imageUrl);
        Product product = modelMapper.map(productDto, Product.class);
        return productRepository.save(product);
    }


    // Update product details
    @Override
    public void update(ProductDto productDto, MultipartFile imageFile) {
        Product product = modelMapper.map(productDto, Product.class);
        Optional<Product> byId = productRepository.findById(product.getId());
        if (byId.isPresent()) {
            Product existingProduct = byId.get();
            existingProduct.setTitle(product.getTitle());
            if (imageFile != null && !imageFile.isEmpty()) {
                String imageUrl = uploadImageToS3(imageFile);  // Upload new image if provided
                existingProduct.setImg(imageUrl);
            }
            existingProduct.setDescription(product.getDescription());
            existingProduct.setDetails(product.getDetails());
            productRepository.save(existingProduct);
        }
    }

    // Delete product by ID
    @Override
    public void delete(Integer id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        }
    }

    // Find product by ID
    @Override
    public ProductDto findById(Integer id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(value -> modelMapper.map(value, ProductDto.class)).orElse(null);
    }

    // Find all products
    @Override
    public List<ProductDto> findAll() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .toList();
    }

    // Upload image to S3 and return the image URL
    @Override
    public String uploadImageToS3(MultipartFile file) {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        try {
            // Convert MultipartFile to File
            File convertedFile = convertMultiPartToFile(file);

            // Create PutObjectRequest without ACL
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key("product-images/" + fileName)
                    .contentType(file.getContentType())
                    .build();

            // Upload the file
            PutObjectResponse response = s3Client.putObject(putObjectRequest,
                    software.amazon.awssdk.core.sync.RequestBody.fromFile(convertedFile));

            // Delete the temporary file
            convertedFile.delete();

            // Generate the public URL
            GetUrlRequest getUrlRequest = GetUrlRequest.builder()
                    .bucket(bucketName)
                    .key("product-images/" + fileName)
                    .build();
            return s3Client.utilities().getUrl(getUrlRequest).toString();
        } catch (S3Exception e) {
            throw new RuntimeException("Error uploading file to S3: " + e.awsErrorDetails().errorMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error uploading file to S3", e);
        }
    }

    // Helper method to convert MultipartFile to File
    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(file.getBytes());
        }
        return convFile;
    }
}
