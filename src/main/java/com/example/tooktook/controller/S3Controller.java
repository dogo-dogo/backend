package com.example.tooktook.controller;

import com.example.tooktook.model.dto.ImageFileDto;
import com.example.tooktook.model.dto.S3Dto;
import com.example.tooktook.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/api/s3")
@RequiredArgsConstructor
public class S3Controller {

    @GetMapping("/getimageinfo")
    @ResponseBody
    public void getImageInfo (@RequestBody ImageFileDto imageFileDto){
        imageFileDto.getDecoration();
        imageFileDto.getDoorColor();

    }


    /*
    @PostMapping("/resource")
    public S3Dto upload(@RequestPart("file") MultipartFile multipartFile) throws IOException {
        return s3Service.upload(multipartFile,"upload");
    }

    @DeleteMapping("/resource")
    public void remove(S3Dto s3Dto) {
        s3Service.remove(s3Dto);
    }

    */
}