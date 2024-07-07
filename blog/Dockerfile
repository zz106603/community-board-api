#베이스 이미지로 Java 17버전이 포함된 Docker 이미지를 사용
FROM bellsoft/liberica-openjdk-alpine:17

#Gradle을 사용해 빌드를 실행하는 명령어
CMD ["./gradlew", "clean", "build"]

# 작업 디렉토리 설정
WORKDIR /app

# Gradle 래퍼 및 빌드 스크립트 복사
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# 소스 파일 복사
COPY src src

# Gradle 래퍼 실행 권한 부여
RUN chmod +x ./gradlew

# Gradle을 사용해 빌드를 실행하는 명령어
RUN ./gradlew clean build -Dspring.profiles.active=local

#Gradle로 빌드한 jar파일의 위치를 변수로 설정
ARG JAR_FILE=build/libs/*.jar
#ARG JAR_FILE_PATH=target/*.jar -> 필요시 Maven으로 빌드한 jar파일의 위치를 변수로 설정

#JAR_FILE 변수에 지정된 파일을 app.jar라는 이름으로 컨테이너에 추가
COPY ${JAR_FILE} app.jar

#컨테이너 내에 /tmp 디렉토리를 불륨으로 설정
VOLUME /tmp

#컨테이너가 사용할 포트 설정
EXPOSE 8080

#컨테이너가 실행될 때 기보넞ㄱ으로 실행될 명령어를 설정, 이 경우에는 Java 애플리케이션을 실행하는 명령어
ENTRYPOINT ["java", "-Dspring.profiles.active=local", "-jar", "/app/app.jar"]