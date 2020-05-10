package com.dzeru.elasticsearchcoursework.controllers;

import com.dzeru.elasticsearchcoursework.dto.ChartDto;
import com.dzeru.elasticsearchcoursework.dto.DataSetDto;
import com.dzeru.elasticsearchcoursework.dto.WordCount;
import com.dzeru.elasticsearchcoursework.entities.HabrDocument;
import com.dzeru.elasticsearchcoursework.repositories.HabrDocumentRepository;
import com.dzeru.elasticsearchcoursework.services.impl.counters.HabrWordElasticsearchCounterImpl;
import com.dzeru.elasticsearchcoursework.services.impl.counters.HabrWordPorterCounterImpl;
import com.dzeru.elasticsearchcoursework.util.Constants;
import com.dzeru.elasticsearchcoursework.util.CountMode;
import com.dzeru.elasticsearchcoursework.util.DateFormats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.*;

@RestController
@RequestMapping("/chart")
public class ChartController {

    private final HabrWordPorterCounterImpl habrWordPorterCounter;
    private final HabrWordElasticsearchCounterImpl habrWordElasticsearchCounter;
    private final HabrDocumentRepository habrDocumentRepository;

    private static final Random random = new Random();
    private static String rgbaTemplate = "rgba(%d,%d,%d,1)";

    @Autowired
    public ChartController(HabrWordPorterCounterImpl habrWordPorterCounter,
                           HabrWordElasticsearchCounterImpl habrWordElasticsearchCounter,
                           HabrDocumentRepository habrDocumentRepository) {
        this.habrWordPorterCounter = habrWordPorterCounter;
        this.habrWordElasticsearchCounter = habrWordElasticsearchCounter;
        this.habrDocumentRepository = habrDocumentRepository;
    }


    @GetMapping("/makeChart")
    public ChartDto makeChart(@RequestParam("words") String words,
                              @RequestParam("countMode") String countMode,
                              @RequestParam(value = "stemmerType") String stemmerType,
                              @RequestParam("countWordInDocument") String countWordInDocument,
                              @RequestParam(value = "beginDate") String beginDate,
                              @RequestParam(value = "endDate") String endDate) {
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
            long beginPostTime = 0;
            long endPostTime = 0;

            try {
                beginPostTime = DateFormats.CUSTOM_DATE_FORMAT.parse(beginDate).getTime();
                endPostTime = DateFormats.CUSTOM_DATE_FORMAT.parse(endDate).getTime();
            }
            catch(ParseException e) {
                e.printStackTrace();
                return new ChartDto();
            }

            for(String word : wordList) {
                WordCount wordCount = new WordCount();
                List<HabrDocument> habrDocuments = getHabrDocumentsByCountMode(
                        word,
                        beginPostTime,
                        endPostTime,
                        countMode);

                if(Constants.CONTAINS.equalsIgnoreCase(countWordInDocument)) {
                    wordCount = habrWordElasticsearchCounter.countDocumentContainsWord(
                            word,
                            habrDocuments,
                            CountMode.valueOf(countMode));
                }
                if(Constants.HOW_MANY.equalsIgnoreCase(countWordInDocument)) {
                    wordCount = habrWordElasticsearchCounter.countDocumentHowManyWord(
                            word,
                            habrDocuments,
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

    private List<HabrDocument> getHabrDocumentsByCountMode(String word,
                                                           long beginPostTime,
                                                           long endPostTime,
                                                           String countMode) {
        List<HabrDocument> habrDocuments = new ArrayList<>();

        if(CountMode.valueOf(countMode).equals(CountMode.ALL)) {
            habrDocuments = habrDocumentRepository.findByWordAndPostTimeCountModeAll(
                    word,
                    beginPostTime,
                    endPostTime);
        }
        if(CountMode.valueOf(countMode).equals(CountMode.ONLY_BODY)) {
            habrDocuments = habrDocumentRepository.findByWordAndPostTimeCountModeBody(
                    word,
                    beginPostTime,
                    endPostTime);
        }
        if(CountMode.valueOf(countMode).equals(CountMode.ONLY_HEADER)) {
            habrDocuments = habrDocumentRepository.findByWordAndPostTimeCountModeHeader(
                    word,
                    beginPostTime,
                    endPostTime);
        }
        return habrDocuments;
    }
}
