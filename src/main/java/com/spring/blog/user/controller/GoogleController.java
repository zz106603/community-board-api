package com.spring.blog.user.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

//@Tag(name = "oAuth", description = "oAuth 관련 API")
//@RestController
//@RequestMapping("/oauth2")
//public class GoogleController {
//
//    private Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @GetMapping("/authorization/google")
//    public void redirectToGoogle(HttpServletResponse response) throws IOException {
//        String googleAuthUrl = "https://accounts.google.com/o/oauth2/auth?response_type=code"
//                + "&client_id=325980586177-3t2u0q00j66388g3q0pooqc6ngsq28en.apps.googleusercontent.com"
//                + "&redirect_uri=http://localhost:8080/login/oauth2/code/google"
//                + "&scope=profile%20email";
//
//        response.sendRedirect(googleAuthUrl);
//    }
//
//}
