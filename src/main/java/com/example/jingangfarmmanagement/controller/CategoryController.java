package com.example.jingangfarmmanagement.controller;

import com.example.jingangfarmmanagement.model.BaseResponse;
import com.example.jingangfarmmanagement.model.CategoryDTO;
import com.example.jingangfarmmanagement.model.req.AssignRoleMenuReq;
import com.example.jingangfarmmanagement.repository.entity.Category;
import com.example.jingangfarmmanagement.repository.entity.Role;
import com.example.jingangfarmmanagement.service.BaseService;
import com.example.jingangfarmmanagement.service.CategoryService;
import com.example.jingangfarmmanagement.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("categories")
public class CategoryController extends BaseController<Category> {
    @Autowired
    private CategoryService categoryService;

    @Override
    protected BaseService<Category> getService() {
        return categoryService;
    }

    @GetMapping("/tree")
    public List<CategoryDTO> getCategoryTree() {
        return categoryService.getCategoryTree();
    }
    @GetMapping("/tree/by-id")
    public CategoryDTO getCategoryTreeById(@RequestParam UUID parentId){
        return  categoryService.getCategoryTreeById(parentId);
    }
    @GetMapping("/client/tree")
    public List<CategoryDTO> getCategoryClientTree() {
        return categoryService.getCategoryTree();
    }
    @GetMapping("/client/tree/by-id")
    public CategoryDTO getCategoryClientTreeById(@RequestParam UUID parentId){
        return  categoryService.getCategoryTreeById(parentId);
    }

    @PostMapping("/custom/create")
    public ResponseEntity<Void> create(@RequestBody CategoryDTO categoryDTO) {
        // Gọi service để tạo category mới từ categoryDTO
        categoryService.createCategory(categoryDTO);

        // Trả về phản hồi với mã trạng thái 201 (Created) mà không có nội dung (body)
        return ResponseEntity.status(201).build();
    }
    @PutMapping("/custom/update")
    public ResponseEntity<Void> updateCategory(@RequestParam UUID categoryId,@RequestBody CategoryDTO categoryDTO) {
        // Gọi service để tạo category mới từ categoryDTO
        categoryService.updateCategory(categoryId,categoryDTO);

        // Trả về phản hồi với mã trạng thái 201 (Created) mà không có nội dung (body)
        return ResponseEntity.status(201).build();
    }

}