package com.qzh.articleservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qzh.articleservice.entity.Article;
import com.qzh.articleservice.entity.ArticleCollect;
import com.qzh.articleservice.entity.Footprint;
import com.qzh.articleservice.service.ArticleCollectService;
import com.qzh.articleservice.service.FootprintService;

import com.qzh.articleservice.utils.JwtUtils;
import com.qzh.articleservice.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-09-17
 */
@RestController
@RequestMapping("/articleService/article-collect")
public class ArticleCollectController {


    @Autowired
    private ArticleCollectService articleCollectService;

    @Autowired
    private FootprintService footprintService;

    @GetMapping("addCollect/{id}")
    public Result addCollect(@PathVariable String id, HttpServletRequest request){
        String memberId=JwtUtils.getMemberIdByJwtToken(request);
        ArticleCollect articleCollect = new ArticleCollect();
        articleCollect.setArticleId(id);
        articleCollect.setMemberId(memberId);
        boolean save = articleCollectService.save(articleCollect);
        if(save){
            Date gmtCreate = articleCollect.getGmtCreate();
            Footprint footprint = new Footprint();
            footprint.setGmtCreate(gmtCreate);
            footprint.setIscollect(1);
            footprint.setMemberId(memberId);
            footprint.setArticleId(id);
            footprintService.save(footprint);
            return  Result.ok().message("成功收藏");
        }
        return  Result.error().message("faile");
    }

    @DeleteMapping("deleteArticlecollect/{id}")
    public Result deleteCollect(@PathVariable String id, HttpServletRequest request){
        String memberId= JwtUtils.getMemberIdByJwtToken(request);
        Integer isok = articleCollectService.deleteCollect(id, memberId);
        if(isok==1){
            footprintService.remove(new QueryWrapper<Footprint>()
                    .eq("member_id",memberId)
                    .eq("article_id",id)
                    .eq("iscollect",1)
            );
            return  Result.ok().message("取消收藏");
        }else {
            return  Result.error().message("服务器开小差，稍等");
        }
    }

    @GetMapping("getAllCollect")
    public Result getAllCollect(HttpServletRequest request){
        String memberId= JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(memberId)){
            return Result.error().code(20002);
        }
        List<Article> allBymemberId = articleCollectService.getAllBymemberId(memberId);
        return Result.ok().data("collectlist",allBymemberId);
    }
}

