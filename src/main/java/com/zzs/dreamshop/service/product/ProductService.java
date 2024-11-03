package com.zzs.dreamshop.service.product;

import com.zzs.dreamshop.ServiceRequest.ProductRequest;
import com.zzs.dreamshop.ServiceRequest.ProductUpdateRequest;
import com.zzs.dreamshop.entity.Category;
import com.zzs.dreamshop.entity.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    Product addProduct(ProductRequest product);
    Product getProductById(int id);
    void deleteProductById(int id);
    Product updateProduct(ProductUpdateRequest product, int id);
    List<Product> getAllProducts();
    List<Product> getProductsByName(String name);
    List<Product> getProductsByCategoryName(String categoryName);
    List<Product> getProductsByPrice(BigDecimal price);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByBrandAndName(String brand, String name);
    int countProductsByBrandAndName(String brand, String name);
}
