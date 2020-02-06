package com.dzeru.elasticsearchcoursework.controllers;

import com.dzeru.elasticsearchcoursework.dto.DocumentWordCount;
import com.dzeru.elasticsearchcoursework.services.ChartDataServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/chart")
@RestController
public class ChartController {

    @Autowired
    private ChartDataServiceImpl chartDataService;

    @GetMapping("/test")
    public DocumentWordCount test(@RequestParam("word") String word) {
        return chartDataService.getDataByDocument(word, "habr");
    }
}
