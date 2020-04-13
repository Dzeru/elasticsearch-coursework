package com.dzeru.elasticsearchcoursework.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

@Data
@NoArgsConstructor
@Document(indexName = "vk_author", type = "vk_author")
@Setting(settingPath = "/elasticsearch/vk_author_setting.json")
@Mapping(mappingPath = "/elasticsearch/vk_author_mapping.json")
public class VkAuthor {

    @Id
    private long authorId;

    private String url;
    private String name;

    public VkAuthor(long authorId,
                    String url,
                    String name) {
        this.authorId = authorId;
        this.url = url;
        this.name = name;
    }
}
