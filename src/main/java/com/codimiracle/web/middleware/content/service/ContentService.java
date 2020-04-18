package com.codimiracle.web.middleware.content.service;

import com.codimiracle.web.middleware.content.pojo.po.Content;
import com.codimiracle.web.middleware.content.contract.Service;
import com.codimiracle.web.middleware.content.pojo.vo.ContentVO;

public interface ContentService extends Service<String, Content, ContentVO> {
    String STATISTICS_FIELD_COMMENTS = "comments";
    String STATISTICS_FIELD_REPOSTS = "reposts";
    String STATISTICS_FIELD_LIKES = "likes";
    String STATISTICS_FIELD_DISLIKES = "dislikes";
    void updateStatistics(String field, String contentId, int increment);
}
