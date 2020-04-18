package com.codimiracle.web.middleware.content.service.impl;

import com.codimiracle.web.middleware.content.inflation.TagInflater;
import com.codimiracle.web.middleware.content.mapper.ContentTagsMapper;
import com.codimiracle.web.middleware.content.pojo.eo.Tag;
import com.codimiracle.web.middleware.content.pojo.po.ContentTag;
import com.codimiracle.web.middleware.content.service.ContentTagsService;
import com.codimiracle.web.mybatis.contract.AbstractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
public class ContentTagsServiceImpl extends AbstractService<String, ContentTag> implements ContentTagsService {
    @Resource
    private ContentTagsMapper tagsMapper;

    @Autowired(required = false)
    private TagInflater tagInflater;

    @Override
    public void save(List<ContentTag> models) {
        models.forEach((e) -> {
            e.setDeleted(false);
        });
        super.save(models);
    }

    @Override
    public List<ContentTag> findByContentId(String contentId) {
        List<ContentTag> contentTags = tagsMapper.selectByContentId(contentId);
        if (Objects.isNull(tagInflater)) {
            log.warn("tag inflater is not found, null will be used.");
            return contentTags;
        }
        contentTags.forEach(contentTag -> tagInflater.inflate(contentTag));
        return contentTags;
    }

    @Override
    public List<Tag> findTagByContentId(String contentId) {
        return findByContentId(contentId).stream().map(ContentTag::getTag).collect(Collectors.toList());
    }
}
