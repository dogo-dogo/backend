package com.example.tooktook.service;

import com.example.tooktook.common.response.ResponseCode;
import com.example.tooktook.exception.GlobalException;
import com.example.tooktook.model.dto.answerDto.AnswerDownDto;
import com.example.tooktook.model.dto.memberDto.MemberDetailsDto;
import com.example.tooktook.model.entity.Member;
import com.example.tooktook.model.repository.AnswerNeo4jRepository;
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

    private final AnswerNeo4jRepository answerNeo4jRepository;
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

    public int countingMember() {
        return memberNeo4jRepository.findByCountingMember();
    }

    public AnswerDownDto downloadsContents(MemberDetailsDto loginMember) {
        Long memberId = loginMember.getId();

        if(!memberNeo4jRepository.findByMemberId(memberId).isPresent()){
            throw new GlobalException(ResponseCode.ErrorCode.NOT_FIND_MEMBER);
        }else if(answerNeo4jRepository.findByAnswerCount(memberId) > 0){
            return answerNeo4jRepository.AnswerDownLoads(memberId);
        }else{
            throw new GlobalException(ResponseCode.ErrorCode.NOT_FIND_ANSWERS);
        }

    }
}
