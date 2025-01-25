package com.example.jingangfarmmanagement.model.req;

import lombok.Data;

import javax.persistence.Column;
import java.util.UUID;

@Data
public class RefLinkReq {
    private String link;
    private String no;
}
