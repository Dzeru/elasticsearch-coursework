package com.dzeru.elasticsearchcoursework.processors.pipelines;

import com.dzeru.elasticsearchcoursework.processors.StringProcessor;
import com.dzeru.elasticsearchcoursework.processors.processors.HtmlCleanerProcessor;
import com.dzeru.elasticsearchcoursework.processors.processors.WhitespaceProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HtmlCleanerPipeline implements StringProcessor {

    private final WhitespaceProcessor whitespaceProcessor;
    private final HtmlCleanerProcessor htmlCleanerProcessor;

    @Autowired
    public HtmlCleanerPipeline(WhitespaceProcessor whitespaceProcessor,
                               HtmlCleanerProcessor htmlCleanerProcessor) {
        this.whitespaceProcessor = whitespaceProcessor;
        this.htmlCleanerProcessor = htmlCleanerProcessor;
    }

    @Override
    public String process(String string) {
        return whitespaceProcessor.process(
                htmlCleanerProcessor.process(string)
        );
    }
}
