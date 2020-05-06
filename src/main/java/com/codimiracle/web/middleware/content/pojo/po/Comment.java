package com.codimiracle.web.middleware.content.pojo.po;

import javax.persistence.Table;
import java.util.Objects;

public class Comment extends ContentArticle {
    public Comment() {
        setType(Content.CONTENT_TYPE_COMMENT);
    }

    @Override
    public void setType(String type) {
        if (Objects.equals(type, Content.CONTENT_TYPE_COMMENT)) {
            super.setType(type);
            return;
        }
        throw new IllegalArgumentException("Content.CONTENT_TYPE_COMMENT only can be set ");
    }
}
