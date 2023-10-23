package com.example.tooktook.model.entity;

import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import javax.persistence.PrePersist;
import java.time.LocalDateTime;

@Node
@Getter
public class Answer{
    @Id
    @GeneratedValue
    private Long answerId;
    private String mainText;
    private String optionalText;
    private LocalDateTime createdAt;

    public void setMainText(String mainText) {

        this.mainText = mainText;
    }

    public void setOptionalText(String optionalText) {

        this.optionalText = optionalText;
    }
    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }

}