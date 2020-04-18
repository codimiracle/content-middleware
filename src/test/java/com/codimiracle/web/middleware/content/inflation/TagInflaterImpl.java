package com.codimiracle.web.middleware.content.inflation;

import com.codimiracle.web.middleware.content.inflation.TagInflater;
import com.codimiracle.web.middleware.content.inflation.ContentTagInflatable;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.stereotype.Component;

public class TagInflaterImpl implements TagInflater {
    @Override
    public void inflate(ContentTagInflatable inflatingPersistentObject) {
    }
}
