FROM openjdk:17-jdk-slim-buster
COPY build/libs/floodguard-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 3000
ENTRYPOINT ["java", "-jar", "/app.jar"]
