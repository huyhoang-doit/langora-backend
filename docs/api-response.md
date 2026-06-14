🧩 1. Nguyên tắc thiết kế API Response
Một response chuẩn nên:
 ✅ Consistent (tất cả API cùng format) 
 ✅ Phân biệt rõ success / error
 ✅ Dễ parse cho frontend (React / Next.js) 
 ✅ Có metadata (pagination, timestamp…) 
 ✅ Không phụ thuộc UI 
📦 2. Format chuẩn tổng quát

{
  "success": true,
  "message": "string",
  "data": {},
  "meta": {},
  "errors": null,
  "timestamp": "2026-04-14T10:00:00Z"
}

✅ 3. Response SUCCESS
🔹 3.1. API lấy data (GET)

{
  "success": true,
  "message": "Fetch auctions successfully",
  "data": [
    {
      "id": 1,
      "name": "Diamond Ring",
      "startPrice": 1000,
      "currentPrice": 1500,
      "status": "ONGOING"
    }
  ],
  "meta": {
    "page": 1,
    "limit": 10,
    "total": 100,
    "totalPages": 10
  },
  "errors": null,
  "timestamp": "2026-04-14T10:00:00Z"
}

🔹 3.2. API tạo mới (POST)

{
  "success": true,
  "message": "Auction created successfully",
  "data": {
    "id": 101,
    "name": "Gold Necklace"
  },
  "meta": null,
  "errors": null,
  "timestamp": "2026-04-14T10:00:00Z"
}

🔹 3.3. API update/delete

{
  "success": true,
  "message": "Auction updated successfully",
  "data": null,
  "meta": null,
  "errors": null,
  "timestamp": "2026-04-14T10:00:00Z"
}

❌ 4. Response ERROR
🔹 4.1. Validation error (quan trọng nhất)

{
  "success": false,
  "message": "Validation failed",
  "data": null,
  "meta": null,
  "errors": [
    {
      "field": "name",
      "message": "Name is required"
    },
    {
      "field": "startPrice",
      "message": "Must be greater than 0"
    }
  ],
  "timestamp": "2026-04-14T10:00:00Z"
}

👉 Frontend (React Hook Form / Zod) map cực dễ
🔹 4.2. Business error

{
  "success": false,
  "message": "Auction has already ended",
  "data": null,
  "meta": null,
  "errors": null,
  "timestamp": "2026-04-14T10:00:00Z"
}

🔹 4.3. Unauthorized

{
  "success": false,
  "message": "Unauthorized",
  "data": null,
  "meta": null,
  "errors": null,
  "timestamp": "2026-04-14T10:00:00Z"
}

🚀 5. Chuẩn HTTP Status Code


