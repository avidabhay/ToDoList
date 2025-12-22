# Multi-stage build for optimized image size
FROM maven:3.9-eclipse-temurin-21-alpine AS build

WORKDIR /build

# Copy project files
COPY ../pom.xml ./pom.xml
COPY ../src ./src

# Download dependencies and build
RUN mvn dependency:go-offline -B
RUN mvn clean package -DskipTests

# Runtime stage - smaller image
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy the jar from build stage
COPY --from=build /build/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Set environment variables (can be overridden)
ENV SPRING_PROFILES_ACTIVE=prod

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
