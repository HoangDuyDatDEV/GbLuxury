package com.example.jingangfarmmanagement.service.Impl;


import com.example.jingangfarmmanagement.model.response.CategoryRes;
import com.example.jingangfarmmanagement.model.response.MenuConfigRes;
import com.example.jingangfarmmanagement.model.response.NewsRes;
import com.example.jingangfarmmanagement.repository.BaseRepository;
import com.example.jingangfarmmanagement.repository.MenuConfigRepository;
import com.example.jingangfarmmanagement.repository.NewsRepository;
import com.example.jingangfarmmanagement.repository.entity.MenuConfig;
import com.example.jingangfarmmanagement.repository.entity.News;
import com.example.jingangfarmmanagement.service.MenuConfigService;
import com.example.jingangfarmmanagement.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Service
public class MenuConfigServiceImpl extends BaseServiceImpl<MenuConfig> implements MenuConfigService {
    @Autowired
    private MenuConfigRepository menuConfigRepository;
    @Autowired
    private NewsRepository newsRepository;

    @Override
    protected BaseRepository<MenuConfig> getRepository() {
        return menuConfigRepository;
    }
    @Override
    public List<MenuConfigRes> searchMenuConfig(){
        List<MenuConfigRes> menuConfigRes = new ArrayList<>();
        List<MenuConfig> menuConfigs = menuConfigRepository.findAllByStatus(1);
        for (MenuConfig menuConfig : menuConfigs) {
            menuConfigRes.add(mapToMenuConfigRes(menuConfig));
        }
        return menuConfigRes;
    }
    @Override
    public MenuConfigRes getMenuConfigById(UUID id){
        MenuConfig menuConfig= menuConfigRepository.findById(id);
        return mapToMenuConfigRes(menuConfig);
    }
    private MenuConfigRes mapToMenuConfigRes(MenuConfig entity){
        MenuConfigRes menuConfigRes = new MenuConfigRes();
        if (entity.getCategories() != null) {
            List<CategoryRes> categoryResList = new ArrayList<>();
            entity.getCategories().forEach(category -> {
                CategoryRes categoryRes = new CategoryRes();
                categoryRes.setId(category.getId().toString());
                categoryRes.setPath(category.getPath());
                categoryRes.setCode(category.getCode());
                categoryRes.setTitle(category.getTitle());
                categoryRes.setName(category.getName());
                categoryResList.add(categoryRes);
            });
            menuConfigRes.setCategoryRes(categoryResList);
        }

        menuConfigRes.setId(entity.getId().toString());
        String[] newsIdArray = entity.getNewsId().split(",");
        List<UUID> listUUID = new ArrayList<>();
        for (String id : newsIdArray) {
            listUUID.add(UUID.fromString(id));
        }
        List<NewsRes> newsResList = new ArrayList<>();
        for (UUID uuid : listUUID) {
            News news = newsRepository.findById(uuid);
            if (news!= null) {
                newsResList.add(mapToNewsRes(news));
            }
        }
        menuConfigRes.setNewsRes(newsResList);
        return menuConfigRes;
    }

    private NewsRes mapToNewsRes(News entity) {
        NewsRes newsRes = new NewsRes();
        newsRes.setId(entity.getId().toString());
        newsRes.setTitle(entity.getTitle());
        newsRes.setDescription(entity.getDescription());
        newsRes.setImage(entity.getImage());
        newsRes.setContent(entity.getContent());

        // Map CategoryRes nếu tồn tại
        if (entity.getCategory() != null) {
            CategoryRes categoryRes = new CategoryRes();
            categoryRes.setId(entity.getCategory().getId().toString());
            categoryRes.setPath(entity.getCategory().getPath());
            categoryRes.setCode(entity.getCategory().getCode());
            categoryRes.setTitle(entity.getCategory().getTitle());
            categoryRes.setName(entity.getCategory().getName());
            newsRes.setCategoryRes(categoryRes);
        }

        return newsRes;
    }

}

