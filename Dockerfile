FROM openjdk:17-alpine

ENV APP_HOME=/usr/app/

WORKDIR $APP_HOME
ENV DATABASE_URL=jdbc:mariadb://mariadb:3306/groomthon

COPY . .
RUN gradle wrapper

RUN ./gradlew clean build

EXPOSE 8080

CMD ["java", "-jar", "-Dspring.profiles.active=prod", "/usr/app/build/libs/tooktook-0.0.1-SNAPSHOT.jar"]