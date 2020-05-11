package com.codimiracle.web.middleware.content.service;

import com.codimiracle.web.basic.contract.PageSlice;
import com.codimiracle.web.middleware.content.pojo.po.ContentReference;
import com.codimiracle.web.mybatis.contract.support.vo.Service;
import com.codimiracle.web.middleware.content.pojo.vo.ContentReferenceVO;

import java.util.List;

/**
 * In cms, some content maybe refer other content, that is named reference.
 */
public interface ReferenceService extends Service<String, ContentReference, ContentReferenceVO> {
    ContentReference findByContentIdAndReferenceTarget(String contentId, String referenceTargetId, String referececeTargetType);

    List<ContentReferenceVO> findByContentIdIntegrally(String contentId);

    /**
     * try to inflate with associating inflater.
     * @param inflatedObject target object ot inflate
     * @return a inflated object.
     */
    ContentReferenceVO inflate(ContentReferenceVO inflatedObject);

    PageSlice<ContentReferenceVO> inflate(PageSlice<ContentReferenceVO> slice);

    List<ContentReference> findByContentId(String contentId);
}
