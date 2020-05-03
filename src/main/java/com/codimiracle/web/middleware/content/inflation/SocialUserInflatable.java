package com.codimiracle.web.middleware.content.inflation;

import com.codimiracle.web.middleware.content.pojo.eo.SocialUser;
import com.codimiracle.web.mybatis.contract.support.vo.inflation.Inflatable;

public interface SocialUserInflatable extends Inflatable {
    String getUserId();
    void setSocialUser(SocialUser user);
    SocialUser getSocialUser();
}
