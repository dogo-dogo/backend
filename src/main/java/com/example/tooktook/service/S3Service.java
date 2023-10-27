package com.example.tooktook.service;

import com.amazonaws.services.ec2.model.ResponseError;
import com.amazonaws.services.s3.AmazonS3;
import com.example.tooktook.common.response.ResponseCode;
import com.example.tooktook.exception.GlobalException;
import com.example.tooktook.model.dto.decoDto.ImageFileDto;
import com.example.tooktook.model.dto.decoDto.ImageUrlDto;
import com.example.tooktook.model.dto.memberDto.MemberDetailsDto;
import com.example.tooktook.model.entity.Member;
import com.example.tooktook.model.repository.MemberNeo4jRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URL;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@ResponseBody
@Transactional
public class S3Service {

    private final AmazonS3 amazonS3;

    private final MemberNeo4jRepository memberNeo4jRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    public void saveS3Url(ImageFileDto imageFileDto, MemberDetailsDto memberDto) {
        Member member = memberNeo4jRepository.findByMemberId(memberDto.getId())
                .orElseThrow( () -> new GlobalException(ResponseCode.ErrorCode.NOT_FIND_MEMBER));


        String s3Url = "";

        try{
            s3Url = getBucketS3Url(imageFileDto);

            ImageUrlDto imageUrlDto= ImageUrlDto.builder()
                    .doorUrl(s3Url)
                    .giftUrl(s3Url)
                    .build();

            if(imageFileDto.getType().equals("BG")) {
                imageUrlDto.setDoorUrl(s3Url);
                member.setDoorImg(s3Url);
            }else{
                imageUrlDto.setGiftUrl(s3Url);
                member.setGiftImg(s3Url);
            }

        }catch (GlobalException e){
            throw new GlobalException(ResponseCode.ErrorCode.AWS_S3_ERROR);
        }

        memberNeo4jRepository.save(member);

    }

    private String getBucketS3Url(ImageFileDto imageFileDto){
        return amazonS3.getUrl(bucket,
                String.format("%s_%s_%s",imageFileDto.getType(),imageFileDto.getDecoration(),imageFileDto.getDoorColor())
                        ).toString() + ".png";
    }

    public ImageUrlDto getS3Url(Long memberId) {
        Member member = memberNeo4jRepository.findByMemberId(memberId)
                .orElseThrow(() -> new GlobalException(ResponseCode.ErrorCode.NOT_FIND_MEMBER));

        return ImageUrlDto.builder()
                .doorUrl(member.getDoorImg())
                .giftUrl(member.getGiftImg())
                .build();
    }

}