FROM openjdk:17

EXPOSE 8080

RUN mkdir /app

COPY target/basket-0.0.1-SNAPSHOT-exec.jar /app/basket.jar
RUN chown 777 /app/basket.jar

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-jar","/app/basket.jar"]
