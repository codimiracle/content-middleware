package com.codimiracle.web.middleware.content.inflation;

import com.codimiracle.web.middleware.content.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class FollowingUserInflaterImpl implements FollowingUserInflater {
    @Autowired
    private UserService userService;

    @Override
    public void inflate(FollowingUserInflatable inflatingPersistentObject) {
        inflatingPersistentObject.setFollowingUser(userService.findUserById(inflatingPersistentObject.getFollowingUserId()));
    }
}
