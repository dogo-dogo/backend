package com.example.tooktook.service;

import com.amazonaws.services.s3.AmazonS3;
import com.example.tooktook.common.response.ResponseCode;
import com.example.tooktook.exception.GlobalException;
import com.example.tooktook.model.dto.decoDto.DoorImgDto;
import com.example.tooktook.model.dto.decoDto.GiftImgDto;
import com.example.tooktook.model.dto.decoDto.ImageUrlDto;
import com.example.tooktook.model.dto.memberDto.MemberDetailsDto;
import com.example.tooktook.model.entity.Answer;
import com.example.tooktook.model.entity.Member;
import com.example.tooktook.model.repository.AnswerNeo4jRepository;
import com.example.tooktook.model.repository.MemberNeo4jRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@ResponseBody
@Transactional(readOnly = true)
public class S3Service {

    private final AmazonS3 amazonS3;

    private final MemberNeo4jRepository memberNeo4jRepository;

    private final AnswerNeo4jRepository answerNeo4jRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    public void saveDoorS3Url(DoorImgDto doorImgDto, MemberDetailsDto memberDto) {
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

        memberNeo4jRepository.save(member);

    }

    @Transactional
    public void saveGiftS3Url(MemberDetailsDto memberDto, GiftImgDto giftImgDto){
        Member member = memberNeo4jRepository.findByMemberId(memberDto.getId())
                .orElseThrow( () -> new GlobalException(ResponseCode.ErrorCode.NOT_FIND_MEMBER));
        Answer answer= answerNeo4jRepository.findByAnswerId(giftImgDto.getAnswerId())
                .orElseThrow( () -> new GlobalException(ResponseCode.ErrorCode.NOT_FIND_MEMBER));
        String s3Url="";

        try{
            if(!giftImgDto.getGiftColor().isEmpty() && !giftImgDto.getDecoration().isEmpty()){
                s3Url = getBucketS3Url(giftImgDto);
                answer.setGiftImg(s3Url);
            }
        }catch (GlobalException e){
            throw new GlobalException(ResponseCode.ErrorCode.AWS_S3_ERROR);
        }

        answerNeo4jRepository.save(answer);

    }

    private String getBucketS3Url(DoorImgDto doorImgDto){
        return amazonS3.getUrl(bucket,
                String.format("%s_%s_%s", doorImgDto.getType(),doorImgDto.getDoorColor(),doorImgDto.getDecoration())
                        ).toString() + ".png";
    }

    private String getBucketS3Url(GiftImgDto giftImgDto){
        return amazonS3.getUrl(bucket,
                String.format("%s_%s_%s", giftImgDto.getType(), giftImgDto.getGiftColor(),giftImgDto.getDecoration())
        ).toString() + ".png";
    }

    public String getS3Url(Long memberId) {
        Member member = memberNeo4jRepository.findByMemberId(memberId)
                .orElseThrow(() -> new GlobalException(ResponseCode.ErrorCode.NOT_FIND_MEMBER));

        return member.getDoorImg();
    }

}