package com.example.jingangfarmmanagement.model.response;

import lombok.Data;

import javax.persistence.Column;

@Data
public class CategoryRes {
    private String id;
    private String path;
    private String code;
    private String title;
    private String name;
}
