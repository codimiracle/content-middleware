package com.codimiracle.web.middleware.content.pojo.eo;

public class LoggedUserHolder implements LoggedUser {
    @Override
    public String getLoggedUserId() {
        return "1";
    }
}
