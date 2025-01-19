package com.example.jingangfarmmanagement.service.Impl;
import com.example.jingangfarmmanagement.model.response.CategoryRes;
import cz.jirutka.rsql.parser.ast.Node;

import com.example.jingangfarmmanagement.model.CategoryDTO;
import com.example.jingangfarmmanagement.model.req.NewsReq;
import com.example.jingangfarmmanagement.model.req.SearchReq;
import com.example.jingangfarmmanagement.model.response.NewsRes;
import com.example.jingangfarmmanagement.query.CustomRsqlVisitor;
import com.example.jingangfarmmanagement.repository.BaseRepository;
import com.example.jingangfarmmanagement.repository.CategoryRepository;
import com.example.jingangfarmmanagement.repository.NewsRepository;
import com.example.jingangfarmmanagement.repository.entity.Category;
import com.example.jingangfarmmanagement.repository.entity.News;
import com.example.jingangfarmmanagement.service.CategoryService;
import com.example.jingangfarmmanagement.service.NewsService;
import cz.jirutka.rsql.parser.RSQLParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NewsServiceImpl extends BaseServiceImpl<News> implements NewsService {
    private static final String DELETED_FILTER = ";status>-1";
    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    protected BaseRepository<News> getRepository() {
        return newsRepository;
    }
    @Override
    public void createNews(NewsReq newsReq) {

        Category category = categoryRepository.findById(newsReq.getCategoryId());
        News news = new News();
        news.setTitle(newsReq.getTitle());
        news.setDescription(newsReq.getDescription());
        news.setContent(newsReq.getContent());
        news.setImage(newsReq.getImage());
        news.setCategory(category);
        newsRepository.save(news);
    }

    @Override
    public void updateNews(UUID newsId, NewsReq newsReq) {
        // Tìm Category theo ID
        News news = newsRepository.findById(newsId);

        // Cập nhật thông tin từ DTO vào News
        news.setTitle(newsReq.getTitle());
        news.setDescription(newsReq.getDescription());
        news.setContent(newsReq.getContent());
        news.setImage(newsReq.getImage());

        // Nếu có categoryId, tìm và gán category
        if (newsReq.getCategoryId()!= null) {
            Category category = categoryRepository.findById(newsReq.getCategoryId());
            news.setCategory(category);
        }

        // Lưu lại News đã cập nhật
        newsRepository.save(news);
    }
    @Override
    public Page<NewsRes> searchNews(String title, String categoryId, Pageable pageable, boolean isCategoryParent) {
        Page<News> resultPage;

        if (categoryId == null || categoryId.isEmpty()) {
            resultPage = newsRepository.findNewsByTitleContaining(title, pageable);
        } else if (isCategoryParent) {
            // Tìm theo parent category
            resultPage = newsRepository.findNewsByCategoryParent(categoryId, title, pageable);
        } else {
            // Tìm theo categoryId
            resultPage = newsRepository.findByCategoryIdAndTitle(categoryId, title, pageable);
        }

        // Map kết quả từ News sang NewsRes
        return resultPage.map(this::mapToNewsRes);
    }

    private NewsRes mapToNewsRes(News entity) {
        NewsRes newsRes = new NewsRes();
        newsRes.setId(entity.getId().toString());
        newsRes.setTitle(entity.getTitle());
        newsRes.setDescription(entity.getDescription());
//        newsRes.setImage(entity.getImage());
//        newsRes.setContent(entity.getContent());

        // Map CategoryRes nếu tồn tại
        if (entity.getCategory() != null) {
            CategoryRes categoryRes = new CategoryRes();
            categoryRes.setId(entity.getCategory().getId().toString());
            categoryRes.setPath(entity.getCategory().getPath());
            categoryRes.setCode(entity.getCategory().getCode());
            categoryRes.setTitle(entity.getCategory().getTitle());
            categoryRes.setName(entity.getCategory().getName());
            if(entity.getCategory().getParentCategory() != null) {
                CategoryRes categoryResParent = new CategoryRes();
                categoryResParent.setId(entity.getCategory().getParentCategory().getId().toString());
                categoryResParent.setPath(entity.getCategory().getParentCategory().getPath());
                categoryResParent.setCode(entity.getCategory().getParentCategory().getCode());
                categoryResParent.setTitle(entity.getCategory().getParentCategory().getTitle());
                categoryResParent.setName(entity.getCategory().getParentCategory().getName());
                categoryRes.setParentCategory(categoryResParent);
            }
            newsRes.setCategoryRes(categoryRes);
        }

        return newsRes;
    }




}

