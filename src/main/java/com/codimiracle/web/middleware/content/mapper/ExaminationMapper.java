package com.codimiracle.web.middleware.content.mapper;

import com.codimiracle.web.mybatis.contract.support.vo.Mapper;
import com.codimiracle.web.middleware.content.pojo.po.Content;
import com.codimiracle.web.middleware.content.pojo.po.ContentArticle;
import com.codimiracle.web.middleware.content.pojo.po.ContentExamination;
import com.codimiracle.web.middleware.content.pojo.vo.ContentExaminationVO;
import com.codimiracle.web.middleware.content.pojo.vo.ContentVO;

@org.apache.ibatis.annotations.Mapper
public interface ExaminationMapper extends Mapper<ContentExamination, ContentExaminationVO> {
    ContentExaminationVO selectLastByContentId(String contentId);
}
