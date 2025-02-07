package com.example.jingangfarmmanagement.controller;

import com.example.jingangfarmmanagement.model.BaseResponse;
import com.example.jingangfarmmanagement.model.response.MenuConfigRes;
import com.example.jingangfarmmanagement.repository.entity.MenuConfig;
import com.example.jingangfarmmanagement.repository.entity.RefLink;
import com.example.jingangfarmmanagement.service.BaseService;
import com.example.jingangfarmmanagement.service.MenuConfigService;
import com.example.jingangfarmmanagement.service.RefLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("ref_link")
public class ReflinkController extends BaseController<RefLink> {
    @Autowired
    private RefLinkService refLinkService;

    @Override
    protected BaseService<RefLink> getService() {
        return refLinkService;
    }
    @PostMapping("upload")
    public BaseResponse handleFileUpload(@RequestParam("file") MultipartFile file) {
        return refLinkService.uploadFile(file);
    }
}