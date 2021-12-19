package com.amit.gre.entity;

import lombok.Builder;
import lombok.Data;
import org.bson.types.Binary;

import java.time.LocalDateTime;

@Data
@Builder
public class Image {
    private String name; // file name
    private LocalDateTime createdTime; // upload time
    private Binary content; // file content
    private String contentType; // file type
    private long size; // file size
}
