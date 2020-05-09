package com.dzeru.elasticsearchcoursework.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public abstract class AbstractExtractorParams {
    private List<String> postIds;

    public AbstractExtractorParams(List<String> postIds) {
        this.postIds = postIds;
    }
}
