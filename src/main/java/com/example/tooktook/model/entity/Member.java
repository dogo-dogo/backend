package com.example.tooktook.model.entity;

import com.example.tooktook.model.dto.enumDto.MemberRole;
import lombok.*;
import org.hibernate.envers.AuditOverride;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;
@Node
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member{
    @Id
    @GeneratedValue
    private Long memberId;
    @Column(columnDefinition = "NVARCHAR(30) NOT NULL")
    private String loginEmail;
    @Column(columnDefinition = "NVARCHAR(30) NOT NULL")

    private String nickname;

    @Column(columnDefinition = "TEXT")
    private String doorImg;

    @Column(columnDefinition = "SMALLINT")
    private Boolean visit;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    public void setDoorImg(String doorImg) {
        this.doorImg = doorImg;
    }

    @Builder
    public Member (String loginEmail, String nickname,
                   Boolean visit, MemberRole role,String color,String decorate,String doorImg ){
        this.loginEmail = loginEmail;
        this.nickname = nickname;
        this.visit = visit;
        this.role = role;
        this.doorImg = doorImg;
    }

    public void changeVisit(){
        this.visit = Boolean.TRUE;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Relationship(type = "CATEGORY")
    private List<Category> categories = new ArrayList<>();

    @Relationship(type = "NOTIFICATION")
    private List<Notification> notifications = new ArrayList<>();


    public void addCategory(Category category) {
        categories.add(category);
    }
    public void addNotification(Notification notification){
        notifications.add(notification);
    }

}
