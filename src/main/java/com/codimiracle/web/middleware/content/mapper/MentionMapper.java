package com.codimiracle.web.middleware.content.mapper;

import com.codimiracle.web.middleware.content.pojo.po.ContentArticle;
import com.codimiracle.web.middleware.content.pojo.po.ContentMention;
import com.codimiracle.web.mybatis.contract.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface MentionMapper extends Mapper<ContentMention> {
}
