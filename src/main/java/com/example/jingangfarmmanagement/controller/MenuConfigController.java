package com.example.jingangfarmmanagement.controller;

import com.example.jingangfarmmanagement.model.response.MenuConfigRes;
import com.example.jingangfarmmanagement.repository.entity.Category;
import com.example.jingangfarmmanagement.repository.entity.MenuConfig;
import com.example.jingangfarmmanagement.service.BaseService;
import com.example.jingangfarmmanagement.service.CategoryService;
import com.example.jingangfarmmanagement.service.MenuConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("menu_config")
public class MenuConfigController extends BaseController<MenuConfig> {
    @Autowired
    private MenuConfigService menuConfigService;

    @Override
    protected BaseService<MenuConfig> getService() {
        return menuConfigService;
    }
    @GetMapping("/search/custom")
    public ResponseEntity<List<MenuConfigRes>> searchMenuConfig() {
        try {
            List<MenuConfigRes> menuConfigList = menuConfigService.searchMenuConfig();
            return new ResponseEntity<>(menuConfigList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/client/search/custom")
    public ResponseEntity<List<MenuConfigRes>> searchMenuConfigClient() {
        try {
            List<MenuConfigRes> menuConfigList = menuConfigService.searchMenuConfig();
            return new ResponseEntity<>(menuConfigList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint để lấy menu config theo id
    @GetMapping("/custom/by-id")
    public ResponseEntity<MenuConfigRes> getMenuConfigById(@PathVariable UUID id) {
        try {
            MenuConfigRes menuConfig = menuConfigService.getMenuConfigById(id);
            if (menuConfig != null) {
                return new ResponseEntity<>(menuConfig, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/client/custom/by-id")
    public ResponseEntity<MenuConfigRes> getMenuConfigClientById(@PathVariable UUID id) {
        try {
            MenuConfigRes menuConfig = menuConfigService.getMenuConfigById(id);
            if (menuConfig != null) {
                return new ResponseEntity<>(menuConfig, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}