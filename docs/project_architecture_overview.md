# Tổng quan Kiến trúc dự án Langora Backend

Tài liệu này cung cấp cái nhìn toàn diện về kiến trúc, cấu trúc module, và các nguyên tắc thiết kế được áp dụng trong dự án **Langora Backend** tính tới thời điểm hiện tại.

## 1. Kiến trúc Hệ thống: Modular Monolith
Dự án đã được nâng cấp từ kiến trúc nguyên khối truyền thống (Layered Architecture) sang kiến trúc **Modular Monolith** kết hợp với tư tưởng **Clean Architecture**.

**Lý do chọn Modular Monolith:**
- Cho phép chia tách các miền nghiệp vụ (Domain) thành các **Module độc lập**.
- Giữ được sự đơn giản của một ứng dụng Monolith (dễ deploy, quản lý transaction nguyên khối) trong giai đoạn đầu.
- Sẵn sàng và cực kỳ dễ dàng để tách thành các Microservices độc lập trong tương lai khi dự án scale up (mở rộng).

## 2. Các Module Nghiệp vụ (Domains)
Hệ thống hiện tại được chia thành 6 module chính, mỗi module đảm nhận một miền nghiệp vụ (Bounded Context) riêng biệt. Tất cả các Entities đều được thiết kế đối chiếu chính xác 1-1 với DBML.

1. **Identity Module (`com.langora.identity`)**
   - Nhiệm vụ: Xác thực (Authentication), phân quyền (Authorization), và quản lý an ninh.
   - Các Entities chính: `User` (lưu credentials như email, password_hash), `Role`, `Permission`, `UserSession`, `RefreshToken`, `OauthAccount`, `SecurityAuditLog`.

2. **User Module (`com.langora.user`)**
   - Nhiệm vụ: Quản lý hồ sơ, cài đặt cá nhân, và mục tiêu học tập của người dùng.
   - Các Entities chính: `UserProfile`, `UserPreference`, `UserLearningGoal`, `UserLanguageProgress`, `UserDevice`.

3. **Learning Module (`com.langora.learning`)**
   - Nhiệm vụ: Quản lý cấu trúc của chương trình học (Ngôn ngữ -> Lộ trình -> Unit -> Bài học).
   - Các Entities chính: `Language`, `LearningPath`, `Unit`, `Lesson`, `UserLessonProgress`.

4. **Vocabulary Module (`com.langora.vocabulary`)**
   - Nhiệm vụ: Xây dựng kho từ vựng đa dạng bao gồm ngữ nghĩa, phát âm, từ đồng nghĩa/trái nghĩa.
   - Các Entities chính: `Vocabulary`, `VocabularyTopic`, `VocabularyExample`, `VocabularySynonym`, `UserCustomVocabulary`, `VocabularyCollection`.

5. **Writing Module (`com.langora.writing`)**
   - Nhiệm vụ: Quản lý tính năng luyện viết tiếng Anh, chấm điểm và AI Tutor.
   - Các Entities chính: `WritingTopic`, `WritingExercise`, `WritingSession`, `WritingAiFeedback`, `WritingHintUsage`.

6. **Billing Module (`com.langora.billing`)**
   - Nhiệm vụ: Xử lý thanh toán, nạp/rút tín dụng (credits), phần thưởng và ví điện tử.
   - Các Entities chính: `CreditPackage`, `UserWallet`, `Payment`, `CreditLedger`, `WalletTransaction`, `CreditRewardRule`.

---

## 3. Cấu trúc nội bộ của một Module
Mỗi module đều phải tuân thủ nghiêm ngặt cấu trúc gói (package) phân lớp sau đây:

```text
com.langora.<module_name>/
├── controller/         # Lớp giao tiếp với bên ngoài qua REST API (@RestController).
├── application/        # Lớp logic nghiệp vụ (Use cases).
│    ├── service/       # Xử lý logic (@Service). Không phụ thuộc HTTP request.
│    ├── exception/     # Các custom exception riêng của module.
│    └── validator/     # Logic kiểm tra dữ liệu đầu vào.
├── domain/             # Lớp trung tâm chứa business model.
│    ├── entity/        # Các JPA Entities ánh xạ database.
│    ├── repository/    # Interfaces định nghĩa việc thao tác dữ liệu (extends JpaRepository).
│    └── enums/         # Enums được sử dụng trong Entity.
├── infrastructure/     # Lớp tương tác với framework, thư viện bên thứ 3 hoặc module khác.
│    ├── mapper/        # MapStruct Interfaces chuyển đổi Entity <-> DTO.
│    └── configuration/ # Cấu hình Spring Boot riêng cho module.
└── dto/                # Data Transfer Objects.
     ├── request/       # Dữ liệu từ Client gửi lên.
     └── response/      # Dữ liệu trả về cho Client.
```

## 4. Các Nguyên tắc Thiết kế Bắt Buộc (Strict Rules)

1. **Nguyên tắc cô lập Module (Loose Coupling):**
   - Các Entity thuộc các module khác nhau **TUYỆT ĐỐI KHÔNG** được tạo quan hệ thông qua Hibernate `@ManyToOne` hay `@OneToOne`.
   - Để trỏ đến Entity của module khác, chúng ta chỉ sử dụng biến dạng `String` (để lưu UUID). 
   - *Ví dụ:* Bảng `user_wallets` (trong Billing module) trỏ đến `users` (trong Identity module) bằng biến `String userId;` chứ không phải `User user;`.

2. **Dữ liệu xuyên suốt (DTO & MapStruct):**
   - Entity là tài sản nội bộ của Module. **Không bao giờ trả Entity trực tiếp ra ngoài HTTP Response.**
   - Mọi dữ liệu vào/ra đều phải đi qua các class `DTO` (Data Transfer Object).
   - Sử dụng thư viện `MapStruct` để ánh xạ tự động dữ liệu từ Entity sang DTO và ngược lại.

3. **Shared Module (`com.langora.shared`):**
   - Chứa những cấu hình, thư viện, hoặc logic tiện ích mà mọi module đều sử dụng.
   - Ví dụ: `GlobalExceptionHandler` (bắt lỗi tập trung), `ApiResponse` (định dạng response trả về cho mọi API), cấu hình Security JWT.

4. **Quản lý khoá chính (Primary Key):**
   - Tất cả các khoá chính (ID) đều sử dụng kiểu `UUID` để phân tán dữ liệu và bảo mật thông tin (không để lộ số lượng bản ghi bằng ID tự tăng auto-increment).
   - Trong Entity, ID được khai báo là `String` với cấu hình sinh mã:
     ```java
     @Id
     @GeneratedValue(strategy = GenerationType.UUID)
     String id;
     ```
