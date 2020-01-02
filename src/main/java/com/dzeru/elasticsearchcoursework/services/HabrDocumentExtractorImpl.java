package com.dzeru.elasticsearchcoursework.services;

import com.dzeru.elasticsearchcoursework.entities.HabrDocument;
import com.dzeru.elasticsearchcoursework.repositories.HabrDocumentRepository;
import com.dzeru.elasticsearchcoursework.util.DocumentDownloader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Service
public class HabrDocumentExtractorImpl implements DocumentExtractor {

    private final String HEADER_START = "<span class=\"post__title-text\">";
    private final String HEADER_END = "</h1>";
    private final String POST_TIME_START = "<span class=\"post__time\" data-time_published=\"";
    private final String POST_TIME_END = "Z\">";
    private final String BODY_START = "<div class=\"post__body post__body_full\">";
    private final String BODY_END = "<script class=\"js-mediator-script\">";
    private final String COMMENTS_COUNT_START = "<span class=\"comments-section__head-counter\" id=\"comments_count\">";
    private final String COMMENTS_COUNT_END = "</span>";

    private final String HABR_URL_POST= "https://habr.com/ru/post/";
    private final SimpleDateFormat HABR_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

    private final HabrDocumentRepository habrDocumentRepository;

    @Autowired
    public HabrDocumentExtractorImpl(HabrDocumentRepository habrDocumentRepository) {
        this.habrDocumentRepository = habrDocumentRepository;
    }

    @Override
    public void extractDocument(String[] args) throws Exception {
        final long postId = Long.parseLong(args[0]);
        String document = DocumentDownloader.downloadDocument(HABR_URL_POST + postId);

        String header = document.substring(
                getStartPositionWithOffset(document, HEADER_START),
                document.indexOf(HEADER_END)
        );
        String postTime = document.substring(
                getStartPositionWithOffset(document, POST_TIME_START),
                document.indexOf(POST_TIME_END) + 1
        );
        String body = document.substring(
                getStartPositionWithOffset(document, BODY_START),
                document.indexOf(BODY_END)
        );

        String comCount = document.substring(
                getStartPositionWithOffset(document, COMMENTS_COUNT_START));
        String commentsCount = comCount.substring(0, comCount.indexOf(COMMENTS_COUNT_END));

        habrDocumentRepository.save(new HabrDocument(
                postId,
                header,
                body,
                Integer.parseInt(commentsCount.trim()),
                HABR_DATE_FORMAT.parse(postTime))
        );
    }

    private int getStartPositionWithOffset(String string, String point) {
        return string.indexOf(point) + point.length();
    }
}
