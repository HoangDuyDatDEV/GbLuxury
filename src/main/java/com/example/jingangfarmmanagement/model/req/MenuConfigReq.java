package com.example.jingangfarmmanagement.model.req;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class MenuConfigReq {
    private UUID id;
    private String categoryId;
    private String newsId;
    private Long quantity;

}
