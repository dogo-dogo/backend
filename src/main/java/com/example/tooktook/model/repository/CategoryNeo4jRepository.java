package com.example.tooktook.model.repository;


import com.example.tooktook.model.dto.categoryDto.CategoryListDto;
import com.example.tooktook.model.entity.Category;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryNeo4jRepository extends Neo4jRepository<Category,Long> {
    // 카테고리별 답변의 갯수를 출력하는 Cypher 쿼리문
    @Query("MATCH (m:Member)-[:CATEGORY]->(c:Category) " +
            "WHERE id(m) = $memberId " +
            "OPTIONAL MATCH (c)-[:ASKS]->(:Question)-[:HAS_ANSWER]->(aw:Answer) " +
            "WITH COLLECT(aw) AS totalAnswerList " +
            "WITH CASE WHEN totalAnswerList IS NULL THEN 0 ELSE SIZE(totalAnswerList) END as totalCount " +
            "RETURN null as cid, \"전체\" as categoryName, 0 as answerCount, totalCount " +
            "UNION " +
            "MATCH (m:Member)-[:CATEGORY]->(c:Category) " +
            "WHERE id(m) = $memberId " +
            "OPTIONAL MATCH (c)-[:ASKS]->(:Question)-[:HAS_ANSWER]->(aw:Answer) " +
            "WITH c, COLLECT(aw) AS answerList " +
            "WITH c, answerList, CASE WHEN answerList IS NULL THEN 0 ELSE SIZE(answerList) END as answerCount " +
            "RETURN id(c) as cid, c.text as categoryName, answerCount, 0 as totalCount ")
    List<CategoryListDto> findCategoryByCount(@Param("memberId") Long memberId);

}
