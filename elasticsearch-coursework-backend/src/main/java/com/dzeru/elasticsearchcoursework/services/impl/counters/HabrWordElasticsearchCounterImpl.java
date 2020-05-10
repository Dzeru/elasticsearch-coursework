package com.dzeru.elasticsearchcoursework.services.impl.counters;

import com.dzeru.elasticsearchcoursework.dto.WordCount;
import com.dzeru.elasticsearchcoursework.entities.AbstractDocument;
import com.dzeru.elasticsearchcoursework.entities.HabrDocument;
import com.dzeru.elasticsearchcoursework.services.WordCounter;
import com.dzeru.elasticsearchcoursework.util.CountMode;
import com.dzeru.elasticsearchcoursework.util.DateFormats;
import org.apache.lucene.index.PostingsEnum;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.util.BytesRef;
import org.elasticsearch.action.termvectors.TermVectorsResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.ToXContent;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HabrWordElasticsearchCounterImpl implements WordCounter {

    private final Client client;

    public HabrWordElasticsearchCounterImpl(Client client) {
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
                TermVectorsResponse response = client
                        .prepareTermVectors()
                        .setIndex("habr_document")
                        .setType("habr_document")
                        .setId(String.valueOf(habrDocument.getPostId()))
                        .setPositions(true)
                        .execute()
                        .actionGet();

                //!!!!!!!!!!!!!!!!!!!!!!!
                Map<Integer,String> map = new HashMap<>();
                Terms terms = response.getFields().terms("body");
                if (terms==null){
                    System.out.println("null terms");
                }
                TermsEnum iterator = terms.iterator();
                PostingsEnum postings = null;

                for (BytesRef termBytes = null; (termBytes = iterator.next()) != null; ) {
                    String term = termBytes.utf8ToString();

                    postings = iterator.postings(postings, PostingsEnum.ALL);

                    //there can only be one doc since we are getting with id. get the doc and the position
                    postings.nextDoc();

                    int tf = postings.freq();

                    for(int i = 0; i < tf; i++) {
                        int pos = postings.nextPosition();
                        map.put(pos, term);
                    }
                }
                //!!!!!!!!!!!!!!!!!!!!!!!
            }
            catch(Exception e) {
                e.printStackTrace();
            }
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
}
