package com.example.tooktook.model.repository;

import com.example.tooktook.model.dto.decoDto.ImageUrlDto;
import com.example.tooktook.model.entity.Member;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;



public interface MemberNeo4jRepository extends Neo4jRepository<Member, Long> {
    Optional<Member> findByLoginEmail(String email);
    Optional<Member> findByMemberId(Long memberId);

//    Optional<Member> findByColor(String color);
//
//    Optional<Member> findByDecorate(String decorate);


//  답변을 가지고 있는 질문들을 추출하는 JPA
//    @Query("MATCH (m:Member)-[:ASKS]->(q:Question)-[:HAS_ANSWER]->(a:Answer) " +
//            "WHERE id(m) = $memberId RETURN id(q) as questionId, q.text as questionText, collect(id(a)) as answerIds")
//    List<QuestionDTO> findQuestionsByMemberId(@Param("memberId") Long memberId);

    @Query("MATCH (m:Member)-[C:CATEGORY]->(c:Category)-[A:ASKS]->(Q:Question)" +
            "WHERE ID(m) = $memberId" +
            "OPTIONAL MATCH (Q)-[H:HAS_ANSWER]->(a:Answer)" +
            "WITH m, C, c, A, Q, H, a" +
            "FOREACH (ignoreMe IN CASE WHEN H IS NOT NULL AND a IS NOT NULL THEN [1] ELSE [] END |" +
            "    DELETE H, a, Q, A, c, C" +
            ")" +
            "FOREACH (ignoreMe IN CASE WHEN H IS NULL AND a IS NULL THEN [1] ELSE [] END |" +
            "    DELETE Q, A, c, C" +
            ") " +
            "DELETE m, C, c, A, Q")
    void deleteMemberInfo(@Param("memberId") Long memberId);

    @Query("MATCH (m:Member)-[N:NOTIFICATION]->(n:Notification)" + "WHERE id(m)=$memberId" + "DETACH DELETE m,N,n")
    void deleteNotification(@Param("memberId") Long memberId);


}
