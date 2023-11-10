package com.example.tooktook.controller;

import com.example.tooktook.common.response.ApiResponse;
import com.example.tooktook.common.response.ResponseCode;
import com.example.tooktook.model.dto.decoDto.ImageFileDto;
import com.example.tooktook.model.dto.decoDto.ImageUrlDto;
import com.example.tooktook.model.dto.memberDto.MemberDetailsDto;
import com.example.tooktook.model.repository.MemberNeo4jRepository;
import com.example.tooktook.service.MemberService;
import com.example.tooktook.service.S3Service;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/s3")
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service s3Service;

    private final MemberNeo4jRepository memberNeo4jRepository;
    private final MemberService memberService;

    /*
    @PostMapping("/choose-door-deco")

    public ApiResponse<?> chooseDoorDeco(@RequestBody ImageFileDto imageFileDto, @AuthenticationPrincipal MemberDetailsDto member){
        ImageUrlDto imageUrlDto =s3Service.getS3Url("dogo-dogo",imageFileDto);
        return ApiResponse.ok(ResponseCode.Normal.SUCCESS,imageUrlDto);
    }
    */

    @PostMapping("/save-door-deco")
    public ApiResponse<?> saveDoorDeco(@RequestBody ImageFileDto imageFileDto,
                            @AuthenticationPrincipal MemberDetailsDto member){
g
        return ApiResponse.ok(ResponseCode.Normal.SUCCESS,
                s3Service.saveS3Url("dogo-dogo",imageFileDto,member));
    }
    @PostMapping("/send-door-deco")
    public void putDoorDeco(@RequestBody ImageFileDto imageFileDto, @AuthenticationPrincipal MemberDetailsDto member){
//        ImageUrlDto imageUrlDto =s3Service.getS3Url("dogo-dogo",imageFileDto);
//        return ApiResponse.ok(ResponseCode.Normal.SUCCESS,imageUrlDto);

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