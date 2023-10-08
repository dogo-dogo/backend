package com.example.tooktook.model.enumDto;

public enum GrowthEnum {
    QUESTION_1("%s를 만나 면서 한 새로운 경험은 뭐야?"),
    QUESTION_2("%s를 만나 면서 성장한 부분이 있어?")
    ;

    private final String text;

    GrowthEnum(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
