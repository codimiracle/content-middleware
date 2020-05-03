package com.codimiracle.web.middleware.content.service;

import com.codimiracle.web.mybatis.contract.support.vo.Service;
import com.codimiracle.web.middleware.content.pojo.po.ContentArticle;
import com.codimiracle.web.middleware.content.pojo.vo.ContentArticleVO;
import com.codimiracle.web.basic.contract.PageSlice;

import java.util.List;

public interface ArticleService extends Service<String, ContentArticle, ContentArticleVO> {
    List<ContentArticle> findByTargetContentId(String targetContentId);

    List<ContentArticleVO> findByTargetContentIdIntegrally(String targetContentId);
}
