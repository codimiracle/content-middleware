# content-middleware
for content-based service, we need to implement Content like topic, article, comment, movie, music and so on. the content-middleware can finish those requirements.

considering extensible, we abstract some interface with two concept:

* Inflatable: meaning that the object attach another object likes user, tag, and so on.
* Inflater: meaning that will mutate inflatable object to really object. commonly, it will access data source (as database) and generate correctly object.

# Feature
the middleware provides some useful content service implementation for content-base service.
* ContentService
* ArticleService
* ContentTagsService
* ReferenceService
* RateService
* MentionService
* LikeService
* ExaminationService
* FollowService

# Usage
1. includes content-middleware in `pom.xml` (see [Maven Section](#Maven))
2. register beans with `@ComponentScan`:
    
     ```java
     @ComponentScan(basePackages = "com.codimiracle.web.middleware.content")
     ```

3. implements inflaters
   
   if you using vo convenience, you will be implement those inflater for getting an entire object.
   
   * ExaminerInflater
   * MentionUserInflater
   * ReferenceTargetInflater
   * OwnerInflater
   * FollowingUserInflater
   * FollowerInflater
   * TagInflater
    
## Maven
(notes: doesn't publish to maven central yet)
```xml
<dependency>
    <groupId>com.codimiracle.web.middleware</groupId>
    <artifactId>content-middleware</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

  
