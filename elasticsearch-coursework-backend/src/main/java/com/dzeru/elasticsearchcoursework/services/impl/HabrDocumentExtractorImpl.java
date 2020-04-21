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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HabrDocumentExtractorImpl implements DocumentExtractor {

    private static final String HEADER_CLASS = "post__title-text";
    private static final String POST_TIME_CLASS = "post__time";
    private static final String POST_TIME_ATTRIBUTE = "data-time_published";
    private static final String BODY_ID = "post-content-body";
    private static final String COMMENTS_COUNT_ID = "comments_count";

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
                    Document html = Jsoup.parse(document);
                    String header = html.getElementsByClass(HEADER_CLASS).get(0).text();
                    String postTime = html.getElementsByClass(POST_TIME_CLASS).get(0).attr(POST_TIME_ATTRIBUTE);
                    String body = html.getElementById(BODY_ID).text();
                    String commentsCount = html.getElementById(COMMENTS_COUNT_ID).text();

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
