package com.dzeru.elasticsearchcoursework.services.impl.counters;

import com.dzeru.elasticsearchcoursework.dto.WordCount;
import com.dzeru.elasticsearchcoursework.entities.AbstractDocument;
import com.dzeru.elasticsearchcoursework.entities.HabrDocument;
import com.dzeru.elasticsearchcoursework.services.WordCounter;
import com.dzeru.elasticsearchcoursework.util.CountMode;

import java.util.ArrayList;

public class HabrWordElasticsearchCounterImpl implements WordCounter {

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
    }

    @Override
    public WordCount countDocumentHowManyWord(String word,
                                              Iterable<? extends AbstractDocument> documents,
                                              CountMode countMode) {
        return null;
    }
}
