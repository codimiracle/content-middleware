package com.codimiracle.web.middleware.content.service;

import com.codimiracle.web.middleware.content.extension.ExaminatedPostProcessor;
import com.codimiracle.web.middleware.content.pojo.po.ContentExamination;
import com.codimiracle.web.middleware.content.contract.Service;
import com.codimiracle.web.middleware.content.pojo.vo.ContentExaminationVO;

public interface ExaminationService extends Service<String, ContentExamination, ContentExaminationVO> {
    ContentExaminationVO findLastByContentIdIntegrally(String contentId);

    void addPostProcessor(ExaminatedPostProcessor postProcessor);
    void removePostProcessor(ExaminatedPostProcessor postProcessor);

    void revoke(String examinationId);

    void accept(String contentId, String examinerId, String reason);

    void reject(String contentId, String examinerId, String reason);
}
