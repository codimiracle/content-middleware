package com.codimiracle.web.middleware.content.pojo.po;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
public class ContentArticle {
    public static final String CONTENT_STATUS_DRAFT = "draft";
    public static final String CONTENT_STATUS_EXAMINING = "examining";
    public static final String CONTENT_STATUS_PUBLISHED = "published";
    public static final String CONTENT_STATUS_REJECTED = "rejected";

    @Id
    @Column(name = "content_id")
    private String contentId;

    @Column(name = "target_content_id")
    private String targetContentId;
    @Column(name = "title")
    private String title;
    @Column(name = "words")
    private Long words;
    @Column(name = "article_type")
    private String articleType;
    @Column(name = "article_source")
    private String articleSource;
    @Column(name = "status")
    private String status;

    /**
     * same as {@link Content}
     */
    @Transient
    private String id;
    @Transient
    private String type;
    @Transient
    private String ownerId;
    @Transient
    private Long comments;
    @Transient
    private Long likes;
    @Transient
    private Long dislikes;
    @Transient
    private Long reposts;

    @Transient
    private Boolean deleted;

    @Transient
    private Date createdAt;
    @Transient
    private Date updatedAt;
    @Transient
    private Date deletedAt;

    @Transient
    private List<ContentMention> mentionList;
    @Transient
    private List<ContentReference> referenceList;
    @Transient
    private List<ContentRate> rateList;
}
