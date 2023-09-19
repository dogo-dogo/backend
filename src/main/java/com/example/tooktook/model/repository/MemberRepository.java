package com.example.tooktook.model.repository;

import com.example.tooktook.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByLoginEmail(String email);
    Optional<Member> findByMemberId(Long memberId);

}
