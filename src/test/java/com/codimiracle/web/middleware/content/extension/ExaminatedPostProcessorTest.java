package com.codimiracle.web.middleware.content.extension;

import com.codimiracle.web.middleware.content.pojo.po.ContentArticle;

public class ExaminatedPostProcessorTest implements ExaminatedPostProcessor {
    @Override
    public void onExaminated(ContentArticle article, String fromStatus, String toStatus) {
    }
}
