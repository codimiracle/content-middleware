package com.codimiracle.web.middleware.content.pojo.po;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "content_examination")
public class ContentExamination {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column(name = "target_content_id")
    private String targetContentId;
    @Column(name = "from_status")
    private String fromStatus;
    @Column(name = "to_status")
    private String toStatus;
    @Column(name = "examiner_id")
    private String examinerId;
    @Column(name = "reason")
    private String reason;
    @Column(name = "revoked")
    private Boolean revoked;

    @Column(name = "revoked_at")
    private Date revokedAt;
    @Column(name = "examined_at")
    private Date examinedAt;
}
