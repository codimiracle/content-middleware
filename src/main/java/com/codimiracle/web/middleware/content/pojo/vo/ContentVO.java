package com.codimiracle.web.middleware.content.pojo.vo;

import com.codimiracle.web.middleware.content.inflation.OwnerInflatable;
import com.codimiracle.web.middleware.content.pojo.eo.ReferenceTarget;
import com.codimiracle.web.middleware.content.pojo.eo.SocialUser;
import com.codimiracle.web.middleware.content.pojo.eo.Tag;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ContentVO implements ReferenceTarget, OwnerInflatable {
    private String id;
    private String contentId;
    private String type;
    private String ownerId;
    private Long comments;
    private Long likes;
    private Long dislikes;
    private Long reposts;

    private Boolean liked;
    private Boolean disliked;


    private SocialUser owner;

    private ContentRateVO rate;
    private List<ContentRateVO> rateList;
    private List<Tag> tagList;

    private Date createdAt;
    private Date updatedAt;

    @Override
    public String getReferenceTargetId() {
        return this.getId();
    }

    @Override
    public void setReferenceTargetId(String referenceTargetId) {
        this.setId(referenceTargetId);
    }
}
