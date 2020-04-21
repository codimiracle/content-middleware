package com.codimiracle.web.middleware.content.service.impl;

import com.codimiracle.web.basic.contract.Page;
import com.codimiracle.web.basic.contract.PageSlice;
import com.codimiracle.web.middleware.content.TestWithBeans;
import com.codimiracle.web.middleware.content.pojo.po.Comment;
import com.codimiracle.web.middleware.content.pojo.po.Content;
import com.codimiracle.web.middleware.content.pojo.vo.CommentVO;
import com.codimiracle.web.middleware.content.service.CommentService;
import com.codimiracle.web.middleware.content.service.ContentService;
import com.codimiracle.web.middleware.content.service.ExaminationService;
import com.codimiracle.web.mybatis.contract.ServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TestWithBeans.class)
class CommentServiceImplTest {

    @Resource
    private CommentService commentService;
    @Resource
    private ContentService contentService;
    @Resource
    private ExaminationService examinationService;

    @Test
    void save() {
        Comment comment = new Comment();
        comment.setArticleType("plaintext");
        comment.setArticleSource("Hello world");
        assertThrows(ServiceException.class, () -> {
            commentService.save(comment);
        });
        comment.setTargetContentId("10000");
        commentService.save(comment);
        Comment result = commentService.findById(comment.getContentId());
        assertEquals(comment.getArticleSource(), result.getArticleSource());
        assertEquals(comment.getArticleType(), result.getArticleType());
        assertEquals(comment.getTargetContentId(), result.getTargetContentId());
    }

    @Test
    void update() {
        Comment comment = new Comment();
        comment.setArticleType("plaintext");
        comment.setArticleSource("Hello world");
        comment.setTargetContentId("30000");
        commentService.save(comment);
        comment.setArticleSource("Updated Value");
        commentService.update(comment);
        Comment result = commentService.findById(comment.getContentId());

        assertEquals(comment.getArticleSource(), result.getArticleSource());
        assertEquals(comment.getArticleType(), result.getArticleType());
        assertEquals(comment.getTargetContentId(), result.getTargetContentId());
    }

    @Test
    void testUpdateStatistics() {
        Content content = new Content();
        content.setType("content-type");
        contentService.save(content);

        Comment comment = new Comment();
        comment.setArticleType("plaintext");
        comment.setArticleSource("Hello world");
        comment.setTargetContentId(content.getId());
        commentService.save(comment);

        examinationService.accept(comment.getContentId(), "1", "it is ok.");
        Content targetContent = contentService.findById(content.getId());
        assertEquals(targetContent.getComments(), 1);
    }

    @Test
    void findAllIntegrally() {
        for (int i = 0; i < 10; i++) {
            Comment comment = new Comment();
            comment.setArticleType("plaintext");
            comment.setArticleSource("Hello world" + i);
            comment.setTargetContentId("30000");
            commentService.save(comment);
        }
        PageSlice<CommentVO> slice = commentService.findAllIntegrally(null, null, new Page());
        assertTrue(slice.getTotal() >= 10);
    }

    @Test
    void findByIdIntegrally() {
        Comment comment = new Comment();
        comment.setArticleType("plaintext");
        comment.setArticleSource("Hello world");
        comment.setTargetContentId("30000");
        commentService.save(comment);
        CommentVO commentVO = commentService.findByIdIntegrally(comment.getContentId());
        assertNotNull(commentVO);
    }
}