package com.qzh.articleservice.entity;

import lombok.Data;

import java.util.Date;

@Data
public class FootVo {

    private String author;
    private String articleTitle;
    private Integer iscollect;
    private Date gmtCreate;
}
