FROM gradle:jdk17-alpine AS build

LABEL Description="Currency service"

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon -x test

FROM openjdk:17

EXPOSE 8081

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/currency-0.0.1-SNAPSHOT.jar /app/basket.jar

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-jar","/app/basket.jar"]
