package com.codimiracle.web.middleware.content.pojo.po;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "content_likes")
public class ContentLike {
    public static final String HIT_TYPE_LIKE = "like";
    public static final String HIT_TYPE_DISLIKE = "dislike";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "liker_id")
    private String likerId;
    @Column(name = "content_id")
    private String contentId;
    @Column(name = "type")
    private String type;
    @Column(name = "hited")
    private Boolean hited;
    @Column(name = "hited_at")
    private Date hitedAt;
}
