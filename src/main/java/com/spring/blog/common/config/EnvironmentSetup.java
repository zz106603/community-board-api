package com.spring.blog.common.config;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class EnvironmentSetup {

//    @Autowired
//    private Environment environment; // Environment 객체를 주입받습니다
//
//    @PostConstruct
//    public void init() {
//        String[] activeProfiles = environment.getActiveProfiles(); // Spring의 Environment 객체를 사용하여 활성화된 프로파일 읽기
//        Dotenv dotenv;
//
//        // activeProfiles 배열을 통해 어떤 프로파일이 활성화되었는지 확인
//        if (Arrays.asList(activeProfiles).contains("local")) {
//            dotenv = Dotenv.configure().filename(".env.local").load(); // .env.local 파일 로드
//        } else {
//            dotenv = Dotenv.configure().load(); // 기본 .env 파일 로드
//        }
//
//        System.setProperty("spring.datasource.username", dotenv.get("DB_USERNAME"));
//        System.setProperty("spring.datasource.password", dotenv.get("DB_PASSWORD"));
//        System.setProperty("spring.datasource.url", dotenv.get("DB_URL"));
//    }
}