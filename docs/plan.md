Kiến trúc Langora nên phát triển từ đây

Hiện tại:

identity-service

↓

Tách thành

gateway-service

identity-service

learning-service

ai-service
Phase 1 — Refactor Identity Service
Mục tiêu

Biến project hiện tại thành:

langora-identity-service
Bước 1

Đổi package

Ví dụ:

com.example.identityservice

↓

com.langora.identity
Bước 2

Chuẩn hóa module

Hiện tại:

controller
service
repository
entity
dto
mapper

Refactor thành:

src/main/java

com.langora.identity

├── auth
│   ├── controller
│   ├── service
│   ├── dto
│
├── user
│   ├── controller
│   ├── service
│   ├── entity
│
├── role
│
├── permission
│
├── common
│
├── configuration
│
└── exception
Bước 3

Thêm các field của Langora

Hiện tại User có thể đang là:

username
password
firstName
lastName

Cần mở rộng:

User
UUID id;

String email;

String password;

String displayName;

String avatar;

String learningLanguage;

String subscriptionPlan;

String status;

Instant createdAt;

Instant updatedAt;

Vì Langora cần:

English

Japanese

Chinese

và các gói học tập sau này.

Bước 4

Tách User Profile khỏi Auth

Auth chỉ làm:

Login

Refresh

Logout

Validate

User chỉ làm:

Profile

Avatar

Subscription

Tách rõ:

auth/*
user/*
Phase 2 — Xây Gateway

Repo mới:

langora-gateway-service

Tech:

Spring Cloud Gateway

Structure

gateway

├── config
├── filters
├── routes
├── security
└── exception

Gateway xử lý:

JWT Verify

Rate Limit

CORS

Routing

Logging
Flow
Client

↓

Gateway

↓

Identity

Ví dụ

POST /api/auth/login

↓

identity-service
GET /api/users/me

↓

identity-service
Phase 3 — Chuẩn hóa JWT

Hiện tại JWT của bạn nhiều khả năng:

{
  "sub":"admin"
}

Nên đổi thành:

{
  "sub":"uuid",

  "email":"abc@gmail.com",

  "roles":[
      "USER"
  ],

  "permissions":[
      "VOCAB_READ"
  ]
}

Sau này Learning Service không cần gọi lại User DB.

Phase 4 — Learning Service

Repo mới:

langora-learning-service

Tech

Spring Boot
PostgreSQL
Redis

Modules

language

lesson

vocabulary

flashcard

srs

writing_template

Database riêng

learning_db

Không được dùng chung DB với Identity.

Phase 5 — Kết nối Identity và Learning

Gateway decode JWT.

Inject header:

X-User-Id

X-User-Email

X-User-Role

Learning nhận:

String userId =
request.getHeader("X-User-Id");

Không query sang Identity.

Đây là nguyên tắc quan trọng của Microservice.

Phase 6 — AI Service

Theo tài liệu dự án, AI là service độc lập giao tiếp với OpenAI/Gemini.

Tôi khuyên:

langora-ai-service

sử dụng:

NestJS
TypeScript
MongoDB

Modules

writing

grammar

quiz

vocabulary-generation

prompt
Phase 7 — RabbitMQ

Sau khi Learning và AI ổn định.

Thêm:

RabbitMQ

Event

USER_REGISTERED

LESSON_COMPLETED

WRITING_SUBMITTED

FLASHCARD_MASTERED

Ví dụ

Learning:

Lesson Completed

↓

RabbitMQ

↓

Analytics

↓

Notification

Roadmap thực tế cho bạn (8 tuần)
Tuần 1

Refactor Identity Service

auth
user
role
permission
Tuần 2

Thêm:

Refresh Token

User Profile

Subscription
Tuần 3

Xây Gateway

Routing
JWT
CORS
Tuần 4

Khởi tạo Learning Service

Language
Lesson
Vocabulary
Tuần 5

Flashcard + SRS

Tuần 6

AI Service

Writing Correction
Grammar Check
Tuần 7

Redis Cache

Tuần 8

RabbitMQ + Notification