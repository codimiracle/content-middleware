package com.codimiracle.web.middleware.content.pojo.po;

import com.codimiracle.web.mybatis.contract.annotation.LogicDelete;
import com.codimiracle.web.mybatis.contract.annotation.LogicDeletedDate;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "content")
public class Content {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column(name = "type")
    private String type;
    @Column(name = "owner_id")
    private String ownerId;
    @Column(name = "comments")
    private Long comments;
    @Column(name = "likes")
    private Long likes;
    @Column(name = "dislikes")
    private Long dislikes;
    @Column(name = "reposts")
    private Long reposts;

    @LogicDelete
    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;
    @LogicDeletedDate
    @Column(name = "deleted_at")
    private Date deletedAt;
}
