package com.codimiracle.web.middleware.content.service.impl;

import com.codimiracle.web.middleware.content.inflation.FollowerInflater;
import com.codimiracle.web.middleware.content.inflation.FollowingUserInflater;
import com.codimiracle.web.middleware.content.mapper.FollowMapper;
import com.codimiracle.web.middleware.content.pojo.po.Following;
import com.codimiracle.web.middleware.content.pojo.vo.FollowingVO;
import com.codimiracle.web.middleware.content.service.FollowService;
import com.codimiracle.web.mybatis.contract.support.vo.AbstractService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@Transactional
public class FollowServiceImpl extends AbstractService<String, Following, FollowingVO> implements FollowService {
    @Autowired(required = false)
    private FollowingUserInflater followingUserInflater;
    @Autowired(required = false)
    private FollowerInflater followerInflater;

    @Resource
    private FollowMapper followMapper;

    @Override
    protected FollowingVO mutate(FollowingVO inflatedObject) {
        if (Objects.nonNull(followingUserInflater)) {
            followerInflater.inflate(inflatedObject);
        } else {
            log.warn("social user inflater bean is not found, null will be used");
        }
        if (Objects.nonNull(followingUserInflater)) {
            followingUserInflater.inflate(inflatedObject);
        } else {
            log.warn("following user inflater bean is not found, null will be used.");
        }
        return super.mutate(inflatedObject);
    }

    @Override
    public void save(Following model) {
        model.setCreatedAt(new Date());
        model.setUpdatedAt(model.getCreatedAt());
        super.save(model);
    }

    @Override
    public void update(Following model) {
        model.setUpdatedAt(new Date());
        super.update(model);
    }

    @Override
    public Following findByFollowerIdAndFollowingUserId(String followerId, String followingUserId) {
        Condition condition = new Condition(Following.class);
        condition.createCriteria()
                .andEqualTo("followerId", followerId)
                .andEqualTo("followingUserId", followingUserId);
        List<Following> result = findByCondition(condition);
        if (result.size() == 1) {
            return result.get(0);
        } else if (result.isEmpty()) {
            return null;
        } else {
            throw new TooManyResultsException();
        }
    }

    @Override
    public void follow(String followerId, String followingUserId) {
        Following following = findByFollowerIdAndFollowingUserId(followerId, followingUserId);
        if (Objects.isNull(following)) {
            following = new Following();
            following.setFollowingUserId(followingUserId);
            following.setFollowerId(followerId);
            save(following);
        }
    }


    @Override
    public void unfollow(String followerId, String followingUserId) {
        Following following = findByFollowerIdAndFollowingUserId(followerId, followingUserId);
        if (Objects.nonNull(following)) {
            deleteByIdLogically(following.getId());
        }
    }

    @Override
    public List<Following> findByFollowingUserId(String followingUserId) {
        Condition condition = new Condition(Following.class);
        condition.createCriteria()
                .andEqualTo("followingUserId", followingUserId);
        return findByCondition(condition);
    }

    @Override
    public List<Following> findByFollowerId(String followerId) {
        Condition condition = new Condition(Following.class);
        condition.createCriteria()
                .andEqualTo("followerId", followerId);
        return findByCondition(condition);
    }
}
