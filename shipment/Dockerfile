FROM openjdk:17

EXPOSE 8083

RUN mkdir /app

COPY shipment-0.0.1-SNAPSHOT.jar /app/shipment.jar

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-jar","/app/shipment.jar"]
