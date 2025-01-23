FROM openjdk:17
WORKDIR /app

COPY .env /app/.env

# 로컬에서 빌드된 JAR 복사
COPY build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
