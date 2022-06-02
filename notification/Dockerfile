FROM openjdk:17

EXPOSE 8082

RUN mkdir /app

COPY target/notification-0.0.1-SNAPSHOT-exec.jar /app/notification.jar
RUN chown 777 /app/notification.jar

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-jar","/app/notification.jar"]

