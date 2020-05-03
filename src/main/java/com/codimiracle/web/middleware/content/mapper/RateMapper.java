package com.codimiracle.web.middleware.content.mapper;

import com.codimiracle.web.mybatis.contract.support.vo.Mapper;
import com.codimiracle.web.middleware.content.pojo.po.Content;
import com.codimiracle.web.middleware.content.pojo.po.ContentArticle;
import com.codimiracle.web.middleware.content.pojo.po.ContentRate;
import com.codimiracle.web.middleware.content.pojo.po.ContentReference;
import com.codimiracle.web.middleware.content.pojo.vo.ContentRateVO;
import com.codimiracle.web.middleware.content.pojo.vo.ContentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface RateMapper extends Mapper<ContentRate, ContentRateVO>  {
    List<ContentRateVO> selectByContentIdIntegrally(String contentId);

    void updateStatistics(@Param("rateId") String rateId, @Param("rate") Float rate);
}
