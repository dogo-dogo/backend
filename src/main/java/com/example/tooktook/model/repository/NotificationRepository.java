package com.example.tooktook.model.repository;

import com.example.tooktook.model.entity.Notification;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

public interface NotificationRepository extends Neo4jRepository<Notification , Long> {
    @Query("MATCH(m:Member)-[:NOTIFICATION]->(n:Notification) WHERE id(m) = $memberId RETURN n;")
    Notification findByNotification(@Param("memberId") Long memberId);

}
