# Langora Backend API List (Client Web/App)

Tài liệu này mô tả danh sách các API backend dành cho Client (Web/App), được thiết kế bám sát tuyệt đối vào cấu trúc Entity của hệ thống Backend hiện tại. Tất cả API đều trả về cấu trúc chuẩn `ApiResponse<T>`.

---

## Phase 1: Identity & User (Authentication & Profile)

Giai đoạn 1 tập trung vào định danh người dùng (Identity) và thông tin cá nhân (User).

### 1. Module Identity (`com.langora.identity`)
*Xử lý authentication, tạo phiên đăng nhập và các quy trình bảo mật.*

| Entity Tương Ứng | Endpoint | Method | Mô Tả | Request DTO |
| :--- | :--- | :--- | :--- | :--- |
| **User** | `/api/v1/auth/login` | `POST` | Đăng nhập, trả về Access Token & Refresh Token (tạo `UserSession`) | `ClientLoginRequest` (email, password) |
| **User** | `/api/v1/auth/register` | `POST` | Đăng ký tài khoản mới | `ClientRegisterRequest` (email, password, fullName) |
| **EmailVerification** | `/api/v1/email-verifications` | `POST` | Xác thực email | `VerifyEmailRequest` (token) |
| **PasswordReset** | `/api/v1/password-resets/request` | `POST` | Yêu cầu gửi link đổi mật khẩu qua email | `ForgotPasswordRequest` (email) |
| **PasswordReset** | `/api/v1/password-resets/reset` | `POST` | Đặt lại mật khẩu mới | `ResetPasswordRequest` (token, newPassword) |
| **RefreshToken** | `/api/v1/auth/refresh-token` | `POST` | Cấp lại Access Token mới dựa vào Refresh Token | `RefreshTokenRequest` (refreshToken) |
| **UserSession** | `/api/v1/auth/logout` | `POST` | Đăng xuất (Vô hiệu hóa `UserSession` hiện tại) | - |
| **LoginHistory** | `/api/v1/login-histories/me` | `GET` | Lấy lịch sử đăng nhập của user | - |

### 2. Module User (`com.langora.user`)
*Quản lý thông tin hồ sơ, cài đặt, và lộ trình học tập cơ bản của user.*

| Entity Tương Ứng | Endpoint | Method | Mô Tả | Request DTO |
| :--- | :--- | :--- | :--- | :--- |
| **UserProfile** | `/api/v1/user-profiles/me` | `GET` | Lấy thông tin `UserProfile` hiện tại | - |
| **UserProfile** | `/api/v1/user-profiles/me` | `PUT` | Cập nhật `UserProfile` (tên, ngày sinh, bio, v.v.) | `UserProfileUpdateRequest` |
| **UserProfile** | `/api/v1/user-profiles/me/avatar` | `PUT` | Cập nhật Avatar cho `UserProfile` | `MultipartFile` |
| **UserPreference** | `/api/v1/user-preferences/me` | `GET` | Lấy các cài đặt/tùy chọn của user | - |
| **UserPreference** | `/api/v1/user-preferences/me` | `PUT` | Cập nhật `UserPreference` | `UserPreferenceUpdateRequest` |
| **UserDevice** | `/api/v1/user-devices` | `GET` | Lấy danh sách thiết bị (`UserDevice`) để nhận Push Notification | - |
| **UserDevice** | `/api/v1/user-devices` | `POST` | Đăng ký thiết bị mới (`UserDevice`) | `UserDeviceRegisterRequest` |
| **UserLearningProfile**| `/api/v1/user-learning-profiles/me` | `GET` | Lấy hồ sơ học tập tổng quan | - |
| **UserLearningProfile**| `/api/v1/user-learning-profiles/me` | `PUT` | Cập nhật hồ sơ học tập | `UserLearningProfileUpdateRequest` |
| **UserLearningGoal** | `/api/v1/user-learning-goals/me` | `GET` | Lấy mục tiêu học tập của user | - |
| **UserLearningGoal** | `/api/v1/user-learning-goals/me` | `PUT` | Cập nhật mục tiêu học tập | `UserLearningGoalUpdateRequest` |

---

## Phase 2: Learning & Writing (Thực Hành Viết Bài)

Giai đoạn 2 tập trung vào module học tập và trải nghiệm luyện viết câu/bài (Writing).

### 1. Module Learning (`com.langora.learning`)
*Cung cấp metadata chung cho toàn hệ thống học.*

| Entity Tương Ứng | Endpoint | Method | Mô Tả | Request/Query |
| :--- | :--- | :--- | :--- | :--- |
| **Language** | `/api/v1/languages` | `GET` | Lấy danh sách các ngôn ngữ có sẵn | - |
| **Level** | `/api/v1/levels` | `GET` | Lấy danh sách cấp độ (A1, A2, B1, v.v.) | - |

### 2. Module Writing (`com.langora.writting`)
*Luồng thao tác đầy đủ cho tính năng Viết từ chọn đề, thực hành đến nhận đánh giá.*

| Entity Tương Ứng | Endpoint | Method | Mô Tả | Request/Query |
| :--- | :--- | :--- | :--- | :--- |
| **WritingTopic** | `/api/v1/writing-topics` | `GET` | Danh sách chủ đề (Categories/Topics) | - |
| **WritingContentType** | `/api/v1/writing-content-types` | `GET` | Danh sách loại nội dung viết (Essay, Email...) | - |
| **WritingExercises** | `/api/v1/writing-exercises` | `GET` | Danh sách mẫu bài tập (`WritingExercises`) | `?levelId=...&topicId=...` |
| **WritingExercises** | `/api/v1/writing-exercises/{id}` | `GET` | Xem chi tiết bài tập (bao gồm đề bài và các câu hỏi) | - |
| **WritingSession** | `/api/v1/writing-sessions` | `POST` | Bắt đầu phiên thực hành mới (`WritingSession`) cho một bài tập | `WritingSessionCreateRequest` (exerciseId) |
| **WritingSession** | `/api/v1/writing-sessions/{id}` | `PUT` | Cập nhật/Auto-save toàn bộ phiên viết nháp | `WritingSessionUpdateRequest` (content) |
| **WritingSession** | `/api/v1/writing-sessions/{id}/submit`| `POST` | Nộp toàn bộ bài viết để chấm điểm/review | - |
| **WritingSentenceAnswer**| `/api/v1/writing-sessions/{id}/sentence-answers`| `POST`/`PUT`| Gửi câu trả lời cho từng câu (`WritingExerciseSentence`) | `WritingSentenceAnswerRequest` |
| **WritingAiFeedback** | `/api/v1/writing-sessions/{id}/ai-feedbacks` | `GET` | Nhận kết quả đánh giá AI cho phiên viết đã nộp | - |
| **WritingAchievement** | `/api/v1/writing-achievements/me` | `GET` | Lấy các thành tích luyện viết của user | - |

> **Ghi chú cho Client:**
> - Toàn bộ các API yêu cầu Auth (Phase 1) bắt buộc phải có Header `Authorization: Bearer <Token>`.
> - Tên Endpoint được thiết kế trực tiếp theo dạng số nhiều của Backend Entity (`/api/v1/{entities}`).
