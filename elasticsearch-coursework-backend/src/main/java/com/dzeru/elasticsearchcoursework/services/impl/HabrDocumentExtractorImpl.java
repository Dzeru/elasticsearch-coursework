package com.dzeru.elasticsearchcoursework.services.impl;

import com.dzeru.elasticsearchcoursework.dto.AbstractExtractorParams;
import com.dzeru.elasticsearchcoursework.dto.HabrExtractorParams;
import com.dzeru.elasticsearchcoursework.entities.HabrDocument;
import com.dzeru.elasticsearchcoursework.processors.pipelines.HtmlCleanerPipeline;
import com.dzeru.elasticsearchcoursework.processors.pipelines.RussianStemmerPipeline;
import com.dzeru.elasticsearchcoursework.repositories.HabrDocumentRepository;
import com.dzeru.elasticsearchcoursework.services.DocumentExtractor;
import com.dzeru.elasticsearchcoursework.util.DateFormats;
import com.dzeru.elasticsearchcoursework.util.DocumentDownloader;
import com.dzeru.elasticsearchcoursework.util.ExtractorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HabrDocumentExtractorImpl implements DocumentExtractor {

    private static final String HEADER_START = "<span class=\"post__title-text\">";
    private static final String HEADER_END = "</h1>";
    private static final String POST_TIME_START = "<span class=\"post__time\" data-time_published=\"";
    private static final String POST_TIME_END = "Z\">";
    private static final String BODY_START = "<div class=\"post__body post__body_full\">";
    private static final String BODY_END = "<dl class=\"post__tags\">";
    private static final String COMMENTS_COUNT_START = "<span class=\"comments-section__head-counter\" id=\"comments_count\">";
    private static final String COMMENTS_COUNT_END = "</span>";

    private static final String HABR_URL_POST= "https://habr.com/ru/post/";

    private final HabrDocumentRepository habrDocumentRepository;
    private final RussianStemmerPipeline russianStemmerPipeline;
    private final HtmlCleanerPipeline htmlCleanerPipeline;

    @Autowired
    public HabrDocumentExtractorImpl(HabrDocumentRepository habrDocumentRepository,
                                     RussianStemmerPipeline russianStemmerPipeline,
                                     HtmlCleanerPipeline htmlCleanerPipeline) {
        this.habrDocumentRepository = habrDocumentRepository;
        this.russianStemmerPipeline = russianStemmerPipeline;
        this.htmlCleanerPipeline = htmlCleanerPipeline;
    }

    @Override
    public int extractDocument(AbstractExtractorParams abstractExtractorParams) {
        HabrExtractorParams habrExtractorParams = (HabrExtractorParams) abstractExtractorParams;
        int downloadCounter = 0;

        List<String> postIds = habrExtractorParams.getPostIds();

        for(String postIdString : postIds) {
            try{
                final long postId = Long.parseLong(postIdString);
                String document = DocumentDownloader.downloadDocument(HABR_URL_POST + postId);

                if(!StringUtils.isEmpty(document)) {
                    String header = document.substring(
                            ExtractorUtils.getStartPositionWithOffset(document, HEADER_START),
                            document.indexOf(HEADER_END)
                    );
                    String postTime = document.substring(
                            ExtractorUtils.getStartPositionWithOffset(document, POST_TIME_START),
                            document.indexOf(POST_TIME_END) + 1
                    );
                    String body = document.substring(
                            ExtractorUtils.getStartPositionWithOffset(document, BODY_START),
                            document.indexOf(BODY_END)
                    );

                    String comCount = document.substring(
                            ExtractorUtils.getStartPositionWithOffset(document, COMMENTS_COUNT_START));
                    String commentsCount = comCount.substring(0, comCount.indexOf(COMMENTS_COUNT_END));
                    System.out.println("--------");
                    System.out.println(htmlCleanerPipeline.process(header));
                    System.out.println(russianStemmerPipeline.process(header));
                    System.out.println(htmlCleanerPipeline.process(body));
                    System.out.println(russianStemmerPipeline.process(body));
                    System.out.println(commentsCount);
                    System.out.println(DateFormats.CUSTOM_DATE_FORMAT.parse(postTime));
                    System.out.println("--------");

                    habrDocumentRepository.save(new HabrDocument(
                            postId,
                            htmlCleanerPipeline.process(header),
                            htmlCleanerPipeline.process(body),
                            russianStemmerPipeline.process(header),
                            russianStemmerPipeline.process(body),
                            Integer.parseInt(commentsCount.trim()),
                            DateFormats.CUSTOM_DATE_FORMAT.parse(postTime))
                    );
                    downloadCounter++;
                }
            }
            catch(Exception e) {

            }
        }
        return downloadCounter;
    }
}
