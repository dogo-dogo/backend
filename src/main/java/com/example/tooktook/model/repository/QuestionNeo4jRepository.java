package com.example.tooktook.model.repository;

import com.example.tooktook.model.dto.categoryDto.CategoryDto;
import com.example.tooktook.model.dto.questionDto.QuestionAllDto;
import com.example.tooktook.model.dto.questionDto.QuestionDto;
import com.example.tooktook.model.dto.questionDto.QuestionRndDto;
import com.example.tooktook.model.entity.Question;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionNeo4jRepository extends Neo4jRepository<Question, Long> {
    @Query("MATCH (m:Member)-[:CATEGORY]->(c:Category) " +
            "WHERE id(m) =$memberId RETURN id(c) " +
            "as categoryId, c.text as categoryName")
    List<CategoryDto> findQuestionsByMemberId(@Param("memberId") Long memberId);

//    @Query("MATCH (m:Member)-[:CATEGORY]->(c:Category)-[:ASKS]->(q:Question)-[:HAS_ANSWER]->(a:Answer)" +
//            " WHERE id(m) = $memberId and id(c)=$cid" +
//            " RETURN id(q) as qid , q.text as questions, id(a) as aid")
//    List<QuestionDto> findCategoryIdToQuestion(@Param("memberId")Long memberId,@Param("cid") Long cid);
    @Query("MATCH (m:Member)-[:CATEGORY]->(c:Category)-[:ASKS]->(q:Question)-[:HAS_ANSWER]->(a:Answer) " +
            "WHERE id(m) = $memberId AND id(c) = $cid " +
            "WITH id(q) AS qid, q.text AS questions, COLLECT(id(a)) AS answerIds, " +
            "COLLECT(a.giftImg) AS giftImg,COLLECT(a.mainText) as mainText ,COLLECT(a.optionalText) as optionalText " +
            "RETURN qid, questions, answerIds,giftImg,mainText,optionalText;")
    List<QuestionDto> findCategoryIdToQuestion(@Param("memberId")Long memberId,@Param("cid") Long cid);

    @Query("MATCH (m:Member)-[:CATEGORY]->(c:Category)-[:ASKS]->(q:Question)" +
            " WHERE id(m) = $memberId and id(c)=$cid" +
            " RETURN id(q) as qid , q.text as questions")
    List<QuestionRndDto> findCategoryIdToRandomQuestion(@Param("memberId")Long memberId, @Param("cid") Long cid);

    @Query("MATCH (m:Member)-[:CATEGORY]->(c:Category)-[:ASKS]->(q:Question)-[:HAS_ANSWER]->(a:Answer) " +
            "WHERE id(m) = $memberId RETURN id(q) as qid, q.text as questions, collect(id(a)) as answerIds,COLLECT(a.giftImg) AS giftImg,COLLECT(a.mainText) as mainText ,COLLECT(a.optionalText) as optionalText")
    List<QuestionDto> findByAllAnswers(@Param("memberId") Long memberId);

    @Query("MATCH (m:Member)-[:CATEGORY]->(c:Category)-[:ASKS]->(q:Question) " +
            "WHERE id(m) = $memberId " +
            "RETURN COLLECT(id(q)) as qid, COLLECT(q.text) as questions,id(c) as cid, c.text as categoryName;")
    List<QuestionAllDto> findByAllCategoryQuestions(@Param("memberId") Long memberId);
}
