package com.example.tooktook.config;


import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Valid;

@Configuration
public class S3Config {

    //S3를 등록한 사람이 전달받은, 접속하기 위한 key 값
    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    //S3를 등록한 사람이 전달받은, 접속하기 위한 secret key값
    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    //S3를 등록한 사람이 S3를 사용할 지역
    @Value("${cloud.aws.region.static}")
    private String region;

    //전달 받은 Accesskey와 SecretKey로 아마존 서비스 실행 준비
    @Bean
    public AmazonS3 amazonS3Client() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        return AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }
}
