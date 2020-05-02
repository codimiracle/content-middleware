package com.codimiracle.web.middleware.content.inflation;

import com.codimiracle.web.middleware.content.pojo.eo.SocialUser;

public interface FollowingUserInflatable {
    String getFollowingUserId();
    void setFollowingUser(SocialUser user);
    SocialUser getFollowingUser();
}
