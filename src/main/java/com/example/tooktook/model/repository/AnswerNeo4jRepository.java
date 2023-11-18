package com.example.tooktook.model.repository;

import com.example.tooktook.model.dto.answerDto.AnswerDAO;
import com.example.tooktook.model.dto.answerDto.AnswerPageListDto;
import com.example.tooktook.model.entity.Answer;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AnswerNeo4jRepository extends Neo4jRepository<Answer, Long> {
    @Query("MATCH(M:Member)-[:CATEGORY]->(C:Category)-[:ASKS]->(Q:Question)-[:HAS_ANSWER]->(A:Answer) " +
            "WHERE id(M) = $memberId " +
            "RETURN id(A) as id, C.text as category, A.createdAt as createdAt A.giftImg as giftImg" +
            "ORDER BY A.createdAt")
    List<AnswerPageListDto> findAllByCategoryFromAnswer(@Param("memberId") Long memberId);

    @Query("MATCH(M:Member)-[:CATEGORY]->(C:Category)-[:ASKS]->(Q:Question)-[:HAS_ANSWER]->(A:Answer) " +
            "WHERE id(M) = $memberId " +
            "RETURN count(A)")
    int countByMemberId(@Param("memberId") Long memberId);


    Optional<Answer> findByAnswerId(Long answerId);

    @Query("MATCH(m:Member)-[:CATEGORY]->(c:Category)-[:ASKS]->(q:Question)-[:HAS_ANSWER]->(a:Answer)" +
            "WHERE id(m) = $memberId and id(a) = $answerId " +
            "RETURN id(a) as aid, a.giftImg as giftImg , a.mainText as mainText , a.optionalText as optionalText")
    AnswerDAO findByAnswersDetails(@Param("memberId") Long memberId, @Param("answerId") Long answerId);
}
