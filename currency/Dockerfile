FROM openjdk:17

EXPOSE 8081

RUN mkdir /app

COPY target/currency-0.0.1-SNAPSHOT-exec.jar /app/currency.jar
RUN chown 777 /app/currency.jar


ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-jar","/app/currency.jar"]
