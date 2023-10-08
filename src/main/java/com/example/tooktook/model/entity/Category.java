package com.example.tooktook.model.entity;

import lombok.Getter;
import org.springframework.data.neo4j.core.schema.*;

import java.util.ArrayList;
import java.util.List;

@Node
@Getter
public class Category {

    @Id
    @GeneratedValue
    private Long categoryId;
    private String text;

    @Property("enumValue")
    private String enumValue;

    private List<Long> answerIds;

    @Relationship(type = "ASKS")
    private List<Question> questions = new ArrayList<>();

    public Category(String enumValue) {
        this.text = enumValue;
    }

    public void askQuestion(Question question) {
        questions.add(question);
    }
}

