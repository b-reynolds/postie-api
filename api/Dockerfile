FROM gradle:5.6.2-jdk8 as cache
RUN mkdir -p /home/gradle/cache_home
ENV GRADLE_USER_HOME /home/gradle/cache_home
COPY build.gradle /home/gradle/postie/
WORKDIR /home/gradle/postie
RUN gradle clean build -i --stacktrace

FROM gradle:5.6.2-jdk8 as build
COPY --from=cache /home/gradle/cache_home /home/gradle/.gradle
COPY . /usr/src/postie/
WORKDIR /usr/src/postie
RUN gradle shadowJar -i --stacktrace

FROM openjdk:8-jre-slim
EXPOSE 7000
RUN mkdir /app
COPY --from=build /usr/src/postie/build/libs/*all.jar /app/postie.jar
ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-Djava.security.egd=file:/dev/./urandom","-jar", "/app/postie.jar", "run"]
