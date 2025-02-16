package com.example.jingangfarmmanagement.repository;

import com.example.jingangfarmmanagement.repository.entity.Category;
import com.example.jingangfarmmanagement.repository.entity.News;
import com.example.jingangfarmmanagement.repository.entity.RefLink;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RefLinkRepository extends BaseRepository<RefLink> {
 List<RefLink> findByNews(News news);
 @Modifying
 @Query("DELETE FROM RefLink r WHERE r.news.id = :newsId")
 void deleteRefLinksByNewsId(@Param("newsId") UUID newsId);
}
