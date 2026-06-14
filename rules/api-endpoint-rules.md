# Nguyên tắc thiết kế và quản lý API Endpoints

Để dễ dàng quản lý, bảo trì và tra cứu toàn bộ các đường dẫn API của hệ thống (tránh việc hardcode rải rác ở khắp các Controller), Langora Backend áp dụng quy tắc tập trung hoá API Endpoints như sau:

## 1. File cấu hình tập trung (`ApiEndpoint.java`)

Mọi đường dẫn (URL) của hệ thống **BẮT BUỘC** phải được khai báo dưới dạng hằng số (`public static final String`) trong class `com.langora.shared.constant.ApiEndpoint`.

### Cấu trúc class `ApiEndpoint`
Class này được thiết kế theo dạng cây (nested classes) để phản ánh cấu trúc phân cấp của URL.

**Ví dụ:**
```java
package com.langora.shared.constant;

public final class ApiEndpoint {
    
    private ApiEndpoint() {} // Ngăn không cho khởi tạo class này
    
    public static final String API_V1 = "/api/v1";
    
    // Cấu trúc cho nhánh Admin (/api/v1/admin/...)
    public static final class Admin {
        public static final String BASE = API_V1 + "/admin";
        
        // Đường dẫn cho module Auth
        public static final class Auth {
            public static final String BASE = Admin.BASE + "/auth";
            public static final String LOGIN = "/login";
            public static final String ME = "/me";
        }
        
        // Đường dẫn cho module Roles
        public static final class Roles {
            public static final String BASE = Admin.BASE + "/roles";
        }
    }
    
    // Cấu trúc cho nhánh Public/Client app (nếu có sau này)
    public static final class Client {
        public static final String BASE = API_V1 + "/client";
        // ...
    }
}
```

## 2. Cách sử dụng trong Controller

Khi tạo các `RestController`, tuyệt đối **không** được gõ chuỗi String URL trực tiếp vào `@RequestMapping`, `@PostMapping`, `@GetMapping`... Thay vào đó, hãy gọi hằng số từ class `ApiEndpoint`.

**Cách dùng ĐÚNG (✅):**
```java
@RestController
@RequestMapping(ApiEndpoint.Admin.Auth.BASE)
public class AdminAuthController {

    @PostMapping(ApiEndpoint.Admin.Auth.LOGIN)
    public ApiResponse<AdminAuthResponse> login(...) { ... }
}
```

**Cách dùng SAI (❌):**
```java
@RestController
@RequestMapping("/api/v1/admin/auth") // Lỗi: Hardcode chuỗi
public class AdminAuthController {

    @PostMapping("/login") // Lỗi: Hardcode chuỗi
    public ApiResponse<AdminAuthResponse> login(...) { ... }
}
```

## 3. Quản lý phân quyền (SecurityConfig)

Tương tự như Controller, khi khai báo các public endpoints (cho phép truy cập không cần token) trong `SecurityConfig`, phải ghép chuỗi từ `ApiEndpoint` để đảm bảo tính nhất quán nếu URL có thay đổi trong tương lai.

**Ví dụ trong `SecurityConfig`:**
```java
private final String[] PUBLIC_ENPOINTS = {
    ApiEndpoint.Admin.Auth.BASE + ApiEndpoint.Admin.Auth.LOGIN
};
```

## Tổng kết

Bằng việc tuân thủ quy tắc này:
1. Bạn có thể xem toàn bộ hệ thống API tại một nơi duy nhất (`ApiEndpoint.java`).
2. Nếu sau này cần đổi `/api/v1` thành `/api/v2`, chỉ cần sửa ở **MỘT CHỖ DUY NHẤT** thay vì phải tìm/thay thế trên hàng trăm file Controller.
