package com.codimiracle.web.middleware.content.mapper;

import com.codimiracle.web.middleware.content.contract.Mapper;
import com.codimiracle.web.middleware.content.pojo.po.Content;
import com.codimiracle.web.middleware.content.pojo.po.ContentArticle;
import com.codimiracle.web.middleware.content.pojo.vo.ContentArticleVO;
import com.codimiracle.web.middleware.content.pojo.vo.ContentVO;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface ArticleMapper extends Mapper<ContentArticle, ContentArticleVO> {
    List<ContentArticleVO> selectByTargetContentId(String targetContentId);
}
