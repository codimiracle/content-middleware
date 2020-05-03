package com.codimiracle.web.middleware.content.service;

import com.codimiracle.web.mybatis.contract.support.vo.Service;
import com.codimiracle.web.middleware.content.pojo.po.Following;
import com.codimiracle.web.middleware.content.pojo.vo.FollowingVO;

import java.util.List;

public interface FollowService extends Service<String, Following, FollowingVO> {
    Following findByFollowerIdAndFollowingUserId(String followerId, String followingUserId);

    void follow(String followerId, String followingUserId);
    void unfollow(String followerId, String followingUserId);

    List<Following> findByFollowingUserId(String followingUserId);
    List<Following> findByFollowerId(String followerId);
}
