package com.codimiracle.web.middleware.content.inflation;

import com.codimiracle.web.middleware.content.pojo.eo.ReferenceTarget;
import com.codimiracle.web.mybatis.contract.support.vo.inflation.Inflatable;

public interface ReferenceTargetInflatable extends Inflatable {
    String getReferenceTargetType();
    String getReferenceTargetId();
    ReferenceTarget getReferenceTarget();
    void setReferenceTarget(ReferenceTarget referenceTarget);
}
