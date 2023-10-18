package com.example.tooktook.model.enumDto;

public enum CategoryEnum {
    CATEGORY("BYE2023"),
    CATEGORY2("COMPLIMENT"),
    CATEGORY3("FI"),
    CATEGORY4("GROWTH"),
    CATEGORY5("HELLO2024")
    ;
    private final String text;

    CategoryEnum(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
