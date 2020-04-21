package com.codimiracle.web.middleware.content.inflation;

import com.codimiracle.web.middleware.content.service.UserService;
import lombok.Data;

@Data
public class SocialUserInflaterImpl implements SocialUserInflater {
    private UserService userService;

    @Override
    public void inflate(SocialUserInflatable inflatingPersistentObject) {
        inflatingPersistentObject.setSocialUser(userService.findUserById(inflatingPersistentObject.getUserId()));
    }
}