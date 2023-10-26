package com.example.tooktook.controller;

import com.example.tooktook.common.response.ApiResponse;
import com.example.tooktook.common.response.ResponseCode;
import com.example.tooktook.model.dto.ImageFileDto;
import com.example.tooktook.model.dto.S3Dto;
import com.example.tooktook.model.dto.memberDto.MemberDetailsDto;
import com.example.tooktook.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/api/s3")
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service s3Service;

    @PostMapping("/choose-door-deco")
    public ApiResponse<?> chooseDoorDeco(@RequestBody ImageFileDto imageFileDto, @AuthenticationPrincipal MemberDetailsDto member){
        String dolorColorUrl=s3Service.getS3("dogo-dogo",imageFileDto);
        return ApiResponse.ok(ResponseCode.Normal.SUCCESS,dolorColorUrl);
    }

    @PostMapping("/put-door_deco")
    public ApiResponse<?> putDoorDeco(@RequestBody ImageFileDto imageFileDto, @AuthenticationPrincipal MemberDetailsDto member){
        String dolorColorUrl=s3Service.getS3("dogo-dogo",imageFileDto);
        return ApiResponse.ok(ResponseCode.Normal.SUCCESS,dolorColorUrl);
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