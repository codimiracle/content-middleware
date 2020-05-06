package com.codimiracle.web.middleware.content.inflation;

import com.codimiracle.web.middleware.content.service.UserService;
import lombok.Data;

@Data
public class FollowerInflaterImpl implements FollowerInflater {
    private UserService userService;

    @Override
    public void inflate(FollowerInflatable inflatingPersistentObject) {
        inflatingPersistentObject.setFollower(userService.findUserById(inflatingPersistentObject.getFollowerId()));
    }
}
