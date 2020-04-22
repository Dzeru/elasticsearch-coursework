package com.dzeru.elasticsearchcoursework.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class HabrExtractorParams extends AbstractExtractorParams {

    public HabrExtractorParams(List<String> postIds) {
        super(postIds);
    }
}
