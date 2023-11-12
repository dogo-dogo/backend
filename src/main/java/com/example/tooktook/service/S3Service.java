package com.example.tooktook.service;

import com.amazonaws.services.s3.AmazonS3;
import com.example.tooktook.common.response.ResponseCode;
import com.example.tooktook.exception.GlobalException;
import com.example.tooktook.model.dto.decoDto.*;
import com.example.tooktook.model.dto.memberDto.MemberDetailsDto;
import com.example.tooktook.model.entity.Answer;
import com.example.tooktook.model.entity.Member;
import com.example.tooktook.model.repository.AnswerNeo4jRepository;
import com.example.tooktook.model.repository.MemberNeo4jRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@ResponseBody
@Transactional(readOnly = true)
@Slf4j
public class S3Service {

    private final AmazonS3 amazonS3;

    private final MemberNeo4jRepository memberNeo4jRepository;
    private final AnswerNeo4jRepository answerNeo4jRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    public void saveDoorS3Url(DoorImgDto doorImgDto, MemberDetailsDto memberDto) {
        log.info("------------S3service 시작 ----------------");
        Member member = memberNeo4jRepository.findByMemberId(memberDto.getId())
                .orElseThrow( () -> new GlobalException(ResponseCode.ErrorCode.NOT_FIND_MEMBER));


        String s3Url = "";

        try{
            if(!doorImgDto.getDoorColor().isEmpty() && !doorImgDto.getDecoration().isEmpty()) {
                s3Url = getBucketS3Url(doorImgDto);
                member.setDoorImg(s3Url);
            }
        }catch (GlobalException e){
            throw new GlobalException(ResponseCode.ErrorCode.AWS_S3_ERROR);
        }
        log.info("------------S3service 종료 ----------------");
        memberNeo4jRepository.save(member);

    }
    @Transactional
    public void saveGiftS3Url(MemberDetailsDto memberDto, GiftImgDto giftImgDto){
        log.info("------------S3service 시작 ----------------");
        Member member = memberNeo4jRepository.findByMemberId(memberDto.getId())
                .orElseThrow( () -> new GlobalException(ResponseCode.ErrorCode.NOT_FIND_MEMBER));

        Answer answer = answerNeo4jRepository.findByAnswerId(giftImgDto.getAnswerId())
                .orElseThrow(() -> new GlobalException(ResponseCode.ErrorCode.NOT_FIND_ANSWER_ID));

        String s3Url = "";
        try {
            if (!giftImgDto.getGiftColor().isEmpty() && !giftImgDto.getDecoration().isEmpty()) {
                s3Url = getBucketS3Url(giftImgDto);
                answer.setGiftImg(s3Url);
            }
        }catch (GlobalException e){
            throw new GlobalException(ResponseCode.ErrorCode.AWS_S3_ERROR);
        }
        log.info("------------S3service 종료 ----------------");
        answerNeo4jRepository.save(answer);
    }

    private String getBucketS3Url(DoorImgDto doorImgDto){


        return amazonS3.getUrl(bucket,
                String.format("%s_%s_%s", doorImgDto.getType(), doorImgDto.getDoorColor(), doorImgDto.getDecoration())
                        ).toString() + ".png";
    }
    private String getBucketS3Url(GiftImgDto giftImgDto){

        return amazonS3.getUrl(bucket,
                String.format("%s_%s_%s", giftImgDto.getType(), giftImgDto.getGiftColor(), giftImgDto.getDecoration())
        ).toString() + ".png";
    }

    public String getS3Url(Long memberId) {
        log.info("------------S3service 시작 ----------------");
        Member member = memberNeo4jRepository.findByMemberId(memberId)
                .orElseThrow(() -> new GlobalException(ResponseCode.ErrorCode.NOT_FIND_MEMBER));

        log.info("------------S3service 종료 ----------------");
        log.info("return Data : > {}" , member.getDoorImg());
        return member.getDoorImg();
    }

    public ArrayList<DoorColorDto> putDoorColor(){


        DoorColorDto red= new DoorColorDto("#EA383F","red");
        DoorColorDto green = new DoorColorDto("#009456", "green");
        DoorColorDto white = new DoorColorDto("#F5F5F5", "white");
        DoorColorDto brown = new DoorColorDto("#905700", "brown");
        ArrayList<DoorColorDto> doorColorDto =new ArrayList<>();
        doorColorDto.add(red);
        doorColorDto.add(green);
        doorColorDto.add(white);
        doorColorDto.add(brown);
        return doorColorDto;

    }


    public ArrayList<GiftColorDto> putGiftColor() {

        GiftColorDto red = new GiftColorDto("#EA383F", "red");
        GiftColorDto green = new GiftColorDto("#009456", "green");
        GiftColorDto yellow = new GiftColorDto("#F9F0A7", "yellow");
        GiftColorDto pink = new GiftColorDto("#FEC8C8", "pink");
        GiftColorDto skyblue = new GiftColorDto("#D4DBFF", "skyblue");


        ArrayList<GiftColorDto> giftColorDto = new ArrayList<>();
        giftColorDto.add(green);
        giftColorDto.add(yellow);
        giftColorDto.add(red);
        giftColorDto.add(pink);
        giftColorDto.add(skyblue);
        return giftColorDto;

    }
    public ArrayList<RibbonColorDto> putRibbonColor(){

        RibbonColorDto white = new RibbonColorDto("#F5F5F5","01");
        RibbonColorDto red = new RibbonColorDto("#EA383F","02");
        RibbonColorDto green = new RibbonColorDto("#009456","03");


        ArrayList<RibbonColorDto> ribbonColorDto =new ArrayList<>();
        ribbonColorDto.add(green);
        ribbonColorDto.add(red);
        ribbonColorDto.add(white);
        return ribbonColorDto;

    }




    public ArrayList<DoorDecoDto> putDoorDeco(){

        ArrayList<DoorDecoDto> doorDecoDto =new ArrayList<>();

        for (int num=1; num<6; num++){
            String decoKey="0"+ num;
            DoorDecoDto decoration= new DoorDecoDto(getBucketS3Url(decoKey),decoKey);
            doorDecoDto.add(decoration);
        }


        return doorDecoDto;
    }

    private String getBucketS3Url(String decoKey){

        return amazonS3.getUrl(bucket, decoKey) + ".png";

    }

}