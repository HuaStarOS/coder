package com.qzh.articleservice.controller;


import com.qzh.articleservice.entity.Article;
import com.qzh.articleservice.service.ArticleService;
import com.qzh.articleservice.utils.JwtUtils;
import com.qzh.articleservice.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-09-13
 */
@RestController
@RequestMapping("/articleService/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    @PostMapping("create")
    public Result createArticle(@RequestBody Article article, HttpServletRequest request){
        String memberID= JwtUtils.getMemberIdByJwtToken(request);
        System.out.println(article);
        Boolean aBoolean = articleService.insetArticle(article, memberID);
        if(aBoolean){
            return Result.ok().message("创建文章成功");
        }
        return Result.error().message("创建文章失败");
    }

    @GetMapping("getAll")
    public Result getAllaritcile(HttpServletRequest request){
        String memberId=JwtUtils.getMemberIdByJwtToken(request);
        List<Article> allarticle = articleService.getAllarticle(memberId);
        return Result.ok().data("ariticle",allarticle);
    }

    @GetMapping("addVistist/{id}")
    public Result addVitist(@PathVariable String id){
        Integer vitsi = articleService.addVitsi(id);
        return Result.ok().data("vitsi",vitsi);
    }

    @GetMapping("getSome/{title}")
    public Result getSomeList(@PathVariable("title") String title,HttpServletRequest request){
        String memberId=JwtUtils.getMemberIdByJwtToken(request);
        List<Article> someList = articleService.getSomeList(title,memberId);
        return Result.ok().data("ariticle",someList);
    }

}

