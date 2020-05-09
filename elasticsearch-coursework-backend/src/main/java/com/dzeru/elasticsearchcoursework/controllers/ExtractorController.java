package com.dzeru.elasticsearchcoursework.controllers;

import com.dzeru.elasticsearchcoursework.dto.HabrExtractorParams;
import com.dzeru.elasticsearchcoursework.services.impl.extractors.HabrDocumentExtractorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@RequestMapping("/extract")
@RestController
public class ExtractorController {

    private final HabrDocumentExtractorImpl habrDocumentExtractor;

    private static final Pattern numberPattern = Pattern.compile("^-?\\d*$");

    @Autowired
    public ExtractorController(HabrDocumentExtractorImpl habrDocumentExtractor) {
        this.habrDocumentExtractor = habrDocumentExtractor;
    }

    @GetMapping("/habr")
    public ResponseEntity<String> habr(@RequestParam("postIds") String postIds) throws Exception {
        HabrExtractorParams params = new HabrExtractorParams();
        params.setPostIds(getPostIds(postIds));

        int extractedCounter = habrDocumentExtractor.extractDocument(params);
        String documentCount = extractedCounter + "/" + params.getPostIds().size();
        return new ResponseEntity<>(documentCount, HttpStatus.OK);
    }

    private List<String> getPostIds(String postIds) {
        if(postIds.contains(",")) {
            return Arrays.asList(postIds.split(","));
        }
        else if(postIds.contains("-")){
            List<String> idsList = new ArrayList<>();
            String[] ids = postIds.split("-");
            int firstId = Integer.parseInt(ids[0]);
            int secondId = Integer.parseInt(ids[1]);
            int beginId = Math.min(firstId, secondId);
            int endId = Math.max(firstId, secondId);

            idsList.add(ids[0]);
            for(int id = beginId + 1; id < endId; id++) {
                idsList.add(String.valueOf(id));
            }
            idsList.add(ids[1]);
            return idsList;
        }
        else if(numberPattern.matcher(postIds).matches()) {
            List<String> idsList = new ArrayList<>();
            idsList.add(postIds);
            return idsList;
        }
        return new ArrayList<>();
    }
}
