package com.qzh.articleservice.service;

import com.qzh.articleservice.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2020-09-13
 */
public interface ArticleService extends IService<Article> {

    Boolean insetArticle(Article article, String memberID);

    List<Article> getAllarticle(String memberId);

    Integer addVitsi(String id);

    List<Article> getSomeList(String title,String memberId);
}
