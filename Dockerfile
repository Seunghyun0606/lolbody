FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/mongoApi-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} docker-springboot.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/docker-springboot.jar"]