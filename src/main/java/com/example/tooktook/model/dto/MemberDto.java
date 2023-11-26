package com.example.tooktook.model.dto;

import com.example.tooktook.model.entity.Member;
import lombok.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class MemberDto {
    private Long memberId;
    private String loginEmail;
    private String nickname;
    private String gender;
    private String color;
    private String decorate;
    public static MemberDto from(Member member) {
        return MemberDto.builder()
                .memberId(member.getMemberId())
                .nickname(member.getNickname())
                .gender(member.getGender())
                .loginEmail(member.getLoginEmail())
                .color(member.getColor())
                .decorate(member.getDecorate())
                .build();
    }
}