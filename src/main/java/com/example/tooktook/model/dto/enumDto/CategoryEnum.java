package com.example.tooktook.model.dto.enumDto;

public enum CategoryEnum {
    CATEGORY("Bye 2023"),
    CATEGORY2("칭찬"),
    CATEGORY3("만약에"),
    CATEGORY4("성장"),
    CATEGORY5("Hello 2024")
    ;
    private final String text;

    CategoryEnum(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
