package com.spring.blog;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlogApplication {

	public static void main(String[] args) {

		// .env 파일 로드
		Dotenv dotenv = Dotenv.load();

		// 환경 변수 값으로 시스템 속성 설정
		System.setProperty("spring.datasource.username", dotenv.get("DB_USERNAME"));
		System.setProperty("spring.datasource.password", dotenv.get("DB_PASSWORD"));
		System.setProperty("spring.datasource.url", dotenv.get("DB_URL"));

		System.setProperty("google.client-id", dotenv.get("GOOGLE_CLIENT_ID"));
		System.setProperty("google.client-secret", dotenv.get("GOOGLE_CLIENT_SECRET"));

		System.setProperty("jwt.secret", dotenv.get("JWT_SECRET"));

		System.setProperty("spring.profiles.active", dotenv.get("SPRING_PROFILES_ACTIVE"));

		SpringApplication.run(BlogApplication.class, args);
	}

}
