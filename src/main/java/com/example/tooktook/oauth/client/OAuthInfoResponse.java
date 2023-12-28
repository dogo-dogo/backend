package com.example.tooktook.oauth.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

public interface OAuthInfoResponse {

    String getEmail();

    String getNickName();
    long getId();

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class KakaoInfoResponse implements OAuthInfoResponse {

        @JsonProperty("kakao_account")
        private KakaoAccount kakaoAccount;

        @JsonProperty("id")
        private long id;

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
        public long getId() {
            return id;
        }


    }
}
