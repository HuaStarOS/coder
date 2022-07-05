package com.qzh.articleservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qzh.articleservice.entity.Article;
import com.qzh.articleservice.entity.FootVo;
import com.qzh.articleservice.entity.Footprint;
import com.qzh.articleservice.mapper.FootprintMapper;
import com.qzh.articleservice.service.ArticleService;
import com.qzh.articleservice.service.FootprintService;
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
public class FootprintServiceImpl extends ServiceImpl<FootprintMapper, Footprint> implements FootprintService {

    @Autowired
    private ArticleService articleService;

    @Override
    public List<FootVo> getAll() {
        List<Footprint> gmt_create = this.baseMapper.selectList(new QueryWrapper<Footprint>().orderByAsc("gmt_create"));
        ArrayList<FootVo> footVos = new ArrayList<>();
        for (Footprint a:gmt_create) {
            Article byId = articleService.getById(a.getArticleId());
            FootVo footVo = new FootVo();
            footVo.setGmtCreate(a.getGmtCreate());
            footVo.setIscollect(a.getIscollect());
            footVo.setAuthor(byId.getAuthor());
            footVo.setArticleTitle(byId.getArticleTitle());
            footVos.add(footVo);
        }
        return footVos;
    }
}
