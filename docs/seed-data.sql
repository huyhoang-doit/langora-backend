-- =====================================================================
-- KỊCH BẢN KHỞI TẠO DỮ LIỆU MẪU (SEED DATA) CHO DỰ ÁN LANGORA
-- Chạy script này bằng tay vào Database (qua pgAdmin, DBeaver, DataGrip)
-- =====================================================================

-- 1. Thêm Bảng Roles
-- Sử dụng UUID cố định để dễ dàng map với các bảng khác
INSERT INTO roles (id, code, name, description, is_system, created_at) VALUES 
('role-admin-0000-0000-0000-000000000000', 'ADMIN', 'System Administrator', 'Full system access', true, NOW()),
('role-member-0000-0000-0000-000000000000', 'MEMBER', 'Member User', 'Standard user access', true, NOW())
ON CONFLICT (id) DO NOTHING;

-- 2. Thêm Bảng Permissions
INSERT INTO permissions (id, code, name, description, created_at) VALUES 
('perm-usrview-0000-0000-0000-000000000000', 'USERS_VIEW', 'USERS VIEW', 'View users list and details', NOW()),
('perm-usrmang-0000-0000-0000-000000000000', 'USERS_MANAGE', 'USERS MANAGE', 'Manage users', NOW()),
('perm-rolview-0000-0000-0000-000000000000', 'ROLES_VIEW', 'ROLES VIEW', 'View roles and permissions', NOW()),
('perm-rolmang-0000-0000-0000-000000000000', 'ROLES_MANAGE', 'ROLES MANAGE', 'Manage roles', NOW()),
('perm-conview-0000-0000-0000-000000000000', 'CONTENT_VIEW', 'CONTENT VIEW', 'View learning content', NOW()),
('perm-conmang-0000-0000-0000-000000000000', 'CONTENT_MANAGE', 'CONTENT MANAGE', 'Manage learning content', NOW())
ON CONFLICT (id) DO NOTHING;

-- 3. Gán Permissions cho Roles (Bảng role_permissions)
-- Admin nhận tất cả quyền
INSERT INTO role_permissions (id, role_id, permission_id) VALUES 
(gen_random_uuid()::varchar, 'role-admin-0000-0000-0000-000000000000', 'perm-usrview-0000-0000-0000-000000000000'),
(gen_random_uuid()::varchar, 'role-admin-0000-0000-0000-000000000000', 'perm-usrmang-0000-0000-0000-000000000000'),
(gen_random_uuid()::varchar, 'role-admin-0000-0000-0000-000000000000', 'perm-rolview-0000-0000-0000-000000000000'),
(gen_random_uuid()::varchar, 'role-admin-0000-0000-0000-000000000000', 'perm-rolmang-0000-0000-0000-000000000000'),
(gen_random_uuid()::varchar, 'role-admin-0000-0000-0000-000000000000', 'perm-conview-0000-0000-0000-000000000000'),
(gen_random_uuid()::varchar, 'role-admin-0000-0000-0000-000000000000', 'perm-conmang-0000-0000-0000-000000000000')
ON CONFLICT DO NOTHING;

-- Member chỉ nhận quyền CONTENT_VIEW
INSERT INTO role_permissions (id, role_id, permission_id) VALUES 
(gen_random_uuid()::varchar, 'role-member-0000-0000-0000-000000000000', 'perm-conview-0000-0000-0000-000000000000')
ON CONFLICT DO NOTHING;

-- 4. Thêm Users
-- Mật khẩu đã được mã hoá BCrypt cho chữ "admin123" và "member123"
INSERT INTO users (id, email, password_hash, status, email_verified, created_at) VALUES 
('user-admin-0000-0000-0000-000000000000', 'admin@langora.com', '$2a$10$EblZqNptyYvcLm/VwDCVAuIssG//wE0aJ./2w1Yx9E4M7EwJ3E1Tq', 'ACTIVE', true, NOW()),
('user-member-0000-0000-0000-000000000000', 'member@langora.com', '$2a$10$EblZqNptyYvcLm/VwDCVAuIssG//wE0aJ./2w1Yx9E4M7EwJ3E1Tq', 'ACTIVE', true, NOW())
ON CONFLICT (id) DO NOTHING;

-- 5. Gán Roles cho Users (Bảng user_roles)
INSERT INTO user_roles (id, user_id, role_id, assigned_at, assigned_by) VALUES 
(gen_random_uuid()::varchar, 'user-admin-0000-0000-0000-000000000000', 'role-admin-0000-0000-0000-000000000000', NOW(), 'SYSTEM'),
(gen_random_uuid()::varchar, 'user-member-0000-0000-0000-000000000000', 'role-member-0000-0000-0000-000000000000', NOW(), 'SYSTEM')
ON CONFLICT DO NOTHING;
