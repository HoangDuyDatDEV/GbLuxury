package com.example.jingangfarmmanagement.controller;

import com.example.jingangfarmmanagement.model.req.NewsReq;
import com.example.jingangfarmmanagement.model.response.NewsRes;
import com.example.jingangfarmmanagement.repository.entity.News;
import com.example.jingangfarmmanagement.service.BaseService;
import com.example.jingangfarmmanagement.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("news")
public class NewsController extends BaseController<News> {
    private static final String UPLOAD_DIR = "src/main/resources/uploads/";
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
            @RequestParam(value = "isCategoryParent", defaultValue = "false", required = false) boolean isCategoryParent,
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
    @GetMapping("/detail/custom")
    public ResponseEntity<NewsRes> detailNews(
            @RequestParam(value = "id") UUID id) {
        NewsRes newsRes = newsService.searchNewsDetail(id);
        return ResponseEntity.ok(newsRes);
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

    @PostMapping("/custom/image/upload")
    public String uploadImage(@RequestParam("file")MultipartFile file) {
        try {
            // Kiểm tra file có tồn tại và có phải hình ảnh hay không
            if (file.isEmpty()) {
                throw new RuntimeException("File không hợp lệ");
            }

            // Tạo tên file duy nhất
            String fileName = UUID.randomUUID().toString() + "." + getFileExtension(file.getOriginalFilename());
            String uploadDir = "src/main/resources/static/uploads/";  // Đường dẫn thư mục trong dự án
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Lưu file vào thư mục uploads trong dự án
            String filePath = uploadDir + fileName;
            Path path = Paths.get(filePath);
            Files.write(path, file.getBytes());

            return "/uploads/" + fileName;

        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tải lên file: " + e.getMessage(), e);
        }
    }
    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) { // Kiểm tra xem tên file có chứa dấu chấm (.) không
            return fileName.substring(dotIndex + 1).toLowerCase(); // Lấy phần mở rộng sau dấu chấm và chuyển thành chữ thường
        } else {
            return ""; // Nếu không có phần mở rộng, trả về chuỗi rỗng
        }
    }
}