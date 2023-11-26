FROM openjdk:17-alpine

ARG JAR_FILE=/build/libs/tooktook-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} /tooktook.jar

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","-Duser.timezone=Asia/Seoul", "/tooktook.jar"]
