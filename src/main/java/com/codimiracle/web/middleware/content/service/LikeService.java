package com.codimiracle.web.middleware.content.service;

import com.codimiracle.web.middleware.content.pojo.po.ContentLike;
import com.codimiracle.web.mybatis.contract.Service;

public interface LikeService extends Service<String, ContentLike> {
    ContentLike findByLikerIdAndContentId(String likerId, String contentId);

    void like(String likerId, String contentId);

    void dislike(String likerId, String contentId);

    void undo(String likerId, String contentId);

    Boolean isLiked(String likerId, String contentId);

    Boolean isDisliked(String likerId, String contentId);
}
