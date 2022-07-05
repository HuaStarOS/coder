package com.qzh.articleservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qzh.articleservice.entity.Article;
import com.qzh.articleservice.entity.ArticleCollect;
import com.qzh.articleservice.mapper.ArticleCollectMapper;
import com.qzh.articleservice.service.ArticleCollectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qzh.articleservice.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-09-17
 */
@Service
public class ArticleCollectServiceImpl extends ServiceImpl<ArticleCollectMapper, ArticleCollect> implements ArticleCollectService {

    @Autowired
    private ArticleService articleService;

    @Override
    public Integer deleteCollect(String id, String memberId) {
        int delete = this.baseMapper.delete(new QueryWrapper<ArticleCollect>()
                .eq("member_id", memberId)
                .eq("article_id", id)
        );

        return delete;
    }

    @Override
    public List<Article> getAllBymemberId(String memberId) {
        List<ArticleCollect> membercollect_list = this.baseMapper.selectList(new QueryWrapper<ArticleCollect>().eq("member_id", memberId));
        ArrayList<Article> articles = new ArrayList<>();
        for (ArticleCollect a:membercollect_list) {
            Article byId = articleService.getById(a.getArticleId());
            byId.setIscollect(true);
            articles.add(byId);
        }
        return articles;
    }
}
