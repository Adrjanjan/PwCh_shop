FROM openjdk:17

EXPOSE 8083

RUN mkdir /app

COPY target/order-0.0.1-SNAPSHOT-exec.jar /app/order.jar
COPY kubernetes/pwch-shop-8a69e6d70dcb.json /app/auth.json

RUN chown 777 /app/order.jar

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-jar","/app/order.jar"]
