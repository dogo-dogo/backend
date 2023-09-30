package com.example.tooktook.model.entity;

import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;

@Node
@Getter
@Setter
public class Question {
    @Id
    @GeneratedValue
    private Long questionId;
    private String text;
    private List<Long> answerIds;

    @Relationship(type = "HAS_ANSWER")
    private List<Answer> answers = new ArrayList<>();

    public void askAnswer(Answer answer) {
        answers.add(answer);
    }
}
