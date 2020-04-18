package com.codimiracle.web.middleware.content.service;

import com.codimiracle.web.middleware.content.pojo.eo.Tag;
import com.codimiracle.web.middleware.content.pojo.po.ContentTag;
import com.codimiracle.web.mybatis.contract.Service;

import java.util.List;

public interface ContentTagsService extends Service<String, ContentTag> {
    List<ContentTag> findByContentId(String contentId);
    List<Tag> findTagByContentId(String contentId);
}
