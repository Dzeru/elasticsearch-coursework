package com.dzeru.elasticsearchcoursework.services;

import com.dzeru.elasticsearchcoursework.dto.AbstractExtractorParams;

public interface DocumentExtractor {
    int extractDocument(AbstractExtractorParams abstractExtractorParams);
}
