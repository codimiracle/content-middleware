package com.codimiracle.web.middleware.content.pojo.po;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
public class ContentReference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column(name = "content_id")
    private String contentId;
    @Column(name = "reference_target_id")
    private String referenceTargetId;
    @Column(name = "reference_target_type")
    private String referenceTargetType;

    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "deleted_at")
    private Date deletedAt;
    @Column(name = "referenced_at")
    private Date referencedAt;
}
