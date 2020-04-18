package com.codimiracle.web.middleware.content.inflation;

import com.codimiracle.web.middleware.content.pojo.eo.Examiner;

public interface ExaminerInflatable {
    String getExaminerId();
    void setExaminer(Examiner examiner);
    Examiner getExaminer();
}
