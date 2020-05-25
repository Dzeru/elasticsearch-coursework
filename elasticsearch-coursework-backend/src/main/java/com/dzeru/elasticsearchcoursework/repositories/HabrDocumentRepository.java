package com.dzeru.elasticsearchcoursework.repositories;

import com.dzeru.elasticsearchcoursework.entities.HabrDocument;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabrDocumentRepository extends ElasticsearchRepository<HabrDocument, Long> {

    @Query("{\n" +
            "\"bool\": {\n" +
            "  \"must\": {\n" +
            "    \"multi_match\": {\n" +
            "      \"query\":\"?0\",\n" +
            "      \"fields\":[\"header\",\"body\"]\n" +
            "    }\n" +
            "  },\n" +
            "  \"filter\": {\n" +
            "    \"range\": {\n" +
            "      \"postTime\": {\n" +
            "        \"gte\": ?1,\n" +
            "        \"lte\": ?2\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}\n" +
            "}")
    List<HabrDocument> findByWordAndPostTimeCountModeAll(String word, Long beginDate, Long endDate, Pageable pageable);

    @Query("{\n" +
            "\"bool\": {\n" +
            "  \"must\": {\n" +
            "    \"multi_match\": {\n" +
            "      \"query\":\"?0\",\n" +
            "      \"fields\":[\"body\"]\n" +
            "    }\n" +
            "  },\n" +
            "  \"filter\": {\n" +
            "    \"range\": {\n" +
            "      \"postTime\": {\n" +
            "        \"gte\": ?1,\n" +
            "        \"lte\": ?2\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}\n" +
            "}")
    List<HabrDocument> findByWordAndPostTimeCountModeBody(String word, Long beginDate, Long endDate, Pageable pageable);

    @Query("{\n" +
            "\"bool\": {\n" +
            "  \"must\": {\n" +
            "    \"multi_match\": {\n" +
            "      \"query\":\"?0\",\n" +
            "      \"fields\":[\"header\"]\n" +
            "    }\n" +
            "  },\n" +
            "  \"filter\": {\n" +
            "    \"range\": {\n" +
            "      \"postTime\": {\n" +
            "        \"gte\": ?1,\n" +
            "        \"lte\": ?2\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}\n" +
            "}")
    List<HabrDocument> findByWordAndPostTimeCountModeHeader(String word, Long beginDate, Long endDate, Pageable pageable);

    @Query("{\n" +
            "  \"range\": {\n" +
            "    \"postTime\": {\n" +
            "      \"gte\": ?0,\n" +
            "      \"lte\": ?1\n" +
            "    }\n" +
            "  }\n" +
            "}")
    List<HabrDocument> findByPostTimeBetween(Long beginDate, Long endDate, Pageable pageable);
}
