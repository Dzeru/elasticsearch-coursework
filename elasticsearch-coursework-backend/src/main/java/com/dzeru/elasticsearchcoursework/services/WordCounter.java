package com.dzeru.elasticsearchcoursework.services;

import com.dzeru.elasticsearchcoursework.dto.DocumentWordCount;
import com.dzeru.elasticsearchcoursework.dto.WordCount;
import com.dzeru.elasticsearchcoursework.entities.AbstractDocument;
import com.dzeru.elasticsearchcoursework.util.CountMode;

import java.util.List;
import java.util.Map;

public interface WordCounter {
    Map<String, List<WordCount>> countAllWords(Iterable<? extends AbstractDocument> documents, CountMode countMode);
    List<WordCount> countByWord(String word, Iterable<? extends AbstractDocument> documents, CountMode countMode);
    DocumentWordCount countWordByDocument(String word, Iterable<? extends AbstractDocument> documents);
}
