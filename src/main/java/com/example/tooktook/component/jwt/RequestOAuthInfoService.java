package com.example.tooktook.component.jwt;


import com.example.tooktook.oauth.client.OAuthApiClient;
import com.example.tooktook.oauth.client.OAuthInfoResponse;
import com.example.tooktook.oauth.client.OAuthLoginParams;
import com.example.tooktook.oauth.client.OAuthProvider;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Component
public class RequestOAuthInfoService {

    private final Map<OAuthProvider, OAuthApiClient> clients;


    public RequestOAuthInfoService(List<OAuthApiClient> clients) {

        this.clients = clients.stream().collect(
            Collectors.toUnmodifiableMap(OAuthApiClient::oAuthProvider, Function.identity())
        );
    }


    public OAuthInfoResponse request(OAuthLoginParams kakaoAccessCode) {

        OAuthApiClient client = clients.get(kakaoAccessCode.oAuthProvider());
        String accessToken = client.requestAccessToken(kakaoAccessCode);
        return client.requestOauthInfo(accessToken);
    }

//    public String getAccessToken(OAuthLoginParams accessCode) {
//
//        OAuthApiClient client = clients.get(accessCode.oAuthProvider());
//        return client.requestAccessToken(accessCode);
//    }
}
