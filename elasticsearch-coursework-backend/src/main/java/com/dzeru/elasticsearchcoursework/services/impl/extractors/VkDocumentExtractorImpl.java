package com.dzeru.elasticsearchcoursework.services.impl.extractors;

import com.dzeru.elasticsearchcoursework.dto.AbstractExtractorParams;
import com.dzeru.elasticsearchcoursework.dto.VkExtractorParams;
import com.dzeru.elasticsearchcoursework.entities.VkAuthor;
import com.dzeru.elasticsearchcoursework.entities.VkDocument;
import com.dzeru.elasticsearchcoursework.processors.pipelines.HtmlCleanerPipeline;
import com.dzeru.elasticsearchcoursework.processors.pipelines.RussianStemmerPipeline;
import com.dzeru.elasticsearchcoursework.repositories.VkAuthorRepository;
import com.dzeru.elasticsearchcoursework.repositories.VkDocumentRepository;
import com.dzeru.elasticsearchcoursework.services.DocumentExtractor;
import com.dzeru.elasticsearchcoursework.util.DocumentDownloader;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
public class VkDocumentExtractorImpl implements DocumentExtractor {

    private static final String POST_TIME_CLASS = "wi_date";
    private static final String BODY_CLASS = "pi_text";

    /*
    1st param - author url
    2nd param - author id
    3rd param - post id
     */
    private static final String VK_URL_POST= "https://vk.com/%s?w=wall%s_%s";

    private final VkAuthorRepository vkAuthorRepository;
    private final VkDocumentRepository vkDocumentRepository;
    private final RussianStemmerPipeline russianStemmerPipeline;
    private final HtmlCleanerPipeline htmlCleanerPipeline;

    @Autowired
    public VkDocumentExtractorImpl(VkAuthorRepository vkAuthorRepository,
                                   VkDocumentRepository vkDocumentRepository,
                                   RussianStemmerPipeline russianStemmerPipeline,
                                   HtmlCleanerPipeline htmlCleanerPipeline) {
        this.vkAuthorRepository = vkAuthorRepository;
        this.vkDocumentRepository = vkDocumentRepository;
        this.russianStemmerPipeline = russianStemmerPipeline;
        this.htmlCleanerPipeline = htmlCleanerPipeline;
    }

    @Override
    public int extractDocument(AbstractExtractorParams abstractExtractorParams) {
        VkExtractorParams vkExtractorParams = (VkExtractorParams) abstractExtractorParams;
        int downloadCounter = 0;

        if(null != vkExtractorParams.getAuthorId()) {
            List<String> postIds = vkExtractorParams.getPostIds();
            for(String postIdString : postIds) {
                try {
                    VkAuthor vkAuthor = vkAuthorRepository
                            .findById(vkExtractorParams.getAuthorId())
                            .orElseThrow(IllegalArgumentException::new);
                    final long postId = Long.parseLong(postIdString);

                    String document = DocumentDownloader.downloadDocument(String.format(
                            VK_URL_POST,
                            vkAuthor.getUrl(),
                            vkAuthor.getAuthorId(),
                            postId
                    ));

                    if(StringUtils.isNotEmpty(document)) {
                        Document html = Jsoup.parse(document);
                        String postTimeString = html.getElementsByClass(POST_TIME_CLASS).get(0).text();
                        Date postTime = getPostTime(postTimeString);
                        String body = html.getElementsByClass(BODY_CLASS).get(0).text();

                        System.out.println("--------");
                        System.out.println(htmlCleanerPipeline.process(body));
                        System.out.println(russianStemmerPipeline.process(body));
                        System.out.println(postTimeString + " " + postTime);
                        System.out.println("--------");

                        vkDocumentRepository.save(new VkDocument(
                                postId,
                                vkExtractorParams.getAuthorId(),
                                body,
                                russianStemmerPipeline.process(body),
                                postTime
                        ));

                        downloadCounter++;
                    }
                }
                catch(Exception e) {

                }
            }
        }

        return downloadCounter;
    }

    private Date getPostTime(String postTimeString) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Samara"));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        if(postTimeString.contains("сегодня")) {
            return calendar.getTime();
        }
        if(postTimeString.contains("вчера")) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            return calendar.getTime();
        }

        String dateParts[] = postTimeString.split(" ");
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateParts[0]));
        calendar.set(Calendar.MONTH, getMonthByString(dateParts[1]));
        calendar.set(Calendar.YEAR, Integer.parseInt(dateParts[2]));

        return calendar.getTime();
    }

    private int getMonthByString(String datePart) {
        switch(datePart) {
            case "янв":
                return Calendar.JANUARY;
            case "фев":
                return Calendar.FEBRUARY;
            case "мар":
                return Calendar.MARCH;
            case "апр":
                return Calendar.APRIL;
            case "мая":
                return Calendar.MAY;
            case "июн":
                return Calendar.JUNE;
            case "июл":
                return Calendar.JULY;
            case "авг":
                return Calendar.AUGUST;
            case "сен":
                return Calendar.SEPTEMBER;
            case "окт":
                return Calendar.OCTOBER;
            case "ноя":
                return Calendar.NOVEMBER;
            case "дек":
                return Calendar.DECEMBER;
        }
        throw new IllegalArgumentException();
    }
}
