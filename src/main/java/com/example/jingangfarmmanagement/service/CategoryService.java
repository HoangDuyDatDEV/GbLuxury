package com.example.jingangfarmmanagement.service;


import com.example.jingangfarmmanagement.model.CategoryDTO;
import com.example.jingangfarmmanagement.repository.entity.Category;
import com.example.jingangfarmmanagement.repository.entity.Role;

import java.util.List;
import java.util.UUID;

public interface CategoryService extends BaseService<Category> {
    public List<CategoryDTO> getCategoryTree();
    public CategoryDTO getCategoryTreeById(UUID parentId);
    CategoryDTO getCategoryTreeByCode(String code);
    public void createCategory(CategoryDTO categoryDTO);
    public void updateCategory(UUID categoryId, CategoryDTO categoryDTO);
}
