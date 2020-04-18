package com.codimiracle.web.middleware.content.service.impl;

import com.codimiracle.web.middleware.content.TestWithBeans;
import com.codimiracle.web.middleware.content.pojo.po.ContentReference;
import com.codimiracle.web.middleware.content.pojo.vo.ContentReferenceVO;
import com.codimiracle.web.middleware.content.service.ReferenceService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TestWithBeans.class)
class ReferenceServiceImplTest {


    @Resource
    private ReferenceService referenceService;

    @Test
    void save() {
        ContentReference contentReference = new ContentReference();
        contentReference.setReferenceTargetType("content-article");
        contentReference.setContentId("10000");
        contentReference.setReferenceTargetId("100200");
        referenceService.save(contentReference);
        assertNotNull(contentReference.getId());
        List<ContentReference> contentReferenceList = referenceService.findByContentId(contentReference.getContentId());
        assertEquals(1, contentReferenceList.size());
        ContentReference reference = contentReferenceList.get(0);
        assertEquals(contentReference.getReferenceTargetType(), reference.getReferenceTargetType());
        assertEquals(contentReference.getContentId(), reference.getContentId());
        assertEquals(contentReference.getReferenceTargetId(), reference.getReferenceTargetId());
    }

    @Test
    void testMutate() {
        ContentReferenceVO contentReferenceVO = new ContentReferenceVO();
        contentReferenceVO.setReferenceTargetId("hello");
        contentReferenceVO.setReferenceTargetType("content");
        ((ReferenceServiceImpl) referenceService).mutate(contentReferenceVO);
        assertNull(contentReferenceVO.getReferenceTarget());
    }

    @Test
    void findByContentIdIntegrally() {
        ContentReference contentReference = new ContentReference();
        contentReference.setReferenceTargetType("content-article");
        contentReference.setContentId("20000");
        contentReference.setReferenceTargetId("100200");
        referenceService.save(contentReference);
        List<ContentReferenceVO> referenceList = referenceService.findByContentIdIntegrally(contentReference.getContentId());
        assertEquals(1, referenceList.size());
        ContentReferenceVO referenceVO = referenceList.get(0);
        assertEquals(contentReference.getContentId(), referenceVO.getContentId());
        assertEquals(contentReference.getReferenceTargetId(), referenceVO.getReferenceTargetId());
        assertEquals(contentReference.getReferenceTargetType(), referenceVO.getReferenceTargetType());
    }
}