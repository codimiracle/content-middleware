package com.codimiracle.web.middleware.content.service.impl;

import com.codimiracle.web.middleware.content.inflation.MentionUserInflater;
import com.codimiracle.web.middleware.content.mapper.MentionMapper;
import com.codimiracle.web.middleware.content.pojo.eo.MentionUser;
import com.codimiracle.web.middleware.content.pojo.po.ContentMention;
import com.codimiracle.web.middleware.content.service.MentionService;
import com.codimiracle.web.mybatis.contract.AbstractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
public class MentionServiceImpl extends AbstractService<String, ContentMention> implements MentionService {

    @Autowired(required = false)
    private MentionUserInflater mentionUserInflater;

    @Resource
    private MentionMapper mentionMapper;

    @Override
    public List<ContentMention> findByContentId(String contentId) {
        Condition condition = new Condition(ContentMention.class);
        condition.createCriteria().andEqualTo("contentId", contentId);
        List<ContentMention> contentMentionList = findByCondition(condition);
        if (Objects.nonNull(mentionUserInflater)) {
            contentMentionList.forEach(mentionUserInflater::inflate);
        } else {
            log.warn("mention user inflater is not found, null will be used.");
        }
        return contentMentionList;
    }

    @Override
    public List<MentionUser> findMentionUserByContentId(String contentId) {
        return findByContentId(contentId).stream().map(ContentMention::getMentionUser).collect(Collectors.toList());
    }
}
