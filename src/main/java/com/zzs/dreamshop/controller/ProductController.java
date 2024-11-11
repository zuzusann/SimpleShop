package com.zzs.dreamshop.controller;

import com.zzs.dreamshop.dto.ProductDto;
import com.zzs.dreamshop.dto.request.ProductRequest;
import com.zzs.dreamshop.dto.request.ProductUpdateRequest;
import com.zzs.dreamshop.dto.response.ApiResponse;
import com.zzs.dreamshop.entity.Product;
import com.zzs.dreamshop.exceptions.ProductNotFoundException;
import com.zzs.dreamshop.exceptions.ResourceNotFoundException;
import com.zzs.dreamshop.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all-products")
    public ResponseEntity<ApiResponse> getAllProducts() {
        try {
            List<ProductDto> products = productService.getAllProducts();
            return ResponseEntity.ok(new ApiResponse("success", products));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("error", e.getMessage()));
        }
    }

    @GetMapping("/get-product/by-id/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable int id) {
        try {
            ProductDto products = productService.getProductById(id);
            return ResponseEntity.ok(new ApiResponse("success", products));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("error", e.getMessage()));
        }
    }

    @GetMapping("/get-product/by-name/{name}")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name) {
        try {
            List<ProductDto> products = productService.getProductsByName(name);
            return ResponseEntity.ok(new ApiResponse("success", products));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("error", e.getMessage()));
        }
    }

    @GetMapping("/get-product/by-category-name/{categoryName}")
    public ResponseEntity<ApiResponse> getProductByCategoryName(@PathVariable String categoryName) {
        try {
            List<ProductDto> products = productService.getProductsByCategoryName(categoryName);
            return ResponseEntity.ok(new ApiResponse("success", products));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("error", e.getMessage()));
        }
    }

    @GetMapping("/get-product/by-price/{price}")
    public ResponseEntity<ApiResponse> getProductByPrice(@PathVariable BigDecimal price) {
        try {
            List<ProductDto> products = productService.getProductsByPrice(price);
            return ResponseEntity.ok(new ApiResponse("success", products));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("error", e.getMessage()));
        }
    }

    @GetMapping("/get-product/by-brand/{brand}")
    public ResponseEntity<ApiResponse> getProductByBrand(@PathVariable String brand) {
        try {
            List<ProductDto> products = productService.getProductsByBrand(brand);
            return ResponseEntity.ok(new ApiResponse("success", products));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("error", e.getMessage()));
        }
    }

    @GetMapping("/get-product/by-category-and-brand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String category, @RequestParam String brand) {
        try {
            List<ProductDto> products = productService.getProductsByCategoryAndBrand(category, brand);
            return ResponseEntity.ok(new ApiResponse("success", products));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("error", e.getMessage()));
        }
    }

    @GetMapping("/get-product/by-brand-and-name")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brand, @RequestParam String name) {
        try {
            List<ProductDto> products = productService.getProductsByBrandAndName(brand, name);
            return ResponseEntity.ok(new ApiResponse("success", products));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("error", e.getMessage()));
        }
    }


    @GetMapping("/count-product/by-brand-and-name")
    public ResponseEntity<ApiResponse> countProductByBrandAndName(@RequestParam String brand, @RequestParam String name) {
        try {
            int count = productService.countProductsByBrandAndName(brand, name);
            return ResponseEntity.ok(new ApiResponse("success", count));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("error", e.getMessage()));
        }
    }

    @PostMapping("/add-product")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductRequest product) {
        try {
            ProductDto createproduct = productService.addProduct(product);
            return ResponseEntity.ok(new ApiResponse("success", createproduct));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("error", e.getMessage()));
        }
    }

    @PutMapping("/update-product/{id}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable int id, @RequestBody ProductUpdateRequest product) {
        try {
            ProductDto updateProduct = productService.updateProduct(product, id);
            return ResponseEntity.ok(new ApiResponse("success", updateProduct));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("error", e.getMessage()));
        }
    }

    @DeleteMapping("/delete-product/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        try {
            productService.deleteProductById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
