FROM openjdk:17-alpine

EXPOSE 8085

RUN mkdir /app

COPY target/product-catalog-0.0.1-SNAPSHOT.jar /app/product-catalog.jar

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-jar","/app/product-catalog.jar"]
