package com.codimiracle.web.middleware.content.inflation;

import com.codimiracle.web.middleware.content.pojo.eo.SocialUser;
import com.codimiracle.web.mybatis.contract.support.vo.inflation.Inflatable;

public interface FollowerInflatable extends Inflatable {
    String getFollowerId();
    void setFollower(SocialUser user);
    SocialUser getFollower();
}
