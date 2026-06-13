# Danh sách API cho Langora Admin Portal

Tài liệu này liệt kê các API endpoints cần thiết để phục vụ cho các module trên Admin Portal, dựa trên cấu trúc UI (`app/(auth)`, `app/users`, `app/content`, `app/moderation`, `app/roles`) và cấu trúc DBML.

---

## 1. Module Auth (`app/(auth)`)

Dùng để xác thực và ủy quyền cho quản trị viên.

| Feature | Method | Endpoint | Description |
| :--- | :--- | :--- | :--- |
| Login | `POST` | `/api/v1/admin/auth/login` | Đăng nhập Admin. Trả về `accessToken` & `refreshToken`. |
| Logout | `POST` | `/api/v1/admin/auth/logout` | Đăng xuất Admin. Thu hồi refresh token. |
| Refresh Token | `POST` | `/api/v1/admin/auth/refresh` | Lấy `accessToken` mới bằng `refreshToken`. |
| Get Me | `GET` | `/api/v1/admin/auth/me` | Lấy thông tin admin hiện tại và danh sách quyền (permissions). |
| Forgot Password | `POST` | `/api/v1/admin/auth/forgot-password`| Gửi email đặt lại mật khẩu. |
| Reset Password | `POST` | `/api/v1/admin/auth/reset-password` | Đặt lại mật khẩu mới thông qua token. |

---

## 2. Module Roles & Permissions (`app/roles`)

Phục vụ việc phân quyền (RBAC) cho người dùng hệ thống. (Nguồn: `identity.dbml`)

| Feature | Method | Endpoint | Description |
| :--- | :--- | :--- | :--- |
| List Roles | `GET` | `/api/v1/admin/roles` | Lấy danh sách Roles (có phân trang/tìm kiếm). |
| Get Role | `GET` | `/api/v1/admin/roles/:id` | Xem chi tiết 1 Role và danh sách Permissions của role đó. |
| Create Role | `POST` | `/api/v1/admin/roles` | Tạo mới Role kèm theo danh sách Permissions. |
| Update Role | `PUT` | `/api/v1/admin/roles/:id` | Cập nhật Role và Permissions. |
| Delete Role | `DELETE`| `/api/v1/admin/roles/:id` | Xóa Role (Cần check điều kiện ràng buộc nếu có user đang dùng). |
| List Permissions| `GET` | `/api/v1/admin/permissions` | Lấy toàn bộ danh sách các Permissions có sẵn trong hệ thống. |

---

## 3. Module Users (`app/users`)

Quản lý danh sách người dùng, xem tiến trình học và gán role cho User nội bộ. (Nguồn: `user.dbml`, `identity.dbml`)

| Feature | Method | Endpoint | Description |
| :--- | :--- | :--- | :--- |
| List Users | `GET` | `/api/v1/admin/users` | Danh sách người dùng (Tìm kiếm, phân trang, lọc theo status/role). |
| Get User | `GET` | `/api/v1/admin/users/:id` | Xem chi tiết user (profile, account info). |
| Update Status | `PATCH` | `/api/v1/admin/users/:id/status` | Khóa (Suspend), Kích hoạt (Active) tài khoản. |
| Assign Roles | `POST` | `/api/v1/admin/users/:id/roles` | Gán vai trò (Role) cho User (dành cho cấp quyền Admin/Mod). |
| User Progress | `GET` | `/api/v1/admin/users/:id/progress`| Lấy thống kê quá trình học của user (từ `user_language_progress`). |
| Login History | `GET` | `/api/v1/admin/users/:id/history` | Xem lịch sử đăng nhập (từ `login_histories`). |

---

## 4. Module Content (`app/content`)

Quản lý các học liệu bao gồm: Ngôn ngữ, Cấp độ, Bài học, Từ vựng, Bài viết.

### 4.1. Ngôn ngữ & Cấp độ (`learning.dbml`)
| Feature | Method | Endpoint | Description |
| :--- | :--- | :--- | :--- |
| List Languages | `GET` | `/api/v1/admin/languages` | Lấy danh sách ngôn ngữ đang được hỗ trợ. |
| Toggle Language | `PATCH` | `/api/v1/admin/languages/:id/status`| Kích hoạt / Vô hiệu hóa ngôn ngữ. |
| List Levels | `GET` | `/api/v1/admin/languages/:langId/levels`| Lấy danh sách các cấp độ (A1, A2...) của một ngôn ngữ. |
| Create Level | `POST` | `/api/v1/admin/languages/:langId/levels`| Tạo mới cấp độ (Level). |
| Update Level | `PUT` | `/api/v1/admin/levels/:id` | Sửa thông tin Level. |

