package com.dzeru.elasticsearchcoursework.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.util.Date;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(indexName = "habr_document", type = "habr_document")
@Setting(settingPath = "/elasticsearch/habr_document_setting.json")
@Mapping(mappingPath = "/elasticsearch/habr_document_mapping.json")
public class HabrDocument extends AbstractDocument {

    @Id
    private long postId;

    private String header;
    private String body;
    private String stemmedHeader;
    private String stemmedBody;
    private int commentsCount;

    @Field(type = FieldType.Date)
    private Date postTime;

    public HabrDocument(long postId,
                        String header,
                        String body,
                        String stemmedHeader,
                        String stemmedBody,
                        int commentsCount,
                        Date postTime) {
        this.postId = postId;
        this.header = header;
        this.body = body;
        this.stemmedHeader = stemmedHeader;
        this.stemmedBody = stemmedBody;
        this.commentsCount = commentsCount;
        this.postTime = postTime;
    }
}
