package com.dzeru.elasticsearchcoursework.controllers;

import com.dzeru.elasticsearchcoursework.entities.HabrDocument;
import com.dzeru.elasticsearchcoursework.processors.processors.WhitespaceProcessor;
import com.dzeru.elasticsearchcoursework.repositories.HabrDocumentRepository;
import com.dzeru.elasticsearchcoursework.services.DocumentExtractor;
import com.dzeru.elasticsearchcoursework.services.WordCounter;
import com.dzeru.elasticsearchcoursework.services.impl.HabrDocumentExtractorImpl;
import com.dzeru.elasticsearchcoursework.util.CountMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class Test {

    @Autowired
    private HabrDocumentRepository habrDocumentRepository;

    @Autowired
    private HabrDocumentExtractorImpl documentExtractor;

    @Autowired
    private WordCounter wordCounter;

    @Autowired
    private WhitespaceProcessor whitespaceProcessor;

    @GetMapping("/t")
    public Iterable<HabrDocument> b() {
        return habrDocumentRepository.findAll();
    }


    @GetMapping("/f/{find}")
    public List<HabrDocument> find(@PathVariable("find") String find) {
        return habrDocumentRepository.findByWord(find);
    }
}
