package com.example.tooktook.model.dto.memberDto;

import com.example.tooktook.model.dto.enumDto.MemberRole;
import com.example.tooktook.model.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDetailsDto implements UserDetails {
    private Long id;
    private String email;
    private String nickName;
    private String password;
    private MemberRole memberRole;

    @Builder
    public MemberDetailsDto(Long id, String email, String nickName, MemberRole memberRole) {
        this.id = id;
        this.email = email;
        this.nickName = nickName;
        this.password = null;
        this.memberRole = memberRole;
    }

    public static MemberDetailsDto from(Member member){
        return MemberDetailsDto.builder()
                .id(member.getMemberId())
                .email(member.getLoginEmail())
                .nickName(member.getNickname())
                .memberRole(member.getRole())
                .build();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.getMemberRole().toString()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    //계정 만료 (true 면 만료 x)
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    // 계정 잠금
    public boolean isAccountNonLocked() {
        return true;
    }


    @Override
    // 계정패스워드 만료
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    // 계정 사용가능한지
    public boolean isEnabled() {
        return true;
    }
}
