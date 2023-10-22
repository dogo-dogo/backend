package com.example.tooktook.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class S3Dto {
    private String key;
    private String path;

    public S3Dto() {

    }

    @Builder
    public S3Dto(String key, String path) {
        this.key = key;
        this.path = path;
    }
}