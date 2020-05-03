package com.codimiracle.web.middleware.content.inflation;

import com.codimiracle.web.middleware.content.pojo.eo.MentionUser;
import com.codimiracle.web.mybatis.contract.support.vo.inflation.Inflatable;

public interface MentionUserInflatable extends Inflatable {
    String getMentionUserId();
    MentionUser getMentionUser();
    void setMentionUser(MentionUser mentionUser);
}
