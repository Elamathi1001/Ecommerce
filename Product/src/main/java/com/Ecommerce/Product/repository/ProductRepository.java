package com.Ecommerce.Product.repository;


import com.Ecommerce.Product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(String category);

    List<Product> findByBrand(String brand);

    List<Product> findByIsActive(Boolean isActive);

    List<Product> findByProductNameContainingIgnoreCase(String productName);
}

