package com.codimiracle.web.middleware.content.pojo.vo;

import com.codimiracle.web.middleware.content.pojo.eo.ArticleSource;
import com.codimiracle.web.middleware.content.pojo.eo.MentionUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ContentArticleVO extends ContentVO {
    private String contentId;
    private String targetContentId;
    private String title;
    private Long words;
    private ArticleSource article;
    private String status;
    private List<MentionUser> mentionList;
    private List<ContentReferenceVO> referenceList;
    private ContentExaminationVO examination;
}
