package com.example.jingangfarmmanagement.model.response;

import lombok.Data;

import java.util.List;

@Data
public class NewsRes {
    private String id;
    private String title;
    private String description;
    private String image;
    private String content;
    private CategoryRes categoryRes;
    private List<RefLinkRes> refLinkRes;
}
