package com.codimiracle.web.middleware.content.service.impl;

import com.codimiracle.web.middleware.content.TestWithBeans;
import com.codimiracle.web.middleware.content.pojo.eo.MentionUser;
import com.codimiracle.web.middleware.content.pojo.po.Content;
import com.codimiracle.web.middleware.content.pojo.po.ContentMention;
import com.codimiracle.web.middleware.content.service.ContentService;
import com.codimiracle.web.middleware.content.service.MentionService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = TestWithBeans.class)
class MentionServiceImplTest {

    @Resource
    private MentionService mentionService;
    @Resource
    private ContentService contentService;

    @Test
    void testSave() {
        Content content = new Content();
        content.setType("test-content");
        contentService.save(content);
        ContentMention mention = new ContentMention();
        mention.setContentId(content.getId());
        mention.setMentionUserId("11");
        mentionService.save(mention);
        List<ContentMention> mentions = mentionService.findByContentId(mention.getContentId());
        ContentMention result = mentions.get(0);
        assertEquals(mention.getMentionUserId(), result.getMentionUserId());
        assertEquals(mention.getContentId(), result.getContentId());
    }

    @Test
    void testSaveList() {
        Content content = new Content();
        content.setType("test-content");
        contentService.save(content);
        List<ContentMention> mentions = IntStream.range(0, 10).mapToObj((e) -> {
            ContentMention mention = new ContentMention();
            mention.setContentId(content.getId());
            mention.setMentionUserId("220" + e);
            return mention;
        }).collect(Collectors.toList());
        mentionService.save(mentions);
        List<ContentMention> mentionList = mentionService.findByContentId(content.getId());
        assertEquals(10, mentionList.size());
    }

    @Test
    void findByContentId() {
        Content content = new Content();
        content.setType("test-content");
        contentService.save(content);
        ContentMention mention = new ContentMention();
        mention.setContentId(content.getId());
        mention.setMentionUserId("11");
        mentionService.save(mention);
        List<ContentMention> mentions = mentionService.findByContentId(mention.getContentId());
        assertEquals(1, mentions.size());
    }

    @Test
    void findMentionUserByContentId() {
        Content content = new Content();
        content.setType("test-content");
        contentService.save(content);
        ContentMention mention = new ContentMention();
        mention.setContentId(content.getId());
        mention.setMentionUserId("11");
        mentionService.save(mention);
        List<MentionUser> mentionUser = mentionService.findMentionUserByContentId(content.getId());
        assertEquals(1, mentionUser.size());
    }
}