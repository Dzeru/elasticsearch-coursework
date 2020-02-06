package com.dzeru.elasticsearchcoursework.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class HabrExtractorParams extends AbstractExtractorParams {
    private List<String> postIds;
}
