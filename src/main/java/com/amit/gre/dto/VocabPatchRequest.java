package com.amit.gre.dto;

import lombok.Data;

@Data
public class VocabPatchRequest {

    private String meaning;

    private boolean simple;
    private boolean known;
    private Integer familiarLevel;
    private String notes;

}
