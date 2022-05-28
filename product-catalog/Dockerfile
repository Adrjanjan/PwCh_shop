FROM openjdk:17

EXPOSE 8081

RUN mkdir /app

COPY product-catalog-0.0.1-SNAPSHOT.jar /app/product-catalog.jar

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-jar","/app/product-catalog.jar"]
