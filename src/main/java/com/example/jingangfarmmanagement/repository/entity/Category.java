package com.example.jingangfarmmanagement.repository.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Category extends BaseEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "path")
    private String path;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<News> news;


    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parentCategory;

    @Getter
    @Setter
    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> subCategories = new ArrayList<>();


}
