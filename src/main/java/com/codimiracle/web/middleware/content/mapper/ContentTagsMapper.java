package com.codimiracle.web.middleware.content.mapper;

import com.codimiracle.web.middleware.content.pojo.po.ContentArticle;
import com.codimiracle.web.middleware.content.pojo.po.ContentTag;
import com.codimiracle.web.mybatis.contract.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface ContentTagsMapper extends Mapper<ContentTag> {
    List<ContentTag> selectByContentId(String contentId);

}
