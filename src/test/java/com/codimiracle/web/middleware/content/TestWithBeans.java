package com.codimiracle.web.middleware.content;

import com.codimiracle.web.middleware.content.inflation.*;
import com.codimiracle.web.middleware.content.pojo.eo.LoggedUser;
import com.codimiracle.web.middleware.content.pojo.eo.LoggedUserHolder;
import com.codimiracle.web.middleware.content.service.UserService;
import com.codimiracle.web.mybatis.contract.Paginator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(basePackages = "com.codimiracle.web.middleware.content")
@ComponentScan(excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = TestWithoutBeans.class)})
public class TestWithBeans {

    @Bean
    UserService getUserService() {
        return new UserService();
    }
    @Bean
    Paginator getPaginator() {
        return new Paginator();
    }

    @Bean
    LoggedUser getLoggedUser() {
        return new LoggedUserHolder();
    }

    @Bean
    OwnerInflater ownerInflater(UserService userService) {
        OwnerInflaterImpl ownerInflater = new OwnerInflaterImpl();
        ownerInflater.setUserService(userService);
        return ownerInflater;
    }
    @Bean
    FollowerInflater followerInflater(UserService userService) {
        FollowerInflaterImpl userInflater = new FollowerInflaterImpl();
        userInflater.setUserService(userService);
        return userInflater;
    }
    @Bean
    FollowingUserInflater followingUserInflater() {
        return new FollowingUserInflaterImpl();
    }

    @Bean
    TagInflater getTagInFlater() {
        return new TagInflaterImpl();
    }
}
