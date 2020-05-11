package com.codimiracle.web.middleware.content.inflation;

import com.codimiracle.web.middleware.content.pojo.eo.SimpleTag;

public class ContentTagInflaterImpl implements ContentTagInflater {
    @Override
    public void inflate(ContentTagInflatable inflatingPersistentObject) {
        inflatingPersistentObject.setTag(new SimpleTag(inflatingPersistentObject.getTagId(), "inflated result"));
    }
}
