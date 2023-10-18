package com.example.tooktook.model.entity;

import lombok.*;
import org.hibernate.envers.AuditOverride;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Node
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AuditOverride(forClass = BaseTimeEntity.class)
public class Member extends BaseTimeEntity{
    @Id
    @GeneratedValue
    private Long memberId;
    @Column(columnDefinition = "NVARCHAR(30) NOT NULL")
    private String loginEmail;
    @Column(columnDefinition = "NVARCHAR(30) NOT NULL")
    private String nickname;
    @Column(columnDefinition = "NVARCHAR(10)")
    private String gender;
    @Column(columnDefinition = "NVARCHAR(20)")
    private String color;
    @Column(columnDefinition = "NVARCHAR(255)")
    private String decorate;

    @Column(columnDefinition = "SMALLINT")
    private Boolean visit;

    @Builder
    public Member (String loginEmail, String nickname, String gender,Boolean visit){
        this.loginEmail = loginEmail;
        this.nickname = nickname;
        this.gender = gender;
        this.visit = visit;
    }

    public void changeVisit(){
        this.visit = Boolean.TRUE;
    }

    @Relationship(type = "CATEGORY")
    private List<Category> categories = new ArrayList<>();


    public void addCategory(Category category) {
        categories.add(category);
    }

}
