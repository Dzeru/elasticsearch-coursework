package com.dzeru.elasticsearchcoursework.repositories;

import com.dzeru.elasticsearchcoursework.entities.VkDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VkDocumentRepository extends ElasticsearchRepository<VkDocument, Long> {
}
