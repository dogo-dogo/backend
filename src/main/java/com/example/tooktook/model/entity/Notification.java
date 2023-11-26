package com.example.tooktook.model.entity;

import lombok.Getter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.List;


@Node
@Getter
public class Notification {
    @Id
    @GeneratedValue
    private Long notificationId;
    private Integer beforeCnt;
    private int[] answerCounts;
    public void setBeforeCnt(Integer beforeCnt) {
        this.beforeCnt = beforeCnt;
    }

    public void setAnswerCounts(int[] answerCounts) {
        this.answerCounts = answerCounts;
    }
}
