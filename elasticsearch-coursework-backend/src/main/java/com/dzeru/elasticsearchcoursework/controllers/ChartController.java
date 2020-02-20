package com.dzeru.elasticsearchcoursework.controllers;

import com.dzeru.elasticsearchcoursework.dto.*;
import com.dzeru.elasticsearchcoursework.services.ChartDataServiceImpl;
import com.dzeru.elasticsearchcoursework.util.DateFormats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/chart")
@RestController
public class ChartController {

    @Autowired
    private ChartDataServiceImpl chartDataService;

    @GetMapping("/test")
    public ChartDto test(@RequestParam("word") String word) {
        DocumentWordCount documentWordCount = chartDataService.getDataByDocument(word, "habr");
        List<String> labels = documentWordCount.getCounts()
                .stream()
                .sorted(Comparator.comparing(DocumentWordCountEntry::getDate))
                .map(d -> DateFormats.CUSTOM_DATE_FORMAT.format(d.getDate()))
                .collect(Collectors.toList());
        List<Integer> data = documentWordCount.getCounts()
                .stream()
                .map(DocumentWordCountEntry::getCount)
                .collect(Collectors.toList());
        return new ChartDto(labels, new DataSetDto(documentWordCount.getWord(), data));
    }
}
