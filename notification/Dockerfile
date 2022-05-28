FROM openjdk:17

EXPOSE 8082

RUN mkdir /app

COPY notification-0.0.1-SNAPSHOT.jar /app/notification.jar

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-jar","/app/notification.jar"]

