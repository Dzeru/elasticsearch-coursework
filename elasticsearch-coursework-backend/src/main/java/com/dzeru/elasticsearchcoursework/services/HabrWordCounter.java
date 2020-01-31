package com.dzeru.elasticsearchcoursework.services;

import com.dzeru.elasticsearchcoursework.dto.WordCount;
import com.dzeru.elasticsearchcoursework.entities.AbstractDocument;
import com.dzeru.elasticsearchcoursework.entities.HabrDocument;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HabrWordCounter implements WordCounter {

    private Map<String, List<WordCount>> wordCountMap;

    @Override
    public Map<String, List<WordCount>> countAllWords(Iterable<? extends AbstractDocument> documents) {
        wordCountMap = new HashMap<>();
        Iterable<HabrDocument> docs = new ArrayList<>();
        try {
            docs = (Iterable<HabrDocument>) documents;
        }
        catch(ClassCastException e) {
            e.printStackTrace();
        }

        docs.forEach(habrDocument -> {
            countWordsAll(habrDocument, getCountedString(habrDocument));
        });

        return wordCountMap;
    }

    @Override
    public List<WordCount> countByWord(String word, Iterable<? extends AbstractDocument> documents) {
        List<WordCount> wordCountList = new ArrayList<>();
        Iterable<HabrDocument> docs = new ArrayList<>();
        try {
            docs = (Iterable<HabrDocument>) documents;
        }
        catch(ClassCastException e) {
            e.printStackTrace();
        }

        docs.forEach(habrDocument -> {
            WordCount wordCount = countWordsOneWord(habrDocument, getCountedString(habrDocument), word);

            if(wordCount.getCount() > 0) {
                wordCountList.add(wordCount);
            }
        });

        return wordCountList;
    }

    private void countWordsAll(HabrDocument habrDocument, String string) {
        String[] words = string.split(" ");
        Map<String, WordCount> wordCounts = new HashMap<>();

        for(String word : words) {
            if(wordCounts.containsKey(word)) {
                WordCount currentWordCount = wordCounts.get(word);
                currentWordCount.setCount(currentWordCount.getCount() + 1);
            }
            else {
                wordCounts.put(word, new WordCount(word, 1, habrDocument.getPostTime()));
            }
        }

        for(Map.Entry<String, WordCount> wordCountEntry : wordCounts.entrySet()) {
            String word = wordCountEntry.getKey();
            WordCount wordCount = wordCountEntry.getValue();

            if(wordCountMap.containsKey(word)) {
                wordCountMap.get(word).add(wordCount);
            }
            else {
                List<WordCount> wordCountList = new ArrayList<>();
                wordCountList.add(wordCount);
                wordCountMap.put(word, wordCountList);
            }
        }
    }

    private WordCount countWordsOneWord(HabrDocument habrDocument, String string, String countWord) {
        String[] words = string.split(" ");
        WordCount wordCount = new WordCount(countWord, 0, habrDocument.getPostTime());

        for(String word : words) {
            if(word.equalsIgnoreCase(countWord)) {
                wordCount.setCount(wordCount.getCount() + 1);
            }
        }

        return wordCount;
    }

    private String getCountedString(HabrDocument habrDocument) {
        return habrDocument.getStemmedHeader() + " " + habrDocument.getStemmedBody();
    }
}
