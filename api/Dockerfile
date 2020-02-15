FROM gradle:5.6.2-jdk8 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle shadowJar

FROM openjdk:8-jre-slim

EXPOSE 7000

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/*all.jar /app/postroom.jar

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-Djava.security.egd=file:/dev/./urandom","-jar", "/app/postroom.jar", "run"]
