package com.dzeru.elasticsearchcoursework.controllers;

import com.dzeru.elasticsearchcoursework.dto.HabrExtractorParams;
import com.dzeru.elasticsearchcoursework.services.DocumentExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/extract")
@RestController
public class HabrExtractorController {

    private final DocumentExtractor documentExtractor;

    @Autowired
    public HabrExtractorController(DocumentExtractor documentExtractor) {
        this.documentExtractor = documentExtractor;
    }

    @GetMapping("/habr")
    public int a(@RequestParam("postIds") String postIds) throws Exception {
        HabrExtractorParams params = new HabrExtractorParams();
        params.setPostIds(postIds.split(","));
        documentExtractor.extractDocument(params);
        return 0;
    }
}
