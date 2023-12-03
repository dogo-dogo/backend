package com.example.tooktook.service;

import com.example.tooktook.common.response.ResponseCode;
import com.example.tooktook.exception.GlobalException;
import com.example.tooktook.model.entity.Member;
import com.example.tooktook.model.repository.MemberNeo4jRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {

    private final MemberNeo4jRepository memberNeo4jRepository;

    @Transactional
    public void setNickName(String nickName, Long memberId){
        log.info("------------memberService 시작 ----------------");
        Member member = memberNeo4jRepository.findByMemberId(memberId)
                .orElseThrow( () -> new GlobalException(ResponseCode.ErrorCode.NOT_FIND_MEMBER));
        member.setNickname(nickName);
        memberNeo4jRepository.save(member);
        log.info("------------memberService 종료 ----------------");
    }

    @Transactional
    public void deleteMember(Long memberId){

        memberNeo4jRepository.findByMemberId(memberId)
                .orElseThrow(
                        () -> new GlobalException(
                                ResponseCode.ErrorCode.NOT_FIND_MEMBER)
                );

        memberNeo4jRepository.deleteMemberInfo(memberId);
        memberNeo4jRepository.deleteNotification(memberId);

    }
}
