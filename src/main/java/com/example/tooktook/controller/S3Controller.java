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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/s3")
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service s3Service;


    @PostMapping("/save-door-img")
    public ApiResponse<?> saveDoorDeco(@RequestBody ImageFileDto imageFileDto,
                            @AuthenticationPrincipal MemberDetailsDto member){

        s3Service.saveS3Url(imageFileDto,member);
        return ApiResponse.ok(ResponseCode.Normal.SUCCESS,String.format("%s님의 img 설정",member.getNickName()));
    }

    @GetMapping("/get-img")
    public ApiResponse<ImageUrlDto> getDoorImg(@AuthenticationPrincipal MemberDetailsDto member){
        return ApiResponse.ok(ResponseCode.Normal.RETRIEVE,s3Service.getS3Url(member.getId()));
    }


}