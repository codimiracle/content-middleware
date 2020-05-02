package com.codimiracle.web.middleware.content.service.impl;

import com.codimiracle.web.basic.contract.Page;
import com.codimiracle.web.basic.contract.PageSlice;
import com.codimiracle.web.middleware.content.TestWithBeans;
import com.codimiracle.web.middleware.content.pojo.po.Following;
import com.codimiracle.web.middleware.content.pojo.vo.FollowingVO;
import com.codimiracle.web.middleware.content.service.FollowService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TestWithBeans.class)
class FollowServiceImplTest {
    @Resource
    private FollowService followService;

    @Test
    void follow() {
        final String followerId = "6010";
        final String followingUserId = "4033";
        followService.follow(followerId, followingUserId);
        Following following = followService.findByFollowerIdAndFollowingUserId(followerId, followingUserId);
        assertFalse(following.getDeleted());
        assertNotNull(following.getCreatedAt());
        assertNotNull(following.getUpdatedAt());
        assertEquals(followerId, following.getFollowerId());
        assertEquals(followingUserId, following.getFollowingUserId());
    }

    @Test
    void unfollow() throws InterruptedException {
        final String followerId = "6011";
        final String followingUserId = "4034";
        followService.follow(followerId, followingUserId);
        Thread.sleep(3000);
        followService.unfollow(followerId, followingUserId);
        Following following = followService.findByFollowerIdAndFollowingUserId(followerId, followingUserId);
        assertTrue(following.getDeleted());
        assertNotNull(following.getDeletedAt());
    }

    @Test
    void findByIdIntegrall() {
        final String followerId = "1112";
        final String followingUserId = "2223";
        followService.follow(followerId, followingUserId);
        Following following = followService.findByFollowerIdAndFollowingUserId(followerId, followingUserId);
        FollowingVO result = followService.findByIdIntegrally(following.getId());
        assertEquals(followerId, result.getFollowerId());
        assertEquals(followingUserId, result.getFollowingUserId());
        assertNotNull(result.getFollowingUser());
        assertNotNull(result.getFollower());
    }

    @Test
    void mutate() {
        final String followId = "111";
        final String followingUserId = "222";
        followService.follow(followId, followingUserId);
        Following result = followService.findByFollowerIdAndFollowingUserId(followId, followingUserId);
        assertNotNull(result.getId());
        assertEquals(followId, result.getFollowerId());
        assertEquals(followingUserId, result.getFollowingUserId());
    }

    @Test
    void findAllIntegrally() {
        for (int i = 0; i < 10; i++) {
            followService.follow("1020", String.format("500%s", i));
        }
        PageSlice<FollowingVO> slice = followService.findAllIntegrally(null, null, new Page(1, 1000));
        assertTrue(slice.getTotal() >= 10);
        FollowingVO last = slice.getList().get(slice.getList().size() - 1);
        assertNotNull(last);
        assertEquals("1020", last.getFollowerId());
        assertEquals("5009", last.getFollowingUserId());
    }

    @Test
    void findByFollowerId() {
        final String followerId = "1200";
        for (int i = 0; i < 10; i++) {
            followService.follow(followerId, String.format("500%s", i));
        }
        List<Following> followings = followService.findByFollowerId(followerId);
        Following first = followings.get(0);
        Following last = followings.get(followings.size() - 1);
        assertEquals(followerId, first.getFollowerId());
        assertEquals(followerId, last.getFollowerId());
        assertEquals(10, followings.size());
    }

    @Test
    void findByFollowingUserId() {
        final String followingUserId = "3022";
        for (int i = 0; i < 10; i++) {
            followService.follow(String.format("780%s", i), followingUserId);
        }
        List<Following> followings = followService.findByFollowingUserId(followingUserId);
        Following first = followings.get(0);
        Following last = followings.get(followings.size() - 1);
        assertEquals(followingUserId, first.getFollowingUserId());
        assertEquals(followingUserId, last.getFollowingUserId());
        assertEquals(10, followings.size());
    }
}