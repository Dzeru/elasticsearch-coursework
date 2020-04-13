package com.dzeru.elasticsearchcoursework.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class HabrExtractorParams extends AbstractExtractorParams {
    private List<String> postIds;

    public HabrExtractorParams(List<String> postIds) {
        this.postIds = postIds;
    }
}
