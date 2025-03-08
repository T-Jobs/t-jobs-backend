FROM maven:latest AS build
WORKDIR /app
COPY pom.xml .
COPY src/ src/
RUN mvn package

FROM openjdk:21-ea-31-slim
WORKDIR /app
COPY --from=build /app/target/app.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]