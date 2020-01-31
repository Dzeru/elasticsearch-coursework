package com.dzeru.elasticsearchcoursework.processors.pipelines;

import com.dzeru.elasticsearchcoursework.processors.StringProcessor;
import com.dzeru.elasticsearchcoursework.processors.processors.HtmlCleanerProcessor;
import com.dzeru.elasticsearchcoursework.processors.processors.PorterStemmerProcessor;
import com.dzeru.elasticsearchcoursework.processors.processors.PunctuationCleanerProcessor;
import com.dzeru.elasticsearchcoursework.processors.processors.StopWordsProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RussianStemmerPipeline implements StringProcessor {

    private final StopWordsProcessor stopWordsProcessor;
    private final HtmlCleanerProcessor htmlCleanerProcessor;
    private final PorterStemmerProcessor porterStemmerProcessor;
    private final PunctuationCleanerProcessor punctuationCleanerProcessor;

    @Autowired
    public RussianStemmerPipeline(StopWordsProcessor stopWordsProcessor,
                                  HtmlCleanerProcessor htmlCleanerProcessor,
                                  PorterStemmerProcessor porterStemmerProcessor,
                                  PunctuationCleanerProcessor punctuationCleanerProcessor) {
        this.stopWordsProcessor = stopWordsProcessor;
        this.htmlCleanerProcessor = htmlCleanerProcessor;
        this.porterStemmerProcessor = porterStemmerProcessor;
        this.punctuationCleanerProcessor = punctuationCleanerProcessor;
    }

    public String process(String string) {
        return porterStemmerProcessor.process(
                stopWordsProcessor.process(
                        punctuationCleanerProcessor.process(
                                htmlCleanerProcessor.process(
                                        string
                                )
                        )
                )
        );
    }


    public String process1(String string) {
        return porterStemmerProcessor.process(

                        punctuationCleanerProcessor.process(
                                htmlCleanerProcessor.process(
                                        string
                                )
                        )

        );
    }
}
