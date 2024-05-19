package com.spring.blog.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class ProfileChecker {

    @Autowired
    private Environment env;

    @PostConstruct
    public void init() {
        String[] activeProfiles = env.getActiveProfiles();
        System.out.println("현재 활성화된 프로필: " + Arrays.toString(activeProfiles));
    }
}