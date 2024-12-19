package com.example.jingangfarmmanagement.service;



import com.example.jingangfarmmanagement.model.response.MenuConfigRes;
import com.example.jingangfarmmanagement.repository.entity.MenuConfig;
import com.example.jingangfarmmanagement.repository.entity.News;

import java.util.List;
import java.util.UUID;

public interface MenuConfigService extends BaseService<MenuConfig> {
    public List<MenuConfigRes> searchMenuConfig();
    public MenuConfigRes getMenuConfigById(UUID id);

}
