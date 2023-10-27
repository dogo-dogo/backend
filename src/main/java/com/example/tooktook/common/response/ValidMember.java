package com.example.tooktook.common.response;


import com.example.tooktook.exception.GlobalException;
import com.example.tooktook.model.dto.memberDto.MemberDetailsDto;


public class ValidMember {
    public static void validCheckNull(MemberDetailsDto member){
        if(member == null || member.getEmail().isEmpty()){
            throw new GlobalException(ResponseCode.ErrorCode.EMPTY_ACCESS_TOKEN);
        }
    }
}
