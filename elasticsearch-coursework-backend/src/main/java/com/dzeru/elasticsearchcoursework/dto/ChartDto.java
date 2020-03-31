package com.dzeru.elasticsearchcoursework.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class ChartDto {
    Set<String> labels;
    List<DataSetDto> datasets;

    public ChartDto(Set<String> labels, DataSetDto dataSetDto) {
        this.labels = labels;
        datasets = new ArrayList<>();
        datasets.add(dataSetDto);
    }
}
