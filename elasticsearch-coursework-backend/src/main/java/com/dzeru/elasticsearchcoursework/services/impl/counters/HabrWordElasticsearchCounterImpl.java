package com.dzeru.elasticsearchcoursework.services.impl.counters;

import com.dzeru.elasticsearchcoursework.dto.WordCount;
import com.dzeru.elasticsearchcoursework.entities.AbstractDocument;
import com.dzeru.elasticsearchcoursework.entities.HabrDocument;
import com.dzeru.elasticsearchcoursework.services.WordCounter;
import com.dzeru.elasticsearchcoursework.util.CountMode;
import com.dzeru.elasticsearchcoursework.util.DateFormats;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.TermVectorsRequest;
import org.elasticsearch.client.core.TermVectorsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class HabrWordElasticsearchCounterImpl implements WordCounter {

    private final RestHighLevelClient client;

    @Autowired
    public HabrWordElasticsearchCounterImpl(RestHighLevelClient client) {
        this.client = client;
    }

    @Override
    public WordCount countDocumentContainsWord(String word,
                                               Iterable<? extends AbstractDocument> documents,
                                               CountMode countMode) {
        Iterable<HabrDocument> habrDocuments = new ArrayList<>();
        try {
            habrDocuments = (Iterable<HabrDocument>) documents;
        }
        catch(ClassCastException e) {
            e.printStackTrace();
        }

        Map<String, Long> counts = new TreeMap<>();
        for(HabrDocument habrDocument : habrDocuments) {
            String date = DateFormats.CUSTOM_DATE_FORMAT.format(habrDocument.getPostTime());
            if(counts.containsKey(date)) {
                counts.put(date, counts.get(date) + 1);
            }
            else {
                counts.put(date, 1L);
            }
        }

        return new WordCount(word, counts);
    }

    @Override
    public WordCount countDocumentHowManyWord(String word,
                                              Iterable<? extends AbstractDocument> documents,
                                              CountMode countMode) {
        Iterable<HabrDocument> habrDocuments = new ArrayList<>();
        try {
            habrDocuments = (Iterable<HabrDocument>) documents;
        }
        catch(ClassCastException e) {
            e.printStackTrace();
        }

        Map<String, Long> counts = new TreeMap<>();
        for(HabrDocument habrDocument : habrDocuments) {
            try {
                TermVectorsRequest request = new TermVectorsRequest(
                        "habr_document",
                        "habr_document",
                        String.valueOf(habrDocument.getPostId())
                );
                String fields[] = getFields(countMode);
                request.setFields(fields);
                request.setFieldStatistics(false);
                request.setTermStatistics(true);
                request.setPositions(false);
                request.setOffsets(false);
                request.setPayloads(false);

                TermVectorsResponse response = client.termvectors(request, RequestOptions.DEFAULT);

                if(response.getFound()) {
                    for(TermVectorsResponse.TermVector termVector : response.getTermVectorsList()) {
                        List<String> termString = termVector
                                .getTerms()
                                .stream()
                                .map(TermVectorsResponse.TermVector.Term::getTerm)
                                .collect(Collectors.toList());
                        int position = -1;
                        for(int i = 0; i < termString.size(); i++) {
                            if(termString.get(i).startsWith(word) || word.startsWith(termString.get(i))) {
                                position = i;
                            }
                        }
                        if(position != -1) {
                            TermVectorsResponse.TermVector.Term term = termVector.getTerms().get(position);

                            String date = DateFormats.CUSTOM_DATE_FORMAT.format(habrDocument.getPostTime());
                            if(counts.containsKey(date)) {
                                counts.put(date, counts.get(date) + term.getTermFreq());
                            }
                            else {
                                counts.put(date, (long) term.getTermFreq());
                            }
                        }
                    }
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }

        return new WordCount(word, counts);
    }

    private String[] getFields(CountMode countMode) {
        if(CountMode.ONLY_BODY.equals(countMode)) {
            return new String[]{"body"};
        }
        if(CountMode.ONLY_HEADER.equals(countMode)) {
            return new String[]{"header"};
        }
        else {
            return new String[]{"body", "header"};
        }
    }
}
