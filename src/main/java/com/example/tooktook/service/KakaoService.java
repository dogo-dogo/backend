package com.example.tooktook.service;

import com.example.tooktook.component.jwt.RequestOAuthInfoService;
import com.example.tooktook.component.security.AuthTokens;
import com.example.tooktook.component.security.AuthTokensGenerator;
import com.example.tooktook.model.dto.MemberDto;
import com.example.tooktook.model.entity.Member;
import com.example.tooktook.model.repository.MemberRepository;
import com.example.tooktook.oauth.client.OAuthInfoResponse;
import com.example.tooktook.oauth.client.OAuthLoginParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class KakaoService {

    private final MemberRepository memberRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final RequestOAuthInfoService requestOAuthInfoService;
//    private final KakaoApiClient kakaoApiClient;


    public AuthTokens login(OAuthLoginParams kakaoAccessCode) {

        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(kakaoAccessCode);
        Long memberId = findOrCreateMember(oAuthInfoResponse);

        return authTokensGenerator.generate(memberId);
    }


    private Long findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {

        return memberRepository.findByLoginEmail(oAuthInfoResponse.getEmail())
                .map(Member::getMemberId)
                .orElseGet(() -> newMember(oAuthInfoResponse));
    }


    private Long newMember(OAuthInfoResponse oAuthInfoResponse) {

        Member member = Member.builder()
                .loginEmail(oAuthInfoResponse.getEmail())
                .nickname(oAuthInfoResponse.getNickName())
                .gender(oAuthInfoResponse.getGender())
                .build();

        return memberRepository.save(member).getMemberId();
    }

    public MemberDto getMemberInfo(Long memberId) {

        Member member = memberRepository.findByMemberId(memberId).get();
        return MemberDto.from(member);
    }


}
