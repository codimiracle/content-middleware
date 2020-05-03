package com.codimiracle.web.middleware.content.mapper;

import com.codimiracle.web.mybatis.contract.support.vo.Mapper;
import com.codimiracle.web.middleware.content.pojo.po.ContentArticle;
import com.codimiracle.web.middleware.content.pojo.po.ContentReference;
import com.codimiracle.web.middleware.content.pojo.vo.ContentReferenceVO;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface ReferenceMapper extends Mapper<ContentReference, ContentReferenceVO>  {
    List<ContentReferenceVO> selectByContentIdIntegrally(String contentId);
}
