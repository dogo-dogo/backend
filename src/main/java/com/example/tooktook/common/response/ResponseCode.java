package com.example.tooktook.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

public class ResponseCode {
    @Getter
    @RequiredArgsConstructor
    public enum ErrorCode {

        NOT_FIND_MEMBER(HttpStatus.NOT_FOUND,"존재하지 않는 이메일 입니다."),
        WRONG_MEMBER_PASSWORD(HttpStatus.UNAUTHORIZED,"패스워드가 일치 하지 않습니다."),
        ALREADY_MEMBER_EMAIL(HttpStatus.CONFLICT,"이미 이메일이 존재 합니다."),
        EMPTY_MEMBER_EMAIL(HttpStatus.BAD_REQUEST,"이메일을 입력해 주세요"),
        EMPTY_MEMBER_PASSWORD(HttpStatus.BAD_REQUEST,"패스워드를 입력해 주세요"),
        EMPTY_MEMBER_NICKNAME(HttpStatus.BAD_REQUEST,"닉네임을 입력 해주세요"),
        NOT_CHANGED_PASSWORD(HttpStatus.CONFLICT, "기존의 패스워드 입니다."),
        ALREADY_DELETED_MEMBER(HttpStatus.UNAUTHORIZED,"이미 탈퇴한 회원입니다."),

        //질문
        NOT_FIND_QUESTION_ID(HttpStatus.BAD_REQUEST,"질문이 존재 하지 않습니다"),

        //답변
        NOT_FIND_ANSWER_ID(HttpStatus.BAD_REQUEST,"답변이 존재 하지 않습니다"),
        // 카카오API
        WRONG_ACCESS_TOKEN_AUTH(HttpStatus.FORBIDDEN,"잘못된 인증입니다."),
        EMPTY_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED,"토큰이 비었습니다."),

        WRONG_AUTHORIZATION_HEADER(HttpStatus.UNAUTHORIZED,"비정상적인 인증 헤더입니다."),
        EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED,"토큰이 만료되었습니다."),
        JWT_EXCEPTION(HttpStatus.UNAUTHORIZED,"JWT 토큰 예외가 발생하였습니다."),
        INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"서버 내부 에러"),
        BAD_REQUEST(HttpStatus.BAD_REQUEST,"404 에러"),
        ;



        private final HttpStatus status;
        private final String MESSAGE;
    }
    @RequiredArgsConstructor
    @Getter
    public enum Normal {
        SUCCESS(HttpStatus.OK, "성공 하였습니다"),
        UPDATE(HttpStatus.OK, "수정 되었습니다"),
        CREATE(HttpStatus.CREATED, "생성 되었습니다"),
        DELETE(HttpStatus.OK, "삭제 되었습니다"),
        RETRIEVE(HttpStatus.OK, "조회 되었습니다");
        private final HttpStatus status;
        private final String MESSAGE;
    }
}
