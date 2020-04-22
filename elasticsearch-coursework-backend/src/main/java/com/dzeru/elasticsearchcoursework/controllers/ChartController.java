package com.dzeru.elasticsearchcoursework.controllers;

import com.dzeru.elasticsearchcoursework.dto.ChartDto;
import com.dzeru.elasticsearchcoursework.dto.DataSetDto;
import com.dzeru.elasticsearchcoursework.dto.WordCount;
import com.dzeru.elasticsearchcoursework.repositories.HabrDocumentRepository;
import com.dzeru.elasticsearchcoursework.services.impl.HabrWordCounterImpl;
import com.dzeru.elasticsearchcoursework.util.CountMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/chart")
public class ChartController {

    private final HabrWordCounterImpl habrWordCounter;
    private final HabrDocumentRepository habrDocumentRepository;

    private static final Random random = new Random();
    private static String rgbaTemplate = "rgba(%d,%d,%d,1)";

    @Autowired
    public ChartController(HabrWordCounterImpl habrWordCounter,
                           HabrDocumentRepository habrDocumentRepository) {
        this.habrWordCounter = habrWordCounter;
        this.habrDocumentRepository = habrDocumentRepository;
    }

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
    public ChartDto test2(@RequestParam("words") String words,
                          @RequestParam("countMode") String countMode,
                          @RequestParam("stemmerType") String stemmerType) {
        String[] wordList = words.split(",");

        List<WordCount> wordCounts = new ArrayList<>();
        List<DataSetDto> dataSetDtos = new ArrayList<>();

        for(String word : wordList) {
            WordCount wordCount = habrWordCounter.countDocumentHowManyWord(
                    word, habrDocumentRepository.findAll(),
                    CountMode.valueOf(countMode));
            wordCounts.add(wordCount);
        }

        Set<String> labels = new TreeSet<>();

        for(WordCount wordCount : wordCounts) {
            labels.addAll(wordCount.getCounts().keySet());
        }

        for(WordCount wordCount : wordCounts) {
            Map<String, Long> counts = wordCount.getCounts();
            List<Long> data = new ArrayList<>();

            for(String label : labels) {
                data.add(counts.getOrDefault(label, 0L));
            }
            DataSetDto dataSetDto = new DataSetDto(wordCount.getWord(), data);
            dataSetDto.setBorderColor(String.format(
                    rgbaTemplate,
                    (random.nextInt() % 255),
                    (random.nextInt() % 255),
                    (random.nextInt() % 255))
            );
            dataSetDtos.add(dataSetDto);
        }

        ChartDto chartDto = new ChartDto();
        chartDto.setLabels(labels);
        chartDto.setDatasets(dataSetDtos);
        return chartDto;
    }
}
