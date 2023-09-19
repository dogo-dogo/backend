package com.example.tooktook.oauth.client;

public interface OAuthApiClient {

    OAuthProvider oAuthProvider();

    String requestAccessToken(OAuthLoginParams params);

    OAuthInfoResponse requestOauthInfo(String accessToken);


}
