package com.example.jingangfarmmanagement.controller;

import com.example.jingangfarmmanagement.model.req.NewsReq;
import com.example.jingangfarmmanagement.model.response.NewsRes;
import com.example.jingangfarmmanagement.repository.NewsRepository;
import com.example.jingangfarmmanagement.repository.entity.Category;
import com.example.jingangfarmmanagement.repository.entity.News;
import com.example.jingangfarmmanagement.service.BaseService;
import com.example.jingangfarmmanagement.service.CategoryService;
import com.example.jingangfarmmanagement.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("news")
public class NewsController extends BaseController<News> {
    @Autowired
    private NewsService newsService;

    @Override
    protected BaseService<News> getService() {
        return newsService;
    }
    @GetMapping("/search/custom")
    public ResponseEntity<Page<NewsRes>> searchNews(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "categoryId", required = false) String categoryId,
            @RequestParam(value = "isCategoryParent", defaultValue = "false") boolean isCategoryParent,
            Pageable pageable) {
        Page<NewsRes> newsResPage = newsService.searchNews(title, categoryId, pageable, isCategoryParent);
        return ResponseEntity.ok(newsResPage);
    }
    @GetMapping("/client/search/custom")
    public ResponseEntity<Page<NewsRes>> searchNewsClient(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "categoryId", required = false) String categoryId,
            @RequestParam(value = "isCategoryParent", defaultValue = "false") boolean isCategoryParent,
            Pageable pageable) {
        Page<NewsRes> newsResPage = newsService.searchNews(title, categoryId, pageable, isCategoryParent);
        return ResponseEntity.ok(newsResPage);
    }

    /**
     * Update existing news
     * @param newsId the ID of the news to update
     * @param newsReq the updated news data
     * @return a success message
     */
    @PutMapping("/custom/update")
    public ResponseEntity<String> updateNews(
            @RequestParam UUID newsId,
            @RequestBody NewsReq newsReq) {
        newsService.updateNews(newsId, newsReq);
        return ResponseEntity.ok("News updated successfully!");
    }

    /**
     * Create a new news item
     * @param newsReq the new news data
     * @return a success message
     */
    @PostMapping("/custom/create")
    public ResponseEntity<String> createNews(@RequestBody NewsReq newsReq) {
        newsService.createNews(newsReq);
        return ResponseEntity.ok("News created successfully!");
    }
}