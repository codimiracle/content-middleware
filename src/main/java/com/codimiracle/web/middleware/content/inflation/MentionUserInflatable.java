package com.codimiracle.web.middleware.content.inflation;

import com.codimiracle.web.middleware.content.pojo.eo.MentionUser;

public interface MentionUserInflatable {
    String getMentionUserId();
    MentionUser getMentionUser();
    void setMentionUser(MentionUser mentionUser);
}
