# 블로그(BLOG)

- CRUD 기능이 포함된 블로그 개발 프로젝트

- 날짜별로 개발 현황 추가 예정

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

![스크린샷 2024-05-20 123041.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/fe506ab6-2d0c-4278-9200-9029f295aec2/5a2e075b-6497-4413-ac6f-036b131de225/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7_2024-05-20_123041.png)

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

![스크린샷 2024-05-20 143158.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/fe506ab6-2d0c-4278-9200-9029f295aec2/8f9e61d0-5c5a-40ec-89b4-294933db3cd2/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7_2024-05-20_143158.png)

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

![스크린샷 2024-05-20 143308.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/fe506ab6-2d0c-4278-9200-9029f295aec2/680ddbde-2dfc-4bc4-9cab-a3360b0c3b9b/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7_2024-05-20_143308.png)

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

![스크린샷 2024-05-20 142630.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/fe506ab6-2d0c-4278-9200-9029f295aec2/bee3e322-e8c6-4be7-8886-cd3bbfdb42f6/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7_2024-05-20_142630.png)

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

![스크린샷 2024-05-20 142754.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/fe506ab6-2d0c-4278-9200-9029f295aec2/155ed718-bb17-4208-ab51-91c35ed4a7ca/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7_2024-05-20_142754.png)

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

![스크린샷 2024-05-20 142942.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/fe506ab6-2d0c-4278-9200-9029f295aec2/72b5fe61-8d1a-4577-9dab-911ee55a768c/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7_2024-05-20_142942.png)