package com.dzeru.elasticsearchcoursework.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class VkExtractorParams extends AbstractExtractorParams {

    private String author;
    private Long authorId;

    public VkExtractorParams(String author, Long authorId, List<String> postIds) {
        super(postIds);
        this.author = author;
        this.authorId = authorId;
    }
}

