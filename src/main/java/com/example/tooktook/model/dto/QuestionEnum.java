package com.example.tooktook.model.dto;

public enum QuestionEnum {
    QUESTION_1("새로운 질문 1"),
    QUESTION_2("새로운 질문 2"),
    QUESTION_3("새로운 질문 3"),
    QUESTION_4("새로운 질문 4"),
    QUESTION_5("새로운 질문 5"),
    QUESTION_6("새로운 질문 6"),
    QUESTION_7("새로운 질문 7"),
    QUESTION_8("새로운 질문 8");

    private final String text;

    QuestionEnum(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
