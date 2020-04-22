package com.dzeru.elasticsearchcoursework.controllers;

import com.dzeru.elasticsearchcoursework.entities.HabrDocument;
import com.dzeru.elasticsearchcoursework.processors.processors.WhitespaceProcessor;
import com.dzeru.elasticsearchcoursework.repositories.HabrDocumentRepository;
import com.dzeru.elasticsearchcoursework.services.WordCounter;
import com.dzeru.elasticsearchcoursework.services.impl.extractors.HabrDocumentExtractorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.List;

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

    @GetMapping("/f/{find}/{beginDate}/{endDate}")
    public List<HabrDocument> findD(@PathVariable("find") String find,
                                    @PathVariable("beginDate") String beginDate,
                                    @PathVariable("endDate") String endDate) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long beg = dateFormat.parse(beginDate).getTime();
        long end = dateFormat.parse(endDate).getTime();
        List<HabrDocument> d = habrDocumentRepository.findByWordAndDate(find, beg, end);
        for(HabrDocument dd : d) {
            System.out.println(dateFormat.format(dd.getPostTime()));
        }
        System.out.println("---");
        return d;
    }
}
