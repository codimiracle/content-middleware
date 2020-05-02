package com.codimiracle.web.middleware.content.mapper;

import com.codimiracle.web.middleware.content.contract.Mapper;
import com.codimiracle.web.middleware.content.pojo.po.Following;
import com.codimiracle.web.middleware.content.pojo.vo.FollowingVO;

@org.apache.ibatis.annotations.Mapper
public interface FollowMapper extends Mapper<Following, FollowingVO> {
}
