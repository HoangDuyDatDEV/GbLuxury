package com.example.jingangfarmmanagement.service;

import com.example.jingangfarmmanagement.model.req.SearchReq;
import com.example.jingangfarmmanagement.repository.entity.BaseEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface BaseService<T extends BaseEntity> {
    Page<T> search(SearchReq req);

    T create(T t) throws Exception;

    T update(T t) throws Exception;

    T getById(UUID id) throws Exception;

    List<T> getByActive();

    List<T> getAll();

    void delete(UUID id);

    void createAll(List<T> entities);
}
