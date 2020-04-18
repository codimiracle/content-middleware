package com.codimiracle.web.middleware.content.pojo.vo;

import com.codimiracle.web.middleware.content.pojo.eo.ReferenceTarget;
import com.codimiracle.web.middleware.content.inflation.ReferenceTargetInflatable;
import lombok.Data;

import java.util.Date;

@Data
public class ContentReferenceVO implements ReferenceTargetInflatable {
    private String id;
    private String referenceTargetType;
    private String referenceTargetId;
    private ReferenceTarget referenceTarget;
    private String contentId;
    private Date referencedAt;
}
