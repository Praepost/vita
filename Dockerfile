FROM maven:3.8.5-openjdk-11-slim AS build

WORKDIR /app
COPY ./service/pom.xml /app/pom.xml
RUN mvn verify clean --fail-never
COPY service/src /app/src
RUN mvn -f /app/pom.xml clean package -Dmaven.test.skip=true

FROM openjdk:11-slim AS builder
WORKDIR /app
COPY --from=build /app/target/*.jar /app/app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM openjdk:11-slim
RUN apt update && \
    apt install -y netcat-openbsd && \
    apt clean
WORKDIR /app
COPY --from=builder app/dependencies/ ./
COPY --from=builder app/spring-boot-loader/ ./
COPY --from=builder app/snapshot-dependencies/ ./
COPY --from=builder app/application/ ./

COPY ./entrypoint.sh /app/