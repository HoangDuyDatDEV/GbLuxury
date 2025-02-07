package com.example.jingangfarmmanagement.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RefLink extends BaseEntity{
    @Column(name = "link")
    private String link;
    @Column(name = "no")
    private String no;
    @ManyToOne
    @JoinColumn(name = "news_id")
    private News news;
}
