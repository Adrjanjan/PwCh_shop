FROM gradle:jdk17-alpine AS build

LABEL Description="User basket service"

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon -x test

FROM openjdk:17

EXPOSE 8080

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/basket-0.0.1-SNAPSHOT.jar /app/basket.jar

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-jar","/app/basket.jar"]
