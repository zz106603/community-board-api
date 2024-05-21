# 블로그(BLOG)

- CRUD 기능이 포함된 블로그 개발 프로젝트

- 날짜별로 개발 현황 추가 예정

### 2024-05-20
- 포스트 저장/수정/삭제/조회(전체, 개수, 단일) API 추가

### 2024-05-21
- 페이징 코드 적용(Pagination 구현)

---

## 포스트 API

- [포스트 저장 API 명세서]
- [포스트 수정 API 명세서]
- [포스트 삭제 API 명세서]
- [포스트 전체 조회 API 명세서]
- [포스트 전체 개수 API 명세서]
- [포스트 단일 조회 API 명세서]

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