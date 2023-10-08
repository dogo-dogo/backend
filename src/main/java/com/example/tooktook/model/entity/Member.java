package com.example.tooktook.model.entity;

import com.example.tooktook.model.dto.Neo4Dto;
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
    private String size;

    @Column(columnDefinition = "SMALLINT")
    private Boolean visit;

    @Builder
    public Member (String loginEmail, String nickname, String gender,Boolean visit){
        this.loginEmail = loginEmail;
        this.nickname = nickname;
        this.gender = gender;
        this.visit = visit;
    }

    public void update(Neo4Dto neo4Dto){
        this.color = neo4Dto.getColor();
        this.size = neo4Dto.getSize();
        this.nickname = neo4Dto.getNickName();
    }
    public void changeVisit(){
        this.visit = Boolean.TRUE;
    }

    @Relationship(type = "ASKS")
    private List<Question> questions = new ArrayList<>();


    public void askQuestion(Question question) {
        questions.add(question);
    }

    public void chgupdate(String nickname) {
        this.nickname = nickname;
    }
}
