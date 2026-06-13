# 1. CLEAN ARCHITECTURE

## Project Structure

```
langora-backend

src/main/java/com/langora

├── shared
│
├── identity
│
├── user
│
├── learning
│
├── vocabulary
│
├── writing
│
├── assessment
│
├── gamification
│
├── notification
│
├── content
│
├── ai
│
└── analytics
```

---

# 2. MODULE STRUCTURE

Ví dụ:

```
vocabulary
```

---

```
vocabulary

├── controller
│
├── application
│
│    ├── command
│    ├── query
│    ├── service
│
├── domain
│
│    ├── entity
│    ├── repository
│    ├── event
│
├── infrastructure
│
│    ├── persistence
│    ├── mapper
│
└── dto
```

# 3. DOMAIN BREAKDOWN

---

# MODULE 1

# Identity

---

## Tables

### users

```
id

email

password_hash

status

email_verified

created_at

updated_at
```

---

### roles

```
id
name
description
```

---

### permissions

```
id
name
```

---

### role_permissions

```
role_id
permission_id
```

---

### user_roles

```
user_id
role_id
```

---

### refresh_tokens

```
id

user_id

token

expired_at

revoked
```

---

# MODULE 2

# User Profile

---

### user_profiles

```
id

user_id

full_name

avatar_url

country

timezone

native_language

target_language
```

---

### user_preferences

```
id

user_id

daily_goal_minutes

daily_goal_words

notification_enabled
```

---

# 5. DOMAIN BREAKDOWN

---

# MODULE 1

# Identity

---

## Tables

### users

```
id

email

password_hash

status

email_verified

created_at

updated_at
```

---

### roles

```
id
name
description
```

---

### permissions

```
id
name
```

---

### role_permissions

```
role_id
permission_id
```

---

### user_roles

```
user_id
role_id
```

---

### refresh_tokens

```
id

user_id

token

expired_at

revoked
```

---

# MODULE 2

# User Profile

---

### user_profiles

```
id

user_id

full_name

avatar_url

country

timezone

native_language

target_language
```

---

### user_preferences

```
id

user_id

daily_goal_minutes

daily_goal_words

notification_enabled
```

---

# MODULE 3

## languages

```
id

code

name

flag_icon
```