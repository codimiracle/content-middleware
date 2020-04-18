package com.codimiracle.web.middleware.content.service;

import com.codimiracle.web.middleware.content.pojo.po.User;

public class UserService {
    public User findUserById(String userId) {
        User user = new User();
        user.setId(userId);
        user.setName("Hello User" + userId);
        return user;
    }
}
