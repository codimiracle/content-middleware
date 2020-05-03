package com.codimiracle.web.middleware.content.service.impl;

import com.codimiracle.web.mybatis.contract.support.vo.AbstractService;
import com.codimiracle.web.middleware.content.mapper.RateMapper;
import com.codimiracle.web.middleware.content.pojo.po.ContentRate;
import com.codimiracle.web.middleware.content.pojo.vo.ContentRateVO;
import com.codimiracle.web.middleware.content.service.RateService;
import com.codimiracle.web.mybatis.contract.ServiceException;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Transactional
@Service
public class RateServiceImpl extends AbstractService<String, ContentRate, ContentRateVO> implements RateService {

    @Resource
    private RateMapper rateMapper;

    @Override
    public List<ContentRateVO> findByContentIdIntegrally(String contentId) {
        List<ContentRateVO> rateList = rateMapper.selectByContentIdIntegrally(contentId);
        rateList.forEach(this::mutate);
        return rateList;
    }

    @Override
    public void rateUp(String contentId, String type, Float rate) {
        ContentRate contentRate = findByContentIdAndType(contentId, type);
        if (Objects.nonNull(contentRate)) {
            if (Objects.equals(0L, contentRate.getRateCount())) {
                contentRate.setContentId(null);
                contentRate.setType(null);
                contentRate.setRate(rate);
                contentRate.setRateAvg(rate.doubleValue());
                contentRate.setRateCount(1L);
                contentRate.setRateSum(rate.doubleValue());
                update(contentRate);
            } else {
                rateMapper.updateStatistics(contentRate.getId(), rate);
            }
        } else {
            throw new ServiceException("没有找到对应的内容评分项");
        }
    }

    @Override
    public ContentRate findByContentIdAndType(String contentId, String type) {
        Condition condition = new Condition(ContentRate.class);
        condition.createCriteria()
                .andEqualTo("contentId", contentId)
                .andEqualTo("type", type);
        List<ContentRate> rateList = findByCondition(condition);
        if (1 == rateList.size()) {
            return rateList.get(0);
        } else if (rateList.isEmpty()) {
            return null;
        } else {
            throw new TooManyResultsException();
        }
    }

    @Override
    public List<ContentRate> findByContentId(String contentId) {
        Condition condition = new Condition(ContentRate.class);
        condition.createCriteria()
                .andEqualTo("contentId", contentId);
        return findByCondition(condition);
    }

    @Override
    public void rateDown(String contentId, String type, Float rate) {
        ContentRate contentRate = findByContentIdAndType(contentId, type);
        if (Objects.nonNull(contentRate)) {
            if (Objects.equals(1L, contentRate.getRateCount())) {
                contentRate.setContentId(null);
                contentRate.setType(null);
                contentRate.setRate(0.0f);
                contentRate.setRateAvg(0.0d);
                contentRate.setRateCount(0L);
                contentRate.setRateSum(0.0d);
                update(contentRate);
            } else {
                rateMapper.updateStatistics(contentRate.getId(), rate * -1);
            }
        } else {
            throw new ServiceException("没有找到对应的内容评分项");
        }
    }
}
