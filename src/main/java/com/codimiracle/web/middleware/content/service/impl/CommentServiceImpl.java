package com.codimiracle.web.middleware.content.service.impl;

import com.codimiracle.web.basic.contract.Filter;
import com.codimiracle.web.basic.contract.Page;
import com.codimiracle.web.basic.contract.PageSlice;
import com.codimiracle.web.basic.contract.Sorter;
import com.codimiracle.web.middleware.content.pojo.po.Comment;
import com.codimiracle.web.middleware.content.pojo.po.Content;
import com.codimiracle.web.middleware.content.pojo.po.ContentArticle;
import com.codimiracle.web.middleware.content.pojo.vo.CommentVO;
import com.codimiracle.web.middleware.content.pojo.vo.ContentArticleVO;
import com.codimiracle.web.middleware.content.service.ArticleService;
import com.codimiracle.web.middleware.content.service.CommentService;
import com.codimiracle.web.middleware.content.service.ContentService;
import com.codimiracle.web.mybatis.contract.AbstractUnsupportedOperationService;
import com.codimiracle.web.mybatis.contract.ServiceException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Transactional
@Service
public class CommentServiceImpl extends AbstractUnsupportedOperationService<String, Comment> implements CommentService {
    @Resource
    private ArticleService articleService;
    @Resource
    private ContentService contentService;

    private ContentArticle mapping(Comment comment) {
        if (Objects.isNull(comment)) {
            return null;
        }
        ContentArticle contentArticle = new ContentArticle();
        BeanUtils.copyProperties(comment, contentArticle);
        return contentArticle;
    }

    private Comment mapping(ContentArticle contentArticle) {
        if (Objects.isNull(contentArticle)) {
            return null;
        }
        Comment comment = new Comment();
        BeanUtils.copyProperties(contentArticle, comment);
        return comment;
    }

    private CommentVO mapping(ContentArticleVO contentArticleVO) {
        if (Objects.isNull(contentArticleVO)) {
            return null;
        }
        CommentVO commentVO = new CommentVO();
        BeanUtils.copyProperties(contentArticleVO, commentVO);
        return commentVO;
    }

    @Override
    public void save(Comment entity) {
        if (Objects.nonNull(entity)) {
            if (Objects.isNull(entity.getTargetContentId())) {
                throw new ServiceException("请指定评论的目标内容标识");
            }
            ContentArticle article = mapping(entity);
            articleService.save(article);
            BeanUtils.copyProperties(article, entity);
        }
    }

    @Override
    public void save(List<Comment> entities) {
        List<ContentArticle> articles = entities.stream().map((comment) -> {
            if (Objects.isNull(comment.getTargetContentId())) {
                throw new ServiceException("请指定评论的目标内容标识");
            }
            return this.mapping(comment);
        }).collect(Collectors.toList());
        articleService.save(articles);
        for (int i = 0; i < entities.size(); i++) {
            Comment comment = entities.get(i);
            ContentArticle article = articles.get(i);
            BeanUtils.copyProperties(article, comment);
        }
    }

    @Override
    public void update(Comment entity) {
        ContentArticle article = mapping(entity);
        articleService.update(article);
        BeanUtils.copyProperties(article, entity);
    }

    @Override
    public Comment findById(String id) {
        ContentArticle article = articleService.findById(id);
        if (Objects.nonNull(article) && !Objects.equals(article.getType(), Content.CONTENT_TYPE_COMMENT)) {
            return null;
        }
        return mapping(article);

    }

    @Override
    public PageSlice<CommentVO> findAllIntegrally(Filter filter, Sorter sorter, Page page) {
        filter = Objects.nonNull(filter) ? filter : new Filter();
        filter.put("type", new String[]{Content.CONTENT_TYPE_COMMENT});
        PageSlice<ContentArticleVO> slice = articleService.findAllIntegrally(filter, sorter, page);
        PageSlice<CommentVO> commentPageSlice = new PageSlice<>();
        commentPageSlice.setLimit(slice.getLimit());
        commentPageSlice.setPage(slice.getPage());
        commentPageSlice.setTotal(slice.getTotal());
        commentPageSlice.setList(slice.getList().stream().map(this::mapping).collect(Collectors.toList()));
        return commentPageSlice;
    }

    @Override
    public CommentVO findByIdIntegrally(String contentId) {
        ContentArticleVO articleVO = articleService.findByIdIntegrally(contentId);
        if (Objects.nonNull(articleVO) && !Objects.equals(articleVO.getType(), Content.CONTENT_TYPE_COMMENT)) {
            return null;
        }
        return mapping(articleVO);
    }
}
