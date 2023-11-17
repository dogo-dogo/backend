package com.example.tooktook.model.repository;

import com.example.tooktook.model.dto.categoryDto.CategoryDto;
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
    @Query("MATCH (m:Member)-[:CATEGORY]->(c:Category)-[:ASKS]->(q:Question)-[:HAS_ANSWER]->(a:Answer)\n" +
            "WHERE id(m) = $memberId AND id(c) = $cid " +
            "WITH id(q) AS qid, q.text AS questions, COLLECT({answerId: id(a), giftImg: a.giftImg, mainText: a.mainText, optionalText: a.optionalText}) AS answers " +
            "RETURN qid, questions, answers;")
    List<QuestionDto> findCategoryIdToQuestion(@Param("memberId")Long memberId,@Param("cid") Long cid);

    @Query("MATCH (m:Member)-[:CATEGORY]->(c:Category)-[:ASKS]->(q:Question)" +
            " WHERE id(m) = $memberId and id(c)=$cid" +
            " RETURN id(q) as qid , q.text as questions")
    List<QuestionRndDto> findCategoryIdToRandomQuestion(@Param("memberId")Long memberId, @Param("cid") Long cid);

    @Query("MATCH (m:Member)-[:CATEGORY]->(c:Category)-[:ASKS]->(q:Question)-[:HAS_ANSWER]->(a:Answer) " +
            "WHERE id(m) = $memberId RETURN id(q) as qid, q.text as questions, COLLECT({answerId: id(a), giftImg: a.giftImg, mainText: a.mainText, optionalText: a.optionalText}) AS answers")
    List<QuestionDto> findByAllAnswers(@Param("memberId") Long memberId);
}
