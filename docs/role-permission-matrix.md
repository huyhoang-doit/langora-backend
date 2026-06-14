# Bảng Dữ Liệu Khởi Tạo (Seed Data Matrix)

Tài liệu này mô tả danh sách các dữ liệu mặc định (Seed data) được tự động khởi tạo khi hệ thống Langora Backend khởi động lần đầu thông qua file `ApplicationInitConfig.java`.

## 1. Bảng Users (Người dùng)

| Email | Password | Role | Status | Ý nghĩa |
| :--- | :--- | :--- | :--- | :--- |
| `admin@langora.com` | `admin123` | **ADMIN** | `ACTIVE` | Tài khoản Quản trị viên hệ thống có toàn quyền. |
| `member@langora.com` | `member123` | **MEMBER** | `ACTIVE` | Tài khoản Người dùng cơ bản (Client User). |

---

## 2. Bảng Roles (Vai trò)

| Role Code | Role Name | Mô tả | Tính chất |
| :--- | :--- | :--- | :--- |
| **ADMIN** | System Administrator | Full system access (Quản trị toàn quyền) | Hệ thống (Không thể xoá) |
| **MEMBER** | Member User | Standard user access (Người dùng cuối) | Hệ thống (Không thể xoá) |

---

## 3. Bảng Permissions (Quyền hạn)

Dưới đây là các quyền hạn cốt lõi của hệ thống, được phân bổ cho các Roles tương ứng thông qua bảng `Role_Permissions`:

| Permission Code | Ý nghĩa (Mô tả) | Gán cho ADMIN | Gán cho MEMBER |
| :--- | :--- | :---: | :---: |
| `USERS_VIEW` | Xem danh sách và chi tiết người dùng | ✅ | ❌ |
| `USERS_MANAGE` | Quản lý người dùng (Khoá, Tạo, Xoá) | ✅ | ❌ |
| `ROLES_VIEW` | Xem danh sách Role và Permission | ✅ | ❌ |
| `ROLES_MANAGE` | Quản lý Role và gán quyền | ✅ | ❌ |
| `CONTENT_VIEW` | Xem nội dung học tập (Khoá học, Từ vựng) | ✅ | ✅ |
| `CONTENT_MANAGE` | Quản trị nội dung học tập (Tạo, Sửa, Xoá) | ✅ | ❌ |

> **Lưu ý:**
> - Tài khoản **ADMIN** mặc định sở hữu toàn bộ tất cả Permission trong hệ thống.
> - Tài khoản **MEMBER** chỉ được cấp quyền xem nội dung học tập (`CONTENT_VIEW`) để có thể sử dụng App/Web Client, không có quyền truy cập vào các API quản trị (`/api/v1/admin/...`).
> - Dữ liệu này sẽ tự động được chạy ngầm mỗi khi ứng dụng bật lên (nếu DB rỗng) để đảm bảo luôn có tài khoản Admin đăng nhập.
