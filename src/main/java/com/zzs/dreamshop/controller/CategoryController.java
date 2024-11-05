package com.zzs.dreamshop.controller;

import com.zzs.dreamshop.dto.response.ApiResponse;
import com.zzs.dreamshop.entity.Category;
import com.zzs.dreamshop.exceptions.AlreadyExistsException;
import com.zzs.dreamshop.exceptions.ResourceNotFoundException;
import com.zzs.dreamshop.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/all-categories")
    public ResponseEntity<ApiResponse> getAllCategories() {
        try {
            List<Category> categories= categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse("Success", categories));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", e.getMessage()));
        }
    }

    @PostMapping("/add-category")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category category) {
        try {
            Category addCategory =  categoryService.addCategory(category);
            return ResponseEntity.ok(new ApiResponse("Success", addCategory));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("Error", e.getMessage()));
        }
    }


    @GetMapping("/get-category-by-id/{id}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable int id) {
        try {
            Category category = categoryService.getCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Success", category));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Error", e.getMessage()));
        }
    }


    @GetMapping("/get-category-by-name/{name}")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name) {
        try {
            Category category = categoryService.getCategoryByName(name);
            return ResponseEntity.ok(new ApiResponse("Success", category));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Error", e.getMessage()));
        }
    }

    @DeleteMapping("/delete-category/{id}")
    public void deleteCategoryById(@PathVariable int id) {
        categoryService.deleteCategory(id);
    }


    @PutMapping("/update-category/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable int id, @RequestBody Category category) {
        try {
            Category updateCategory = categoryService.updateCategory(category, id);
            return ResponseEntity.ok(new ApiResponse("Success", updateCategory));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Error", e.getMessage()));
        }
    }

}
