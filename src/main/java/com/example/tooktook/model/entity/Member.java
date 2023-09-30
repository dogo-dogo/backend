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
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AuditOverride(forClass = BaseTimeEntity.class)
@Builder
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

    @Relationship(type = "ASKS")
    private List<Question> questions = new ArrayList<>();

    public void askQuestion(Question question) {
        questions.add(question);
    }

}
