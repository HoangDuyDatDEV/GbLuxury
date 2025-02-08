package com.example.jingangfarmmanagement.repository;

import com.example.jingangfarmmanagement.repository.entity.Category;
import com.example.jingangfarmmanagement.repository.entity.News;
import com.example.jingangfarmmanagement.repository.entity.RefLink;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefLinkRepository extends BaseRepository<RefLink> {
 List<RefLink> findByNews(News news);
}
