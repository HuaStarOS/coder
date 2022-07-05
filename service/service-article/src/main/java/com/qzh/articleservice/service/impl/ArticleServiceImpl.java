package com.qzh.articleservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qzh.articleservice.client.UcenterClient;
import com.qzh.articleservice.entity.Article;
import com.qzh.articleservice.entity.ArticleCollect;
import com.qzh.articleservice.entity.Footprint;
import com.qzh.articleservice.mapper.ArticleMapper;
import com.qzh.articleservice.service.ArticleCollectService;
import com.qzh.articleservice.service.ArticleService;
import com.qzh.articleservice.service.FootprintService;

import com.qzh.articleservice.entity.UcenterMemberVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-09-13
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private UcenterClient ucenterClient;

    @Autowired
    private ArticleCollectService collectService;

    @Autowired
    private FootprintService footprintService;

    @Override
    public Boolean insetArticle(Article article, String memberID) {
        UcenterMemberVo info = ucenterClient.getInfo(memberID);
        article.setAuthor(info.getNickname());
        int insert = this.baseMapper.insert(article);
        if(insert==1){
            Date gmtCreate = article.getGmtCreate();
            Footprint footprint = new Footprint();
            footprint.setGmtCreate(gmtCreate);
            footprint.setIscollect(0);
            footprint.setMemberId(memberID);
            footprint.setArticleId(article.getId());
            footprintService.save(footprint);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<Article> getAllarticle(String memberId) {
        List<Article> visits = this.baseMapper.selectList(new QueryWrapper<Article>().orderByDesc("visits"));
        if(memberId==""){
            for (Article a:visits) {
                a.setIscollect(false);
            }
        }else {
            for (Article a:visits) {
                ArticleCollect one = collectService.getOne(new QueryWrapper<ArticleCollect>().eq("article_id", a.getId()).eq("member_id", memberId));
                if(one==null){
                    a.setIscollect(false);
                }else {
                    a.setIscollect(true);
                }
            }
        }
        return visits;
    }

    @Override
    public Integer addVitsi(String id) {
        Article article = new Article();
        article.setId(id);
        Article article1 = this.baseMapper.selectById(id);
        article.setVisits(article1.getVisits()+1);
        this.baseMapper.updateById(article);
        return article.getVisits();
    }

    @Override
    public List<Article> getSomeList(String title,String memberId) {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("visits");
        wrapper.like("article_title",title);
        List<Article> visits = this.baseMapper.selectList(wrapper);
        if(StringUtils.isEmpty(memberId)){
            for (Article a:visits) {
                a.setIscollect(false);
            }
        }else {
            for (Article a:visits) {
                ArticleCollect one = collectService.getOne(new QueryWrapper<ArticleCollect>().eq("article_id", a.getId()).eq("member_id", memberId));
                if(one==null){
                    a.setIscollect(false);
                }else {
                    a.setIscollect(true);
                }
            }
        }
        return visits;
    }
}
