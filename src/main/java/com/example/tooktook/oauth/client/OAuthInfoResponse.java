package com.example.tooktook.oauth.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

public interface OAuthInfoResponse {

    String getEmail();

    String getNickName();

    String getGender();

    String getImg();

    OAuthProvider getOAuthProvider();


    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class KakaoInfoResponse implements OAuthInfoResponse {

        @JsonProperty("kakao_account")
        private KakaoAccount kakaoAccount;


        @Getter
        @JsonIgnoreProperties(ignoreUnknown = true)
        static class KakaoAccount {

            private KakaoProfile profile;
            private String email;
            private String gender;
        }


        @Getter
        @JsonIgnoreProperties(ignoreUnknown = true)
        static class KakaoProfile {

            private String nickname;
            private String profile_image_url;
        }


        @Override
        public String getEmail() {
            return kakaoAccount.email;
        }


        @Override
        public String getNickName() {
            return kakaoAccount.profile.nickname;
        }


        @Override
        public String getGender() {
            return kakaoAccount.gender;
        }


        @Override
        public String getImg() {
            return kakaoAccount.profile.profile_image_url;
        }


        @Override
        public OAuthProvider getOAuthProvider() {
            return OAuthProvider.KAKAO;
        }
    }
}
