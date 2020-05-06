package com.codimiracle.web.middleware.content.inflation;

import com.codimiracle.web.middleware.content.pojo.eo.TargetContent;
import com.codimiracle.web.mybatis.contract.support.vo.inflation.Inflatable;

public interface TargetContentInflatable extends Inflatable {
    String getTargetContentId();
    TargetContent getTargetContent();
    void setTargetContent(TargetContent targetContent);
}
