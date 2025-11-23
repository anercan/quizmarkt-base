FROM maven:3.8.1-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk
COPY --from=build /app/target/*.jar app.jar
EXPOSE 9092
ENTRYPOINT ["java", "-jar", "app.jar"]
