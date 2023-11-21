package com.example.tooktook.common.response;


import com.example.tooktook.exception.GlobalException;
import com.example.tooktook.model.dto.memberDto.MemberDetailsDto;
import org.springframework.util.Base64Utils;


public class ValidMember {
    public static void validCheckNull(MemberDetailsDto member){
        if(member == null || member.getEmail().isEmpty()){
            throw new GlobalException(ResponseCode.ErrorCode.EMPTY_ACCESS_TOKEN);
        }
    }
    public static String getBase64EncodeString(String email){
        return Base64Utils.encodeToString(email.getBytes());
    }
    public static String getBase64DecodeString(String email){
        return new String(Base64Utils.decode(email.getBytes()));
    }
}
