# Multi-stage build for optimized image size
FROM maven:3.9-eclipse-temurin-21-alpine AS build

WORKDIR /build
COPY ../ .

# Copy pom.xml and download dependencies (cached layer)
COPY ../pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY ../src ./src

# Build the application (skip tests for faster builds)
RUN mvn clean package -DskipTests

# Runtime stage - smaller image
FROM eclipse-temurin:21-jre-alpine

# Set working directory
WORKDIR /app

# Copy the jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Set environment variables (can be overridden)
ENV SPRING_PROFILES_ACTIVE=prod

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
