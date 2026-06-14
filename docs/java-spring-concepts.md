# Giải thích các khái niệm Java & Spring Boot cơ bản

Tài liệu này giải thích chi tiết ý nghĩa của các Annotations (Lombok, Spring) cũng như cú pháp Java Stream API thường được sử dụng trong các class Service của dự án Langora.

---

## 1. Ý nghĩa của các Annotations (`@`) trong Service

Trong một class Service (ví dụ `RoleService.java`), bạn thường thấy các Annotation sau ở trên cùng:

```java
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    // ...
}
```

### 1.1. `@Service` (Của Spring Boot)
- **Ý nghĩa:** Báo cho Spring Boot biết class này đóng vai trò là một "Service" (nơi chứa logic nghiệp vụ).
- **Tác dụng:** Khi ứng dụng khởi động, Spring Boot sẽ tự động quét (scan) và tạo ra một đối tượng (Bean) duy nhất (Singleton) của class này và đưa vào bộ nhớ (IoC Container) để quản lý. Nhờ vậy, bạn có thể gọi Service này ở bất kỳ Controller nào mà không cần dùng từ khoá `new RoleService()`.

### 1.2. `@RequiredArgsConstructor` (Của thư viện Lombok)
- **Ý nghĩa:** Tự động sinh ra một hàm khởi tạo (Constructor) chứa tất cả các biến bắt buộc (các biến được đánh dấu là `final`).
- **Tác dụng:** Đây là cách **Dependency Injection (Tiêm phụ thuộc)** chuẩn nhất hiện nay trong Spring Boot. Thay vì phải viết `@Autowired` trên từng biến, Spring sẽ tự động tiêm (inject) các Repository/Mapper vào Service thông qua hàm khởi tạo mà Lombok vừa ngầm sinh ra.

### 1.3. `@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)` (Của thư viện Lombok)
- **Ý nghĩa:** Ép tất cả các biến khai báo bên trong class này tự động có thêm từ khoá `private final` ở đằng trước.
- **Tác dụng:** Giúp code nhìn cực kỳ gọn gàng. Thay vì phải gõ:
  `private final RoleRepository roleRepository;`
  Thì bạn chỉ cần gõ:
  `RoleRepository roleRepository;`
- **Lưu ý:** Vì các biến bị biến thành `final` (bắt buộc phải có giá trị), nó sẽ kết hợp hoàn hảo với `@RequiredArgsConstructor` ở trên để Spring tự động tiêm Bean vào.

### 1.4. `@Transactional` (Của Spring Boot)
- **Ý nghĩa:** Khởi tạo một Giao dịch cơ sở dữ liệu (Database Transaction) cho toàn bộ method.
- **Tác dụng:** Khi lưu nhiều bảng cùng lúc (ví dụ tạo Role xong tạo RolePermission), nếu ở bước thứ 2 xảy ra lỗi rớt mạng hoặc lỗi logic, Spring sẽ tự động **Rollback (hoàn tác)** lại bước 1. Đảm bảo dữ liệu không bao giờ bị tình trạng "lưu một nửa".

---

## 2. Java Stream API (`.stream()`)

Trong các hàm lấy danh sách, bạn hay gặp đoạn code:
```java
roleRepository.findAll().stream().map(...).toList();
```

- **`.stream()` là gì?** Nó biến một cấu trúc dữ liệu (như `List<Role>`) thành một "dòng chảy dữ liệu" (Stream).
- Hãy tưởng tượng danh sách giống như một đống cam trong giỏ. Khi gọi `.stream()`, bạn đổ đống cam đó lên một **băng chuyền nhà máy**.
- Trên băng chuyền đó, từng quả cam (từng object `Role`) sẽ đi qua các khâu xử lý liên tiếp (như gọt vỏ, vắt nước) tương ứng với các hàm `.map()`, `.filter()`.
- Ở cuối dây chuyền, khâu đóng gói `.toList()` sẽ gom tất cả nước cam lại thành một danh sách mới.

=> Nhờ có `.stream()`, bạn không cần phải viết các vòng lặp `for (...) { ... }` dài dòng nữa.

---

## 3. Cú pháp hai chấm `::` trong `.map()`

**`::` (Method Reference)** là một tính năng được giới thiệu từ Java 8, đóng vai trò như một cách viết tắt cực ngắn của **Lambda Expression**.

### Trong `.map(roleMapper::toRoleResponse)`
Thay vì viết theo kiểu hàm Lambda thông thường:
```java
.map(role -> roleMapper.toRoleResponse(role))
```
*(Ý nghĩa: Với mỗi biến `role` trên băng chuyền, đưa nó vào hàm `toRoleResponse` của `roleMapper`)*

Bạn có thể viết tắt lại thành:
```java
.map(roleMapper::toRoleResponse)
```
Trình biên dịch Java sẽ tự hiểu: "À, hãy lấy đối tượng đang chạy trên băng chuyền và nhét nó vào làm tham số cho hàm `toRoleResponse` của đối tượng `roleMapper`".

### Trong `.map(RolePermission::getPermissionId)`
Cũng tương tự, thay vì viết:
```java
.map(rp -> rp.getPermissionId())
```
*(Ý nghĩa: Với mỗi object `rp`, gọi hàm `getPermissionId()` của nó)*

Bạn viết tắt thành:
```java
.map(RolePermission::getPermissionId)
```

**Tóm lại:** Cú pháp `::` chỉ đơn thuần là làm cho code của bạn đọc giống như ngôn ngữ tự nhiên hơn (ngắn gọn, sạch sẽ) chứ bản chất hoạt động y hệt như hàm Lambda `() -> {}`.
