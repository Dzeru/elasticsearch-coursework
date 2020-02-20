package com.dzeru.elasticsearchcoursework.services;

import com.dzeru.elasticsearchcoursework.dto.DocumentWordCount;
import com.dzeru.elasticsearchcoursework.dto.WordCount;
import com.dzeru.elasticsearchcoursework.entities.AbstractDocument;
import com.dzeru.elasticsearchcoursework.entities.HabrDocument;
import com.dzeru.elasticsearchcoursework.util.CountMode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HabrWordCounter implements WordCounter {

    private Map<String, List<WordCount>> wordCountMap;

    /*
    Count all words in documents
     */
    @Override
    public Map<String, List<WordCount>> countAllWords(Iterable<? extends AbstractDocument> documents,
                                                      CountMode countMode) {
        wordCountMap = new HashMap<>();
        Iterable<HabrDocument> docs = new ArrayList<>();
        try {
            docs = (Iterable<HabrDocument>) documents;
        }
        catch(ClassCastException e) {
            e.printStackTrace();
        }

        docs.forEach(habrDocument -> {
            countWordsAll(habrDocument, getCountedString(habrDocument, countMode));
        });

        return wordCountMap;
    }

    /*
    Count one word in documents
     */
    @Override
    public List<WordCount> countByWord(String word,
                                       Iterable<? extends AbstractDocument> documents,
                                       CountMode countMode) {
        List<WordCount> wordCountList = new ArrayList<>();
        Iterable<HabrDocument> docs = new ArrayList<>();
        try {
            docs = (Iterable<HabrDocument>) documents;
        }
        catch(ClassCastException e) {
            e.printStackTrace();
        }

        docs.forEach(habrDocument -> {
            WordCount wordCount = countWordsOneWord(
                    habrDocument,
                    getCountedString(habrDocument, countMode),
                    word
            );

            if(wordCount.getCount() > 0) {
                wordCountList.add(wordCount);
            }
        });

        return wordCountList;
    }

    /*
    Count how many documents contains the word
     */
    @Override
    public DocumentWordCount countWordByDocument(String word,
                                                 Iterable<? extends AbstractDocument> documents) {
        DocumentWordCount documentWordCount = new DocumentWordCount(word);

        Iterable<HabrDocument> docs = new ArrayList<>();
        try {
            docs = (Iterable<HabrDocument>) documents;
        }
        catch(ClassCastException e) {
            e.printStackTrace();
        }

        docs.forEach(habrDocument -> {
                documentWordCount.addDocumentCount(habrDocument.getPostTime());
        });

        return documentWordCount.build();
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

    private String getCountedString(HabrDocument habrDocument, CountMode countMode) {
        switch(countMode) {
            case ALL:
                return habrDocument.getStemmedHeader() + " " + habrDocument.getStemmedBody();
            case ONLY_BODY:
                return habrDocument.getStemmedBody();
            case ONLY_HEADER:
                return habrDocument.getStemmedHeader();
            default:
                return habrDocument.getStemmedHeader() + " " + habrDocument.getStemmedBody();
        }
    }
}
