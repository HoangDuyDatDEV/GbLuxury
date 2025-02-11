package com.example.jingangfarmmanagement.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MenuConfig extends BaseEntity{
    @Column(name = "category_id",columnDefinition = "TEXT")
    private String categoryId;
    @Column(name = "quantity")
    private Long quantity;
    @Column(name = "news_id",columnDefinition = "TEXT")
    private String newsId;
}
