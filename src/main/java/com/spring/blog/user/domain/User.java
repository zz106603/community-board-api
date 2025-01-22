package com.spring.blog.user.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 20, unique = true, nullable = false)
    private String loginId;

    @Column(length = 60, nullable = false)
    private String password;

    @Column(length = 20)
    private String name;

    private LocalDate birthday;

    @Column(length = 10)
    private String deleteYn;

    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private LocalDateTime deleteDate;

    @Column(length = 10)
    private String gender;

    @Column(length = 100, unique = true)
    private String email;

    @Column(length = 100)
    private String phone;

    @Column(length = 100)
    private String roles;

    // Getters and Setters
}