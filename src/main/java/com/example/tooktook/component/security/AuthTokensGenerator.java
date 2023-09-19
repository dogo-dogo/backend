package com.example.tooktook.component.security;

import com.example.tooktook.component.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class AuthTokensGenerator {

    private static final String BEARER_TYPE = "Bearer";


    @Value("${ACCESS_TOKEN_EXPIRE_TIME}")
    private long ACCESS_TOKEN_EXPIRE_TIME;

    @Value("${REFRESH_TOKEN_EXPIRE_TIME}")
    private long REFRESH_TOKEN_EXPIRE_TIME;

    private final JwtTokenProvider jwtTokenProvider;


    public AuthTokens generate(Long memberId) {

        long now = (new Date()).getTime();
        Date accessTokenExpiredAt = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        Date refreshTokenExpiredAt = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);

        String subject = memberId.toString();
        String jwtAccessToken = jwtTokenProvider.generate(subject, accessTokenExpiredAt);
        String refreshToken = jwtTokenProvider.generate(subject, refreshTokenExpiredAt);

        return AuthTokens.of(
            jwtAccessToken, refreshToken, BEARER_TYPE, ACCESS_TOKEN_EXPIRE_TIME / 1000L);
    }


    public Long extractMemberId(String accessToken) {

        return Long.valueOf(jwtTokenProvider.extractSubject(accessToken));
    }
}
