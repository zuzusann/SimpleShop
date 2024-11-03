package com.zzs.dreamshop.repository;

import com.zzs.dreamshop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategoryName(String categoryName);

    List<Product> findByPrice(BigDecimal price);

    List<Product> findByBrand(String brand);

    List<Product> findByCategoryNameAndBrand(String category, String brand);

    List<Product> findByBrandAndName(String brand, String name);

    List<Product> findByName(String name);

    int countByBrandAndName(String brand, String name);
}
