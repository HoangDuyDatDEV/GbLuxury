package com.example.jingangfarmmanagement.model.response;

import lombok.Data;

import java.util.List;

@Data
public class MenuConfigRes {
    private String id;
    private List<CategoryRes> categoryRes;
    private Long quantity;
    private List<NewsRes> newsRes;
}
