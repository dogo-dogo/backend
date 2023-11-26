package com.example.tooktook.model.repository;

import com.example.tooktook.model.dto.categoryDto.CategoryDto;
import com.example.tooktook.model.dto.categoryDto.CategoryNotify;
import com.example.tooktook.model.dto.categoryDto.mainPageDto;
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
//    @Query("MATCH (m:Member)-[:CATEGORY]->(c:Category)-[:ASKS]->(q:Question)-[:HAS_ANSWER]->(a:Answer) " +
//            "WHERE id(m) = $memberId AND id(c) = $cid " +
//            "WITH id(q) AS qid, q.text AS questions, COLLECT(id(a)) AS answerIds, " +
//            "COLLECT(a.giftImg) AS giftImg,COLLECT(a.mainText) as mainText ,COLLECT(a.optionalText) as optionalText, a.createdAt as cdt " +
//            "RETURN qid, questions, answerIds,giftImg,mainText,optionalText, cdt;")
//    List<QuestionDto> findCategoryIdToQuestion(@Param("memberId")Long memberId,@Param("cid") Long cid);

    @Query("MATCH (m:Member)-[:CATEGORY]->(c:Category)-[:ASKS]->(q:Question)-[:HAS_ANSWER]->(a:Answer) " +
            "            WHERE id(m) = $memberId AND id(c) = $cid " +
            "            WITH q, COLLECT(a) AS answers " +
            "RETURN q.text AS questions, " +
            "       id(q) AS qid, " +
            "       [answer IN answers | id(answer)] AS answerIds, " +
            "       [answer IN answers | answer.giftImg] AS giftImg, " +
            "       [answer IN answers | answer.mainText] AS mainText, " +
            "       [answer IN answers | answer.optionalText] AS optionalText, " +
            "       [answer IN answers | answer.createdAt] AS cdt " +
            "ORDER BY cdt DESC;")
    List<QuestionDto> findCategoryIdToQuestion(@Param("memberId")Long memberId,@Param("cid") Long cid);
//    @Query("MATCH (m:Member)-[:CATEGORY]->(c:Category)-[:ASKS]->(q:Question)-[:HAS_ANSWER]->(a:Answer) " +
//            "WHERE id(m) = $memberId RETURN id(q) as qid, q.text as questions, collect(id(a)) as answerIds,COLLECT(a.giftImg) AS giftImg,COLLECT(a.mainText) as mainText ,COLLECT(a.optionalText) as optionalText," +
//            " a.createdAt as cdt;")
//    List<QuestionDto> findByAllAnswers(@Param("memberId") Long memberId);
    @Query("MATCH (m:Member)-[:CATEGORY]->(c:Category)-[:ASKS]->(q:Question)-[:HAS_ANSWER]->(a:Answer) " +
            "WHERE id(m) = $memberId " +
            "WITH q, COLLECT(a) AS answers " +
            "RETURN q.text AS questions, " +
            "       id(q) AS qid, " +
            "       [answer IN answers | id(answer)] AS answerIds, " +
            "       [answer IN answers | answer.giftImg] AS giftImg, " +
            "       [answer IN answers | answer.mainText] AS mainText, " +
            "       [answer IN answers | answer.optionalText] AS optionalText, " +
            "       [answer IN answers | answer.createdAt] AS cdt;")
    List<QuestionDto> findByAllAnswers(@Param("memberId") Long memberId);

    @Query("MATCH (m:Member)-[:CATEGORY]->(c:Category)-[:ASKS]->(q:Question)" +
            " WHERE id(m) = $memberId and id(c)=$cid" +
            " RETURN id(q) as qid , q.text as questions")
    List<QuestionRndDto> findCategoryIdToRandomQuestion(@Param("memberId")Long memberId, @Param("cid") Long cid);


    @Query("MATCH (m:Member)-[:CATEGORY]->(c:Category)-[:ASKS]->(q:Question) " +
            "WHERE id(m) = $memberId " +
            "RETURN COLLECT(id(q)) as qid, COLLECT(q.text) as questions,id(c) as cid, c.text as categoryName;")
    List<QuestionAllDto> findByAllCategoryQuestions(@Param("memberId") Long memberId);

    @Query("MATCH (m:Member)-[:CATEGORY]->(c:Category)-[:ASKS]->(q:Question) " +
            "WHERE id(m) = $memberId " +
            "OPTIONAL MATCH (q)-[:HAS_ANSWER]->(a:Answer) " +
            "WITH c, COUNT(a) AS answerCount " +
            "RETURN c.text as CategoryName, answerCount " +
            "ORDER BY CategoryName DESC;")
    List<CategoryNotify> findAllByCounting(@Param("memberId") Long memberId);

    @Query("MATCH(m:Member)-[:CATEGORY]->(c:Category)-[:ASKS]->(q:Question)-[:HAS_ANSWER]->(a:Answer) " +
            "WHERE id(m) = $memberId " +
            "return c.text as categoryName ,COLLECT(a.giftImg) as giftImg " +
            "ORDER BY categoryName DESC;")
    List<mainPageDto> mySpaceGetAll(@Param("memberId") Long memberId);
}
