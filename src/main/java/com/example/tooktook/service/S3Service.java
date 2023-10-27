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

    /*
    public S3Dto upload(MultipartFile multipartFile, String dirName) throws IOException {
        File file = convertMultipartFileToFile(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File convert fail"));

        return upload(file, dirName);
    }

    private S3Dto upload(File file, String dirName) {
        String key = randomFileName(file, dirName);
        String path = putS3(file, key);
        removeFile(file);

        return S3Dto
                .builder()
                .key(key)
                .path(path)
                .build();
    }

    private String randomFileName(File file, String dirName) {
        return dirName + "/" + UUID.randomUUID() + file.getName();
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return getS3(bucket, fileName);
    }
    */

    public String getS3(String bucket, ImageFileDto imageFileDto) {
        String dolorColor=imageFileDto.getDoorColor();
        String decoration=imageFileDto.getDecoration();
        return dolorColor;

    }

    @Transactional
    public ImageUrlDto saveS3Url(String bucket, ImageFileDto imageFileDto, MemberDetailsDto memberDto) {
        ImageUrlDto imageUrlDto=new ImageUrlDto();

        String dolorColor=imageFileDto.getDoorColor();
        String decoration=imageFileDto.getDecoration();

        imageUrlDto.setDoorColorUrl(amazonS3.getUrl(bucket, dolorColor).toString() + ".png");
        imageUrlDto.setDecorationUrl(amazonS3.getUrl(bucket, decoration).toString() + ".png");

        Member member = memberNeo4jRepository.findByMemberId(memberDto.getId())
                .orElseThrow( () -> new GlobalException(ResponseCode.ErrorCode.NOT_FIND_MEMBER));

        member.setColor(imageUrlDto.getDoorColorUrl());
        member.setDecorate(imageUrlDto.getDecorationUrl());

        memberNeo4jRepository.save(member);

        return imageUrlDto;

    }

    public void saveDoorDeco(String memberId, ImageUrlDto imageUrlDto){

    }

    /*
    private void removeFile(File file) {
        file.delete();
    }

    public Optional<File> convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(System.getProperty("user.dir") + "/" + multipartFile.getOriginalFilename());

        if (file.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(file)){
                fos.write(multipartFile.getBytes());
            }
            return Optional.of(file);
        }
        return Optional.empty();
    }

    public void remove(S3Dto s3Dto) {
        if (!amazonS3.doesObjectExist(bucket, s3Dto.getKey())) {
            throw new AmazonS3Exception("Object " +s3Dto.getKey()+ " does not exist!");
        }
        amazonS3.deleteObject(bucket, s3Dto.getKey());
    }

     */
}