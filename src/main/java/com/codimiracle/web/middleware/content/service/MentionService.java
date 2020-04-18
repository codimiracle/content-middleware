package com.codimiracle.web.middleware.content.service;

import com.codimiracle.web.middleware.content.pojo.eo.MentionUser;
import com.codimiracle.web.middleware.content.pojo.po.ContentMention;
import com.codimiracle.web.mybatis.contract.Service;

import java.util.List;

public interface MentionService extends Service<String, ContentMention> {
    List<ContentMention> findByContentId(String contentId);

    List<MentionUser> findMentionUserByContentId(String contentId);
}
