package com.codimiracle.web.middleware.content.mapper;

import com.codimiracle.web.middleware.content.contract.Mapper;
import com.codimiracle.web.middleware.content.pojo.po.Content;
import com.codimiracle.web.middleware.content.pojo.po.ContentArticle;
import com.codimiracle.web.middleware.content.pojo.vo.ContentVO;

@org.apache.ibatis.annotations.Mapper
public interface ContentMapper extends Mapper<Content, ContentVO> {

    void updateStatistics(String field, String contentId, int increment);
}
