package com.dzeru.elasticsearchcoursework.processors.processors;

import com.dzeru.elasticsearchcoursework.processors.StringProcessor;
import org.springframework.stereotype.Component;

@Component
public class HtmlCleanerProcessor implements StringProcessor {

    private static final String HTML_TAG = "<[^>]*>";

    public String process(String string) {
        return string.replaceAll(HTML_TAG, " ");
    }
}
