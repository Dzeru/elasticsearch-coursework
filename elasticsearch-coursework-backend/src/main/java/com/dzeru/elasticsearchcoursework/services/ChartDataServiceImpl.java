package com.dzeru.elasticsearchcoursework.services;

import com.dzeru.elasticsearchcoursework.dto.DocumentWordCount;
import com.dzeru.elasticsearchcoursework.dto.WordCount;
import com.dzeru.elasticsearchcoursework.entities.HabrDocument;
import com.dzeru.elasticsearchcoursework.repositories.HabrDocumentRepository;
import com.dzeru.elasticsearchcoursework.util.CountMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChartDataServiceImpl {

    private final HabrWordCounterImpl habrWordCounter;
    private final HabrDocumentRepository habrDocumentRepository;

    @Autowired
    public ChartDataServiceImpl(HabrWordCounterImpl habrWordCounter,
                                HabrDocumentRepository habrDocumentRepository) {
        this.habrWordCounter = habrWordCounter;
        this.habrDocumentRepository = habrDocumentRepository;
    }

    /*public WordCount getWordCount(String word,
                                  String documentType,

                                  CountMode countMode) {
        switch(documentType) {
            case "habr": {
                List<HabrDocument> documents = habrDocumentRepository.findByWord(word);
                return habrWordCounter.countByWord(word, documents, countMode);
            }
            default: return Collections.emptyList();
        }
    }*/
}
