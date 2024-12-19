package com.example.jingangfarmmanagement.service;



import com.example.jingangfarmmanagement.model.req.NewsReq;
import com.example.jingangfarmmanagement.model.response.NewsRes;
import com.example.jingangfarmmanagement.repository.entity.News;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.util.UUID;

public interface NewsService extends BaseService<News> {
     Page<NewsRes> searchNews(String title, String categoryId, Pageable pageable, boolean isCategoryParent);
     void updateNews(UUID newsId, NewsReq newsReq);
     void createNews(NewsReq newsReq);
}
