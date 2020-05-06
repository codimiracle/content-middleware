package com.codimiracle.web.middleware.content.inflation;

import com.codimiracle.web.middleware.content.service.UserService;
import lombok.Data;

@Data
public class OwnerInflaterImpl implements OwnerInflater {
    private UserService userService;

    @Override
    public void inflate(OwnerInflatable inflatingPersistentObject) {
        inflatingPersistentObject.setOwner(userService.findUserById(inflatingPersistentObject.getOwnerId()));
    }
}
