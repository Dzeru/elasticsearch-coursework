package com.dzeru.elasticsearchcoursework.controllers;

import com.dzeru.elasticsearchcoursework.dto.*;
import com.dzeru.elasticsearchcoursework.services.ChartDataServiceImpl;
import com.dzeru.elasticsearchcoursework.util.DateFormats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RequestMapping("/chart")
@RestController
public class ChartController {

    @Autowired
    private ChartDataServiceImpl chartDataService;

    private static final Random random = new Random();
    private static String rgbaTemplate = "rgba(%d,%d,%d,1)";

    /*@GetMapping("/test")
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
    }*/

    @GetMapping("/test2")
    public ChartDto test2(@RequestParam("words") String words) {
        List<String> wordList = Arrays.asList(words.split(","));
        List<DocumentWordCount> documentWordCounts = chartDataService.getDataByDocument(wordList, "habr");

        List<String> labels = new ArrayList<>();
        List<DataSetDto> dataSetDtos = new ArrayList<>();

        for(DocumentWordCount documentWordCount : documentWordCounts) {
            labels.addAll(documentWordCount.getCounts()
                    .stream()
                    .sorted(Comparator.comparing(DocumentWordCountEntry::getDate))
                    .map(d -> DateFormats.CUSTOM_DATE_FORMAT.format(d.getDate()))
                    .collect(Collectors.toList()));
        }

        for(DocumentWordCount documentWordCount : documentWordCounts) {
            List<DocumentWordCountEntry> countEntries = documentWordCount.getCounts();
            List<String> documentEntryLabels = countEntries
                    .stream()
                    .map(d -> DateFormats.CUSTOM_DATE_FORMAT.format(d.getDate()))
                    .collect(Collectors.toList());

            List<Integer> data = new ArrayList<>(labels.size());

            for(String label : labels) {
                if(documentEntryLabels.contains(label)) {
                    data.add(countEntries.get(documentEntryLabels.indexOf(label)).getCount());
                } else {
                    data.add(0);
                }
            }

            DataSetDto dataSetDto = new DataSetDto(documentWordCount.getWord(), data);
            dataSetDto.setBorderColor(String.format(rgbaTemplate, (random.nextInt() % 255), (random.nextInt() % 255), (random.nextInt() % 255)));
            dataSetDtos.add(dataSetDto);
        }

        ChartDto chartDto = new ChartDto();
        chartDto.setLabels(labels);
        chartDto.setDatasets(dataSetDtos);
        return chartDto;
    }
}
