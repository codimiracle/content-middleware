package com.codimiracle.web.middleware.content.service.impl;

import com.codimiracle.web.basic.contract.PageSlice;
import com.codimiracle.web.middleware.content.inflation.OwnerInflater;
import com.codimiracle.web.middleware.content.mapper.ContentMapper;
import com.codimiracle.web.middleware.content.pojo.po.Content;
import com.codimiracle.web.middleware.content.pojo.po.ContentTag;
import com.codimiracle.web.middleware.content.pojo.vo.ContentRateVO;
import com.codimiracle.web.middleware.content.pojo.vo.ContentVO;
import com.codimiracle.web.middleware.content.service.ContentService;
import com.codimiracle.web.middleware.content.service.ContentTagsService;
import com.codimiracle.web.middleware.content.service.RateService;
import com.codimiracle.web.mybatis.contract.support.vo.AbstractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
public class ContentServiceImpl extends AbstractService<String, Content, ContentVO> implements ContentService {

    @Resource
    private ContentMapper contentMapper;
    @Resource
    private RateService rateService;
    @Resource
    private ContentTagsService contentTagsService;
    @Autowired(required = false)
    private OwnerInflater ownerInflater;

    private void saveTagsListPart(Content content) {
        Optional.ofNullable(content.getTagList()).ifPresent((tags -> {
            contentTagsService.save(tags.stream().map(tag -> {
                ContentTag contentTag = new ContentTag();
                contentTag.setContentId(content.getId());
                contentTag.setTag(tag);
                return contentTag;
            }).collect(Collectors.toList()));
        }));
    }

    @Override
    public void save(Content model) {
        model.setCreatedAt(new Date());
        model.setUpdatedAt(model.getCreatedAt());
        super.save(model);
        saveTagsListPart(model);
    }

    @Override
    public void save(List<Content> models) {
        models.forEach(content -> {
            content.setComments(0L);
            content.setReposts(0L);
            content.setLikes(0L);
            content.setDislikes(0L);
            content.setDeleted(false);
            content.setCreatedAt(new Date());
            content.setUpdatedAt(content.getCreatedAt());
        });
        super.save(models);
        models.forEach((this::saveTagsListPart));
    }

    @Override
    public void update(Content model) {
        super.update(model);
        Optional.ofNullable(model.getTagList()).ifPresent(tagList ->
                contentTagsService.updateAttachingTags(model.getId(), tagList)
        );
    }

    @Override
    protected ContentVO mutate(ContentVO inflatedObject) {
        if (Objects.nonNull(inflatedObject)) {
            List<ContentRateVO> rateList = rateService.findByContentIdIntegrally(inflatedObject.getId());
            if (rateList.size() > 0) {
                inflatedObject.setRate(rateList.get(0));
            }
            inflatedObject.setRateList(rateList);
            inflatedObject.setTagList(contentTagsService.findTagByContentId(inflatedObject.getId()));
            if (Objects.nonNull(ownerInflater)) {
                ownerInflater.inflate(inflatedObject);
            } else {
                log.warn("user inflater bean is not found, null will be used.");
            }
        }
        return inflatedObject;
    }

    @Override
    public ContentVO inflate(ContentVO contentVO) {
        return mutate(contentVO);
    }

    @Override
    public PageSlice<ContentVO> inflate(PageSlice<ContentVO> slice) {
        return mutate(slice);
    }

    @Override
    public void updateStatistics(String field, String contentId, int increment) {
        contentMapper.updateStatistics(field, contentId, increment);
    }
}
