FROM openjdk:17

EXPOSE 8086

RUN mkdir /app

COPY target/shipment-0.0.1-SNAPSHOT.jar /app/shipment.jar

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-jar","/app/shipment.jar"]
