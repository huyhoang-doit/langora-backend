# Cẩm nang Spring Boot & Kiến trúc Langora Backend

Tài liệu này được viết dành riêng cho bạn - một lập trình viên từng làm việc với Spring Boot và giờ muốn ôn lại kiến thức, đồng thời nắm bắt được tư tưởng thiết kế (Architecture) của dự án **Langora Backend** hiện tại.

---

## Phần 1: Ôn tập cốt lõi Spring Boot

Spring Boot là một framework mạnh mẽ dựa trên Spring, giúp tự động hoá cấu hình (Auto-Configuration) và cho phép bạn chạy ứng dụng ngay lập tức mà không cần setup máy chủ phức tạp.

### 1. Inversion of Control (IoC) và Dependency Injection (DI)
- **IoC Container:** Spring Boot quản lý vòng đời của các đối tượng (gọi là **Beans**). Thay vì bạn tự dùng từ khoá `new` để tạo đối tượng, Spring sẽ tạo và giữ chúng trong một vùng nhớ gọi là IoC Container.
- **Dependency Injection (DI):** Khi một class cần sử dụng class khác, Spring sẽ tự động "tiêm" (inject) nó vào. 
  - *Ví dụ:* `UserController` cần dùng `UserService`. Spring sẽ tự động gán `UserService` vào `UserController` thông qua Constructor hoặc Annotation `@Autowired`.

### 2. Các Annotations phổ biến nhất cần nhớ

**Đánh dấu Bean cho Spring quản lý (Stereotype Annotations):**
- `@Component`: Annotation gốc, đánh dấu một class bình thường là một Bean.
- `@RestController`: Dùng cho tầng Controller, nó là sự kết hợp của `@Controller` và `@ResponseBody` (để trả về JSON thay vì giao diện HTML).
- `@Service`: Dùng cho tầng xử lý nghiệp vụ (Business Logic).
- `@Repository`: Dùng cho tầng giao tiếp với Database.

**Quản lý luồng Request / Response:**
- `@RequestMapping("/api/...")`: Định nghĩa đường dẫn gốc cho toàn bộ Controller.
- `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`: Ánh xạ các HTTP Methods tới các hàm cụ thể.
- `@RequestBody`: Chuyển đổi JSON từ body của request thành Java Object.
- `@PathVariable`: Lấy giá trị trực tiếp từ đường dẫn (VD: `/users/{id}`).
- `@RequestParam`: Lấy giá trị từ query string (VD: `/users?page=1`).

**Cấu hình & Các Annotation khác:**
- `@Configuration`: Đánh dấu class chứa các thiết lập cấu hình của Spring.
- `@Bean`: Thường dùng bên trong class `@Configuration` để cấu hình thủ công một Bean và ném vào IoC Container.
- `@Entity`: (JPA) Đánh dấu một class ánh xạ với 1 bảng trong cơ sở dữ liệu.

### 3. Công cụ bổ trợ cực kỳ quan trọng trong dự án này

Dự án này sử dụng 2 công cụ để giảm thiểu code boilerplate (code lặp đi lặp lại):
1. **Lombok:** 
   - Thay vì tự viết Getter/Setter/Constructor, bạn chỉ cần dùng `@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`, `@Builder`.
   - Đặc biệt, dự án dùng `@FieldDefaults(level = AccessLevel.PRIVATE)` ở đầu class, giúp bạn **không cần viết chữ `private`** cho từng biến nữa, code sẽ cực kỳ gọn.
2. **MapStruct:** 
   - Dùng để map (chuyển đổi) dữ liệu giữa các Entity và DTO. 
   - Bạn chỉ cần định nghĩa Interface (VD: `UserMapper.java`), MapStruct sẽ tự động sinh code implement việc copy dữ liệu lúc build.

---

## Phần 2: Kiến trúc của dự án Langora Backend

Dự án này không viết theo kiểu MVC 3 lớp truyền thống (Controller -> Service -> Repository để lộn xộn trong 1 folder). Nó được thiết kế theo tư tưởng **Modular Monolith** kết hợp **Clean Architecture**.

