package com.dzeru.elasticsearchcoursework.controllers;

import com.dzeru.elasticsearchcoursework.dto.ChartDto;
import com.dzeru.elasticsearchcoursework.dto.DataSetDto;
import com.dzeru.elasticsearchcoursework.dto.WordCount;
import com.dzeru.elasticsearchcoursework.repositories.HabrDocumentRepository;
import com.dzeru.elasticsearchcoursework.services.impl.counters.HabrWordPorterCounterImpl;
import com.dzeru.elasticsearchcoursework.util.Constants;
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

    private final HabrWordPorterCounterImpl habrWordPorterCounter;
    private final HabrDocumentRepository habrDocumentRepository;

    private static final Random random = new Random();
    private static String rgbaTemplate = "rgba(%d,%d,%d,1)";

    @Autowired
    public ChartController(HabrWordPorterCounterImpl habrWordPorterCounter,
                           HabrDocumentRepository habrDocumentRepository) {
        this.habrWordPorterCounter = habrWordPorterCounter;
        this.habrDocumentRepository = habrDocumentRepository;
    }

    @GetMapping("/makeChart")
    public ChartDto makeChart(@RequestParam("words") String words,
                              @RequestParam("countMode") String countMode,
                              @RequestParam(value = "stemmerType") String stemmerType,
                              @RequestParam("countWordInDocument") String countWordInDocument,
                              @RequestParam(value = "beginDate", required = false) String beginDate,
                              @RequestParam(value = "endDate", required = false) String endDate) {
        String[] wordList = words.split(",");

        List<WordCount> wordCounts = new ArrayList<>();
        List<DataSetDto> dataSetDtos = new ArrayList<>();

        if(stemmerType.equalsIgnoreCase(Constants.PORTER)) {
            for(String word : wordList) {
                WordCount wordCount = new WordCount();
                if(Constants.CONTAINS.equalsIgnoreCase(countWordInDocument)) {
                    wordCount = habrWordPorterCounter.countDocumentContainsWord(
                            word,
                            habrDocumentRepository.findAll(),
                            CountMode.valueOf(countMode));
                }
                if(Constants.HOW_MANY.equalsIgnoreCase(countWordInDocument)) {
                    wordCount = habrWordPorterCounter.countDocumentHowManyWord(
                            word,
                            habrDocumentRepository.findAll(),
                            CountMode.valueOf(countMode));
                }

                wordCounts.add(wordCount);
            }
        }
        if(stemmerType.equals(Constants.ELASTICSEARCH)) {
            for(String word : wordList) {
                WordCount wordCount = new WordCount();
                if(Constants.CONTAINS.equalsIgnoreCase(countWordInDocument)) {
                    wordCount = habrWordCounter.countDocumentContainsWord(
                            word,
                            habrDocumentRepository.findAll(),
                            CountMode.valueOf(countMode));
                }
                if(Constants.HOW_MANY.equalsIgnoreCase(countWordInDocument)) {
                    wordCount = habrWordCounter.countDocumentHowManyWord(
                            word,
                            habrDocumentRepository.findAll(),
                            CountMode.valueOf(countMode));
                }

                wordCounts.add(wordCount);
            }
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
