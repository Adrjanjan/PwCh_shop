FROM openjdk:17

EXPOSE 8084

RUN mkdir /app

COPY target/payment-0.0.1-SNAPSHOT-exec.jar /app/payment.jar
RUN chown 777 /app/payment.jar

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-jar","/app/payment.jar"]
