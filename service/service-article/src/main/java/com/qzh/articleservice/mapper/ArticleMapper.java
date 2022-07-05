package com.qzh.articleservice.mapper;

import com.qzh.articleservice.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-09-13
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

}
