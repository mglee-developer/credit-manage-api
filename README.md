# 💳 신용관리 API (Credit Management API)

신용점수 조회 및 대출 한도 시뮬레이션을 제공하는 RESTful API 서버입니다.

## 🛠 기술 스택

| 분류 | 기술 |
| --- | --- |
| Language | Java17 |
| Framework | Spring Boot 4.0.5 |
| Security | Spring Security, JWT |
| ORM | Spring Data JPA |
| Database | MySQL 8 |
| Build | Gradle |
| API Docs | Swagger (springdoc-openapi) |

## 📌 주요 기능

- **회원 인증** : 회원가입, 로그인, JWT 토큰 기반 인증
- **신용점수 관리** : 신용점수 등록/수정/조회, 변동 이력 관리
- **대출 시뮬레이션** : 신용점수 기반 대출 한도·금리·월 상환금액 계산

## 🏗 프로젝트 구조

src/main/java/com/example/credit_manage_api <br>
├── domain <br>
│   ├── user <br>
│   │   ├── controller <br>
│   │   ├── service <br>
│   │   ├── repository <br>
│   │   ├── entity <br>
│   │   └── dto <br>
│   ├── credit <br>
│   │   ├── controller <br>
│   │   ├── service <br>
│   │   ├── repository <br>
│   │   ├── entity <br>
│   │   └── dto <br>
│   └── loan <br>
│       ├── controller <br>
│       ├── service <br>
│       ├── calculator <br>
│       └── dto <br>
└── global <br>
├── config <br>
├── exception <br>
└── security <br>

## 📡 API 명세

### 회원 API

| Method | URL | 설명 | 인증 |
| --- | --- | --- | --- |
| POST | /api/users/sign-up | 회원가입 | ❌ |
| POST | /api/users/login | 로그인 | ❌ |
| GET | /api/users/me | 내 정보 조회 | ✅ |

### 신용점수 API

| Method | URL                  | 설명 | 인증 |
| --- |----------------------| --- | --- |
| POST | /api/credit/score    | 신용점수 등록/수정 | ✅ |
| POST | /api/credit/score    | 신용점수 조회 | ✅ |
| GET | /api/credit/history  | 신용점수 이력 조회 | ✅ |

### 대출 API

| Method | URL | 설명 | 인증 |
| --- | --- | --- | --- |
| POST | /api/loan/simulate | 대출 한도 시뮬레이션 | ✅|

## 💡 신용점수 구간별 대출 한도

| 신용점수 | 최대 한도 | 금리 |
| --- | --- | --- |
| 850 ~ 1000점 | 5,000만원 | 3.5% |
| 750 ~ 849점 | 3,000만원 | 5.0% |
| 650 ~ 749점 | 1,000만원 | 7.5% |
| 0 ~ 549점 | 대출 불가 | - |

## ⚙️ 실행 방법

```sql
CREATE DATABASE credit_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;
```

### 2. application.yml 설정

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/credit_db
    username: {MySQL 유저명}
    password: {MySQL 비밀번호}

jwt:
  srcret: {32글자 이상 시크릿 키}
  expiration: 86400000
```

### 3. 서버 실행
```bash
./gradlew bootRun
```

### 4. Swagger UI 접속
http://localhost:8080/swagger-ui.html

## 🔐 인증 방법

1. `/api/users/login` 으로 로그인
2. 응답의 `accessToken` 복사
3. Swagger UI 우측 상단 **Authorize 🔒** 클릭
4. 토큰값 입력 후 인증

## 🚀 설계 포인트

- **도메인 중심 패키지 구조**: 레이어별이 아닌 도메인별로 응집도 높게 설계
- **JWT 인증**: Stateless 방식으로 서버 확장에 유리
- **변경 감지 (Dirty Checking)**: JPA 변경 감지를 활용한 엔티티 수정
- **전역 예외처리**: GlobalExceptionHandler로 일관된 에러 응답
- **단일 책임 원칙**: LoanCalculator를 별도 분리하여 계산 책임 분리