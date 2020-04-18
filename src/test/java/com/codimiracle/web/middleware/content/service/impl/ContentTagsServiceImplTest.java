package com.codimiracle.web.middleware.content.service.impl;

import com.codimiracle.web.middleware.content.TestWithBeans;
import com.codimiracle.web.middleware.content.pojo.po.ContentTag;
import com.codimiracle.web.middleware.content.service.ContentTagsService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TestWithBeans.class)
class ContentTagsServiceImplTest {

    @Resource
    private ContentTagsService contentTagsService;

    @Test
    void findByContentId() {
        for (int i = 0; i < 20; i++) {
            ContentTag contentTag = new ContentTag();
            contentTag.setContentId("1000");
            contentTag.setTagId("" + i + 1);
            contentTagsService.save(contentTag);
        }
        List<ContentTag> results = contentTagsService.findByContentId("1000");
        assertEquals(20, results.size());
    }

    @Test
    void findTagByContentId() {
        for (int i = 0; i < 20; i++) {
            ContentTag contentTag = new ContentTag();
            contentTag.setContentId("4000");
            contentTag.setTagId("" + i + 1);
            contentTagsService.save(contentTag);
        }
        contentTagsService.findTagByContentId("4000");
    }

    @Test
    void save() {
        ContentTag contentTag = new ContentTag();
        contentTag.setContentId("1");
        contentTag.setTagId("1");
        contentTagsService.save(contentTag);
        ContentTag result = contentTagsService.findById(contentTag.getId());
        assertNotNull(result);
    }

    @Test
    void testSaveList() {
        List<ContentTag> contentTags = IntStream.range(0, 10).mapToObj((e) -> {
            ContentTag contentTag = new ContentTag();
            contentTag.setContentId("40000");
            contentTag.setTagId("20" + e);
            return contentTag;
        }).collect(Collectors.toList());
        contentTagsService.save(contentTags);
        List<ContentTag> results = contentTagsService.findByContentId("40000");
        assertEquals(contentTags.size(), results.size());
        ContentTag contentTag = results.get(results.size() - 1);
        assertFalse(contentTag.getDeleted());
        assertEquals("40000", contentTag.getContentId());
        assertEquals("209", contentTag.getTagId());
        assertNull(contentTag.getTag());
    }

    @Test
    void deleteByIdLogically() {
        ContentTag contentTag = new ContentTag();
        contentTag.setContentId("1");
        contentTag.setTagId("1");
        contentTagsService.save(contentTag);
        contentTagsService.deleteByIdLogically(contentTag.getId());
        ContentTag result = contentTagsService.findById(contentTag.getId());
        assertTrue(result.getDeleted());
        assertNotNull(result.getDeletedAt());
        assertEquals(result.getContentId(), contentTag.getContentId());
        assertEquals(result.getTagId(), contentTag.getTagId());
    }
}