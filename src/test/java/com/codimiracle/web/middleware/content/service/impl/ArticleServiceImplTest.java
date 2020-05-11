package com.codimiracle.web.middleware.content.service.impl;

import com.codimiracle.web.basic.contract.Filter;
import com.codimiracle.web.basic.contract.Page;
import com.codimiracle.web.basic.contract.PageSlice;
import com.codimiracle.web.middleware.content.TestWithBeans;
import com.codimiracle.web.middleware.content.mapper.ArticleMapper;
import com.codimiracle.web.middleware.content.pojo.po.Content;
import com.codimiracle.web.middleware.content.pojo.po.ContentArticle;
import com.codimiracle.web.middleware.content.pojo.vo.ContentArticleVO;
import com.codimiracle.web.middleware.content.service.ArticleService;
import com.codimiracle.web.mybatis.contract.Paginator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TestWithBeans.class)
class ArticleServiceImplTest {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private Paginator paginator;

    @Test
    void testLogicDelete() {
        ContentArticle article = new ContentArticle();
        article.setArticleType("html");
        article.setTitle("Hello world");
        article.setStatus("draft");
        article.setArticleSource("<p>Hello world</p>");
        articleService.save(article);
        articleService.deleteByIdLogically(article.getContentId());
        ContentArticle result = articleService.findById(article.getContentId());
        assertTrue(result.getDeleted());
        assertNotNull(result.getDeletedAt());
    }

    @Test
    void testSave() {
        ContentArticle article = new ContentArticle();
        article.setArticleType("html");
        article.setTitle("Hello world");
        article.setStatus("draft");
        article.setArticleSource("<p>Hello world</p>");
        articleService.save(article);
        ContentArticle result = articleMapper.selectById(article.getId());
        article.setCreatedAt(null);
        article.setUpdatedAt(null);
        article.setId(null);
        assertEquals(article, result);
    }

    @Test
    void testSaveList() {
        String[] args = new String[]{"<p>Hello world 1</p>", "<p>Hello world 2</p>", "<p>Hello world 3</p>"};
        List<ContentArticle> articleList = Arrays.stream(args).map((e) -> {
            ContentArticle article = new ContentArticle();
            article.setArticleType("html");
            article.setArticleSource(e);
            return article;
        }).collect(Collectors.toList());
        articleService.save(articleList);
        List<ContentArticle> results = articleMapper.selectByIds(articleList.stream().map(ContentArticle::getId).collect(Collectors.joining(",")));
        for (int i = 0; i < results.size(); i++) {
            ContentArticle article = articleList.get(0);
            ContentArticle result = results.get(0);
            article.setId(null);
            assertEquals(article, result);
        }
    }

    @Test
    void testUpdate() {
        ContentArticle contentArticle = new ContentArticle();
        contentArticle.setArticleType("html");
        contentArticle.setArticleSource("<p>Hello world testing update</p>");
        articleService.save(contentArticle);
        contentArticle.setArticleSource("hello");
        contentArticle.setStatus("draft");
        articleService.update(contentArticle);
        ContentArticle result = articleService.findById(contentArticle.getId());
        assertEquals(contentArticle.getStatus(), result.getStatus());
        assertEquals(contentArticle.getArticleSource(), result.getArticleSource());
    }

    @Test
    void testLoggedUser() {
        ContentArticle contentArticle = new ContentArticle();
        contentArticle.setTitle("Hello world");
        contentArticle.setArticleSource("<p>Hello world</p>");
        contentArticle.setArticleType("html");
        articleService.save(contentArticle);
        ContentArticleVO articleVO = articleService.findByIdIntegrally(contentArticle.getContentId());
        assertNotNull(articleVO.getOwner());
        assertFalse(articleVO.getLiked());
        assertFalse(articleVO.getDisliked());
    }

    @Test
    void testFindByIdIntegrally() {
        ContentArticle article = new ContentArticle();
        article.setType("test-content");
        article.setArticleType("html");
        article.setArticleSource("<p>test</p>");
        articleService.save(article);
        ContentArticleVO articleVO = articleService.findByIdIntegrally(article.getContentId());
        assertNotNull(articleVO);
        assertEquals(article.getType(), articleVO.getType());
        assertEquals(article.getArticleType(), articleVO.getArticle().getType());
        assertEquals(article.getArticleSource(), articleVO.getArticle().getSource());
    }

    @Test
    void testPaginatorIsManaged() {
        assertNotNull(paginator, "pagination should be managed but not found.");
    }

    @Test
    void testFindAllIntegrally() {
        for (int i = 0; i < 20; i++) {
            String source = "<p>Hello world" + i + "</p>";
            ContentArticle article = new ContentArticle();
            article.setTitle("Hello world");
            article.setWords(0L);
            if (i % 2 == 0) {
                article.setType(Content.CONTENT_TYPE_COMMENT);
            } else {
                article.setType("other");
            }
            article.setArticleType("html");
            article.setArticleSource(source);
            articleService.save(article);
        }
        Filter filter = new Filter();
        filter.put("type", new String[] {Content.CONTENT_TYPE_COMMENT});
        PageSlice<ContentArticleVO> slice = articleService.findAllIntegrally(filter, null, new Page());
        assertTrue(slice.getTotal() >= 10);
        ContentArticleVO result = slice.getList().get(slice.getList().size() - 1);
        assertNotNull(result.getId());
        assertNotNull(result.getContentId());
        assertNotNull(result.getMentionList());
        assertNotNull(result.getReferenceList());
        assertEquals(Content.CONTENT_TYPE_COMMENT, result.getType());
        assertEquals(Content.CONTENT_TYPE_COMMENT, slice.getList().get(slice.getList().size() - 2).getType());
        assertNotNull(result.getTitle());
        assertNotNull(result.getLikes());
        assertNotNull(result.getDislikes());
        assertNotNull(result.getComments());
    }

    @Test
    void findByTargetContentId() {
        for (int i = 0; i < 30; i++) {
            ContentArticle article = new ContentArticle();
            article.setTargetContentId("4445454");
            articleService.save(article);
        }
        List<ContentArticle> byTargetContentId = articleService.findByTargetContentId("4445454");
        assertEquals(30, byTargetContentId.size());
    }

    @Test
    void findByTargetContentIdIntegrally() {
        for (int i = 0; i < 30; i++) {
            ContentArticle article = new ContentArticle();
            article.setTargetContentId("44454547");
            articleService.save(article);
        }
        List<ContentArticleVO> byTargetContentId = articleService.findByTargetContentIdIntegrally("44454547");
        assertEquals(30, byTargetContentId.size());

    }
}