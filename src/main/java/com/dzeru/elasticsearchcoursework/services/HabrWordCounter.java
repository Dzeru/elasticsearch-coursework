package com.dzeru.elasticsearchcoursework.services;

import com.dzeru.elasticsearchcoursework.dto.WordCount;
import com.dzeru.elasticsearchcoursework.entities.AbstractDocument;
import com.dzeru.elasticsearchcoursework.entities.HabrDocument;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class HabrWordCounter implements WordCounter {

    private Map<String, WordCount> wordCountMap;

    @Override
    public Map<String, WordCount> count(Iterable<? extends AbstractDocument> documents) {
        wordCountMap = new HashMap<>();
        Iterable<HabrDocument> docs = new ArrayList<>();
        try {
            docs = (Iterable<HabrDocument>) documents;
        }
        catch(ClassCastException e) {
            e.printStackTrace();
        }

        docs.forEach(habrDocument -> {
            countWords(habrDocument, habrDocument.getStemmedHeader());
            countWords(habrDocument, habrDocument.getStemmedBody());
        });

        return wordCountMap;
    }

    private void countWords(HabrDocument habrDocument, String string) {
        String[] words = string.split(" ");

        for(String word : words) {
            if(wordCountMap.containsKey(word)) {
                wordCountMap.get(word).setCount(wordCountMap.get(word).getCount() + 1);
                wordCountMap.get(word).addDate(habrDocument.getPostTime());
            }
            else {
                wordCountMap.put(word, new WordCount(word, 1, habrDocument.getPostTime()));
            }
        }
    }
}
