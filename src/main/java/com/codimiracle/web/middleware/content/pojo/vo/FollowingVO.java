package com.codimiracle.web.middleware.content.pojo.vo;

import com.codimiracle.web.middleware.content.inflation.FollowerInflatable;
import com.codimiracle.web.middleware.content.inflation.FollowingUserInflatable;
import com.codimiracle.web.middleware.content.pojo.eo.SocialUser;
import lombok.Data;

import java.util.Date;

@Data
public class FollowingVO implements FollowerInflatable, FollowingUserInflatable {
    private String id;
    private String followingUserId;
    private SocialUser followingUser;
    private String followerId;
    private SocialUser follower;
    private Date createdAt;
    private Date updatedAt;

    @Override
    public SocialUser getFollowingUser() {
        return this.followingUser;
    }

    @Override
    public void setFollowingUser(SocialUser user) {
        this.followingUser = user;
    }
}
