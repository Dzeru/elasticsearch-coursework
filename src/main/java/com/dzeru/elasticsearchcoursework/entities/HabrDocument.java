package com.dzeru.elasticsearchcoursework.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.util.Date;

@Data
@NoArgsConstructor
@Document(indexName = "habr_document", type = "habr_document")
@Setting(settingPath = "/elasticsearch/habr-document-setting.json")
@Mapping(mappingPath = "/elasticsearch/habr-document-mapping.json")
public class HabrDocument {
    @Id
    private long postId;
    private String header;
    private String body;
    private int commentsCount;

    @Field(type = FieldType.Date)
    private Date postTime;

    public HabrDocument(long postId,
                        String header,
                        String body,
                        int commentsCount,
                        Date postTime) {
        this.postId = postId;
        this.header = header;
        this.body = body;
        this.commentsCount = commentsCount;
        this.postTime = postTime;
    }
}
