package com.codimiracle.web.middleware.content.service.impl;

import com.codimiracle.web.middleware.content.contract.AbstractService;
import com.codimiracle.web.middleware.content.extension.ExaminatedPostProcessor;
import com.codimiracle.web.middleware.content.inflation.ExaminerInflater;
import com.codimiracle.web.middleware.content.mapper.ExaminationMapper;
import com.codimiracle.web.middleware.content.pojo.po.ContentArticle;
import com.codimiracle.web.middleware.content.pojo.po.ContentExamination;
import com.codimiracle.web.middleware.content.pojo.vo.ContentExaminationVO;
import com.codimiracle.web.middleware.content.service.ArticleService;
import com.codimiracle.web.middleware.content.service.ContentService;
import com.codimiracle.web.middleware.content.service.ExaminationService;
import com.codimiracle.web.mybatis.contract.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Transactional
@Service
public class ExaminationServiceImpl extends AbstractService<String, ContentExamination, ContentExaminationVO> implements ExaminationService {
    @Resource
    private ContentService contentService;
    @Resource
    private ArticleService articleService;
    @Resource
    private ExaminationMapper examinationMapper;
    @Autowired
    private List<ExaminatedPostProcessor> examinatedPostProcessors;

    @Autowired(required = false)
    private ExaminerInflater examinerInflater;

    public ExaminationServiceImpl() {
    }

    @Override
    protected ContentExaminationVO mutate(ContentExaminationVO inflatedObject) {
        if (Objects.nonNull(inflatedObject)) {
            if (Objects.nonNull(examinerInflater)) {
                examinerInflater.inflate(inflatedObject);
            }
        }
        return inflatedObject;
    }

    @Override
    public ContentExaminationVO findLastByContentIdIntegrally(String contentId) {
        return mutate(examinationMapper.selectLastByContentId(contentId));
    }

    private void examinate(String contentId, String examinerId, String reason, String toStatus) {
        ContentArticle article = articleService.findById(contentId);
        if (Objects.isNull(article)) {
            throw new ServiceException("无法找到内容！");
        }
        String fromStatus = article.getStatus();
        // 更新评审后状态
        ContentArticle updatingArticle = new ContentArticle();
        updatingArticle.setStatus(toStatus);
        updatingArticle.setContentId(article.getContentId());
        articleService.update(updatingArticle);
        // 回写状态
        article.setStatus(toStatus);
        //写入评审结果
        ContentExamination examination = new ContentExamination();
        examination.setReason(reason);
        examination.setTargetContentId(contentId);
        examination.setExaminedAt(new Date());
        examination.setFromStatus(fromStatus);
        examination.setToStatus(toStatus);
        examination.setExaminerId(examinerId);
        save(examination);
        // 发出评审结果
        examinatedPostProcessors.forEach((listener) -> listener.onExaminated(article, fromStatus, toStatus));
    }

    @Override
    public void revoke(String examinationId) {
        ContentExamination examination = findById(examinationId);
        if (Objects.isNull(examination)) {
            throw new ServiceException("没有找到对应的评审记录！");
        }
        ContentArticle updatingArticle = new ContentArticle();
        updatingArticle.setContentId(examination.getTargetContentId());
        updatingArticle.setStatus(examination.getFromStatus());
        articleService.update(updatingArticle);
        ContentExamination updatingExamination = new ContentExamination();
        updatingExamination.setId(examination.getId());
        updatingExamination.setRevoked(true);
        updatingExamination.setRevokedAt(new Date());
        update(updatingExamination);
    }

    @Override
    public void accept(String contentId, String examinerId, String reason) {
        examinate(contentId, examinerId, reason, ContentArticle.CONTENT_STATUS_PUBLISHED);
    }

    @Override
    public void reject(String contentId, String examinerId, String reason) {
        examinate(contentId, examinerId, reason, ContentArticle.CONTENT_STATUS_REJECTED);
    }

    @Override
    public void addPostProcessor(ExaminatedPostProcessor postProcessor) {
        examinatedPostProcessors.add(postProcessor);
    }

    @Override
    public void removePostProcessor(ExaminatedPostProcessor postProcessor) {
        examinatedPostProcessors.remove(postProcessor);
    }
}
