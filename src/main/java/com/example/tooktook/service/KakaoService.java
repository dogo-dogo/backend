package com.example.tooktook.service;

import com.example.tooktook.common.response.ResponseCode;
import com.example.tooktook.common.response.ValidMember;
import com.example.tooktook.component.jwt.RequestOAuthInfoService;
import com.example.tooktook.component.security.AuthTokens;
import com.example.tooktook.component.security.AuthTokensGenerator;
import com.example.tooktook.exception.GlobalException;
import com.example.tooktook.model.dto.memberDto.MemberDto;
import com.example.tooktook.model.dto.enumDto.MemberRole;
import com.example.tooktook.model.dto.memberDto.MemberDetailsDto;
import com.example.tooktook.model.entity.Member;
import com.example.tooktook.model.repository.MemberNeo4jRepository;
import com.example.tooktook.oauth.client.OAuthInfoResponse;
import com.example.tooktook.oauth.client.OAuthLoginParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.servlet.http.HttpServletResponse;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class KakaoService {

    private final MemberNeo4jRepository memberNeo4jRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final RequestOAuthInfoService requestOAuthInfoService;
    public AuthTokens login(OAuthLoginParams kakaoAccessCode, HttpServletResponse response) {

        log.info("------------kakaoService  login 시작---------------");
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(kakaoAccessCode);
        String memberEmail = findOrCreateMember(oAuthInfoResponse);
        log.info("login memberId (login)  :: " + memberEmail);

        return authTokensGenerator.generate(memberEmail,response);
    }


    @Transactional
    public String findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
        String strEmail = "";
        log.error("findOrCreateMember :: " + oAuthInfoResponse.getEmail());

        if(oAuthInfoResponse.getEmail() == null || oAuthInfoResponse.getEmail().isEmpty()){
            String chgNick = ValidMember.getBase64EncodeString(oAuthInfoResponse.getNickName());
            strEmail = chgNick + "@" + "dogodogo.com";
            return memberNeo4jRepository.findByLoginEmail(strEmail).map(Member::getLoginEmail)
                    .orElseGet(() -> newMember(oAuthInfoResponse));
        }else{
            return memberNeo4jRepository.findByLoginEmail(oAuthInfoResponse.getEmail())
                    .map(Member::getLoginEmail)
                    .orElseGet(() -> newMember(oAuthInfoResponse));
        }
    }

    @Transactional
    public String newMember(OAuthInfoResponse oAuthInfoResponse) {

        String strEmail = "";

        if(oAuthInfoResponse.getEmail() == null || oAuthInfoResponse.getEmail().isEmpty()){
            String chgNick = ValidMember.getBase64EncodeString(oAuthInfoResponse.getNickName());
            strEmail = chgNick + "@" + "dogodogo.com";
        }else{
            strEmail = oAuthInfoResponse.getEmail();
        }
        Member member = Member.builder()
                .loginEmail(strEmail)
                .nickname(oAuthInfoResponse.getNickName())
                .gender(oAuthInfoResponse.getGender())
                .visit(Boolean.FALSE)
                .role(MemberRole.KAKAO)
                .doorImg("default")
                .build();
        log.error("newMember :: " + member.getMemberId());
        memberNeo4jRepository.save(member);

        return member.getLoginEmail();
//        return member == null ? strEmail : member.getLoginEmail();
    }

    public MemberDto getMemberInfo(Long memberId) {

        Member member = memberNeo4jRepository.findByMemberId(memberId).get();
        return MemberDto.from(member);
    }

    public MemberDetailsDto loadMemberByEmail(String email) {
        Member member = memberNeo4jRepository.findByLoginEmail(email)
                .orElseThrow(() -> new GlobalException(ResponseCode.ErrorCode.NOT_FIND_MEMBER));
        return MemberDetailsDto.from(member);
    }



}
