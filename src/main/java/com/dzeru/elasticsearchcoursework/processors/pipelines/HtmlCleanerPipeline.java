package com.dzeru.elasticsearchcoursework.processors.pipelines;

import com.dzeru.elasticsearchcoursework.processors.StringProcessor;
import com.dzeru.elasticsearchcoursework.processors.processors.HtmlCleanerProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HtmlCleanerPipeline implements StringProcessor {

    private final HtmlCleanerProcessor htmlCleanerProcessor;

    @Autowired
    public HtmlCleanerPipeline(HtmlCleanerProcessor htmlCleanerProcessor) {
        this.htmlCleanerProcessor = htmlCleanerProcessor;
    }

    @Override
    public String process(String string) {
        return htmlCleanerProcessor.process(string);
    }
}
