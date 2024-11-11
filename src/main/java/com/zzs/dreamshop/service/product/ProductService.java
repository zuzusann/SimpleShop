package com.zzs.dreamshop.service.product;

import com.zzs.dreamshop.dto.ProductDto;
import com.zzs.dreamshop.dto.request.ProductRequest;
import com.zzs.dreamshop.dto.request.ProductUpdateRequest;
import com.zzs.dreamshop.entity.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    ProductDto addProduct(ProductRequest product);
    ProductDto getProductById(int id);
    void deleteProductById(int id);
    ProductDto updateProduct(ProductUpdateRequest product, int id);
    List<ProductDto> getAllProducts();
    List<ProductDto> getProductsByName(String name);
    List<ProductDto> getProductsByCategoryName(String categoryName);
    List<ProductDto> getProductsByPrice(BigDecimal price);
    List<ProductDto> getProductsByBrand(String brand);
    List<ProductDto> getProductsByCategoryAndBrand(String category, String brand);
    List<ProductDto> getProductsByBrandAndName(String brand, String name);
    int countProductsByBrandAndName(String brand, String name);
}
