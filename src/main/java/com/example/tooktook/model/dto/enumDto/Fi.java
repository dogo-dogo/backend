package com.example.tooktook.model.dto.enumDto;

public enum Fi {
    QUESTION_1("%s님(을)를 너 친구들 한테 소개 하면 뭐라고 소개 할꺼야?"),
    QUESTION_2("%s님(을)를 너 회사 사람들 한테 소개 하면 뭐라고 소개 할꺼야?"),
    QUESTION_3("만약에 우리가 아이돌이 되었어. %s는 어떤 포지션이 어울려? 너도 궁금하다!"),
    QUESTION_4("만약에 12월 31일에 %s님(이)랑 있는다면 어디로 가고 싶어?"),
    QUESTION_5("%s님에게 연말 상을 수여 한다면 어떤 상을 주고 싶어?")
    ;
    private final String text;

    Fi(String text) {this.text = text;}

    public String getText() {return text;}
}
