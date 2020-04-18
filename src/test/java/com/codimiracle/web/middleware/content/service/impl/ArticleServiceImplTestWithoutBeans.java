package com.codimiracle.web.middleware.content.service.impl;

import com.codimiracle.web.middleware.content.TestWithoutBeans;
import com.codimiracle.web.middleware.content.mapper.ArticleMapper;
import com.codimiracle.web.middleware.content.pojo.po.ContentArticle;
import com.codimiracle.web.middleware.content.pojo.vo.ContentArticleVO;
import com.codimiracle.web.middleware.content.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(classes = TestWithoutBeans.class)
class ArticleServiceImplTestWithoutBeans {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleMapper articleMapper;

    @Test
    void testWithoutLoggedUser() {
        ContentArticle contentArticle = new ContentArticle();
        contentArticle.setOwnerId("1");
        contentArticle.setTitle("Hello world");
        contentArticle.setArticleSource("<p>Hello world</p>");
        contentArticle.setArticleType("html");
        articleService.save(contentArticle);
        ContentArticleVO articleVO = articleService.findByIdIntegrally(contentArticle.getContentId());
        assertNull(articleVO.getOwner());
        assertNull(articleVO.getLiked());
        assertNull(articleVO.getDisliked());
    }
}