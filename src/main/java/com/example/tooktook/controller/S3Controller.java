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

    private final S3Service s3Service;
    @GetMapping("/getimageinfo")

    public ImageFileDto getImageInfo (@RequestBody ImageFileDto imageFileDto){
        imageFileDto.getDoorColor();
        imageFileDto.getDecoration();
        //String url=putImageInfo(imageFileDto);
        //System.out.println("geturl"+url);
        return imageFileDto;
    }

    @PostMapping("/putimageinfo")
    public String putImageInfo(ImageFileDto imageFileDto){
        String dolorColor=(imageFileDto.getDoorColor())+".png";
        String decoration=imageFileDto.getDecoration();
        String dolorColorUrl=s3Service.getS3("dogo-dogo",dolorColor);
        System.out.println("posturl"+dolorColorUrl);
        return dolorColorUrl;
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