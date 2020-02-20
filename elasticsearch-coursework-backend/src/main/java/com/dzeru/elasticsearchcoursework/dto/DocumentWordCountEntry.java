package com.dzeru.elasticsearchcoursework.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class DocumentWordCountEntry {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date date;
    private Integer count;

    DocumentWordCountEntry(Date date, Integer count) {
        this.date = date;
        this.count = count;
    }
}
