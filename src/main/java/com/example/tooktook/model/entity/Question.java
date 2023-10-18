package com.example.tooktook.model.entity;

import lombok.*;
import org.springframework.data.neo4j.core.schema.*;

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

    @Property("question")
    private String question;
    @Relationship(type = "HAS_ANSWER")
    private List<Answer> answers = new ArrayList<>();

    public void askAnswer(Answer answer) {
        answers.add(answer);
    }
    public Question(String question){
        this.text = question;
    }
}
