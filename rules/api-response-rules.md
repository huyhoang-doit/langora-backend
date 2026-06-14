# Nguyên tắc thiết kế và chuẩn hoá API Response

Dựa trên cấu trúc chuẩn của hệ thống, mọi API được thiết kế phải tuân thủ nghiêm ngặt các quy tắc sau về kiểu trả về (Response):

## 1. Cấu trúc Response Tổng Quát (Wrapper)

Mọi endpoint API **KHÔNG BAO GIỜ** được trả về trực tiếp kiểu dữ liệu nguyên thuỷ (String, int...) hay Object/DTO trần. Tất cả kết quả trả về bắt buộc phải được bọc trong class `com.langora.shared.dto.response.ApiResponse<T>`.

Cấu trúc chuẩn của `ApiResponse<T>`:
```json
{
  "success": true, // false nếu xảy ra lỗi
  "message": "Thông điệp mô tả (vd: Lấy dữ liệu thành công)",
  "data": { ... }, // Dữ liệu payload thực tế trả về cho client (có thể là mảng hoặc object)
  "meta": { ... }, // Thông tin phân trang (page, limit, total, totalPages) - null nếu không cần thiết
  "errors": null,  // Danh sách lỗi chi tiết (nếu có)
  "timestamp": "2026-04-14T10:00:00Z"
}
```

## 2. Quy tắc cho các kịch bản cụ thể

### 2.1. Thành công (Success = true)
- Sử dụng hàm builder: `ApiResponse.<DTO>builder().data(result).message("Thành công").build();`
- Trường hợp GET danh sách: `data` chứa một mảng DTO, `meta` bắt buộc chứa thông tin phân trang (nếu có).
- Trường hợp POST/PUT: `data` chứa Object vừa được tạo/cập nhật.
- Trường hợp DELETE hoặc không cần trả data: `data` có thể là `null`.

### 2.2. Lỗi nghiệp vụ (Business Error)
- **KHÔNG** tự tay trả về `ResponseEntity` chứa mã lỗi trong controller.
- **BẮT BUỘC** throw exception: `throw new AppException(ErrorCode.YOUR_ERROR_CODE);`
- `GlobalExeptionHandler` sẽ tự động catch `AppException` và format lại thành:
```json
{
  "success": false,
  "message": "Nội dung lỗi nghiệp vụ (vd: User đã tồn tại)",
  "data": null,
  "meta": null,
  "errors": null,
  "timestamp": "2026-04-14T10:00:00Z"
}
```

### 2.3. Lỗi Validation (Data format / Required fields)
- Luôn sử dụng annotation `@Valid` ở tham số `@RequestBody` của Controller.
- Nếu dữ liệu không hợp lệ, `MethodArgumentNotValidException` sẽ được ném ra.
- `GlobalExeptionHandler` tự động map các lỗi constraint thành mảng `errors` chuẩn xác như sau:
```json
{
  "success": false,
  "message": "Validation failed",
  "data": null,
  "meta": null,
  "errors": [
    {
      "field": "email",
      "message": "Email is required"
    }
  ],
  "timestamp": "..."
}
```

## 3. Chú ý khi phát triển
- Luôn định nghĩa các `ErrorCode` mới vào enum `ErrorCode` nếu phát sinh lỗi nghiệp vụ mới.
- Không dùng biến `code` trong response để thông báo trạng thái, thay vào đó phân biệt qua HTTP Status và boolean `success`. Lỗi cụ thể sẽ được ghi trong `message` và `errors`.
