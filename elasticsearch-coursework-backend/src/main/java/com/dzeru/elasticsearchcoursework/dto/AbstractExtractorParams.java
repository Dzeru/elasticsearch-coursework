package com.dzeru.elasticsearchcoursework.dto;

import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public abstract class AbstractExtractorParams {
    private List<String> postIds;

    public AbstractExtractorParams(List<String> postIds) {
        this.postIds = postIds;
    }
}
