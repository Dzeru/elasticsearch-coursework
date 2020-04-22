package com.dzeru.elasticsearchcoursework.services;

import com.dzeru.elasticsearchcoursework.repositories.HabrDocumentRepository;
import com.dzeru.elasticsearchcoursework.services.impl.counters.HabrWordPorterCounterImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChartDataServiceImpl {

    private final HabrWordPorterCounterImpl habrWordCounter;
    private final HabrDocumentRepository habrDocumentRepository;

    @Autowired
    public ChartDataServiceImpl(HabrWordPorterCounterImpl habrWordCounter,
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
