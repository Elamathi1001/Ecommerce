package com.Ecommerce.Product.service;



import com.Ecommerce.Product.dto.ProductRequest;
import com.Ecommerce.Product.dto.ProductResponse;
import com.Ecommerce.Product.entity.Product;
import com.Ecommerce.Product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Create product
    @Override
    public ProductResponse createProduct(ProductRequest request) {

        Product product = new Product();

        product.setProductName(request.getProductName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setCategory(request.getCategory());
        product.setBrand(request.getBrand());
        product.setImageUrl(request.getImageUrl());

        product.setIsActive(true);

        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        Product savedProduct = productRepository.save(product);

        return mapToResponse(savedProduct);
    }

    // Get product by ID
    @Override
    public ProductResponse getProductById(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new RuntimeException("Product not found: " + productId));

        return mapToResponse(product);
    }

    // Get all products
    @Override
    public List<ProductResponse> getAllProducts() {

        return productRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // Get products by category
    @Override
    public List<ProductResponse> getProductsByCategory(String category) {

        return productRepository.findByCategory(category)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // Search products by name
    @Override
    public List<ProductResponse> searchProducts(String productName) {

        return productRepository.findByProductNameContainingIgnoreCase(productName)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // Update product
    @Override
    public ProductResponse updateProduct(
            Long productId, ProductRequest request) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new RuntimeException("Product not found: " + productId));

        product.setProductName(request.getProductName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setCategory(request.getCategory());
        product.setBrand(request.getBrand());
        product.setImageUrl(request.getImageUrl());

        product.setUpdatedAt(LocalDateTime.now());

        Product updatedProduct = productRepository.save(product);

        return mapToResponse(updatedProduct);
    }

    // Update stock quantity
    @Override
    public ProductResponse updateStock(Long productId, Integer quantity) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new RuntimeException("Product not found: " + productId));

        if (quantity < 0) {
            throw new IllegalArgumentException(
                    "Stock quantity cannot be negative");
        }

        product.setStockQuantity(quantity);
        product.setUpdatedAt(LocalDateTime.now());

        Product updatedProduct = productRepository.save(product);

        return mapToResponse(updatedProduct);
    }

    // Delete product
    @Override
    public void deleteProduct(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new RuntimeException("Product not found: " + productId));

        productRepository.delete(product);
    }

    // Convert Entity to Response DTO
    private ProductResponse mapToResponse(Product product) {

        ProductResponse response = new ProductResponse();

        response.setProductId(product.getProductId());
        response.setProductName(product.getProductName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setStockQuantity(product.getStockQuantity());
        response.setCategory(product.getCategory());
        response.setBrand(product.getBrand());
        response.setImageUrl(product.getImageUrl());
        response.setIsActive(product.getIsActive());
        response.setCreatedAt(product.getCreatedAt());
        response.setUpdatedAt(product.getUpdatedAt());

        return response;
    }
}

