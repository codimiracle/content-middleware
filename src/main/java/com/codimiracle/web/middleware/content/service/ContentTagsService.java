package com.codimiracle.web.middleware.content.service;

import com.codimiracle.web.middleware.content.pojo.eo.Tag;
import com.codimiracle.web.middleware.content.pojo.po.ContentTag;
import com.codimiracle.web.mybatis.contract.Service;

import java.util.List;

public interface ContentTagsService extends Service<String, ContentTag> {
    void updateAttachingTags(String contentId, List<Tag> tagList);

    List<ContentTag> findByContentIdWithDeleted(String contentId);
    List<ContentTag> findByContentId(String contentId);
    List<Tag> findTagByContentId(String contentId);

}
