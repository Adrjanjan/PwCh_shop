FROM openjdk:17-alpine

EXPOSE 8085

RUN mkdir /app

COPY target/product-catalog-0.0.1-SNAPSHOT-exec.jar /app/product-catalog.jar
RUN chown 777 /app/product-catalog.jar

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-jar","/app/product-catalog.jar"]
