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


//    @Override
//    public String requestAccessToken(OAuthLoginParams params) {
//
//
//        String url = authUrl + "/oauth/token";
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        MultiValueMap<String, String> body = params.makeBody();
//        body.add("grant_type", GRANT_TYPE);
//        body.add("client_id", clientId);
//        body.add("client_secret",clientSecret);
//
//        log.info("---------------grant_type----------- : {} " , GRANT_TYPE);
//        log.info("---------------client_id----------- : {} " , clientId);
//        log.info("---------------client_secret----------- : {} " , clientSecret);
//
//        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);
//
//        log.info("---------request : {} {}----------" , request.getBody() ,request.getHeaders());
//        KakaoTokens response = restTemplate.postForObject(url, request, KakaoTokens.class);
//
//        assert response != null;
//        log.info("-------------response : {} ----------- ",response.getAccessToken());
//        return response.getAccessToken();
//    }
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
    public OAuthInfoResponse requestOauthInfo(String accessToken) {

        String url = apiUrl + "/v2/user/me";

        log.info("-----------requestOauthInfo -----------------");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Bearer " + accessToken);

        log.info("-----------accessToken : {}  -----------------" , accessToken);
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("property_keys", "[\"kakao_account.email\", \"kakao_account.gender\", \"kakao_account.profile\"]");

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

        return restTemplate.postForObject(url, request, OAuthInfoResponse.KakaoInfoResponse.class);
    }

}