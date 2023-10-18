package com.example.tooktook.model.entity;

import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
@Getter
@Setter
public class Answer {
    @Id
    @GeneratedValue
    private Long answerId;
    private String mainText;
    private String optionalText;
}
