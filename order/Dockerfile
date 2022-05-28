FROM openjdk:17

EXPOSE 8083

RUN mkdir /app

COPY order-0.0.1-SNAPSHOT.jar /app/order.jar

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-jar","/app/order.jar"]