### 4.2. Lộ trình & Bài học (`learning.dbml`)
| Feature | Method | Endpoint | Description |
| :--- | :--- | :--- | :--- |
| List Paths | `GET` | `/api/v1/admin/languages/:langId/paths` | Danh sách Lộ trình học (Learning Paths). |
| List Units | `GET` | `/api/v1/admin/paths/:pathId/units` | Lấy các Units thuộc một lộ trình. |
| List Lessons | `GET` | `/api/v1/admin/units/:unitId/lessons`| Danh sách bài học của một Unit. |
| Create Lesson | `POST` | `/api/v1/admin/units/:unitId/lessons`| Tạo bài học mới (Vocabulary, Grammar, v.v.). |
| Update Lesson | `PUT` | `/api/v1/admin/lessons/:id` | Cập nhật thông tin bài học. |
| Lesson Resource | `POST` | `/api/v1/admin/lessons/:id/resources`| Upload/thêm file tài liệu (PDF, Audio) cho bài học. |

### 4.3. Từ vựng (`vocabulary.dbml`)
| Feature | Method | Endpoint | Description |
| :--- | :--- | :--- | :--- |
| List Topics | `GET` | `/api/v1/admin/languages/:langId/topics`| Danh sách chủ đề từ vựng. |
| List Vocab | `GET` | `/api/v1/admin/topics/:topicId/vocabularies`| Danh sách từ vựng theo chủ đề. |
| Create Vocab | `POST` | `/api/v1/admin/topics/:topicId/vocabularies`| Thêm mới từ vựng. |
| Update Vocab | `PUT` | `/api/v1/admin/vocabularies/:id` | Sửa từ vựng. |
| Vocab Pronounce | `POST` | `/api/v1/admin/vocabularies/:id/pronounce`| Upload file audio phát âm cho từ vựng. |
| Vocab Examples | `POST` | `/api/v1/admin/vocabularies/:id/examples` | Thêm ví dụ cho từ vựng (Câu ví dụ + Dịch + Giải thích). |

### 4.4. Luyện Viết (`writting.dbml`)
| Feature | Method | Endpoint | Description |
| :--- | :--- | :--- | :--- |
| List Exercises | `GET` | `/api/v1/admin/writing-exercises` | Lấy danh sách các bài luyện viết. |
| Create Exercise | `POST` | `/api/v1/admin/writing-exercises` | Thêm bài tập viết mới. |
| Update Exercise | `PUT` | `/api/v1/admin/writing-exercises/:id` | Sửa thông tin bài tập viết. |
| List Sentences | `GET` | `/api/v1/admin/writing-exercises/:id/sentences`| Lấy các câu trong bài tập luyện viết. |
| Create Sentence | `POST` | `/api/v1/admin/writing-exercises/:id/sentences`| Thêm/Tạo câu (kèm target_text và hints). |

---

## 5. Module Moderation & Kiểm duyệt (`app/moderation`)

Quản lý User Reports và xem xét các nội dung AI sinh ra.

### 5.1. User Reports (`user.dbml`)
| Feature | Method | Endpoint | Description |
| :--- | :--- | :--- | :--- |
| List Reports | `GET` | `/api/v1/admin/reports` | Lấy danh sách báo cáo từ người dùng (Bug, AI Error, Content Error). |
| Get Report | `GET` | `/api/v1/admin/reports/:id` | Xem chi tiết báo cáo. |
| Update Report | `PATCH` | `/api/v1/admin/reports/:id/status`| Đổi trạng thái xử lý report (VD: RESOLVED). |

### 5.2. AI Content / Feedbacks (`writting.dbml`)
| Feature | Method | Endpoint | Description |
| :--- | :--- | :--- | :--- |
| List Feedbacks | `GET` | `/api/v1/admin/ai-feedbacks` | Danh sách các AI Feedbacks (để kiểm duyệt). |
| Override Feedback| `PUT` | `/api/v1/admin/ai-feedbacks/:id` | Admin sửa trực tiếp giải thích của AI (Human Override). |
