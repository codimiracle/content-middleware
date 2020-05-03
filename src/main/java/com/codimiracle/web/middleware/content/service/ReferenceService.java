package com.codimiracle.web.middleware.content.service;

import com.codimiracle.web.middleware.content.pojo.po.ContentReference;
import com.codimiracle.web.mybatis.contract.support.vo.Service;
import com.codimiracle.web.middleware.content.pojo.vo.ContentReferenceVO;

import java.util.List;

public interface ReferenceService extends Service<String, ContentReference, ContentReferenceVO> {
    ContentReference findByContentIdAndReferenceTarget(String contentId, String referenceTargetId, String referececeTargetType);

    List<ContentReferenceVO> findByContentIdIntegrally(String contentId);

    List<ContentReference> findByContentId(String contentId);
}
