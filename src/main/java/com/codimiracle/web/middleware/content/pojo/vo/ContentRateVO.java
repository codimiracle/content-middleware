package com.codimiracle.web.middleware.content.pojo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ContentRateVO {
    private String id;
    private String contentId;
    private String type;
    private Float rate;
    private Long rateCount;
    private Float rateSum;
    private Float rateAvg;

    private Date ratedAt;
}
