FROM openjdk:17

EXPOSE 8081

RUN mkdir /app

COPY currency-0.0.1-SNAPSHOT.jar /app/currency.jar

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-jar","/app/currency.jar"]
