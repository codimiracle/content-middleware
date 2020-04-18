package com.codimiracle.web.middleware.content.pojo.po;

import com.codimiracle.web.middleware.content.inflation.ContentTagInflatable;
import com.codimiracle.web.middleware.content.pojo.eo.Tag;
import com.codimiracle.web.mybatis.contract.annotation.LogicDelete;
import com.codimiracle.web.mybatis.contract.annotation.LogicDeletedDate;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "content_tags")
public class ContentTag implements ContentTagInflatable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column(name = "content_id")
    private String contentId;
    @Column(name = "tag_id")
    private String tagId;

    @LogicDelete
    @Column(name = "deleted")
    private Boolean deleted;

    @LogicDeletedDate
    @Column(name = "deleted_at")
    private Date deletedAt;

    @Transient
    private Tag tag;
}
