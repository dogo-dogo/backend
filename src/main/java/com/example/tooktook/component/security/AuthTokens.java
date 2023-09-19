package com.example.tooktook.component.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthTokens {

    private String jwtAccessToken;
    private String refreshToken;
    private String grantType;
    private Long expiresIn;


    public static AuthTokens of(
        String jwtAccessToken, String refreshToken, String grantType, Long expiresIn) {

        return new AuthTokens(jwtAccessToken, refreshToken, grantType, expiresIn);
    }
}
