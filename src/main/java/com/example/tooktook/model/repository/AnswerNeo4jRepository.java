package com.example.tooktook.model.repository;

import com.example.tooktook.model.dto.answerDto.AnswerPageDto;
import com.example.tooktook.model.dto.answerDto.AnswerPageListDto;
import com.example.tooktook.model.entity.Answer;
import com.example.tooktook.model.entity.Notification;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnswerNeo4jRepository extends Neo4jRepository<Answer, Long> {
    @Query("MATCH(M:Member)-[:CATEGORY]->(C:Category)-[:ASKS]->(Q:Question)-[:HAS_ANSWER]->(A:Answer) " +
            "WHERE id(M) = $memberId " +
            "RETURN id(A) as id, C.text as category, A.createdAt as createdAt " +
            "ORDER BY A.createdAt")
    List<AnswerPageListDto> findAllByCategoryFromAnswer(@Param("memberId") Long memberId);

    @Query("MATCH(M:Member)-[:CATEGORY]->(C:Category)-[:ASKS]->(Q:Question)-[:HAS_ANSWER]->(A:Answer) " +
            "WHERE id(M) = $memberId " +
            "RETURN count(A)")
    int countByMemberId(@Param("memberId") Long memberId);


}
