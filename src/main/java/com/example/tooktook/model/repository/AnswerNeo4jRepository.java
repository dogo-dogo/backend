package com.example.tooktook.model.repository;

import com.example.tooktook.model.entity.Answer;
import org.springframework.data.neo4j.repository.Neo4jRepository;


public interface AnswerNeo4jRepository extends Neo4jRepository<Answer, Long> {
}
