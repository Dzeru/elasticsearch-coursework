package com.dzeru.elasticsearchcoursework.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HabrExtractorParams extends AbstractExtractorParams {
    private String[] postIds;
}
