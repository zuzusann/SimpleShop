package com.zzs.dreamshop.service.category;

import com.zzs.dreamshop.entity.Category;

import java.util.List;

public interface CategoryService {

    Category getCategoryById(int id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category addCategory(Category category);
    Category updateCategory(Category category, int id);
    void deleteCategory(int id);
}
