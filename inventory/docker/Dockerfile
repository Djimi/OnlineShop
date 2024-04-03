FROM ubuntu:22.04

RUN apt update -y && apt install openjdk-17-jdk -y
COPY inventory-0.0.1-SNAPSHOT.jar /app/country-service/inventory.jar

CMD ["java", "-jar", "/app/country-service/inventory.jar"]