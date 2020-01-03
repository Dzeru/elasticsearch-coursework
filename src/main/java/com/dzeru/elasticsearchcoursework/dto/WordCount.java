package com.dzeru.elasticsearchcoursework.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class WordCount {
    private String word;
    private long count;
    private List<Date> dates;

    public WordCount(String word, long count, Date date) {
        this.word = word;
        this.count = count;
        this.dates = new ArrayList<>();
        dates.add(date);
    }

    public void addDate(Date date) {
        if(!dates.contains(date)) {
            this.dates.add(date);
        }
    }
}
