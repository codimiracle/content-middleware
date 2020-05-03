package com.codimiracle.web.middleware.content.inflation;

import com.codimiracle.web.middleware.content.pojo.eo.Examiner;
import com.codimiracle.web.mybatis.contract.support.vo.inflation.Inflatable;

public interface ExaminerInflatable extends Inflatable {
    String getExaminerId();
    void setExaminer(Examiner examiner);
    Examiner getExaminer();
}
