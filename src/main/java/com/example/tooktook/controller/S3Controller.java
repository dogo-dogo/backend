package com.example.tooktook.controller;

import com.example.tooktook.common.response.ApiResponse;
import com.example.tooktook.common.response.ResponseCode;
import com.example.tooktook.model.dto.decoDto.*;
import com.example.tooktook.model.dto.memberDto.MemberDetailsDto;
import com.example.tooktook.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/s3")
@RequiredArgsConstructor
@Slf4j
public class S3Controller {

    private final S3Service s3Service;


    @PostMapping("/save-door-img")
    public ApiResponse<?> saveDoorDeco(@RequestBody DoorImgDto doorImgDto,
                            @AuthenticationPrincipal MemberDetailsDto member){

        log.info("------------S3Controller 시작 ----------------");
        log.info("--------------path : /api/s3/save-door-img ---------------");
        s3Service.saveDoorS3Url(doorImgDto,member);
        log.info("------------S3Controller 종료 ----------------");
        return ApiResponse.ok(ResponseCode.Normal.SUCCESS,String.format("%s님의 img 설정",member.getNickName()));
    }
    @GetMapping("/get-img")
    public ApiResponse<String> getDoorImg(@AuthenticationPrincipal MemberDetailsDto member){
        log.info("------------S3Controller 시작 ----------------");
        log.info("--------------path : /api/s3/get-img ---------------");
        return ApiResponse.ok(ResponseCode.Normal.RETRIEVE,s3Service.getS3Url(member.getId()));
    }

    @PostMapping("/save-gift-img")
    public ApiResponse<?> saveGiftImg(@AuthenticationPrincipal MemberDetailsDto member,
                                      @RequestBody GiftImgDto giftImgDto){

        log.info("------------S3Controller 시작 ----------------");
        log.info("--------------path : /api/s3/save-gift-img ---------------");
        s3Service.saveGiftS3Url(member,giftImgDto);
        return ApiResponse.ok(ResponseCode.Normal.CREATE,String.format("%s님의 Gift img 설정",member.getNickName()));
    }

    @GetMapping("/send-door-details")
    public DoorSendDto sendDoorDetails(){

        DoorSendDto doorSendDto= new DoorSendDto(s3Service.putDoorColor(),s3Service.putDoorDeco());
        return doorSendDto;
    }

    @GetMapping("/send-gift-details")
    public GiftSendDto sendGiftDetails(){

        GiftSendDto giftSendDto=new GiftSendDto(s3Service.putGiftColor(),s3Service.putRibbonColor());
        return giftSendDto;
    }


}