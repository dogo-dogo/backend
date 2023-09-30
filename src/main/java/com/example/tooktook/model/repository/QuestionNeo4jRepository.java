package com.example.tooktook.model.repository;

import com.example.tooktook.model.entity.Question;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface QuestionNeo4jRepository extends Neo4jRepository<Question, Long> {
}
