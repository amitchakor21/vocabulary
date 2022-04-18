package com.amit.gre.entity;

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

    private Integer familiarLevel;

    private Boolean trickySpell;
    private Boolean trickyPronounce;

    private Long total;
}
