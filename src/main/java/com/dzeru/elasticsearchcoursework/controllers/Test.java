package com.dzeru.elasticsearchcoursework.controllers;

import com.dzeru.elasticsearchcoursework.entities.HabrDocument;
import com.dzeru.elasticsearchcoursework.repositories.HabrDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {

    @Autowired
    private HabrDocumentRepository habrDocumentRepository;

    @GetMapping("/t")
    public Iterable<HabrDocument> b() {
        return habrDocumentRepository.findAll();
    }
}
