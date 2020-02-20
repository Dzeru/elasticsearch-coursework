package com.dzeru.elasticsearchcoursework.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ChartDto {
    List<String> labels;
    List<DataSetDto> datasets;

    public ChartDto(List<String> labels, DataSetDto dataSetDto) {
        this.labels = labels;
        datasets = new ArrayList<>();
        datasets.add(dataSetDto);
    }
}
