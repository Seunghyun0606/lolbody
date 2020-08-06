FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/mongoApi-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} docker-springboot.jar

COPY SummonerValue.py SummonerValue.py
COPY csv/ csv/
RUN apk update && apk upgrade && apk add python3 && apk add py3-pip && pip3 install --upgrade pip
RUN apk add py3-numpy && apk add py3-scipy && apk add python3-dev && apk add g++
RUN pip3 install pandas


ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/docker-springboot.jar"]