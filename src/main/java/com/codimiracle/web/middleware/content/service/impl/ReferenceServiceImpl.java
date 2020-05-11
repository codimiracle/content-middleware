package com.codimiracle.web.middleware.content.service.impl;

import com.codimiracle.web.basic.contract.PageSlice;
import com.codimiracle.web.mybatis.contract.support.vo.AbstractService;
import com.codimiracle.web.middleware.content.inflation.ReferenceTargetInflater;
import com.codimiracle.web.middleware.content.mapper.ReferenceMapper;
import com.codimiracle.web.middleware.content.pojo.po.ContentReference;
import com.codimiracle.web.middleware.content.pojo.vo.ContentReferenceVO;
import com.codimiracle.web.middleware.content.service.ReferenceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Slf4j
@Transactional
@Service
public class ReferenceServiceImpl extends AbstractService<String, ContentReference, ContentReferenceVO> implements ReferenceService {
    @Resource
    private ReferenceMapper referenceMapper;

    @Autowired(required = false)
    private ReferenceTargetInflater referenceTargetInflater;

    @Override
    protected ContentReferenceVO mutate(ContentReferenceVO inflatedObject) {
        if (Objects.nonNull(referenceTargetInflater)) {
            referenceTargetInflater.inflate(inflatedObject);
        } else {
            log.warn("reference target inflater bean is not found, null will be used.");
        }
        return inflatedObject;
    }

    @Override
    public ContentReferenceVO inflate(ContentReferenceVO inflatedObject) {
        return mutate(inflatedObject);
    }

    @Override
    public PageSlice<ContentReferenceVO> inflate(PageSlice<ContentReferenceVO> slice) {
        return mutate(slice);
    }

    @Override
    public List<ContentReference> findByContentId(String contentId) {
        Condition condition = new Condition(ContentReference.class);
        condition.createCriteria()
                .andEqualTo("contentId", contentId)
                .andEqualTo("deleted", false);
        return findByCondition(condition);
    }

    @Override
    public ContentReference findByContentIdAndReferenceTarget(String contentId, String referenceTargetId, String referececeTargetType) {
        Condition condition = new Condition(ContentReference.class);
        condition.createCriteria()
                .andEqualTo("contentId", contentId)
                .andEqualTo("referenceTargetId", referenceTargetId)
                .andEqualTo("referenceTargetType", referececeTargetType);
        List<ContentReference> list = findByCondition(condition);
        if (list.size() == 1) {
            return list.get(0);
        } else if (list.isEmpty()) {
            return null;
        } else {
            throw new TooManyResultsException();
        }
    }

    @Override
    public List<ContentReferenceVO> findByContentIdIntegrally(String contentId) {
        List<ContentReferenceVO> list = referenceMapper.selectByContentIdIntegrally(contentId);
        list.forEach(this::mutate);
        return list;
    }
}
