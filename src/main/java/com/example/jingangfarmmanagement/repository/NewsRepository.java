package com.example.jingangfarmmanagement.repository;

import com.example.jingangfarmmanagement.repository.entity.Category;
import com.example.jingangfarmmanagement.repository.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends BaseRepository<News> {
    @Query(value = "SELECT * FROM News n " +
            "WHERE n.category_id = :categoryId " +
            "AND (:title IS NULL OR n.title LIKE CONCAT('%', :title, '%'))",
            countQuery = "SELECT COUNT(*) FROM News n " +
                    "WHERE n.category_id = :categoryId" +
                    "AND (:title IS NULL OR n.title LIKE CONCAT('%', :title, '%'))",
            nativeQuery = true)
    Page<News> findByCategoryIdAndTitle(@Param("categoryId") String categoryId,
                                        @Param("title") String title,
                                        Pageable pageable);
    @Query(value = "SELECT * FROM News n " +
            "JOIN Category c ON n.category_id = c.id " +
            "WHERE c.parent_id = :parentId " +
            "AND (:title IS NULL OR n.title LIKE CONCAT('%', :title, '%'))",
            countQuery = "SELECT COUNT(*) FROM News n " +
                    "JOIN Category c ON n.category_id = c.id " +
                    "WHERE c.parent_id = :parentId" +
                    "AND (:title IS NULL OR n.title LIKE CONCAT('%', :title, '%'))",
            nativeQuery = true)
    Page<News> findNewsByCategoryParent(@Param("parentId") String parentId,
                                        @Param("title") String title,
                                        Pageable pageable);
    @Query(value = "SELECT * FROM News n " +
            "WHERE (:title IS NULL OR n.title LIKE CONCAT('%', :title, '%'))",
            countQuery = "SELECT COUNT(*) FROM News n " +
                    "WHERE (:title IS NULL OR n.title LIKE CONCAT('%', :title, '%'))",
            nativeQuery = true)
    Page<News> findNewsByTitleContaining(@Param("title") String title, Pageable pageable);
}
