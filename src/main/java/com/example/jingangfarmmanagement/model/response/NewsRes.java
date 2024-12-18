package com.example.jingangfarmmanagement.model.response;

import lombok.Data;

@Data
public class NewsRes {
    private String id;
    private String title;
    private String description;
    private String image;
    private String content;
    private CategoryRes categoryRes;
}
