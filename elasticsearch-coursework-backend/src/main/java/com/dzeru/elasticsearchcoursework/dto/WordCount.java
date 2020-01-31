package com.dzeru.elasticsearchcoursework.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class WordCount {
    private String word;
    private long count;
    private Date date;

    public WordCount(String word, long count, Date date) {
        this.word = word;
        this.count = count;
        this.date = date;
    }
}
