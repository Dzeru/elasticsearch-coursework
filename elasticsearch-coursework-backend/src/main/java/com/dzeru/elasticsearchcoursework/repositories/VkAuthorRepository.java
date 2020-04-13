package com.dzeru.elasticsearchcoursework.repositories;

import com.dzeru.elasticsearchcoursework.entities.VkAuthor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VkAuthorRepository extends ElasticsearchRepository<VkAuthor, Long> {
}
