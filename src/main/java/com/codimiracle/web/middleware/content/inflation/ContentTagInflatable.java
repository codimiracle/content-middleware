package com.codimiracle.web.middleware.content.inflation;

import com.codimiracle.web.middleware.content.pojo.eo.Tag;

public interface ContentTagInflatable {
    String getTagId();
    Tag getTag();
    void setTag(Tag tag);
}
