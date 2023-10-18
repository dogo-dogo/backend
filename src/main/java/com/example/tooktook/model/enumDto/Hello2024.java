package com.example.tooktook.model.enumDto;

public enum Hello2024 {
    QUESTION_1("내년은 %s와 어떤 한 해가 되 었으면 좋겠어?"),
    QUESTION_2("%s랑 2024년에 꼭 같이 하고 싶은 거 있어?"),
    QUESTION_3("%s한테 들려 주고 싶은 2024년 첫 곡 알려줘! 바로 들을게")
    ;
    private final String text;

    Hello2024(String text) {this.text = text;}
    public String getText(){return text;}
}
