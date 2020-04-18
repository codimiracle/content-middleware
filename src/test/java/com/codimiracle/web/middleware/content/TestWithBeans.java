package com.codimiracle.web.middleware.content;

import com.codimiracle.web.middleware.content.inflation.TagInflater;
import com.codimiracle.web.middleware.content.inflation.TagInflaterImpl;
import com.codimiracle.web.middleware.content.inflation.UserInflater;
import com.codimiracle.web.middleware.content.inflation.UserInflaterImpl;
import com.codimiracle.web.middleware.content.pojo.eo.LoggedUser;
import com.codimiracle.web.middleware.content.pojo.eo.LoggedUserHolder;
import com.codimiracle.web.middleware.content.service.UserService;
import com.codimiracle.web.mybatis.contract.Paginator;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ContextConfiguration;

@SpringBootApplication
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
    UserInflater getUserInflater(UserService userService) {
        UserInflaterImpl userInflater = new UserInflaterImpl();
        userInflater.setUserService(userService);
        return userInflater;
    }

    @Bean
    TagInflater getTagInFlater() {
        return new TagInflaterImpl();
    }
}
