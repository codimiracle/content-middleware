package com.codimiracle.web.middleware.content.mapper;

import com.codimiracle.web.middleware.content.pojo.po.ContentArticle;
import com.codimiracle.web.middleware.content.pojo.po.ContentLike;
import com.codimiracle.web.mybatis.contract.Mapper;

@org.apache.ibatis.annotations.Mapper
public interface LikeMapper extends Mapper<ContentLike>  {
}
