package com.dzeru.elasticsearchcoursework.services;

import com.dzeru.elasticsearchcoursework.dto.WordCount;
import com.dzeru.elasticsearchcoursework.entities.AbstractDocument;

import java.util.List;
import java.util.Map;

public interface WordCounter {
    Map<String, List<WordCount>> countAllWords(Iterable<? extends AbstractDocument> documents);
    List<WordCount> countByWord(String word, Iterable<? extends AbstractDocument> documents);
}
