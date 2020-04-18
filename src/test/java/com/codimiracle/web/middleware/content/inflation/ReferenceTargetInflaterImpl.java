package com.codimiracle.web.middleware.content.inflation;

import com.codimiracle.web.middleware.content.inflation.ReferenceTargetInflater;
import com.codimiracle.web.middleware.content.inflation.ReferenceTargetInflatable;
import com.codimiracle.web.middleware.content.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class ReferenceTargetInflaterImpl implements ReferenceTargetInflater {
    @Autowired
    private ContentService contentService;
    @Override
    public void inflate(ReferenceTargetInflatable inflatingPersistentObject) {
        inflatingPersistentObject.setReferenceTarget(contentService.findByIdIntegrally(inflatingPersistentObject.getReferenceTargetId()));
    }
}
