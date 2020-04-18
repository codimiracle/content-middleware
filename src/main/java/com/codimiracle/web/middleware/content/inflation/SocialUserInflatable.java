package com.codimiracle.web.middleware.content.inflation;

import com.codimiracle.web.middleware.content.pojo.eo.SocialUser;

public interface SocialUserInflatable {
    String getUserId();
    void setSocialUser(SocialUser user);
    SocialUser getSocialUser();
}
