package com.example.tooktook.service;

import com.example.tooktook.common.response.ResponseCode;
import com.example.tooktook.exception.ErrorCode;
import com.example.tooktook.exception.GlobalException;
import com.example.tooktook.model.entity.Member;
import com.example.tooktook.model.repository.MemberNeo4jRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberNeo4jRepository memberNeo4jRepository;

    @Transactional
    public void setNickName(String nickName, Long memberId){
        Member member = memberNeo4jRepository.findByMemberId(memberId)
                .orElseThrow( () -> new GlobalException(ResponseCode.ErrorCode.NOT_FIND_MEMBER));
        member.setNickname(nickName);
        memberNeo4jRepository.save(member);
    }
}
