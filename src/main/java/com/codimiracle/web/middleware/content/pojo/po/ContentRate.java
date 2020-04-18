package com.codimiracle.web.middleware.content.pojo.po;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
public class ContentRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column(name = "content_id")
    private String contentId;
    @Column(name = "type")
    private String type;
    @Column(name = "rate")
    private Float rate;
    @Column(name = "rate_count")
    private Long rateCount;
    @Column(name = "rate_sum")
    private Double rateSum;
    @Column(name = "rate_avg")
    private Double rateAvg;
}
