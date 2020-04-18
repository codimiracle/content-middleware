package com.codimiracle.web.middleware.content.service.impl;

import com.codimiracle.web.middleware.content.TestWithBeans;
import com.codimiracle.web.middleware.content.pojo.po.ContentArticle;
import com.codimiracle.web.middleware.content.pojo.po.ContentExamination;
import com.codimiracle.web.middleware.content.pojo.vo.ContentExaminationVO;
import com.codimiracle.web.middleware.content.service.ArticleService;
import com.codimiracle.web.middleware.content.service.ContentService;
import com.codimiracle.web.middleware.content.service.ExaminationService;
import com.codimiracle.web.mybatis.contract.ServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TestWithBeans.class)
class ExaminationServiceImplTest {

    @Resource
    private ExaminationService examinationService;
    @Resource
    private ContentService contentService;
    @Resource
    private ArticleService articleService;

    @Test
    void findLastByContentIdIntegrallyNotExists() {
        assertThrows(ServiceException.class, () -> {
            examinationService.accept("11111", "1", "Test accept");
        });
    }

    @Test
    void findLastByContentIdIntegrally() {
        ContentArticle article = new ContentArticle();
        article.setArticleType("html");
        article.setArticleSource("<p>Hello world</p>");
        articleService.save(article);
        examinationService.accept(article.getId(), "1", "Testing accept");
        ContentExaminationVO lastExaminationVO = examinationService.findLastByContentIdIntegrally(article.getId());
        examinationService.accept(article.getId(), "1", "Testing accept");
        ContentExaminationVO lastExaminationVOAgain = examinationService.findLastByContentIdIntegrally(article.getId());
        assertNotEquals(lastExaminationVO.getId(), lastExaminationVOAgain.getId());
        assertTrue(lastExaminationVOAgain.getExaminedAt().getTime() > lastExaminationVO.getExaminedAt().getTime());
        assertNotNull(lastExaminationVO);
    }

    @Test
    void revoke() {
        ContentArticle article = new ContentArticle();
        article.setStatus(ContentArticle.CONTENT_STATUS_DRAFT);
        article.setArticleType("html");
        article.setArticleSource("<p>Hello world</p>");
        articleService.save(article);
        examinationService.accept(article.getId(), "1", "Testing accept");
        ContentArticle resultArticle1 = articleService.findById(article.getId());
        assertEquals(ContentArticle.CONTENT_STATUS_PUBLISHED, resultArticle1.getStatus());
        ContentExaminationVO examinationVO = examinationService.findLastByContentIdIntegrally(article.getId());
        assertNotNull(examinationVO);
        examinationService.revoke(examinationVO.getId());
        ContentExamination result = examinationService.findById(examinationVO.getId());
        ContentArticle resultArticle2 = articleService.findById(article.getId());
        assertEquals(ContentArticle.CONTENT_STATUS_DRAFT, resultArticle2.getStatus());
        assertTrue(result.getRevoked());
        assertNotNull(result.getRevokedAt());
    }

    @Test
    void accept() {
        ContentArticle article = new ContentArticle();
        article.setArticleType("html");
        article.setArticleSource("<p>Hello world</p>");
        articleService.save(article);
        examinationService.accept(article.getId(), "1", "Testing accept");
        ContentExaminationVO examinationVO = examinationService.findLastByContentIdIntegrally(article.getContentId());
        assertEquals(ContentArticle.CONTENT_STATUS_PUBLISHED, examinationVO.getToStatus());
        assertEquals("Testing accept", examinationVO.getReason());
    }

    @Test
    void reject() {
        ContentArticle article = new ContentArticle();
        article.setArticleType("html");
        article.setArticleSource("<p>Hello world</p>");
        articleService.save(article);
        examinationService.reject(article.getId(), "1", "Testing reject");
        ContentExaminationVO examinationVO = examinationService.findLastByContentIdIntegrally(article.getContentId());
        assertEquals(ContentArticle.CONTENT_STATUS_REJECTED, examinationVO.getToStatus());
        assertEquals("Testing reject", examinationVO.getReason());
    }
}