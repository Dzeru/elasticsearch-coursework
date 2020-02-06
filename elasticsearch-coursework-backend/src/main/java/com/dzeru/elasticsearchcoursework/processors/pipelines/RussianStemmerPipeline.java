package com.dzeru.elasticsearchcoursework.processors.pipelines;

import com.dzeru.elasticsearchcoursework.processors.StringProcessor;
import com.dzeru.elasticsearchcoursework.processors.processors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RussianStemmerPipeline implements StringProcessor {

    private final StopWordsProcessor stopWordsProcessor;
    private final WhitespaceProcessor whitespaceProcessor;
    private final HtmlCleanerProcessor htmlCleanerProcessor;
    private final PorterStemmerProcessor porterStemmerProcessor;
    private final PunctuationCleanerProcessor punctuationCleanerProcessor;

    @Autowired
    public RussianStemmerPipeline(StopWordsProcessor stopWordsProcessor,
                                  WhitespaceProcessor whitespaceProcessor,
                                  HtmlCleanerProcessor htmlCleanerProcessor,
                                  PorterStemmerProcessor porterStemmerProcessor,
                                  PunctuationCleanerProcessor punctuationCleanerProcessor) {
        this.stopWordsProcessor = stopWordsProcessor;
        this.whitespaceProcessor = whitespaceProcessor;
        this.htmlCleanerProcessor = htmlCleanerProcessor;
        this.porterStemmerProcessor = porterStemmerProcessor;
        this.punctuationCleanerProcessor = punctuationCleanerProcessor;
    }

    public String process(String string) {
        return porterStemmerProcessor.process(
                whitespaceProcessor.process(
                        stopWordsProcessor.process(
                                punctuationCleanerProcessor.process(
                                        htmlCleanerProcessor.process(
                                            string
                                        )
                                )
                        )
                )
        );
    }
}
