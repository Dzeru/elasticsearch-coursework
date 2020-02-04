package com.dzeru.elasticsearchcoursework.controllers;

import com.dzeru.elasticsearchcoursework.dto.HabrExtractorParams;
import com.dzeru.elasticsearchcoursework.services.DocumentExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequestMapping("/extract")
@RestController
public class HabrExtractorController {

    private final DocumentExtractor documentExtractor;

    private static final Pattern numberPattern = Pattern.compile("^\\d*$");

    @Autowired
    public HabrExtractorController(DocumentExtractor documentExtractor) {
        this.documentExtractor = documentExtractor;
    }

    @GetMapping("/habr")
    public void a(@RequestParam("postIds") String postIds) throws Exception {
        HabrExtractorParams params = new HabrExtractorParams();

        if(postIds.contains(",")) {
            params.setPostIds(Arrays.asList(postIds.split(",")));
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
            params.setPostIds(idsList);
        }
        else if(numberPattern.matcher(postIds).matches()) {
            List<String> idsList = new ArrayList<>();
            idsList.add(postIds);
            params.setPostIds(idsList);
        }

        documentExtractor.extractDocument(params);
    }
}
