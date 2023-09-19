package com.example.tooktook.oauth.client;

import org.springframework.util.MultiValueMap;

public interface OAuthLoginParams {

    OAuthProvider oAuthProvider();

    MultiValueMap<String, String> makeBody();
}
