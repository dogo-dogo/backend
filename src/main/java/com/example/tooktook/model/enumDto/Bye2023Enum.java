package com.example.tooktook.model.enumDto;

public enum Bye2023Enum {
    QUESTION_1("2023년의 %s를 7글자로 표현해줘"),
    QUESTION_2("올해가 가기 전 마지막으로 %s에게 듣고 싶은 말을 알려줘!"),
    QUESTION_3("올해 가장 %s와 함께 기뻐했던 순간은 언제야?"),
    QUESTION_4("올해 %s랑 있었던 에피소드 중에 가장 기억에 남는 건 뭐야?"),
    QUESTION_5("올해 새롭게 알게 된 %s의 모습은 뭐야?"),
    QUESTION_6("올해 어떤 계절의 %s 모습이 가장 기억나는지 말해줘!"),
    ;

    private final String text;

    Bye2023Enum(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
