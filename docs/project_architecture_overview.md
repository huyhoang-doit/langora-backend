# Tổng quan Kiến trúc dự án Identity Service

Dựa trên cấu trúc mã nguồn và các tệp tin cấu hình, đây là bản phân tích toàn diện về kiến trúc, các tính năng đã được thực hiện, các model (thực thể) và cấu hình của dự án `identity-service-spring-boot`.

## 1. Kiến trúc dự án
Dự án được xây dựng theo kiến trúc **Layered Architecture (Kiến trúc phân lớp)** chuẩn của Spring Boot, bao gồm các thành phần chính:
- **Controller Layer (`controller`)**: Nơi tiếp nhận các HTTP Request từ client. (Ví dụ: `UserController`, `AuthenticationController`).
- **Service Layer (`service`)**: Chứa logic nghiệp vụ lõi của ứng dụng. (Ví dụ: `UserService`, `AuthenticationService`).
- **Repository Layer (`repository`)**: Chịu trách nhiệm tương tác với cơ sở dữ liệu thông qua Spring Data JPA.
- **Data Access & Entity Layer (`entity`, `dto`)**: Bao gồm các lớp ánh xạ bảng trong cơ sở dữ liệu (`Entity`) và các đối tượng truyền tải dữ liệu (`DTO`).
- **Mapper (`mapper`)**: Sử dụng thư viện `MapStruct` để chuyển đổi qua lại giữa `Entity` và `DTO` một cách tự động và gọn gàng.
- **Security & Configuration Layer (`configuration`)**: Xử lý xác thực (Authentication), phân quyền (Authorization) và cấu hình ứng dụng.
- **Exception Handling (`exception`)**: Quản lý lỗi tập trung để trả về định dạng response chuẩn cho phía client.

---

## 2. Các Model (Entity) hiện có
Dự án tập trung vào tính năng định danh và phân quyền, hiện tại đang bao gồm 4 mô hình chính:

1. **`User` (Người dùng)**:
   - Lưu trữ thông tin tài khoản người dùng như tên đăng nhập, mật khẩu (đã băm), thông tin cá nhân.
   - Có mối quan hệ với `Role`.
2. **`Role` (Vai trò)**:
   - Đại diện cho các nhóm quyền (Ví dụ: `ADMIN`, `USER`).
   - Có mối quan hệ n-n (Many-to-Many) với `Permission`.
3. **`Permission` (Quyền hạn)**:
   - Các quyền hạn cụ thể nhỏ nhất (Ví dụ: `CREATE_DATA`, `UPDATE_DATA`, `APPROVE_POST`).
4. **`InvalidatedToken` (Token đã hủy)**:
   - Lưu trữ các ID của JWT token đã bị người dùng logout hoặc bị vô hiệu hóa, nhằm chặn việc sử dụng lại token cũ.

---

## 3. Các Service và Chức năng đã thực hiện
Dự án đã xây dựng xong một bộ luồng Xác thực và Quản lý Người dùng rất đầy đủ:

* **`AuthenticationService`**:
  - Đăng nhập (Tạo JWT Token).
  - Xác thực Token (Introspect).
  - Làm mới Token (Refresh Token).
  - Đăng xuất (Vô hiệu hóa Token bằng cách đưa vào bảng `InvalidatedToken`).
* **`UserService`**:
  - Đăng ký/Tạo mới tài khoản người dùng (Create User).
  - Lấy thông tin người dùng.
  - Cập nhật thông tin và mật khẩu.
  - Cấp/Thu hồi Role cho người dùng.
* **`RoleService` & `PermissionService`**:
  - Quản lý (Tạo, xem, xóa) các vai trò (Roles) và quyền (Permissions) động trong hệ thống.

---

## 4. Các cấu hình nổi bật (Configuration)

Hệ thống đã thiết lập sẵn các cấu hình bảo mật và vận hành theo tiêu chuẩn rất hiện đại:

* **Spring Security & OAuth2 (`SecurityConfig`)**:
  - Vô hiệu hóa CSRF (phù hợp cho REST API).
  - Cấu hình phân quyền `MethodSecurity` giúp dễ dàng chặn quyền bằng các Annotation như `@PreAuthorize("hasRole('ADMIN')")` ở cấp độ method.
  - Cấu hình OAuth2 Resource Server để tự động parse JWT từ Header `Authorization: Bearer <token>`.
  - Thiết lập các **Public Endpoints** không cần xác thực: `/users` (đăng ký), `/auth/token` (đăng nhập), `/auth/introspect`, `/auth/logout`, `/auth/refresh`.

* **Custom JWT Decoding (`CustomJwtDecoder`)**:
  - Ghi đè logic giải mã JWT mặc định để thực hiện kiểm tra thêm xem Token đó có nằm trong bảng `InvalidatedToken` (đã đăng xuất) hay chưa. Nếu đã bị vô hiệu hóa sẽ từ chối truy cập.

* **Khởi tạo dữ liệu tự động (`ApplicationInitConfig`)**:
  - Mỗi khi chạy ứng dụng, hệ thống sẽ kiểm tra xem tài khoản `admin` đã tồn tại chưa. Nếu chưa, nó sẽ **tự động tạo một tài khoản admin mặc định** để nhà phát triển/quản trị viên có thể đăng nhập ngay mà không cần can thiệp vào Database.

* **Database (PostgreSQL & H2)**:
  - Cấu hình chính sử dụng **PostgreSQL** (`application.yaml`).
  - Môi trường Unit Test / Integration Test dùng **H2 In-Memory Database** (với `MODE=PostgreSQL`) hoặc **Testcontainers** (Docker PostgreSQL) cho các test chân thực nhất (`test.properties`, `UserControllerIntegrationTest`).

* **Exception Handling**:
  - Cấu hình `JwtAuthenticationEntryPoint` để bắt và trả về thông báo lỗi đẹp mắt khi gặp mã `401 Unauthorized`.
