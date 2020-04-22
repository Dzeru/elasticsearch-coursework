package com.dzeru.elasticsearchcoursework.services.impl.counters;

import com.dzeru.elasticsearchcoursework.dto.WordCount;
import com.dzeru.elasticsearchcoursework.entities.AbstractDocument;
import com.dzeru.elasticsearchcoursework.entities.HabrDocument;
import com.dzeru.elasticsearchcoursework.services.WordCounter;
import com.dzeru.elasticsearchcoursework.util.CountMode;
import com.dzeru.elasticsearchcoursework.util.DateFormats;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HabrWordPorterCounterImpl implements WordCounter {

    public WordCount countDocumentContainsWord(String word,
                                               Iterable<? extends AbstractDocument> documents,
                                               CountMode countMode) {
        Iterable<HabrDocument> habrDocuments = new ArrayList<>();
        try {
            habrDocuments = (Iterable<HabrDocument>) documents;
        }
        catch(ClassCastException e) {
            e.printStackTrace();
        }

        Map<String, Long> counts = new TreeMap<>();
        for(HabrDocument habrDocument : habrDocuments) {
            String countedString = getCountedString(habrDocument, countMode);
            if(countedString.contains(word)) {
                String date = DateFormats.CUSTOM_DATE_FORMAT.format(habrDocument.getPostTime());
                if(counts.containsKey(date)) {
                    counts.put(date, counts.get(date) + 1);
                }
                else {
                    counts.put(date, 1L);
                }
            }
        }

        return new WordCount(word, counts);
    }


    public WordCount countDocumentHowManyWord(String word,
                                              Iterable<? extends AbstractDocument> documents,
                                              CountMode countMode) {
        Iterable<HabrDocument> habrDocuments = new ArrayList<>();
        try {
            habrDocuments = (Iterable<HabrDocument>) documents;
        }
        catch(ClassCastException e) {
            e.printStackTrace();
        }

        Map<String, Long> counts = new TreeMap<>();
        for(HabrDocument habrDocument : habrDocuments) {
            String countedString = getCountedString(habrDocument, countMode);
            if(countedString.contains(word)) {
                String date = DateFormats.CUSTOM_DATE_FORMAT.format(habrDocument.getPostTime());
                long count = Arrays.stream(countedString.split("\\s+"))
                        .filter(word::equalsIgnoreCase)
                        .count();
                if(counts.containsKey(date)) {
                    counts.put(date, counts.get(date) + count);
                }
                else {
                    counts.put(date, count);
                }
            }
        }

        return new WordCount(word, counts);

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
