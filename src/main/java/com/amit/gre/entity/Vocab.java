package com.amit.gre.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class Vocab {

    @Id
    private String id;

    private String word;
    private String meaning;
    private String notes;

    private boolean simple;
    private boolean known;
    private Integer familiarLevel;

    private LocalDateTime lastViewed;
    private LocalDateTime lastUpdated;
    private Integer viewedCount;

    private String emotion;
    private Image image;
    private Long total;
}
