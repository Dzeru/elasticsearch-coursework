package com.dzeru.elasticsearchcoursework.services;

import com.dzeru.elasticsearchcoursework.dto.DocumentWordCount;
import com.dzeru.elasticsearchcoursework.dto.WordCount;
import com.dzeru.elasticsearchcoursework.entities.HabrDocument;
import com.dzeru.elasticsearchcoursework.repositories.HabrDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

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

    public List<WordCount> getDataByWord(String word, String documentType) {
        switch(documentType) {
            case "habr": {
                List<HabrDocument> documents = habrDocumentRepository.findByWord(word);
                return habrWordCounter.countByWord(word, documents);
            }
            default: return Collections.emptyList();
        }
    }

    public DocumentWordCount getDataByDocument(String word, String documentType) {
        switch(documentType) {
            case "habr": {
                List<HabrDocument> documents = habrDocumentRepository.findByWord(word);
                return habrWordCounter.countWordByDocument(word, documents);
            }
            default: return new DocumentWordCount(word);
        }
    }
}
