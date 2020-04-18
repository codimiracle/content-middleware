package com.codimiracle.web.middleware.content.service;

import com.codimiracle.web.middleware.content.contract.Service;
import com.codimiracle.web.middleware.content.pojo.po.ContentRate;
import com.codimiracle.web.middleware.content.pojo.vo.ContentRateVO;

import java.util.List;

public interface RateService extends Service<String, ContentRate, ContentRateVO> {
    List<ContentRateVO> findByContentIdIntegrally(String contentId);

    void rateUp(String contentId, String type, Float rate);

    ContentRate findByContentIdAndType(String contentId, String type);

    List<ContentRate> findByContentId(String contentId);

    void rateDown(String contentId, String type, Float rate);
}
