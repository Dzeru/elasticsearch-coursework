package com.dzeru.elasticsearchcoursework.services;

import com.dzeru.elasticsearchcoursework.dto.DocumentWordCount;
import com.dzeru.elasticsearchcoursework.dto.WordCount;
import com.dzeru.elasticsearchcoursework.entities.HabrDocument;
import com.dzeru.elasticsearchcoursework.repositories.HabrDocumentRepository;
import com.dzeru.elasticsearchcoursework.util.CountMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChartDataServiceImpl {

    private final HabrWordCounter habrWordCounter;
    private final HabrDocumentRepository habrDocumentRepository;

    @Autowired
    public ChartDataServiceImpl(HabrWordCounter habrWordCounter,
                                HabrDocumentRepository habrDocumentRepository) {
        this.habrWordCounter = habrWordCounter;
        this.habrDocumentRepository = habrDocumentRepository;
    }

    public List<WordCount> getDataByWord(String word, String documentType, CountMode countMode) {
        switch(documentType) {
            case "habr": {
                List<HabrDocument> documents = habrDocumentRepository.findByWord(word);
                return habrWordCounter.countByWord(word, documents, countMode);
            }
            default: return Collections.emptyList();
        }
    }

    public List<DocumentWordCount> getDataByDocument(List<String> words, String documentType) {
        switch(documentType) {
            case "habr": {
                List<DocumentWordCount> documentWordCounts = new ArrayList<>();
                for(String word : words) {
                    List<HabrDocument> documents = habrDocumentRepository.findByWord(word);
                    documentWordCounts.add(habrWordCounter.countWordByDocument(word, documents));
                }

                return documentWordCounts;
            }
            default: return new ArrayList<>();
        }
    }
}
