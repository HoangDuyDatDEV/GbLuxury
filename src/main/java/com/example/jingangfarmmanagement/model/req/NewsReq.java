package com.example.jingangfarmmanagement.model.req;

import com.example.jingangfarmmanagement.repository.entity.Category;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Data
public class NewsReq {
    private String id;
    private String title;
    private String description;
    private String image;
    private String content;
    private UUID categoryId;
}
