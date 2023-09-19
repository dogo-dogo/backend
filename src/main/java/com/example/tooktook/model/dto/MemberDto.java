package com.example.tooktook.model.dto;

import com.example.tooktook.model.entity.Member;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    private Long memberId;
    private String loginEmail;
    private String nickname;
    private String password;
    private String gender;
    public static MemberDto from(Member member) {
        return MemberDto.builder()
                .memberId(member.getMemberId())
                .nickname(member.getNickname())
                .gender(member.getGender())
                .password(member.getPassword())
                .loginEmail(member.getLoginEmail())
                .build();
    }
}
