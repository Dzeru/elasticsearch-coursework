package com.dzeru.elasticsearchcoursework.repositories;

import com.dzeru.elasticsearchcoursework.entities.HabrDocument;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabrDocumentRepository extends ElasticsearchRepository<HabrDocument, Long> {

    @Query("{\"multi_match\":{ \"query\":\"?0\",\"fields\":[\"header^3\",\"body\"]}}")
    List<HabrDocument> test(String find);
}
