package com.example.tooktook.model.dto.memberDto;

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
    private String doorImg;
    public static MemberDto from(Member member) {
        return MemberDto.builder()
                .memberId(member.getMemberId())
                .nickname(member.getNickname())
                .loginEmail(member.getLoginEmail())
                .doorImg(member.getDoorImg())
                .build();
    }
}
