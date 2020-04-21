package com.codimiracle.web.middleware.content.service.impl;

import com.codimiracle.web.basic.contract.Page;
import com.codimiracle.web.basic.contract.PageSlice;
import com.codimiracle.web.middleware.content.contract.AbstractService;
import com.codimiracle.web.middleware.content.mapper.ArticleMapper;
import com.codimiracle.web.middleware.content.pojo.eo.LoggedUser;
import com.codimiracle.web.middleware.content.pojo.po.Content;
import com.codimiracle.web.middleware.content.pojo.po.ContentArticle;
import com.codimiracle.web.middleware.content.pojo.vo.ContentArticleVO;
import com.codimiracle.web.middleware.content.pojo.vo.ContentVO;
import com.codimiracle.web.middleware.content.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
public class ArticleServiceImpl extends AbstractService<String, ContentArticle, ContentArticleVO> implements ArticleService {

    @Resource
    private ContentService contentService;
    @Resource
    private LikeService likeService;
    @Autowired(required = false)
    private LoggedUser loggedUser;
    @Resource
    private ExaminationService examinationService;
    @Resource
    private MentionService mentionService;
    @Resource
    private ReferenceService referenceService;
    @Resource
    private ArticleMapper articleMapper;

    @Override
    public void save(ContentArticle model) {
        Content content = new Content();
        BeanUtils.copyProperties(model, content);
        contentService.save(content);
        model.setContentId(content.getId());
        BeanUtils.copyProperties(content, model);
        super.save(model);
    }

    @Override
    public void save(List<ContentArticle> models) {
        List<Content> contents = models.stream().map(contentArticle -> {
            Content content = new Content();
            BeanUtils.copyProperties(contentArticle, content);
            return content;
        }).collect(Collectors.toList());
        contentService.save(contents);
        for (int i = 0; i < contents.size(); i++) {
            Content content = contents.get(i);
            ContentArticle article = models.get(i);
            article.setContentId(content.getId());
            article.setId(content.getId());
            super.save(article);
        }
    }

    @Override
    public void deleteByIdLogically(String id) {
        contentService.deleteByIdLogically(id);
    }

    private void virtualJoin(ContentArticle contentArticle) {
        if (Objects.nonNull(contentArticle)) {
            Content content = contentService.findById(contentArticle.getContentId());
            BeanUtils.copyProperties(content, contentArticle);
        }
    }

    @Override
    public ContentArticle findById(String id) {
        ContentArticle article = super.findById(id);
        virtualJoin(article);
        return article;
    }

    @Override
    public List<ContentArticle> findAll() {
        List<ContentArticle> articles = super.findAll();
        articles.forEach(this::virtualJoin);
        return articles;
    }

    @Override
    public PageSlice<ContentArticle> findAll(Page page) {
        PageSlice<ContentArticle> pageSlice = super.findAll(page);
        pageSlice.getList().forEach(this::virtualJoin);
        return pageSlice;
    }

    @Override
    protected ContentArticleVO mutate(ContentArticleVO inflatedObject) {
        if (Objects.nonNull(inflatedObject)) {
            ContentVO contentVO = new ContentVO();
            BeanUtils.copyProperties(inflatedObject, contentVO);
            ((ContentServiceImpl) contentService).mutate(contentVO);
            BeanUtils.copyProperties(contentVO, inflatedObject);
            inflatedObject.setExamination(examinationService.findLastByContentIdIntegrally(inflatedObject.getContentId()));
            inflatedObject.setMentionList(mentionService.findMentionUserByContentId(inflatedObject.getContentId()));
            inflatedObject.setReferenceList(referenceService.findByContentIdIntegrally(inflatedObject.getContentId()));
            if (Objects.nonNull(loggedUser)) {
                inflatedObject.setLiked(likeService.isLiked(loggedUser.getLoggedUserId(), inflatedObject.getContentId()));
                inflatedObject.setDisliked(likeService.isDisliked(loggedUser.getLoggedUserId(), inflatedObject.getContentId()));
            } else {
                log.warn("logged user bean is not found, can not determine whether like/dislike or not");
            }
        }
        return inflatedObject;
    }

    @Override
    public List<ContentArticle> findByTargetContentId(String targetContentId) {
        Condition condition = new Condition(ContentArticle.class);
        condition.createCriteria()
                .andEqualTo("targetContentId", targetContentId);
        List<ContentArticle> articles = findByCondition(condition);
        articles.forEach(this::virtualJoin);
        return articles;
    }

    @Override
    public List<ContentArticleVO> findByTargetContentIdIntegrally(String targetContentId) {
        List<ContentArticleVO> articleList = articleMapper.selectByTargetContentId(targetContentId);
        articleList.forEach(this::mutate);
        return articleList;
    }
}
