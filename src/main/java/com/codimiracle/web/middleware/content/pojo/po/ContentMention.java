package com.codimiracle.web.middleware.content.pojo.po;

import com.codimiracle.web.middleware.content.pojo.eo.MentionUser;
import com.codimiracle.web.middleware.content.inflation.MentionUserInflatable;
import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "content_mention")
public class ContentMention implements MentionUserInflatable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "content_id")
    private String contentId;
    @Column(name = "mention_user_id")
    private String mentionUserId;
    @Transient
    private MentionUser mentionUser;
}
