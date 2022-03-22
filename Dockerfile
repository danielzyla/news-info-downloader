# # syntax=docker/dockerfile:1
FROM openjdk:11
WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

COPY src ./src

CMD ["./mvnw", "surefire:test"]
CMD ["./mvnw", "spring-boot:run"]