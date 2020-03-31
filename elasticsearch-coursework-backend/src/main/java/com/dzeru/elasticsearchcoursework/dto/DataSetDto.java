package com.dzeru.elasticsearchcoursework.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class DataSetDto {
    private String label;

    private boolean fill;

    private float lineTension;

    private String backgroundColor;

    private String borderColor;

    private String borderCapStyle;

    private List<Object> borderDash;

    private float borderDashOffset;

    private String borderJoinStyle;

    private String pointBorderColor;

    private String pointBackgroundColor;

    private float pointBorderWidth;

    private float pointHoverRadius;

    private String pointHoverBackgroundColor;

    private String pointHoverBorderColor;

    private float pointHoverBorderWidth;

    private float pointRadius;

    private float pointHitRadius;

    private List<Long> data;

    public DataSetDto(String label, List<Long> data) {
        this.label = label;
        this.data = data;

        this.fill = false;
        this.lineTension = 0.1f;
        this.backgroundColor = "rgba(75,192,192,0.4)";
        this.borderColor = "orange";
        this.borderCapStyle = "butt";
        this.borderDash = new ArrayList<>();
        this.borderDashOffset = 0.0f;
        this.borderJoinStyle = "miter";
        this.pointBorderColor = "rgba(75,192,192,1)";
        this.pointBackgroundColor = "#fff";
        this.pointBorderWidth = 1;
        this.pointHoverRadius = 5;
        this.pointHoverBackgroundColor = "rgba(75,192,192,1)";
        this.pointHoverBorderColor = "rgba(220,220,220,1)";
        this.pointHoverBorderWidth = 2;
        this.pointRadius = 1;
        this.pointHitRadius = 10;
    }
}