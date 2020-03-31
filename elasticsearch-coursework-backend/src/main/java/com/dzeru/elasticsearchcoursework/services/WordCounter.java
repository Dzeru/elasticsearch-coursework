package com.dzeru.elasticsearchcoursework.services;

import com.dzeru.elasticsearchcoursework.dto.WordCount;
import com.dzeru.elasticsearchcoursework.entities.AbstractDocument;
import com.dzeru.elasticsearchcoursework.util.CountMode;

public interface WordCounter {

    WordCount countDocumentContainsWord(String word,
                                        Iterable<? extends AbstractDocument> documents,
                                        CountMode countMode);

    WordCount countDocumentHowManyWord(String word,
                                       Iterable<? extends AbstractDocument> documents,
                                       CountMode countMode);
}
