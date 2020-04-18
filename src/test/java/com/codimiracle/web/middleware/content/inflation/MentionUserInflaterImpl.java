package com.codimiracle.web.middleware.content.inflation;

import com.codimiracle.web.middleware.content.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

public class MentionUserInflaterImpl implements MentionUserInflater {

    @Autowired
    private UserService userService;
    @Override
    public void inflate(MentionUserInflatable inflatingPersistentObject) {
        inflatingPersistentObject.setMentionUser(userService.findUserById(inflatingPersistentObject.getMentionUserId()));
    }
}
