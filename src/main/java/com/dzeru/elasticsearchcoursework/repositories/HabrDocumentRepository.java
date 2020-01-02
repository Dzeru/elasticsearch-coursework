package com.dzeru.elasticsearchcoursework.repositories;

import com.dzeru.elasticsearchcoursework.entities.HabrDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabrDocumentRepository extends ElasticsearchRepository<HabrDocument, Long> {
}
