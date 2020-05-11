package com.codimiracle.web.middleware.content.pojo.eo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SimpleTag implements Tag {
    private String tagId;
    private String name;
}
