package com.codimiracle.web.middleware.content.service;

import com.codimiracle.web.mybatis.contract.support.vo.Service;
import com.codimiracle.web.middleware.content.pojo.po.ContentArticle;
import com.codimiracle.web.middleware.content.pojo.vo.ContentArticleVO;
import com.codimiracle.web.basic.contract.PageSlice;

import java.util.List;

/**
 * In cms, article is represent a long text and will be show up for user.
 */
public interface ArticleService extends Service<String, ContentArticle, ContentArticleVO> {
    ContentArticleVO inflate(ContentArticleVO contentArticleVO);

    PageSlice<ContentArticleVO> inflate(PageSlice<ContentArticleVO> slice);

    List<ContentArticle> findByTargetContentId(String targetContentId);

    List<ContentArticleVO> findByTargetContentIdIntegrally(String targetContentId);
}
