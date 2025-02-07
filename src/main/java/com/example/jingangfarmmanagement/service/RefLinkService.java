package com.example.jingangfarmmanagement.service;



import com.example.jingangfarmmanagement.model.BaseResponse;
import com.example.jingangfarmmanagement.model.response.MenuConfigRes;
import com.example.jingangfarmmanagement.repository.entity.MenuConfig;
import com.example.jingangfarmmanagement.repository.entity.RefLink;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface RefLinkService extends BaseService<RefLink> {
    public BaseResponse uploadFile(MultipartFile file);

}
