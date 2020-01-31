package com.dzeru.elasticsearchcoursework.services;

import com.dzeru.elasticsearchcoursework.dto.AbstractExtractorParams;

public interface DocumentExtractor {
    void extractDocument(AbstractExtractorParams abstractExtractorParams) throws Exception;
}
