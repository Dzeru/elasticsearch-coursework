package com.dzeru.elasticsearchcoursework.processors.processors;

import com.dzeru.elasticsearchcoursework.processors.StringProcessor;
import org.springframework.stereotype.Component;

@Component
public class PunctuationCleanerProcessor implements StringProcessor {

    private static final String PUNCTUATION_MARKS = "!|\\?|\\.|,|\\(|\\)|\"|:|;|…|—|«|»";

    public String process(String string) {
        return string.replaceAll(PUNCTUATION_MARKS, " ");
    }
}
