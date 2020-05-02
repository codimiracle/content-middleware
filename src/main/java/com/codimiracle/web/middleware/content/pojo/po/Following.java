package com.codimiracle.web.middleware.content.pojo.po;

import com.codimiracle.web.mybatis.contract.annotation.LogicDelete;
import com.codimiracle.web.mybatis.contract.annotation.LogicDeletedDate;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
public class Following {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column(name = "following_user_id")
    private String followingUserId;
    @Column(name = "follower_id")
    private String followerId;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;
    @LogicDelete
    private Boolean deleted;
    @LogicDeletedDate
    private Date deletedAt;
}
