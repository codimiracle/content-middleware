package com.codimiracle.web.middleware.content.service.impl;

import com.codimiracle.web.middleware.content.TestWithBeans;
import com.codimiracle.web.middleware.content.pojo.po.Content;
import com.codimiracle.web.middleware.content.pojo.po.ContentLike;
import com.codimiracle.web.middleware.content.service.ContentService;
import com.codimiracle.web.middleware.content.service.LikeService;
import com.codimiracle.web.mybatis.contract.ServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TestWithBeans.class)
class LikeServiceImplTest {

    @Resource
    private LikeService likeService;
    @Resource
    private ContentService contentService;

    @Test
    void findByUserIdAndContentId() {
        Content content = new Content();
        contentService.save(content);
        likeService.like("1", content.getId());
        ContentLike contentLike = likeService.findByLikerIdAndContentId("1", content.getId());
        assertNotNull(contentLike);
        assertEquals(contentLike.getType(), ContentLike.HIT_TYPE_LIKE);
        assertEquals(contentLike.getHited(), true);
        assertNotNull(contentLike.getHitedAt());
    }

    @Test
    void like() {
        Content content = new Content();
        contentService.save(content);
        likeService.like("1", content.getId());
        ContentLike contentLike = likeService.findByLikerIdAndContentId("1", content.getId());
        assertEquals(ContentLike.HIT_TYPE_LIKE, contentLike.getType());
        assertTrue(contentLike.getHited());
        assertNotNull(contentLike.getHitedAt());
        assertThrows(ServiceException.class, () -> {
            likeService.like("1", content.getId());
        });
    }

    @Test
    void dislike() {
        Content content = new Content();
        contentService.save(content);
        likeService.dislike("1", content.getId());
        ContentLike contentLike = likeService.findByLikerIdAndContentId("1", content.getId());
        assertEquals(ContentLike.HIT_TYPE_DISLIKE, contentLike.getType());
        assertTrue(contentLike.getHited());
        assertNotNull(contentLike.getHitedAt());
        assertThrows(ServiceException.class, () -> {
            likeService.dislike("1", content.getId());
        });
    }

    @Test
    void undoLike() {
        Content content = new Content();
        contentService.save(content);
        likeService.like("1", content.getId());
        assertTrue(likeService.isLiked("1", content.getId()));
        likeService.undo("1", content.getId());
        ContentLike contentLike = likeService.findByLikerIdAndContentId("1", content.getId());
        assertFalse(contentLike.getHited());
        assertFalse(likeService.isLiked("1", content.getId()));
        assertFalse(likeService.isDisliked("1", content.getId()));
    }

    @Test
    void undoDislike() {
        Content content = new Content();
        contentService.save(content);
        likeService.dislike("1", content.getId());
        assertTrue(likeService.isDisliked("1", content.getId()));
        likeService.undo("1", content.getId());
        ContentLike contentLike = likeService.findByLikerIdAndContentId("1", content.getId());
        assertFalse(contentLike.getHited());
        assertFalse(likeService.isLiked("1", content.getId()));
        assertFalse(likeService.isDisliked("1", content.getId()));
    }

    @Test
    void isLiked() {
        Content content = new Content();
        contentService.save(content);
        likeService.like("1", content.getId());
        assertTrue(likeService.isLiked("1", content.getId()));
        assertFalse(likeService.isDisliked("1", content.getId()));
    }

    @Test
    void isDisliked() {
        Content content = new Content();
        contentService.save(content);
        likeService.dislike("1", content.getId());
        assertFalse(likeService.isLiked("1", content.getId()));
        assertTrue(likeService.isDisliked("1", content.getId()));
    }
}