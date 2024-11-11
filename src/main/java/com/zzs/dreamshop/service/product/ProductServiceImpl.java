package com.zzs.dreamshop.service.product;


import com.zzs.dreamshop.dto.CategoryDto;
import com.zzs.dreamshop.dto.ProductDto;
import com.zzs.dreamshop.dto.request.ProductRequest;
import com.zzs.dreamshop.dto.request.ProductUpdateRequest;
import com.zzs.dreamshop.entity.Category;
import com.zzs.dreamshop.entity.Product;
import com.zzs.dreamshop.exceptions.ProductNotFoundException;
import com.zzs.dreamshop.repository.CategoryRepository;
import com.zzs.dreamshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    @Override
    public ProductDto addProduct(ProductRequest product) {
        Category category = Optional.ofNullable(categoryRepository.findByName(product.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = new Category(product.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        product.setCategory(category);
        Product saveProduct =  productRepository.save(createProduct(product, category));
        return mapToDto(saveProduct);
    }

    private Product createProduct(ProductRequest product, Category category) {
        return new Product(
                product.getName(),
                product.getBrand(),
                product.getPrice(),
                product.getInventory(),
                product.getDescription(),
                category
        );
    }

    @Override
    public ProductDto getProductById(int id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product Not Found!"));
        return mapToDto(product);
    }

    @Override
    public void deleteProductById(int id) {
        productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete,
                        () -> {throw new ProductNotFoundException("Product Not Found!");});
    }

    @Override
    public ProductDto updateProduct(ProductUpdateRequest product, int id) {
         Product updateProduct = productRepository.findById(id)
                .map(existingProduct -> updateExistingProduct(existingProduct, product))
                .map(productRepository :: save)
                .orElseThrow(() -> new ProductNotFoundException("Product Not Found!"));
         return mapToDto(updateProduct);

    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest updateRequest) {
        existingProduct.setName(updateRequest.getName());
        existingProduct.setBrand(updateRequest.getBrand());
        existingProduct.setPrice(updateRequest.getPrice());
        existingProduct.setInventory(updateRequest.getInventory());
        existingProduct.setDescription(updateRequest.getDescription());
        Category category = categoryRepository.findByName(updateRequest.getCategory().getName());
        existingProduct.setCategory(category);
        return productRepository.save(existingProduct);

    }

    @Override
    public List<ProductDto> getAllProducts() {

        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::mapToDto) // mapToDto is applied to each Product
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByName(String name) {
        List<Product> products = productRepository.findByName(name);
        return products.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByCategoryName(String categoryName) {
        List<Product> products = productRepository.findByCategoryName(categoryName);
        return products.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByPrice(BigDecimal price) {
        List<Product> products = productRepository.findByPrice(price);
        return products.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByBrand(String brand) {
        List<Product> products = productRepository.findByBrand(brand);
        return products.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByCategoryAndBrand(String category, String brand) {
        List<Product> products = productRepository.findByCategoryNameAndBrand(category, brand);
        return products.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByBrandAndName(String brand, String name) {
        List<Product> products = productRepository.findByBrandAndName(brand, name);
        return products.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public int countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }

    private ProductDto mapToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setName(product.getName());
        productDto.setBrand(product.getBrand());
        productDto.setPrice(product.getPrice());
        productDto.setInventory(product.getInventory());
        productDto.setDescription(product.getDescription());

        if (product.getCategory() != null) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setName(product.getCategory().getName());
            productDto.setCategory(categoryDto);
        }

        return productDto;
    }

}
