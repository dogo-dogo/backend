package com.example.tooktook.controller;

import com.example.tooktook.model.dto.S3Dto;
import com.example.tooktook.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/s3")
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service s3Service;

    @PostMapping("/resource")
    public S3Dto upload(@RequestPart("file") MultipartFile multipartFile) throws IOException {
        return s3Service.upload(multipartFile,"upload");
    }

    @DeleteMapping("/resource")
    public void remove(S3Dto s3Dto) {
        s3Service.remove(s3Dto);
    }
}