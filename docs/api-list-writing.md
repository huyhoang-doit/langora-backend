# Danh sách API cho Module Luyện Viết (Writing Management)

Tài liệu này định nghĩa chi tiết các API endpoints dành cho quản trị viên (Admin) để quản lý toàn bộ hệ sinh thái tính năng Luyện Viết (Writing) trong Langora, dựa trên cấu trúc cơ sở dữ liệu `writting.dbml`.

---

## 1. Writing Levels (Cấp độ Viết)
Quản lý các cấp độ kỹ năng viết (VD: BEGINNER, INTERMEDIATE, ADVANCED).

| Feature | Method | Endpoint | Description |
| :--- | :--- | :--- | :--- |
| List Levels | `GET` | `/api/v1/admin/writing-levels` | Lấy danh sách các cấp độ viết (có phân trang/sắp xếp theo display_order). |
| Get Level | `GET` | `/api/v1/admin/writing-levels/:id` | Xem chi tiết một cấp độ viết. |
| Create Level | `POST` | `/api/v1/admin/writing-levels` | Tạo mới một cấp độ viết. |
| Update Level | `PUT` | `/api/v1/admin/writing-levels/:id` | Cập nhật thông tin cấp độ (name, description, display_order). |
| Delete Level | `DELETE`| `/api/v1/admin/writing-levels/:id` | Xóa cấp độ viết (yêu cầu không có bài tập nào đang tham chiếu tới). |

---

## 2. Writing Content Types (Thể loại nội dung)
Quản lý các thể loại bài viết (VD: EMAIL, DIARY, ESSAY, ARTICLE, REPORT, STORY).

| Feature | Method | Endpoint | Description |
| :--- | :--- | :--- | :--- |
| List Types | `GET` | `/api/v1/admin/writing-content-types` | Lấy danh sách các thể loại bài viết. |
| Get Type | `GET` | `/api/v1/admin/writing-content-types/:id`| Xem chi tiết một thể loại. |
| Create Type | `POST` | `/api/v1/admin/writing-content-types` | Tạo mới một thể loại bài viết (kèm icon_url). |
| Update Type | `PUT` | `/api/v1/admin/writing-content-types/:id`| Cập nhật thông tin thể loại. |
| Delete Type | `DELETE`| `/api/v1/admin/writing-content-types/:id`| Xóa thể loại bài viết. |

---

## 3. Writing Topics (Chủ đề Viết)
Quản lý các chủ đề viết (VD: PERSONAL, BUSINESS, EDUCATION, TRAVEL, TECHNOLOGY).

| Feature | Method | Endpoint | Description |
| :--- | :--- | :--- | :--- |
| List Topics | `GET` | `/api/v1/admin/writing-topics` | Lấy danh sách các chủ đề. |
| Get Topic | `GET` | `/api/v1/admin/writing-topics/:id` | Xem chi tiết một chủ đề. |
| Create Topic | `POST` | `/api/v1/admin/writing-topics` | Tạo mới một chủ đề viết. |
| Update Topic | `PUT` | `/api/v1/admin/writing-topics/:id` | Cập nhật thông tin chủ đề. |
| Delete Topic | `DELETE`| `/api/v1/admin/writing-topics/:id` | Xóa chủ đề viết. |

---

## 4. Writing Exercises (Bài tập luyện viết)
Entity chính liên kết Ngôn ngữ, Cấp độ, Thể loại và Chủ đề để tạo ra một bài luyện viết hoàn chỉnh.

| Feature | Method | Endpoint | Description |
| :--- | :--- | :--- | :--- |
| List Exercises| `GET` | `/api/v1/admin/writing-exercises` | Danh sách bài tập (Hỗ trợ filter theo `languageId`, `levelId`, `topicId`, `status`). |
| Get Exercise | `GET` | `/api/v1/admin/writing-exercises/:id`| Xem chi tiết thông tin bài tập viết (title, summary, thumbnail, phần thưởng xp/credits). |
| Create Exercise| `POST` | `/api/v1/admin/writing-exercises` | Thêm mới một bài tập viết. |
| Update Exercise| `PUT` | `/api/v1/admin/writing-exercises/:id`| Chỉnh sửa bài tập viết. |
| Toggle Status | `PATCH`| `/api/v1/admin/writing-exercises/:id/status`| Cập nhật trạng thái `is_active` (Bật/Tắt hiển thị với người dùng). |
| Delete Exercise| `DELETE`| `/api/v1/admin/writing-exercises/:id`| Xóa bài tập viết. |

---

## 5. Writing Exercise Sentences (Các câu trong bài tập)
Admin chia đoạn văn/bài viết thành từng câu nhỏ để người dùng luyện dịch và viết lại.

| Feature | Method | Endpoint | Description |
| :--- | :--- | :--- | :--- |
| List Sentences| `GET` | `/api/v1/admin/writing-exercises/:exerciseId/sentences` | Lấy danh sách tất cả các câu thuộc về một bài tập viết (sắp xếp theo `sentence_order`). |
| Get Sentence | `GET` | `/api/v1/admin/writing-exercise-sentences/:id` | Lấy chi tiết một câu. |
| Create Sentence| `POST` | `/api/v1/admin/writing-exercises/:exerciseId/sentences` | Thêm mới câu dịch (cần có `source_text`, `target_text`, và cấu hình `hints`). |
| Update Sentence| `PUT` | `/api/v1/admin/writing-exercise-sentences/:id` | Chỉnh sửa chi tiết một câu (Cập nhật ngữ liệu gợi ý grammar_hints, vocabulary_hints). |
| Sort Sentences| `PATCH`| `/api/v1/admin/writing-exercises/:exerciseId/sentences/reorder` | Cập nhật lại thứ tự các câu (`sentence_order`) trong bài tập. |
| Delete Sentence| `DELETE`| `/api/v1/admin/writing-exercise-sentences/:id` | Xóa một câu ra khỏi bài tập. |

---

## 6. User Tracking & Moderation (Theo dõi và kiểm duyệt)
Các API Read-Only cho Admin để xem xét tiến trình học tập, chất lượng AI chấm điểm.

| Feature | Method | Endpoint | Description |
| :--- | :--- | :--- | :--- |
| List Sessions | `GET` | `/api/v1/admin/writing-sessions` | Danh sách các session luyện viết của người dùng (Theo dõi trạng thái IN_PROGRESS, COMPLETED). |
| Get Session | `GET` | `/api/v1/admin/writing-sessions/:id` | Lấy lịch sử làm bài chi tiết của một session (Bao gồm điểm ngữ pháp, từ vựng, độ chính xác). |
| List AI Feedbacks | `GET` | `/api/v1/admin/writing-ai-feedbacks` | Danh sách phản hồi AI đã được tạo ra để Admin giám sát chất lượng LLM. |
| Override Feedback | `PUT` | `/api/v1/admin/writing-ai-feedbacks/:id` | (Tuỳ chọn) Admin can thiệp sửa đổi/bổ sung feedback từ AI nếu AI chấm sai. |
