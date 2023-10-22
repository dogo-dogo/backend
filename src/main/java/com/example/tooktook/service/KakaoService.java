package com.example.tooktook.service;

import com.example.tooktook.component.jwt.RequestOAuthInfoService;
import com.example.tooktook.component.security.AuthTokens;
import com.example.tooktook.component.security.AuthTokensGenerator;
import com.example.tooktook.model.dto.MemberDto;
import com.example.tooktook.model.entity.Member;
import com.example.tooktook.model.repository.MemberNeo4jRepository;
import com.example.tooktook.oauth.client.OAuthInfoResponse;
import com.example.tooktook.oauth.client.OAuthLoginParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class KakaoService {

    private final MemberNeo4jRepository memberNeo4jRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final RequestOAuthInfoService requestOAuthInfoService;
//    private final KakaoApiClient kakaoApiClient;


    public AuthTokens login(OAuthLoginParams kakaoAccessCode) {

        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(kakaoAccessCode);
        Long memberId = findOrCreateMember(oAuthInfoResponse);
        log.info("login memberId (login)  :: " + memberId);

        return authTokensGenerator.generate(memberId);
    }


    @Transactional
    public Long findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {

        log.error("findOrCreateMember :: " + oAuthInfoResponse.getEmail());
        return memberNeo4jRepository.findByLoginEmail(oAuthInfoResponse.getEmail())
                .map(Member::getMemberId)
                .orElseGet(() -> newMember(oAuthInfoResponse));
    }

    @Transactional
    public Long newMember(OAuthInfoResponse oAuthInfoResponse) {

        Member member = Member.builder()
                .loginEmail(oAuthInfoResponse.getEmail())
                .nickname(oAuthInfoResponse.getNickName())
                .gender(oAuthInfoResponse.getGender())
                .visit(Boolean.FALSE)
                .build();
        log.error("newMember :: " + member.getMemberId());
        memberNeo4jRepository.save(member);

        return member.getMemberId();
    }

    public MemberDto getMemberInfo(Long memberId) {

        Member member = memberNeo4jRepository.findByMemberId(memberId).get();
        return MemberDto.from(member);
    }


}
