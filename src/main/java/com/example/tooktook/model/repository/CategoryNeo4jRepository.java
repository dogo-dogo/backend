package com.example.tooktook.model.repository;


import com.example.tooktook.model.dto.categoryDto.CategoryListDto;
import com.example.tooktook.model.entity.Category;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryNeo4jRepository extends Neo4jRepository<Category,Long> {
    // 카테고리별 답변의 갯수를 출력하는 Cypher 쿼리문
    @Query("MATCH (m:Member)-[:CATEGORY]->(c:Category)-[:ASKS]->(q:Question) " +
            "OPTIONAL MATCH (q)-[:HAS_ANSWER]->(a:Answer) " +
            "WHERE id(m) = $memberId " +
            "WITH c, COALESCE(count(a), 0) AS answerCount " +
            "RETURN id(c) as cid, c.text as categoryName, answerCount")
    List<CategoryListDto> findCategoryByCount(@Param("memberId") Long memberId);

}
