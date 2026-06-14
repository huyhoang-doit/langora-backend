# Cẩm nang toàn tập Spring Boot Concept trong dự án Langora Backend

Tài liệu này tổng hợp **tất cả** các khái niệm, công cụ, và thư viện Java/Spring Boot đang được sử dụng trong dự án Langora Backend. Từ file cấu hình POM, đến Database, và đi sâu vào từng file code.

---

## 1. Tầng Cấu hình & Quản lý thư viện

### `pom.xml` (Project Object Model)
Đây là "trái tim" của công cụ Maven. Chứa toàn bộ thông tin về các thư viện (dependencies) mà dự án cần.
- **`spring-boot-starter-web`**: Cung cấp môi trường chạy web (Tomcat) và các công cụ để viết RESTful API.
- **`spring-boot-starter-data-jpa`**: Tích hợp Hibernate để tương tác với Database thông qua code Java thay vì viết câu lệnh SQL thuần.
- **`spring-boot-starter-validation`**: Thư viện dùng để kiểm tra tính hợp lệ của dữ liệu đầu vào (ví dụ kiểm tra email có đúng định dạng không).
- **`spring-boot-starter-oauth2-resource-server`**: Cung cấp công cụ để giải mã và xác thực token JWT cho hệ thống đăng nhập.
- **`org.mapstruct`**: Thư viện tự động sinh code để "copy" dữ liệu từ Entity (lớp kết nối DB) sang DTO (lớp trả về Client).
- **`com.diffplug.spotless` (Plugin)**: Công cụ tự động format code (căn lề, xoá khoảng trắng thừa, gom nhóm import) theo chuẩn trước khi biên dịch.

### `src/main/resources/application.yaml`
Nơi chứa các cấu hình môi trường cho ứng dụng.
- **`server.port` & `server.servlet.context-path`**: Định nghĩa cổng chạy (8080) và đường dẫn gốc (`/langora`).
- **`spring.datasource`**: Cấu hình kết nối tới Database PostgreSQL.
- **`spring.jpa.hibernate.ddl-auto: create`**: Lệnh yêu cầu Hibernate tự động tạo các bảng trong Database dựa trên các file `Entity` mỗi khi khởi động ứng dụng (chỉ nên dùng ở môi trường Dev).
- **`jwt`**: Các thông số tự định nghĩa để ký (sign) token bảo mật.

---

## 2. Tầng Cấu hình Ứng dụng (Package `configuration`)

Chứa các class được gắn mác `@Configuration`. Khi ứng dụng chạy lên, Spring sẽ đọc các file này đầu tiên để thiết lập hệ thống.

- **`@Configuration`**: Đánh dấu đây là file cấu hình. Spring sẽ phân tích các hàm bên trong để tạo các Bean.
- **`@Bean`**: Được đặt trên các hàm (methods). Báo cho Spring biết: "Hãy chạy hàm này, lấy kết quả (object) trả về và lưu vào bộ nhớ chung (IoC Container) để những nơi khác xài".
- **`SecurityConfig.java`**:
  - `@EnableWebSecurity` & `@EnableMethodSecurity`: Kích hoạt bộ lọc bảo mật của Spring.
  - `SecurityFilterChain`: Là một chuỗi các bộ lọc (filters) mà mọi request (HTTP gọi tới) đều phải đi qua. Tại đây cấu hình đường dẫn nào được phép vào tự do (`permitAll()`), đường dẫn nào cần token (`authenticated()`).
- **`ApplicationInitConfig.java`**:
  - `ApplicationRunner`: Một giao diện (interface) đặc biệt. Code bên trong hàm `run()` của nó sẽ được thực thi **ngay sau khi ứng dụng khởi động thành công**. Dùng để tạo tài khoản Admin mặc định (`admin@langora.com`).

---

## 3. Tầng Tương tác với Client (Package `controller`)

Nơi đón nhận các request (yêu cầu) từ Frontend và trả về kết quả. Không chứa logic tính toán phức tạp.

- **`@RestController`**: Khai báo class này là một API Controller. Nó mặc định sẽ chuyển đổi kết quả trả về thành định dạng JSON.
- **`@RequestMapping("/path")`**: Định nghĩa đường dẫn gốc (Base URL) cho toàn bộ Controller.
- **`@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`**: Tương ứng với các phương thức HTTP (Đọc, Tạo, Sửa, Xoá).
- **`@RequestBody`**: Lấy dữ liệu JSON từ phía client gửi lên và "nhồi" (map) vào một class DTO trong Java.
- **`@PathVariable`**: Lấy biến động từ đường dẫn URL. VD: `/roles/{id}` thì `@PathVariable String id` sẽ lấy giá trị `{id}` đó.
- **`@Valid`**: Đặt cạnh `@RequestBody`, yêu cầu Spring phải kiểm tra tính hợp lệ của dữ liệu (dựa trên các annotation `@NotBlank`, `@NotNull` trong DTO) trước khi cho phép đi vào hàm.

---

## 4. Tầng Logic Nghiệp Vụ (Package `service`)

