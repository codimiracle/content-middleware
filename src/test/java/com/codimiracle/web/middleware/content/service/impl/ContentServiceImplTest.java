package com.codimiracle.web.middleware.content.service.impl;

import com.codimiracle.web.middleware.content.TestWithBeans;
import com.codimiracle.web.middleware.content.pojo.po.Content;
import com.codimiracle.web.middleware.content.pojo.vo.ContentVO;
import com.codimiracle.web.middleware.content.service.ContentService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TestWithBeans.class)
class ContentServiceImplTest {
    @Resource
    private ContentService contentService;

    @Test
    void deleteLogic() {
        Content content = new Content();
        content.setType("test-content");
        contentService.save(content);
        contentService.deleteByIdLogically(content.getId());
        Content result = contentService.findById(content.getId());
        assertNotNull(result);
        assertTrue(result.getDeleted());
        assertNotNull(result.getDeletedAt());
    }

    @Test
    void save() {
        Content content = new Content();
        content.setType("test-content");
        contentService.save(content);
        Content result = contentService.findById(content.getId());
        assertNotNull(result);
        assertEquals(0, result.getComments());
        assertEquals(0, result.getReposts());
        assertEquals(0, result.getLikes());
        assertEquals(0, result.getDislikes());
        assertFalse(result.getDeleted());
    }

    @Test
    void testSaveList() {
        List<Content> contents = IntStream.range(0, 10).mapToObj((e) -> {
            return new Content();
        }).collect(Collectors.toList());
        contentService.save(contents);
        List<Content> result = contentService.findAll();
        assertTrue(result.size() >= 10);
        Content last = result.get(result.size() - 1);
        assertNotNull(result);
        assertEquals(0, last.getComments());
        assertEquals(0, last.getReposts());
        assertEquals(0, last.getLikes());
        assertEquals(0, last.getDislikes());
        assertFalse(last.getDeleted());
    }

    @Test
    void mutate() {
        Content content = new Content();
        content.setType("text-content");
        contentService.save(content);
        ContentVO vo = ((ContentServiceImpl) contentService).mutate(contentService.findByIdIntegrally(content.getId()));
        assertNotNull(vo.getOwner());
        assertNotNull(vo.getRateList());
        assertNotNull(vo.getTagList());
    }

    @Test
    void increaseStatistics() {
        Content content = new Content();
        content.setType("test-content");
        contentService.save(content);
        contentService.updateStatistics(ContentService.STATISTICS_FIELD_LIKES, content.getId(), 1);
        contentService.updateStatistics(ContentService.STATISTICS_FIELD_DISLIKES, content.getId(), 2);
        contentService.updateStatistics(ContentService.STATISTICS_FIELD_COMMENTS, content.getId(), 3);
        contentService.updateStatistics(ContentService.STATISTICS_FIELD_REPOSTS, content.getId(), 4);
        Content result = contentService.findById(content.getId());
        assertEquals(content.getType(), result.getType());
        assertEquals(result.getLikes(), 1);
        assertEquals(result.getDislikes(), 2);
        assertEquals(result.getComments(), 3);
        assertEquals(result.getReposts(), 4);
    }
}