# Kế Hoạch Triển Khai Security & Role-Based Access Control (RBAC)

*Tài liệu này định hướng việc cập nhật cơ chế xác thực và phân quyền (RBAC) cho Langora Backend trong tương lai.*

## Tình trạng hiện tại

1. **SecurityConfig**: Hiện tại đã bật `@EnableMethodSecurity` và quy định các endpoint private bắt buộc phải có token (ngoại trừ các endpoint public như login/register).
2. **Token Generation (`AuthService.java`)**: Quá trình tạo JWT hiện tại chỉ đưa `email` và `userId` (subject) vào token claims, hoàn toàn **chưa nhúng** thông tin `roles` hoặc `permissions` của user vào token.
3. **Phân quyền Controller**: Chưa có bất kỳ endpoint nào sử dụng annotation `@PreAuthorize` (ví dụ: `@PreAuthorize("hasRole('ADMIN')")`). Phân quyền Admin hiện tại được thực hiện thủ công bằng cách kiểm tra trực tiếp trong logic code (ví dụ: kiểm tra role trong method `login` của `AuthService`).

## Hướng giải quyết trong tương lai

Để có thể quản lý quyền truy cập một cách chặt chẽ cho cả **Admin Portal** và **Client Web/App**, hệ thống cần triển khai các bước sau:

### 1. Nhúng Roles và Permissions vào JWT Token
- Cập nhật hàm `generateToken` trong `AuthService` (hoặc các logic cấp phát JWT tương lai).
- Query danh sách `Role` và `Permission` tương ứng của User.
- Đưa danh sách này vào chuỗi JWT claim với key là `scope` (mặc định Spring Security `JwtGrantedAuthoritiesConverter` sẽ tự động parse field này thành các GrantedAuthority tương ứng).

```java
// Ví dụ claim
JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
        // ... các config khác
        .claim("scope", "ROLE_ADMIN PERMISSION_READ PERMISSION_WRITE")
        .build();
```

### 2. Áp dụng Method Security trên Controllers
Sử dụng annotation `@PreAuthorize` trên toàn bộ hệ thống API:

- **Admin APIs** (`com.langora.*.controller.admin.*`):
  Bắt buộc các tài khoản gọi API này phải có Role Admin.
  ```java
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/...")
  public ApiResponse<...> getAdminData() { ... }
  ```

- **Client APIs** (`com.langora.*.controller.client.*`):
  Các endpoint dành cho người dùng thông thường, có thể yêu cầu Role User.
  ```java
  @PreAuthorize("hasRole('USER')")
  @GetMapping("/...")
  public ApiResponse<...> getClientData() { ... }
  ```

### 3. Quy trình Login Tách Biệt
- Hệ thống cần xác định rõ ràng: `Admin Login` sẽ trả về Token mang Role Admin, và `Client Login` sẽ trả về Token mang Role User.
- Khi người dùng bình thường mang Token `ROLE_USER` cố gắng gọi vào các endpoint của Admin, Spring Security sẽ tự động chặn và trả về lỗi **403 Forbidden**.
