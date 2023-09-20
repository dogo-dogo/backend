FROM openjdk:17-alpine
WORKDIR /app
COPY . .
RUN apt-get update && apt-get install -y gradle
RUN gradle clean build
FROM adoptopenjdk:17-jre-hotspot
WORKDIR /app
COPY --from=build /app/build/libs/tooktook-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9090
CMD ["java", "-jar", "app.jar"]
