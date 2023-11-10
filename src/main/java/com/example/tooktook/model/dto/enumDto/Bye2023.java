package com.example.tooktook.model.dto.enumDto;

public enum Bye2023 {
    QUESTION_1("2023년의 %s(을)를 7글자로 표현해줘"),
    QUESTION_2("올해가 가기 전 마지막으로 %s에게 듣고 싶은 말을 알려줘!"),
    QUESTION_3("올해 가장 %s(과)와 함께 기뻐했던 순간은 언제야?"),
    QUESTION_4("올해 %s(이)랑 있었던 에피소드 중에 가장 기억에 남는 건 뭐야?"),
    QUESTION_5("올해 새롭게 알게 된 %s의 모습은 뭐야?"),
    QUESTION_6("올해 어떤 계절의 %s 모습이 가장 기억나는지 말해줘!"),
    QUESTION_7("내 첫인상 어땠어?"),
    QUESTION_8("MBTI 맞춰봐")
    ;

    private final String text;

    Bye2023(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }


}
