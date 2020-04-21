package com.codimiracle.web.middleware.content.extension;

import com.codimiracle.web.middleware.content.pojo.po.Content;
import com.codimiracle.web.middleware.content.pojo.po.ContentArticle;
import com.codimiracle.web.middleware.content.service.ContentService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

@Component
public class CommentExaminatedPostProcessor implements ExaminatedPostProcessor {
    @Resource
    private ContentService contentService;

    @Override
    public void onExaminated(ContentArticle article, String fromStatus, String toStatus) {
        if (Objects.equals(article.getType(), Content.CONTENT_TYPE_COMMENT)) {
            if (Objects.equals(toStatus, ContentArticle.CONTENT_STATUS_PUBLISHED)) {
                contentService.updateStatistics(ContentService.STATISTICS_FIELD_COMMENTS, article.getTargetContentId(), 1);
            }
        }
    }
}
