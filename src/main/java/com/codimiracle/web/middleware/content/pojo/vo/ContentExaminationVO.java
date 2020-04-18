package com.codimiracle.web.middleware.content.pojo.vo;

import com.codimiracle.web.middleware.content.inflation.ExaminerInflatable;
import com.codimiracle.web.middleware.content.pojo.eo.Examiner;
import lombok.Data;

import java.util.Date;

@Data
public class ContentExaminationVO implements ExaminerInflatable {
    private String id;
    private String targetContentId;
    private String fromStatus;
    private String toStatus;
    private String examinerId;
    private Examiner examiner;
    private String reason;
    private Boolean revoked;

    private Date revokedAt;
    private Date examinedAt;
}
