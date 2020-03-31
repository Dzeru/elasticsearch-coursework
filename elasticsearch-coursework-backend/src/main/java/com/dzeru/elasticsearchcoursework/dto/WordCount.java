package com.dzeru.elasticsearchcoursework.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class WordCount {

    private String word;

    //date - count
    private Map<String, Long> counts;

    public WordCount(String word, Map<String, Long> counts) {
        this.word = word;
        this.counts = counts;
    }
}
