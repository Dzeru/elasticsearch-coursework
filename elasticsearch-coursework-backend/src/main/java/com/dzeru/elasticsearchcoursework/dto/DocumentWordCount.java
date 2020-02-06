package com.dzeru.elasticsearchcoursework.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
public class DocumentWordCount {
    private String word;
    private List<DocumentWordCountEntry> counts;

    @JsonIgnore
    private Map<Date, Integer> documentsCountByDate;

    public DocumentWordCount(String word) {
        this.word = word;
        documentsCountByDate = new HashMap<>();
    }

    public void addDocumentCount(Date date) {
        if(documentsCountByDate.containsKey(date)) {
            documentsCountByDate.put(date, documentsCountByDate.get(date) + 1);
        }
        else {
            documentsCountByDate.put(date, 1);
        }
    }

    public DocumentWordCount build() {
        counts = new ArrayList<>();
        if(!documentsCountByDate.isEmpty()) {
            for (Map.Entry<Date, Integer> mapCount : documentsCountByDate.entrySet()) {
                counts.add(new DocumentWordCountEntry(mapCount.getKey(), mapCount.getValue()));
            }
        }

        return this;
    }

    @Data
    @NoArgsConstructor
    private class DocumentWordCountEntry {
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        private Date date;
        private Integer count;

        DocumentWordCountEntry(Date date, Integer count) {
            this.date = date;
            this.count = count;
        }
    }
}
