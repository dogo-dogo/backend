package com.example.tooktook.model.dto.enumDto;

public enum Compliment {

    QUESTION_1("올해 %s님에게 해줄 칭찬을 말해줘!"),
    QUESTION_2("당신이 지켜주고 싶은 %s님만의 올해 매력은 뭐야?"),
    QUESTION_3("%s님한테 꼭 해주고 싶었던 말이 뭐야?")
    ;
    private final String text;

    Compliment(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }


}
