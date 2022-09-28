package com.amit.gre.dto;

import lombok.Data;

@Data
public class VocabPatchRequest {
    private String meaning;
    private Integer familiarLevel;
    private String notes;
}
