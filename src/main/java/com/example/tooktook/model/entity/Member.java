package com.example.tooktook.model.entity;

import lombok.*;
import org.hibernate.envers.AuditOverride;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@AuditOverride(forClass = BaseTimeEntity.class)
public class Member extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    @Column(columnDefinition = "NVARCHAR(30) NOT NULL")
    private String loginEmail;
    @Column(columnDefinition = "NVARCHAR(30) NOT NULL")
    private String nickname;
    @Column(columnDefinition = "NVARCHAR(255)")
    private String password;
    @Column(columnDefinition = "NVARCHAR(10)")
    private String gender;
}
