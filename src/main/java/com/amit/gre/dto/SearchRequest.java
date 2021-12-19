package com.amit.gre.dto;

import lombok.Data;

@Data
public class SearchRequest {

    private String id;
    private String word;
    private String emotion;
    private String score;
    private Integer page;
    private Integer size;
}
