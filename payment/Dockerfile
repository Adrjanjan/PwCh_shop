FROM openjdk:17

EXPOSE 8084

RUN mkdir /app

COPY target/payment-0.0.1-SNAPSHOT.jar /app/payment.jar

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-jar","/app/payment.jar"]
