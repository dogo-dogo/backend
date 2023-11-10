package com.example.tooktook.controller;

import com.example.tooktook.common.response.ApiResponse;
import com.example.tooktook.common.response.ResponseCode;
import com.example.tooktook.model.dto.decoDto.DoorImgDto;
import com.example.tooktook.model.dto.decoDto.GiftImgDto;
import com.example.tooktook.model.dto.decoDto.ImageUrlDto;
import com.example.tooktook.model.dto.memberDto.MemberDetailsDto;
import com.example.tooktook.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/s3")
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service s3Service;


    @PostMapping("/save-door-img")
    public ApiResponse<?> saveDoorDeco(@RequestBody DoorImgDto doorImgDto,
                            @AuthenticationPrincipal MemberDetailsDto member){

        s3Service.saveDoorS3Url(doorImgDto,member);
        return ApiResponse.ok(ResponseCode.Normal.SUCCESS,String.format("%s님의 img 설정",member.getNickName()));
    }

    @GetMapping("/get-img")
    public ApiResponse<String> getDoorImg(@AuthenticationPrincipal MemberDetailsDto member){
        return ApiResponse.ok(ResponseCode.Normal.RETRIEVE,s3Service.getS3Url(member.getId()));
    }

    @PostMapping("/save-gift-img")
    public ApiResponse<?> saveGiftImg(@AuthenticationPrincipal MemberDetailsDto member,@RequestBody GiftImgDto giftImgDto){
        s3Service.saveGiftS3Url(member,giftImgDto);
        return ApiResponse.ok(ResponseCode.Normal.CREATE,String.format("%s님의 Gift img 설정",member.getNickName()));
    }



}