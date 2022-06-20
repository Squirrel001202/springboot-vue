package com.example.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Files implements Serializable {
    private Integer id;
    private String name;
    private String type;
    private Long size;
    private String url;
    private String md5;
    private Boolean isDelete;
    private Boolean enable;
}
