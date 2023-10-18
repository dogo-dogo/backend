package com.example.tooktook.controller;

import com.example.tooktook.component.jwt.JwtTokenProvider;
import com.example.tooktook.component.security.AuthTokens;
import com.example.tooktook.component.security.AuthTokensGenerator;
import com.example.tooktook.model.dto.MemberDto;
import com.example.tooktook.oauth.kakao.KakaoLoginParams;
import com.example.tooktook.service.KakaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final KakaoService kakaoService;

    private final AuthTokensGenerator authTokensGenerator;
    @PostMapping("/auth/kakao")
    public ResponseEntity<AuthTokens> loginKakao(@RequestBody KakaoLoginParams kakaoAccessCode) {

        AuthTokens authTokens = kakaoService.login(kakaoAccessCode);
        return ResponseEntity.ok(authTokens);
    }


    @ResponseBody
    @GetMapping("/kakao/callback")
    public ResponseEntity<String> kakaoCallback(@RequestParam String code) {

        return ResponseEntity.ok(code);
    }


    @GetMapping("/check-access-token")
    public ResponseEntity<MemberDto> findByAccessToken(@RequestHeader("Authorization") String accessToken) {

        String actualAccessToken = extractAccessToken(accessToken);

        Long memberId = authTokensGenerator.extractMemberId(actualAccessToken);

        return ResponseEntity.ok(kakaoService.getMemberInfo(memberId));
    }

    private String extractAccessToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }
}
