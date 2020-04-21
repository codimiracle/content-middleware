package com.codimiracle.web.middleware.content.extension;

import com.codimiracle.web.middleware.content.pojo.po.ContentArticle;

public interface ExaminatedPostProcessor {
    void onExaminated(ContentArticle article, String fromStatus, String toStatus);
}
