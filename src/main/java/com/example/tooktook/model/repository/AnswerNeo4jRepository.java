package com.example.tooktook.model.repository;

import com.example.tooktook.model.entity.Answer;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface AnswerNeo4jRepository extends Neo4jRepository<Answer, Long> {
}
