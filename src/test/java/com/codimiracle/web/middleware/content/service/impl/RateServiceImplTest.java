package com.codimiracle.web.middleware.content.service.impl;

import com.codimiracle.web.middleware.content.TestWithBeans;
import com.codimiracle.web.middleware.content.mapper.RateMapper;
import com.codimiracle.web.middleware.content.pojo.po.ContentRate;
import com.codimiracle.web.middleware.content.pojo.vo.ContentRateVO;
import com.codimiracle.web.middleware.content.service.RateService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = TestWithBeans.class)
class RateServiceImplTest {
    @Resource
    private RateService rateService;
    @Resource
    private RateMapper rateMapper;

    @Test
    void testSave() {
        ContentRate contentRate = new ContentRate();
        contentRate.setContentId("111");
        contentRate.setRate(0.0f);
        rateService.save(contentRate);
        List<ContentRateVO> rateList = rateService.findByContentIdIntegrally(contentRate.getContentId());
        assertEquals(1, rateList.size());
        ContentRateVO rateVO = rateList.get(0);
        assertEquals(contentRate.getRate(), rateVO.getRate());
        assertEquals(0, rateVO.getRateAvg());
        assertEquals(0, rateVO.getRateCount());
        assertEquals(0, rateVO.getRateSum());
        assertEquals(0, rateVO.getRateCount());
    }

    @Test
    void findByContentIdIntegrally() {
        ContentRate contentRate = new ContentRate();
        contentRate.setContentId("84400");
        contentRate.setRate(0.0f);
        rateMapper.insertSelective(contentRate);
        List<ContentRateVO> rateList = rateService.findByContentIdIntegrally(contentRate.getContentId());
        assertEquals(1, rateList.size());
        ContentRateVO rateVO = rateList.get(0);
        assertEquals(contentRate.getRate(), rateVO.getRate());
        assertEquals(0, rateVO.getRateAvg());
        assertEquals(0, rateVO.getRateSum());
        assertEquals(0, rateVO.getRateCount());
    }

    @Test
    void rateUp() {
        ContentRate contentRate = new ContentRate();
        contentRate.setContentId("100101");
        contentRate.setType("test-rate");
        rateService.save(contentRate);
        rateService.rateUp(contentRate.getContentId(), contentRate.getType(), 3.5f);
        ContentRate result1 = rateService.findByContentIdAndType(contentRate.getContentId(), contentRate.getType());
        assertEquals(3.5f, result1.getRate());
        assertEquals(3.5f, result1.getRateAvg());
        assertEquals(3.5f, result1.getRateSum());
        assertEquals(1, result1.getRateCount());
        rateService.rateUp(contentRate.getContentId(), contentRate.getType(), 2.5f);
        ContentRate result2 = rateService.findByContentIdAndType(contentRate.getContentId(), contentRate.getType());
        assertEquals(3.0f, result2.getRate());
        assertEquals(3.0f, result2.getRateAvg());
        assertEquals(6.0f, result2.getRateSum());
        assertEquals(2, result2.getRateCount());
    }

    @Test
    void findByContentIdAndType() {
        ContentRate contentRate = new ContentRate();
        contentRate.setContentId("10101");
        contentRate.setType("comment-rate");
        ContentRate contentRate2 = new ContentRate();
        contentRate2.setContentId("10103");
        contentRate2.setType("comment-rate");
        rateService.save(contentRate);
        rateService.save(contentRate2);
        ContentRate result = rateService.findByContentIdAndType(contentRate.getContentId(), contentRate.getType());
        assertEquals(result.getContentId(), contentRate.getContentId());
        assertEquals(result.getType(), contentRate.getType());
        ContentRate result2 = rateService.findByContentIdAndType(contentRate2.getContentId(), contentRate2.getType());
        assertEquals(result2.getContentId(), contentRate2.getContentId());
        assertEquals(result2.getType(), contentRate2.getType());
    }

    @Test
    void findByContentId() {
        for (int i = 0; i < 20; i++) {
            ContentRate contentRate = new ContentRate();
            contentRate.setType("other-rate");
            contentRate.setContentId("56543");
            contentRate.setRate(i + 20.0f);
            contentRate.setRateAvg(contentRate.getRate().doubleValue());
            contentRate.setRateSum(contentRate.getRate().doubleValue());
            contentRate.setRateCount(1L);
            rateService.save(contentRate);
        }
        List<ContentRate> rates = rateService.findByContentId("56543");
        assertEquals(20, rates.size());
        ContentRate contentRate = rates.get(rates.size() - 1);
        assertEquals("other-rate", contentRate.getType());
        assertEquals("56543", contentRate.getContentId());
        assertEquals(39.0f, contentRate.getRate());
    }

    @Test
    void rateDown() {
        ContentRate contentRate = new ContentRate();
        contentRate.setContentId("400101");
        contentRate.setType("test-rate");
        rateService.save(contentRate);
        rateService.rateUp(contentRate.getContentId(), contentRate.getType(), 3.5f);
        ContentRate result1 = rateService.findByContentIdAndType(contentRate.getContentId(), contentRate.getType());
        assertEquals(3.5f, result1.getRate());
        assertEquals(3.5f, result1.getRateAvg());
        assertEquals(3.5f, result1.getRateSum());
        assertEquals(1, result1.getRateCount());
        rateService.rateDown(contentRate.getContentId(), contentRate.getType(), 3.5f);
        ContentRate result2 = rateService.findByContentIdAndType(contentRate.getContentId(), contentRate.getType());
        assertEquals(0, result2.getRate());
        assertEquals(0f, result2.getRateAvg());
        assertEquals(0f, result2.getRateSum());
        assertEquals(0, result2.getRateCount());
    }
}