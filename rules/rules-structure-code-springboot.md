# Quy tắc cấu trúc Code Spring Boot - Langora Backend

Tài liệu này là "kim chỉ nam" bắt buộc tuân thủ khi viết code cho dự án Langora Backend. Mọi đoạn code được thêm vào dự án phải tuân thủ nghiêm ngặt các quy tắc dưới đây.

## 1. Kiến trúc tổng quan (Modular Monolith & Clean Architecture)
Dự án áp dụng kiến trúc Modular Monolith. Mỗi domain trong hệ thống là một module độc lập và được cô lập trong package riêng `com.langora.<module_name>`.

### Cấu trúc package chuẩn của một Module
```text
com.langora.<module_name>/
├── controller/         # API Endpoints (@RestController). KHÔNG chứa logic nghiệp vụ.
├── application/        # Chứa logic nghiệp vụ.
│    ├── service/       # Chứa các interface và class Service (@Service).
│    ├── exception/     # Các custom exception của module.
│    └── validator/     # Custom validators.
├── domain/             # Lớp cốt lõi, không phụ thuộc vào framework.
│    ├── entity/        # JPA Entities. Phải map chính xác 1-1 với DBML.
│    ├── repository/    # JPA Repositories interfaces.
│    └── enums/         # Các Enums sử dụng trong Entity.
├── infrastructure/     # Tương tác với bên ngoài, thư viện, framework.
│    ├── mapper/        # MapStruct mappers (chuyển đổi DTO <-> Entity).
│    └── configuration/ # Cấu hình Spring Boot riêng cho module.
└── dto/                # Data Transfer Objects.
     ├── request/       # DTO nhận vào từ client.
     └── response/      # DTO trả về cho client.
```

## 2. Quy tắc thiết kế JPA Entity
**BẮT BUỘC:** Entity phải được xây dựng chính xác 100% dựa trên file thiết kế `*.dbml` trong thư mục `data/`. Những cột không có trong DBML phải bị xoá bỏ khỏi code. Những bảng không có trong DBML cũng không được tồn tại.

### Cấu trúc của một class Entity
Mỗi Entity phải luôn đi kèm các Annotation sau của Lombok và JPA:
```java
@Entity
@Table(name = "tên_bảng_số_nhiều") // Tên bảng phải khớp chuẩn DBML
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExampleEntity {
    // fields
}
```

### Mapping kiểu dữ liệu từ DBML sang Java
- `uuid [pk]` -> `String id;` kèm theo `@Id` và `@GeneratedValue(strategy = GenerationType.UUID)`
- `timestamptz` -> `java.time.OffsetDateTime`
- `date` -> `java.time.LocalDate`
- `varchar` / `text` -> `String`
- `boolean` -> `Boolean`
- `integer` -> `Integer`
- `bigint` -> `Long`
- `decimal` -> `java.math.BigDecimal`
- `jsonb` -> Dùng class hoặc chuỗi JSON tuỳ ngữ cảnh (thường map bằng thư viện như Hypersistence Utilities hoặc dạng `String`).

### Quy tắc quan hệ (Relationships & Foreign Keys)
1. **Quan hệ TRONG CÙNG một module (Intra-module):**
   - Được phép dùng `@ManyToOne`, `@OneToMany` (Object reference).
2. **Quan hệ KHÁC module (Cross-module / Inter-module):**
   - **Tối kỵ:** KHÔNG BAO GIỜ dùng `@ManyToOne` trỏ thẳng sang Entity của module khác.
   - **Giải pháp:** Chỉ lưu ID dưới dạng `String` (hoặc `UUID`). Ví dụ: Class `UserProfile` (thuộc module User) muốn trỏ đến `User` (thuộc module Identity) thì chỉ lưu biến `String userId`, tuyệt đối không lưu `User user`. Điều này giúp đảm bảo sự độc lập hoàn toàn giữa các module.

## 3. Quy tắc DTO & MapStruct
- **Không bao giờ trả Entity trực tiếp qua Controller.** Mọi data đi ra/vào API đều phải thông qua các class trong thư mục `dto/request` và `dto/response`.
- Sử dụng **MapStruct** (tạo file Interface trong thư mục `infrastructure/mapper/`) để tự động hoá việc copy dữ liệu giữa Entity và DTO.

## 4. Coding Standard
- Dùng `@FieldDefaults(level = AccessLevel.PRIVATE)` thay vì viết từ khoá `private` lặp đi lặp lại.
- Tên biến: camelCase.
- Tên bảng: snake_case (số nhiều).
- Không tự ý thêm thư viện ngoài vào `pom.xml` trừ khi thực sự cần thiết và được phê duyệt.
- Logic kiểm tra (if/else nghiệp vụ) phải nằm ở lớp Service, không nằm ở Controller.
