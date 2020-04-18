package com.codimiracle.web.middleware.content.service.impl;

import com.codimiracle.web.middleware.content.contract.AbstractService;
import com.codimiracle.web.middleware.content.inflation.UserInflater;
import com.codimiracle.web.middleware.content.mapper.ContentMapper;
import com.codimiracle.web.middleware.content.pojo.po.Content;
import com.codimiracle.web.middleware.content.pojo.vo.ContentRateVO;
import com.codimiracle.web.middleware.content.pojo.vo.ContentVO;
import com.codimiracle.web.middleware.content.service.ContentService;
import com.codimiracle.web.middleware.content.service.ContentTagsService;
import com.codimiracle.web.middleware.content.service.RateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Transactional
@Service
public class ContentServiceImpl extends AbstractService<String, Content, ContentVO> implements ContentService {

    @Resource
    private ContentMapper contentMapper;
    @Resource
    private RateService rateService;
    @Resource
    private ContentTagsService tagsService;
    @Autowired(required = false)
    private UserInflater userInflater;

    @Override
    public void save(Content model) {
        model.setCreatedAt(new Date());
        model.setUpdatedAt(model.getCreatedAt());
        super.save(model);
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
    }

    @Override
    protected ContentVO mutate(ContentVO inflatedObject) {
        if (Objects.nonNull(inflatedObject)) {
            List<ContentRateVO> rateList = rateService.findByContentIdIntegrally(inflatedObject.getId());
            if (rateList.size() > 0) {
                inflatedObject.setRate(rateList.get(0));
            }
            inflatedObject.setRateList(rateList);
            inflatedObject.setTagList(tagsService.findTagByContentId(inflatedObject.getId()));
            if (Objects.nonNull(userInflater)) {
                userInflater.inflate(inflatedObject);
            } else {
                log.warn("user inflater bean is not found, null will be used.");
            }
        }
        return inflatedObject;
    }

    @Override
    public void updateStatistics(String field, String contentId, int increment) {
        contentMapper.updateStatistics(field, contentId, increment);
    }
}
