# Build stage
FROM bellsoft/liberica-openjdk-alpine:17 as builder

# Set the working directory
WORKDIR /app

# Copy the Gradle wrapper and build scripts
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

# Ensure gradlew has execution permissions
RUN chmod +x gradlew

# Run Gradle build
RUN ./gradlew clean build -Pprofile=prod

# Debugging step: List the contents of the build/libs directory
RUN ls -la build/libs

# Production stage
FROM bellsoft/liberica-openjdk-alpine:17

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose the port that the application will run on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]