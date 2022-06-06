FROM openjdk:17

EXPOSE 8082

RUN mkdir /app

COPY target/notification-0.0.1-SNAPSHOT-exec.jar /app/notification.jar
COPY kubernetes/pwch-shop-a4c490bd6950.json /app/auth.json
RUN chown 777 /app/notification.jar

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-jar","/app/notification.jar"]

