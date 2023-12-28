package com.example.tooktook.oauth.kakao;

import com.example.tooktook.oauth.client.OAuthApiClient;
import com.example.tooktook.oauth.client.OAuthInfoResponse;
import com.example.tooktook.oauth.client.OAuthLoginParams;
import com.example.tooktook.oauth.client.OAuthProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
@Slf4j
public class KakaoApiClient implements OAuthApiClient {

    private static final String GRANT_TYPE = "authorization_code";

    @Value("${oauth.kakao.url.auth}")
    private String authUrl;
    @Value("${oauth.kakao.url.api}")
    private String apiUrl;
    @Value("${oauth.kakao.client-id}")
    private String clientId;
    @Value("${oauth.kakao.client-secret}")
    private String clientSecret;

    private final RestTemplate restTemplate;


    @Override
    public OAuthProvider oAuthProvider() {

        return OAuthProvider.KAKAO;
    }

    @Override
    public String requestAccessToken(OAuthLoginParams params) {

            log.info("------------------new kakao logic ------------");
            WebClient webClient = WebClient.create();
            String url = authUrl + "/oauth/token";

            return webClient.post()
                    .uri(url)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData("grant_type", GRANT_TYPE)
                            .with("client_id", clientId)
                            .with("client_secret", clientSecret)
                            .with(params.makeBody()))
                    .retrieve()
                    .bodyToMono(KakaoTokens.class)
                    .block()
                    .getAccessToken();

    }

    @Override
    public void kakaoUnlink(String accessToken) {
        String unlinkUrl = "https://kapi.kakao.com/v1/user/unlink";
        WebClient webClient = WebClient.create();

        String result = webClient.post()
                .uri(unlinkUrl)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        log.info("--------- 탈퇴 한 result : " + result);

    }
//    @Override
//    public OAuthInfoResponse requestOauthInfo(String accessToken) {
//
//        String url = apiUrl + "/v2/user/me";
//
//        log.info("-----------requestOauthInfo -----------------");
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        httpHeaders.set("Authorization", "Bearer " + accessToken);
//
//        log.info("-----------accessToken : {}  -----------------" , accessToken);
//        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
//        body.add("property_keys", "[\"kakao_account.email\", \"kakao_account.gender\", \"kakao_account.profile\"]");
//
//        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);
//
//        return restTemplate.postForObject(url, request, OAuthInfoResponse.KakaoInfoResponse.class);
//    }
    @Override
    public OAuthInfoResponse requestOauthInfo(String accessToken) {
        String url = apiUrl + "/v2/user/me";

        log.info("-----------requestOauthInfo -----------------");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Bearer " + accessToken);

        log.info("-----------accessToken : {}  -----------------", accessToken);
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("property_keys", "[\"kakao_account.email\", \"kakao_account.gender\", \"kakao_account.profile\"]");

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

        OAuthInfoResponse.KakaoInfoResponse responseBody =
                restTemplate.postForObject(url, request, OAuthInfoResponse.KakaoInfoResponse.class);

        if (responseBody != null) {
            long userId = responseBody.getId(); // 사용자 ID 추출
            log.info("User ID: {}", userId);
            return responseBody;
        } else {
            log.error("Error: Failed to retrieve user information");
            return null;
        }
    }



}