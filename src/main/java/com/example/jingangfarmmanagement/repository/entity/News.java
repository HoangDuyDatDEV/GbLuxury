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
public class News extends BaseEntity{
    @Column(name = "title")
    private String title;
    @Column(name = "description",columnDefinition = "LONGTEXT")
    private String description;
    @Column(name = "image",columnDefinition = "LONGTEXT")
    private String image;
    @Column(name = "content",columnDefinition = "LONGTEXT")
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RefLink> refLinks;
}
