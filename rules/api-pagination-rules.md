# Quy tắc chuẩn hóa Phân trang (Pagination) API

Trong dự án Langora, tất cả các API trả về danh sách dữ liệu có áp dụng phân trang (Pagination) ĐỀU PHẢI tuân thủ quy tắc đóng gói metadata phân trang vào thuộc tính `meta` của `ApiResponse<T>`.

## 1. Cấu trúc `PageMeta`

Để đồng bộ cấu trúc trả về cho Frontend, chúng ta sử dụng class `PageMeta` nằm tại `com.langora.shared.dto.response.PageMeta`:

```java
public class PageMeta {
    int page;          // Trang hiện tại (1-indexed)
    int limit;         // Số lượng bản ghi trên mỗi trang
    long totalElements; // Tổng số bản ghi trên toàn DB
    int totalPages;     // Tổng số trang
}
```

## 2. Quy tắc trả về API (Controller)

Khi xây dựng một API có tính năng danh sách, Controller phải:
1. Nhận vào tham số `@RequestParam` cho `page` và `limit` (mặc định page = 1, limit = 10).
2. Gọi Service để lấy kết quả (thường là một object `Page<T>` của Spring Data).
3. Chuyển đổi (Map) Entity sang DTO.
4. Xây dựng object `PageMeta`.
5. Đưa dữ liệu (List DTO) vào trường `data` và đưa `PageMeta` vào trường `meta` của `ApiResponse`.

### Ví dụ tiêu chuẩn

Dưới đây là ví dụ chuẩn mực khi viết API GET List Users:

```java
@GetMapping
public ApiResponse<List<UserResponse>> getUsers(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int limit) {
    
    // 1. Gọi Service để lấy Page<Entity>
    Page<User> userPage = userService.getUsers(page, limit);
    
    // 2. Chuyển đổi Entity List sang DTO List
    List<UserResponse> data = userPage.getContent().stream()
            .map(userMapper::toUserResponse)
            .toList();

    // 3. Khởi tạo PageMeta từ object Page của Spring
    PageMeta pageMeta = PageMeta.builder()
            .page(page)
            .limit(limit)
            .totalElements(userPage.getTotalElements())
            .totalPages(userPage.())
            .build();

    // 4. Bọc vào ApiResponse và trả về
    return ApiResponse.<List<UserResponse>>builder()
            .data(data)
            .meta(pageMeta) // Nhúng metadata phân trang vào đây
            .message("Fetched users successfully")
            .build();
}
```

## 3. Quy tắc phía Service

Ở lớp Service, khi giao tiếp với Repository, chú ý trừ đi 1 cho `page` vì Spring Data JPA dùng **0-indexed** (trang đầu tiên là trang số 0), trong khi client và API của chúng ta dùng **1-indexed**.

```java
public Page<User> getUsers(int page, int size) {
    // Luôn luôn lấy (page - 1) để tương thích với Spring Data
    Pageable pageable = PageRequest.of(page - 1, size);
    
    return userRepository.findAll(pageable);
}
```

## 4. Kết quả JSON trả về cho Frontend

Frontend sẽ nhận được format JSON cực kỳ gọn gàng và dễ parse:

```json
{
  "success": true,
  "message": "Fetched users successfully",
  "data": [
    {
      "id": "abc-123",
      "email": "user@gmail.com",
      "status": "ACTIVE"
    }
  ],
  "meta": {
    "page": 1,
    "limit": 10,
    "totalElements": 50,
    "totalPages": 5
  },
  "timestamp": "2026-06-14T10:00:00Z"
}
```
