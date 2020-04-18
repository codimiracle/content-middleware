package com.codimiracle.web.middleware.content.inflation;

import com.codimiracle.web.middleware.content.pojo.eo.ReferenceTarget;

public interface ReferenceTargetInflatable {
    String getReferenceTargetType();
    String getReferenceTargetId();
    ReferenceTarget getReferenceTarget();
    void setReferenceTarget(ReferenceTarget referenceTarget);
}
