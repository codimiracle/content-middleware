package com.codimiracle.web.middleware.content.service.impl;

import com.codimiracle.web.middleware.content.mapper.LikeMapper;
import com.codimiracle.web.middleware.content.pojo.po.ContentLike;
import com.codimiracle.web.middleware.content.service.ContentService;
import com.codimiracle.web.middleware.content.service.LikeService;
import com.codimiracle.web.mybatis.contract.AbstractService;
import com.codimiracle.web.mybatis.contract.ServiceException;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Transactional
@Service
public class LikeServiceImpl extends AbstractService<String, ContentLike> implements LikeService {

    @Resource
    private ContentService contentService;
    @Resource
    private LikeMapper likeMapper;

    @Override
    public ContentLike findByLikerIdAndContentId(String likerId, String contentId) {
        Condition condition = new Condition(ContentLike.class);
        condition.createCriteria()
                .andEqualTo("likerId", likerId)
                .andEqualTo("contentId", contentId);
        List<ContentLike> contentLikes = findByCondition(condition);
        if (contentLikes.isEmpty()) {
            return null;
        } else if (1 == contentLikes.size()) {
            return contentLikes.get(0);
        } else {
            throw new TooManyResultsException();
        }
    }

    @Override
    public void like(String userId, String contentId) {
        ContentLike contentLike = findByLikerIdAndContentId(userId, contentId);
        if (Objects.isNull(contentLike)) {
            contentLike = new ContentLike();
            contentLike.setContentId(contentId);
            contentLike.setLikerId(userId);
            contentLike.setType(ContentLike.HIT_TYPE_LIKE);
            contentLike.setHitedAt(new Date());
            contentLike.setHited(true);
            contentService.updateStatistics(ContentService.STATISTICS_FIELD_LIKES, contentId, 1);
            save(contentLike);
        } else {
            throw new ServiceException("抱歉，已经点赞了！");
        }
    }

    @Override
    public void dislike(String userId, String contentId) {
        ContentLike contentLike = findByLikerIdAndContentId(userId, contentId);
        if (Objects.isNull(contentLike)) {
            contentLike = new ContentLike();
            contentLike.setContentId(contentId);
            contentLike.setLikerId(userId);
            contentLike.setType(ContentLike.HIT_TYPE_DISLIKE);
            contentLike.setHitedAt(new Date());
            contentLike.setHited(true);
            contentService.updateStatistics(ContentService.STATISTICS_FIELD_DISLIKES, contentId, 1);
            save(contentLike);
        } else {
            throw new ServiceException("抱歉，已经点赞了！");
        }
    }

    @Override
    public void undo(String userId, String contentId) {
        ContentLike contentLike = findByLikerIdAndContentId(userId, contentId);
        if (Objects.nonNull(contentLike)) {
            ContentLike updatingLike = new ContentLike();
            updatingLike.setId(contentLike.getId());
            updatingLike.setHited(false);;
            update(updatingLike);
            switch (contentLike.getType()) {
                case ContentLike.HIT_TYPE_LIKE:
                    contentService.updateStatistics(ContentService.STATISTICS_FIELD_LIKES, contentId, -1);
                    break;
                case ContentLike.HIT_TYPE_DISLIKE:
                    contentService.updateStatistics(ContentService.STATISTICS_FIELD_DISLIKES, contentId, -1);
                    break;
                default:
            }
        }
    }

    @Override
    public Boolean isLiked(String likerId, String contentId) {
        ContentLike contentLike = findByLikerIdAndContentId(likerId, contentId);
        if (Objects.nonNull(contentLike)) {
            return Objects.equals(ContentLike.HIT_TYPE_LIKE, contentLike.getType()) && Objects.nonNull(contentLike.getHited()) && contentLike.getHited();
        }
        return false;
    }

    @Override
    public Boolean isDisliked(String userId, String contentId) {
        ContentLike contentLike = findByLikerIdAndContentId(userId, contentId);
        if (Objects.nonNull(contentLike)) {
            return Objects.equals(ContentLike.HIT_TYPE_DISLIKE, contentLike.getType()) && Objects.nonNull(contentLike.getHited()) && contentLike.getHited();
        }
        return false;
    }
}
