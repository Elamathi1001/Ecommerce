package com.Ecommerce.Product.service;




import com.Ecommerce.Product.dto.ProductRequest;
import com.Ecommerce.Product.dto.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(ProductRequest request);

    ProductResponse getProductById(Long productId);

    List<ProductResponse> getAllProducts();

    List<ProductResponse> getProductsByCategory(String category);

    List<ProductResponse> searchProducts(String productName);

    ProductResponse updateProduct(Long productId, ProductRequest request);

    ProductResponse updateStock(Long productId, Integer quantity);

    void deleteProduct(Long productId);
}

