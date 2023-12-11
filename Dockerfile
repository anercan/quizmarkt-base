# Use Maven for building and OpenJDK for runtime
FROM maven:3.8.3-openjdk-17-slim AS build

WORKDIR /app

# Copy only the necessary files
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package

# Final image with only JRE
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the JAR file into the container
COPY --from=build /app/target/quesmarktbase-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the port on which your Java application will run
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
