package com.codimiracle.web.middleware.content.pojo.po;

import com.codimiracle.web.middleware.content.pojo.eo.MentionUser;
import com.codimiracle.web.middleware.content.pojo.eo.SocialUser;
import lombok.Data;

@Data
public class User implements MentionUser, SocialUser {
    private String id;
    private String name;

    @Override
    public void setMentionUserId(String userId) {
        this.setId(userId);
    }

    @Override
    public String getMentionUserId() {
        return this.getId();
    }

    @Override
    public String getUserId() {
        return this.getId();
    }
}
