# 블로그(BLOG)

- CRUD 기능이 포함된 블로그 개발 프로젝트

- 날짜별로 개발 현황 추가 예정

### 2024-05-20
- 포스트 저장/수정/삭제/조회(전체, 개수, 단일) API 개발

### 2024-05-21
- 페이징 기능 적용(Pagination 구현)

### 2024-05-22
- 사용자 테이블 생성
- 사용자 회원가입 API 개발

### 2024-05-23
- Spring Security 적용 및 로그인 인증(/login)
- CustomHandler 생성 및 클라이언트 응답
- (추후 Jwt Token 적용 예정)

### 2024-05-27
- Security 및 Jwt 토큰 적용
- Jwt 토큰 및 로그인 API 개발

### 2024-05-28
- AccessToken/RefreshToken 처리 및 응답
- RefreshToken 활용 AccessToken 재발급 API 개발

### 2024-05-29
- 사용자 ID 및 로그인/로그아웃 화면 연동
- 조회/추천수 API 개발(조회/추천수 증가, 추천 테이블 생성 및 사용자 정보 연동)

---

## 포스트 API

- [포스트 저장 API 명세서]
- [포스트 수정 API 명세서]
- [포스트 삭제 API 명세서]
- [포스트 전체 조회 API 명세서]
- [포스트 전체 개수 API 명세서]
- [포스트 단일 조회 API 명세서]

## 사용자 API

- [사용자 등록 API 명세서]

---

## 포스트 저장 API 명세서

### **POST /api/posts/create**

**Application/json**

| Param | Type |
| --- | --- |
| title | String |
| writer | String |
| content | text |
| category | String |

### Response

| Status | Message | Data |
| --- | --- | --- |
| 201 | Post created successfully | 성공 |
| 400 | Post creation failed | 실패 |
| 400 | Post creation failed | 에러 메시지 |

![스크린샷 2024-05-20 123041](https://github.com/zz106603/blog_springboot/assets/45379781/f0d573c9-5423-4197-97eb-bfb0bfc961eb)

---

## 포스트 수정 API 명세서

### **UPDATE /api/posts/update**

**Application/json**

| Param | Type |
| --- | --- |
| id | Long |
| title | String |
| writer | String |
| content | Text |
| category | String |

### Response

| Status | Message | Data |
| --- | --- | --- |
| 201 | Post updated successfully | 성공 |
| 400 | Post update failed | 실패 |
| 400 | Post update failed | 에러 메시지 |

![스크린샷 2024-05-20 143158](https://github.com/zz106603/blog_springboot/assets/45379781/3ece5064-e425-4c99-8a43-433a1e15b248)

---

## 포스트 삭제 API 명세서

### **DELETE /api/posts/delete**

| Param | Type |
| --- | --- |
| id | Long |

### Response

| Status | Message | Data |
| --- | --- | --- |
| 201 | Post deleted successfully | 성공 |
| 400 | Post delete failed | 실패 |
| 400 | Post delete failed | 에러 메시지 |

![스크린샷 2024-05-20 143308](https://github.com/zz106603/blog_springboot/assets/45379781/27edc319-7b75-4b84-9fb5-637b892f3ec4)

---

## 포스트 전체 조회 API 명세서

### **GET /api/posts/all**

| Param | Type |
| --- | --- |
| None | None |

### Response

| Status | Message | Data |
| --- | --- | --- |
| 200 | Posts selected successfully | [{}, {} .. ] |
| 404 | Posts not found | [{}] |

![스크린샷 2024-05-21 165512](https://github.com/zz106603/blog_springboot/assets/45379781/82baf557-4e11-43c0-aec9-92a1a8a20a62)

---

## 포스트 전체 개수 API 명세서

### **GET /api/posts/all/count**

| Param | Type |
| --- | --- |
| None | None |

### Response

| Status | Message | Data |
| --- | --- | --- |
| 200 | Posts selected successfully | 카운트 개수 |

![스크린샷 2024-05-20 142754](https://github.com/zz106603/blog_springboot/assets/45379781/607d41b8-e652-4bd9-b6a7-18cb33e2922d)

---

## 포스트 단일 조회 API 명세서

### **GET /api/posts/{id}**

| Param | Type |
| --- | --- |
| None | None |

### Response

| Status | Message | Data |
| --- | --- | --- |
| 200 | Post selected successfully | [{}] |
| 404 | Post not found with id:  + id | [{}] |

![스크린샷 2024-05-20 142942](https://github.com/zz106603/blog_springboot/assets/45379781/1ff0a72e-145b-4d34-8423-7a74d6fa8953)

---

## 사용자 등록 API 명세서

### **POST /api/auth/create**

**Application/json**

| Param | Type |
| --- | --- |
| loginId | String |
| password | String |
| name | String |
| birthday | LocalDate |
| gender | String |
| email | String |
| phone | String |

### Response

| Status | Message | Data |
| --- | --- | --- |
| 201 | User created successfully | 성공 |
| 400 | User creation failed | 실패 |
| 400 | User creation failed | 에러 메시지 |

![스크린샷 2024-05-22 170825](https://github.com/zz106603/blog_springboot/assets/45379781/b1a14d85-af83-46c4-85b9-d4b3518f67ca)

---

## Jwt 토큰 발급(로그인) API 명세서

### **POST /api/auth/login**

**Application/json**

| Param | Type |
| --- | --- |
| loginId | String |
| password | String |

### Response

| Status | Message | Data |
| --- | --- | --- |
| 200 | User login successfully | "grantType", "accessToken", "refreshToken" |
| 400 | User login failed | 실패 |
| 400 | User login failed | 에러 메시지 |

![스크린샷 2024-05-27 200234](https://github.com/zz106603/blog_springboot/assets/45379781/612af233-b453-40a7-86be-80ce8720790e)

---

## Jwt 토큰 재발급(Refresh) API 명세서

### **POST /api/auth/refresh**

**Application/json**

| Param | Type |
| --- | --- |
| refrehToken | String |

### Response

| Status | Message | Data |
| --- | --- | --- |
| 200 | User login successfully | "accessToken"|
| 400 | User login failed | 실패 |
| 400 | User login failed | 에러 메시지 |

![스크린샷 2024-05-28 170707](https://github.com/zz106603/blog_springboot/assets/45379781/0385a31c-5a3c-4ceb-bf3e-61c2305ac94f)

---

## 포스팅 추천 사용자 등록 API 명세서

---

## 포스팅 추천 사용자 조회 API 명세서(클라이언트 버튼 비활성화)