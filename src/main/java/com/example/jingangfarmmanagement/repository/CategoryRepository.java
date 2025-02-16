package com.example.jingangfarmmanagement.repository;

import com.example.jingangfarmmanagement.repository.entity.Category;
import com.example.jingangfarmmanagement.repository.entity.News;
import com.example.jingangfarmmanagement.repository.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends BaseRepository<Category> {
    @Query(value = "SELECT * FROM category c " +
            "WHERE  (:code IS NULL OR c.code LIKE CONCAT('%', :code, '%'))",
            nativeQuery = true)
    Category findByCode(@Param("code") String code);
}
