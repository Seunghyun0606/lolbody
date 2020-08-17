FROM openjdk:8-jdk-alpine

RUN apk update && apk upgrade && apk add python3 && apk add py3-pip && pip3 install --upgrade pip
RUN apk add py3-numpy && apk add py3-scipy && apk add python3-dev && apk add g++
RUN pip3 install pandas

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY Analysis/ Analysis/
# COPY SummonerValue.py SummonerValue.py
# COPY csv/ csv/


ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]