### 1. Tại sao lại là Modular Monolith?
Thay vì chia cắt thành hàng chục dự án Microservices gây khó khăn cho việc quản lý, debug và deploy khi dự án mới bắt đầu, chúng ta giữ mọi thứ trong một dự án duy nhất (Monolith). Tuy nhiên, code được chia nhỏ thành các **Module độc lập** (Identity, Learning, Content...). Các module này không được quyền can thiệp sâu vào code của nhau. 
Cách làm này giúp bạn có tốc độ code của Monolith, nhưng vẫn dễ dàng tách ra thành Microservices trong tương lai nếu ứng dụng lớn lên.

### 2. Cấu trúc Clean Architecture trong từng Module

Hãy lấy thư mục `com.langora.identity` làm ví dụ. Mỗi module sẽ tuân thủ cấu trúc sau:

```
identity/
├── controller/         # (Presentation Layer)
├── application/        # (Application / Use Case Layer)
│    ├── service/       
│    ├── exception/     
│    └── validator/     
├── domain/             # (Domain Layer - TRÁI TIM CỦA ỨNG DỤNG)
│    ├── entity/        
│    ├── repository/    
│    └── enums/         
├── infrastructure/     # (Infrastructure Layer)
│    ├── mapper/        
│    └── configuration/ 
└── dto/                # Data Transfer Object (request/response models)
```

**Nguyên tắc phụ thuộc (Dependency Rule):**
Lớp bên ngoài chỉ được gọi lớp bên trong. Lớp bên trong KHÔNG ĐƯỢC biết về sự tồn tại của lớp bên ngoài.
- `controller` -> `service` -> `domain`
- Trái tim của ứng dụng là `domain` (Entity, Repository Interface). Nó không quan tâm bạn dùng Database gì hay Framework gì.

### 3. Phân chia trách nhiệm (Quy trình của một Request)

Hãy tưởng tượng một tính năng **Đăng ký User (Register)**, luồng code sẽ chạy như sau:

1. **Controller (`UserController`):**
   - Nhận HTTP POST Request từ Frontend.
   - `@RequestBody` lấy ra `UserCreationRequest` (thuộc thư mục `dto`).
   - Gọi đến `UserService.createUser()`.
   - *Lưu ý:* Controller tuyệt đối KHÔNG chứa logic nghiệp vụ, nó chỉ nhận Data và gọi Service.

2. **Application (`UserService`):**
   - Chứa logic nghiệp vụ.
   - Nó kiểm tra xem username đã tồn tại chưa.
   - Nếu qua xác thực, nó dùng `UserMapper` (trong `infrastructure`) để biến `UserCreationRequest` thành `User` (Entity).
   - Gọi `UserRepository.save(user)` để lưu xuống Database.

3. **Domain (`User`, `UserRepository`):**
   - `User` là Entity mapping với bảng `users` trong Postgres.
   - `UserRepository` là interface extend `JpaRepository` của Spring Data JPA. Nó xử lý việc tạo ra các câu lệnh SQL tự động dưới background.

4. **Trả kết quả về:**
   - Sau khi lưu xong, `UserService` biến Entity `User` vừa tạo thành `UserResponse` (thông qua `UserMapper`) và trả ngược lại cho `Controller`.
   - `Controller` đóng gói nó thành JSON và gửi về Frontend.

---

## Phần 3: Thư mục Shared (Dùng chung)

Bạn sẽ thấy có một thư mục là `com.langora.shared`. Đây là nơi chứa những thứ **không thuộc về một nghiệp vụ cụ thể nào** mà phục vụ cho toàn hệ thống:
- **GlobalExceptionHandler:** Dùng để bắt mọi exception văng ra trong quá trình chạy (VD: Lỗi validate, Lỗi không tìm thấy dữ liệu) và format nó thành 1 cục JSON báo lỗi chuẩn hoá trả về cho Frontend.
- **Configurations:** Cấu hình Security (JWT), Cấu hình CORS, Cấu hình Swagger API.

---
## Lời khuyên khi bắt đầu code lại
1. Hãy bắt đầu dọc từ `Controller` -> `Service` -> `Repository` của tính năng Auth (Đăng nhập/Đăng ký) để quen lại luồng code.
2. Mở file `UserMapper.java` và xem cách MapStruct làm việc.
3. Khi tạo tính năng mới, hãy tập thói quen định nghĩa `RequestDTO` và `ResponseDTO` trước, sau đó mới viết logic ở `Service`. Không bao giờ trả thẳng Entity (`User`, `Role`) về phía Frontend để bảo mật thông tin và linh hoạt trong thiết kế.
