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
@Document(indexName = "vk_document", type = "vk_document")
@Setting(settingPath = "/elasticsearch/vk_document_setting.json")
@Mapping(mappingPath = "/elasticsearch/vk_document_mapping.json")
public class VkDocument extends AbstractDocument {

    @Id
    private long postId;

    private long authorId;
    private String body;
    private String stemmedBody;

    @Field(type = FieldType.Date)
    private Date postTime;

    public VkDocument(long postId,
                      long authorId,
                      String body,
                      String stemmedBody,
                      Date postTime) {
        this.postId = postId;
        this.authorId = authorId;
        this.body = body;
        this.stemmedBody = stemmedBody;
        this.postTime = postTime;
    }
}
