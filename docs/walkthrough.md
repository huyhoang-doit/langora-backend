# Hoàn tất chuyển đổi sang PostgreSQL

Quá trình cấu hình chuyển đổi từ MySQL sang PostgreSQL đã được thực hiện thành công dựa theo kế hoạch. Dưới đây là tóm tắt các thay đổi và hướng dẫn sử dụng.

## Các thay đổi đã được thực hiện

- **Dependencies (`pom.xml`)**: Đã cập nhật driver `org.postgresql:postgresql` và Testcontainers sang `org.testcontainers:postgresql`.
- **Cấu hình kết nối (`application.yaml`)**: Đổi URL kết nối sang `jdbc:postgresql://localhost:5432/identity_service_db`, driver `org.postgresql.Driver` và tài khoản mặc định `postgres/123456`.
- **Khởi tạo dữ liệu (`ApplicationInitConfig.java`)**: Điều kiện khởi tạo admin đã đổi sang bắt driver của PostgreSQL.
- **Môi trường Test (`test.properties` và `UserControllerIntegrationTest.java`)**: Chuyển H2 sang chế độ tương thích `MODE=PostgreSQL` và đổi container chạy Integration Test sang `PostgreSQLContainer`.

> [!NOTE]
> Khi chạy `./mvnw test`, bài kiểm thử `UserControllerIntegrationTest` có thể báo lỗi nếu máy bạn chưa bật Docker (do không thể khởi chạy Testcontainers).

---

## Hướng dẫn sử dụng

### 1. Khởi động cơ sở dữ liệu PostgreSQL
Bạn cần có một instance PostgreSQL đang chạy ở port `5432` với database là `identity_service_db`, tài khoản là `postgres`, mật khẩu là `123456`. 

Nếu bạn dùng Docker, có thể nhanh chóng khởi động bằng lệnh sau:
```bash
docker run --name postgres-identity-db \
  -e POSTGRES_DB=identity_service_db \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=123456 \
  -p 5432:5432 -d postgres:16-alpine
```

### 2. Chạy ứng dụng
Sau khi database đã sẵn sàng, bạn có thể chạy ứng dụng Spring Boot bình thường bằng IDE hoặc bằng dòng lệnh:
```bash
./mvnw spring-boot:run
```
> [!TIP]
> Bạn có thể quan sát trong console log lúc khởi chạy, hệ thống sẽ in ra dòng: `Init application.....` và tự động tạo tài khoản `admin` (nếu chưa có).

### 3. Chạy Integration Tests
Để chạy lại các test cases thành công:
1. Đảm bảo ứng dụng Docker Desktop hoặc Docker Engine đang được mở và hoạt động trên máy tính của bạn.
2. Chạy lệnh:
```bash
./mvnw test
```
Lúc này Testcontainers sẽ tự động pull image `postgres:16-alpine` và chạy test bình thường.
