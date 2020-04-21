package com.codimiracle.web.middleware.content.pojo.eo;

public interface LoggedUser {
    String getLoggedUserId();

    default boolean isLogged() {
        return getLoggedUserId() != null;
    }

}
