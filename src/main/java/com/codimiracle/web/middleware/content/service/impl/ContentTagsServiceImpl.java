package com.codimiracle.web.middleware.content.service.impl;

import com.codimiracle.web.middleware.content.inflation.ContentTagInflater;
import com.codimiracle.web.middleware.content.mapper.ContentTagsMapper;
import com.codimiracle.web.middleware.content.pojo.eo.Tag;
import com.codimiracle.web.middleware.content.pojo.po.ContentTag;
import com.codimiracle.web.middleware.content.service.ContentTagsService;
import com.codimiracle.web.mybatis.contract.AbstractService;
import com.codimiracle.web.mybatis.contract.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
public class ContentTagsServiceImpl extends AbstractService<String, ContentTag> implements ContentTagsService {
    @Resource
    private ContentTagsMapper tagsMapper;

    @Autowired(required = false)
    private ContentTagInflater contentTagInflater;

    /**
     * setting tag id by {@link Tag} object.
     */
    private void settingTagId(ContentTag contentTag) {
        if (Objects.isNull(contentTag.getTagId())) {
            Optional.of(contentTag.getTag()).ifPresent(tag -> contentTag.setTagId(tag.getTagId()));
        }
        Objects.requireNonNull(contentTag.getTagId(), "You must set the tag id or tag!");
    }

    @Override
    public void save(ContentTag model) {
        super.save(model);
        settingTagId(model);
    }

    @Override
    public void save(List<ContentTag> models) {
        models.forEach((e) -> {
            settingTagId(e);
            e.setDeleted(false);
        });
        super.save(models);
    }

    @Override
    public void updateAttachingTags(String contentId, List<Tag> tagList) {
        Objects.requireNonNull(contentId, "content id can not be null!");
        Objects.requireNonNull(tagList, "tag list can not be null!");
        // mapping to Map<tagId, ContentTag>
        Map<String, ContentTag> needToAttachTags = tagList.stream().collect(Collectors.toMap(Tag::getTagId, (e) -> {
                    ContentTag contentTag = new ContentTag();
                    contentTag.setContentId(contentId);
                    contentTag.setTagId(e.getTagId());
                    return contentTag;
                }
        ));
        if (needToAttachTags.containsKey(null)) {
            throw new ServiceException("Each tag must have there tag id");
        }
        // exists attached tags
        List<ContentTag> attachedTags = findByContentIdWithDeleted(contentId);
        // process attached tags
        attachedTags.forEach((t) -> {
            if (needToAttachTags.containsKey(t.getTagId())) {
                t.setDeleted(false);
            } else {
                t.setDeleted(true);
            }
            // remove exists tags, update deleted state later.
            needToAttachTags.remove(t.getTagId());
        });
        // needToAttachTags is need to append now
        if (!needToAttachTags.isEmpty()) {
            save(new ArrayList<>(needToAttachTags.values()));
        }
        // update exists tags state.
        attachedTags.forEach(this::update);
    }

    private List<ContentTag> findByContentId(String contentId, boolean withDeleted) {
        Objects.requireNonNull(contentId, "content id can not be null.");
        Condition condition = new Condition(ContentTag.class);
        condition.createCriteria()
                .andEqualTo("contentId", contentId);
        if (!withDeleted) {
            condition.and().andEqualTo("deleted", false);
        }
        List<ContentTag> contentTags = tagsMapper.selectByCondition(condition);
        if (Objects.isNull(contentTagInflater)) {
            log.warn("tag inflater is not found, null will be used.");
            return contentTags;
        }
        contentTags.forEach(contentTag -> contentTagInflater.inflate(contentTag));
        return contentTags;
    }

    @Override
    public List<ContentTag> findByContentId(String contentId) {
        return findByContentId(contentId, false);
    }

    @Override
    public List<ContentTag> findByContentIdWithDeleted(String contentId) {
        return findByContentId(contentId, true);
    }

    @Override
    public List<Tag> findTagByContentId(String contentId) {
        return findByContentId(contentId).stream().map(ContentTag::getTag).collect(Collectors.toList());
    }
}
