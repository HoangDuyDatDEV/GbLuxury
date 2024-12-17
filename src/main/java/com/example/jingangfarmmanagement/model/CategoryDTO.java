package com.example.jingangfarmmanagement.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class CategoryDTO {
    private UUID id;
    private String title;
    private String path;
    private String code;
    private String name;
    private UUID parentCategoryId;
    private List<CategoryDTO> subCategories = new ArrayList<>();
}
