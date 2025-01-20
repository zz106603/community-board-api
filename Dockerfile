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

# .env 파일을 컨테이너에 복사
COPY .env /app/.env

# Ensure gradlew has execution permissions
RUN chmod +x gradlew

# Run Gradle build
RUN ./gradlew clean build -Pprofile=prod --stacktrace --info

# Production stage
FROM bellsoft/liberica-openjdk-alpine:17

# Set the working directory
WORKDIR /app

# Build stage에서 복사한 .env 파일을 Production stage에 복사
COPY --from=builder /app/.env /app/.env

# Copy the built JAR file from the build stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose the port that the application will run on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]