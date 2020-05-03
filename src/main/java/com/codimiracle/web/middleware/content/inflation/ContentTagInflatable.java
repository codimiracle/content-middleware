package com.codimiracle.web.middleware.content.inflation;

import com.codimiracle.web.middleware.content.pojo.eo.Tag;
import com.codimiracle.web.mybatis.contract.support.vo.inflation.Inflatable;

public interface ContentTagInflatable extends Inflatable {
    String getTagId();
    Tag getTag();
    void setTag(Tag tag);
}
