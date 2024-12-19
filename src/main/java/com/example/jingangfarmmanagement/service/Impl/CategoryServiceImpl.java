package com.example.jingangfarmmanagement.service.Impl;


import com.example.jingangfarmmanagement.constants.Status;
import com.example.jingangfarmmanagement.model.CategoryDTO;
import com.example.jingangfarmmanagement.repository.BaseRepository;
import com.example.jingangfarmmanagement.repository.CategoryRepository;
import com.example.jingangfarmmanagement.repository.RoleRepository;
import com.example.jingangfarmmanagement.repository.entity.Category;
import com.example.jingangfarmmanagement.repository.entity.Role;
import com.example.jingangfarmmanagement.service.CategoryService;
import com.example.jingangfarmmanagement.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl extends BaseServiceImpl<Category> implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    protected BaseRepository<Category> getRepository() {
        return categoryRepository;
    }
    @Override
    public List<CategoryDTO> getCategoryTree() {
        // Lấy tất cả các category từ repository
        List<Category> categories = categoryRepository.findAllByStatus(1);

        // Danh sách chứa các category gốc (không có parent)
        List<Category> rootCategories = categories.stream()
                .filter(category -> category.getParentCategory() == null)
                .collect(Collectors.toList());

        // Khởi tạo Set để theo dõi các category đã duyệt
        Set<UUID> visitedCategories = new HashSet<>();

        // Duyệt qua các rootCategories để xây dựng cây phân loại
        for (Category rootCategory : rootCategories) {
            buildCategoryTree(rootCategory, categories, visitedCategories);
        }

        // Chuyển đổi cây phân loại thành danh sách CategoryDTO
        return rootCategories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    @Override
    public CategoryDTO getCategoryTreeById(UUID parentId) {
        Category parentCategory = categoryRepository.findById(parentId);

        List<Category> allCategories = categoryRepository.findAllByStatus(1);

        // Set để lưu các id của category đã được duyệt
        Set<UUID> visitedCategories = new HashSet<>();

        buildCategoryTree(parentCategory, allCategories, visitedCategories);

        // Chuyển đổi category thành DTO trước khi trả về
        return convertToDTO(parentCategory);
    }

    private void buildCategoryTree(Category parent, List<Category> allCategories, Set<UUID> visitedCategories) {
        // Kiểm tra xem category này đã được duyệt qua chưa để tránh vòng lặp
        if (visitedCategories.contains(parent.getId())) {
            return;  // Nếu đã duyệt qua thì bỏ qua
        }

        // Đánh dấu category này đã được duyệt qua
        visitedCategories.add(parent.getId());

        // Lọc các category con của parent
        List<Category> children = allCategories.stream()
                .filter(c -> parent.getId().equals(c.getParentCategory() != null ? c.getParentCategory().getId() : null))
                .collect(Collectors.toList());

        // Duyệt qua từng category con để xây dựng cây
        for (Category child : children) {
            // Nếu child đã có trong subCategories của parent thì bỏ qua
            if (parent.getSubCategories().stream().anyMatch(sub -> sub.getId().equals(child.getId()))) {
                continue;  // Tránh lặp lại
            }

            // Thêm vào subCategories của parent
            parent.getSubCategories().add(child);

            // Đệ quy xây dựng cây cho category con
            buildCategoryTree(child, allCategories, visitedCategories);
        }

        // Sau khi xây dựng xong, loại bỏ parentCategory trong các subCategories
        for (Category child : parent.getSubCategories()) {
            child.setParentCategory(null);  // Loại bỏ parentCategory của subCategory
        }
    }

    private CategoryDTO convertToDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setTitle(category.getTitle());
        categoryDTO.setPath(category.getPath());
        categoryDTO.setCode(category.getCode());
        categoryDTO.setName(category.getName());
        categoryDTO.setSubCategories(category.getSubCategories().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList()));

        return categoryDTO;
    }


    @Override
    public void createCategory(CategoryDTO categoryDTO) {

        Category category = new Category();
        category.setTitle(categoryDTO.getTitle());
        category.setPath(categoryDTO.getPath());
        category.setCode(categoryDTO.getCode());
        category.setName(categoryDTO.getName());

        if (categoryDTO.getParentCategoryId() != null) {
            Optional<Category> parentCategory = Optional.ofNullable(categoryRepository.findById(categoryDTO.getParentCategoryId()));
            parentCategory.ifPresent(category::setParentCategory);
        }

        categoryRepository.save(category);
    }
    @Override
    public void updateCategory(UUID categoryId, CategoryDTO categoryDTO) {
        // Tìm Category theo ID
        Category category = categoryRepository.findById(categoryId);

        // Cập nhật thông tin từ DTO vào Category
        category.setTitle(categoryDTO.getTitle());
        category.setPath(categoryDTO.getPath());
        category.setCode(categoryDTO.getCode());
        category.setName(categoryDTO.getName());

        // Nếu có parentCategoryId, tìm và gán parentCategory
        if (categoryDTO.getParentCategoryId() != null) {
            Category parentCategory = categoryRepository.findById(categoryDTO.getParentCategoryId());
            category.setParentCategory(parentCategory);
        } else {
            category.setParentCategory(null);
        }

        // Lưu lại Category đã cập nhật
        categoryRepository.save(category);
    }

}

