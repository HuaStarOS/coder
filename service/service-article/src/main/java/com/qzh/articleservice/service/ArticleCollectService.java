package com.qzh.articleservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qzh.articleservice.entity.Article;
import com.qzh.articleservice.entity.ArticleCollect;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2020-09-17
 */
public interface ArticleCollectService extends IService<ArticleCollect> {

    Integer deleteCollect(String id, String memberId);

    List<Article> getAllBymemberId(String memberId);
}