Nơi xử lý mọi tính toán, điều kiện nghiệp vụ của hệ thống.

- **`@Service`**: Khai báo đây là khu vực xử lý logic để Spring quản lý (đã giải thích chi tiết ở tài liệu trước).
- **`@Transactional`**: Báo cho Spring biết: "Toàn bộ các lệnh gọi database trong hàm này là một Giao dịch (Transaction) duy nhất". Nếu có bất kỳ lỗi nào xảy ra làm hàm bị văng Exception, mọi thay đổi đối với Database sẽ bị hoàn tác (Rollback) như chưa từng xảy ra.

---

## 5. Tầng Truy cập Dữ liệu (Package `domain/entity` và `repository`)

Nơi giao tiếp trực tiếp với cơ sở dữ liệu (Database).

### JPA Entities (`domain/entity`)
Mỗi class Entity đại diện cho **1 Bảng (Table)** trong Database. Mỗi đối tượng (Object) của class này đại diện cho **1 Dòng (Row)**.
- **`@Entity`**: Đánh dấu class này là thực thể kết nối DB.
- **`@Table(name = "roles")`**: Chỉ định tên bảng trong DB.
- **`@Id`**: Đánh dấu trường (field) này là Khoá chính (Primary Key).
- **`@GeneratedValue(strategy = GenerationType.UUID)`**: Ra lệnh cho Hibernate tự động sinh một mã UUID ngẫu nhiên và gán vào khoá chính mỗi khi tạo mới dòng dữ liệu.

### JPA Repositories (`repository`)
Nơi chứa các câu lệnh truy vấn (Query).
- **`@Repository`**: Khai báo đây là lớp giao tiếp với CSDL.
- **`JpaRepository<Entity, Id_Type>`**: Interface siêu phàm của Spring Data JPA. Khi Controller kế thừa nó, bạn tự động có sẵn hàng chục hàm truy vấn cơ bản như `findAll()`, `findById()`, `save()`, `delete()` mà **không cần viết 1 dòng SQL nào**.
- **Derived Query Methods**: Là "ma thuật" của Spring. Bạn chỉ cần đặt tên hàm theo đúng cấu trúc, Spring sẽ tự dịch tên hàm ra câu SQL.
  - VD: `Optional<Role> findByCode(String code);` -> Spring sẽ tự dịch thành `SELECT * FROM roles WHERE code = ?`.

---

## 6. Tầng Trao đổi dữ liệu và Mapping (`dto` & `mapper`)

Lớp cách ly giữa Database và Client, đảm bảo Client chỉ nhận được những gì ta cho phép.

- **DTO (Data Transfer Object)**: Là các class thuần Java chỉ chứa dữ liệu (không có `@Entity`). `RoleCreationRequest` nhận data đầu vào, `RoleResponse` chứa data trả ra.
- **`@Mapper` (MapStruct)**: Dùng trong package `mapper`. MapStruct sẽ nhìn vào interface này và tự động viết một class thực thi để copy dữ liệu từ `Role` sang `RoleResponse`.
- **`@Mapping(target = "abc", ignore = true)`**: Báo cho MapStruct biết là hãy bỏ qua, không copy trường dữ liệu có tên là `abc`.

---

## 7. Tầng Xử lý Lỗi Toàn Cục (`exception`)

Nơi bắt gọn mọi lỗi văng ra trong hệ thống để format lại cho thân thiện với Frontend.

- **`@ControllerAdvice`**: Khai báo class này là "Bác sĩ trực cấp cứu" cho toàn bộ hệ thống API. Nó đứng ở ngoài cùng, hứng mọi Exception văng ra từ Controllers.
- **`@ExceptionHandler(AppException.class)`**: Báo cho Spring biết: "Nếu hệ thống bị văng ra `AppException`, hãy đưa nó vào hàm này để xử lý thay vì báo lỗi trắng trang trên màn hình của user". Tại đây ta bọc lỗi lại vào `ApiResponse` và gửi mã HTTP tương ứng.

---

## Tổng kết

Quy trình chảy của một Request trong Langora:
1. Gõ URL -> Bị **SecurityFilterChain** (tầng config) xét duyệt giấy tờ (Token).
2. Qua vòng gửi xe, chạy thẳng vào **Controller**. `Controller` kiểm tra định dạng data (`@Valid`).
3. Bàn giao data cho **Service** xử lý nghiệp vụ (`@Service`, `@Transactional`).
4. Nếu Service cần data từ DB, nó gọi **Repository** để thực hiện Query.
5. Repository lấy **Entity** từ Database lên và đưa cho Service.
6. Service lấy Entity đưa qua cho **Mapper** chuyển đổi thành **DTO**.
7. Service ném DTO lại cho Controller. Controller bọc DTO vào trong `ApiResponse` và trả về dạng JSON cho Client!
8. Nếu giữa đường có bất kỳ lỗi nào văng ra, **GlobalExceptionHandler** (`@ControllerAdvice`) sẽ chụp lấy và format lỗi trả về đàng hoàng.
