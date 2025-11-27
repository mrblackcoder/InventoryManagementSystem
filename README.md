# Inventory Management System

**Student Name:** Mehmet Taha Boynikoğlu
**Student ID:** 212 125 10 34
**Project:** Web Design and Programming - Term Project
**Institution:** Fatih Sultan Mehmet Vakıf Üniversitesi

---

## 📋 Project Description

This project is a comprehensive **web-based inventory management system** that enables users to remotely track and manage products, categories, suppliers, and stock transactions. The system provides secure authentication, real-time inventory monitoring, and integration with external web services for currency conversion.

Users can perform the following operations:
- **User Management**: Register, login, and manage user accounts
- **Product Management**: Add, update, organize, and track product information
- **Stock Operations**: Process purchases, sales, returns, and inventory adjustments
- **Real-time Monitoring**: Monitor inventory levels and generate reports
- **Admin Panel**: Advanced management features for administrators

---

## 🗄️ Database Structure (5 Tables)

| Table Name | Description | Relationships |
|------------|-------------|---------------|
| **users** | User authentication and authorization | → products (createdBy), → stock_transactions (performedBy) |
| **products** | Product inventory information | → categories, → suppliers, → users |
| **categories** | Product categorization | ← products (One-to-Many) |
| **suppliers** | Supplier information and contacts | ← products (One-to-Many) |
| **stock_transactions** | Stock movement history (purchases, sales, returns) | → products, → users |

---

## 🛠️ Technical Stack

### Backend
- **Framework**: Spring Boot 3.5.6
- **Language**: Java 21
- **Security**: Spring Security with BCrypt password encoding
- **Database**: MySQL 8.x
- **ORM**: Spring Data JPA / Hibernate
- **Build Tool**: Maven
- **Validation**: Jakarta Bean Validation

### Frontend
- **Template Engine**: Thymeleaf
- **Styling**: Custom CSS
- **JavaScript**: ES6+ for dynamic features

### External Integration
- **Currency API**: ExchangeRate-API for real-time currency conversion

---

## ✨ Key Features

### 1. Authentication & Authorization
- ✅ User registration with validation
- ✅ Secure login with Spring Security
- ✅ Role-based access control (USER, ADMIN)
- ✅ Password encryption with BCrypt
- ✅ Session management

### 2. Product Management
- ✅ Full CRUD operations for products
- ✅ SKU-based product identification
- ✅ Category and supplier associations
- ✅ Stock level tracking with reorder levels
- ✅ Product status management (ACTIVE, INACTIVE, DISCONTINUED)

### 3. Stock Transaction System
- ✅ Purchase tracking (IN)
- ✅ Sales recording (OUT)
- ✅ Return processing (RETURN)
- ✅ Manual adjustments (ADJUSTMENT)
- ✅ Automatic stock quantity updates
- ✅ Transaction history with user attribution

### 4. Category & Supplier Management
- ✅ Category organization for products
- ✅ Supplier information management
- ✅ Search and filtering capabilities

### 5. Admin Panel
- ✅ User management interface
- ✅ Stock transaction reports
- ✅ System statistics dashboard
- ✅ Role-based access restrictions

### 6. External Web Service Integration
- ✅ Real-time currency exchange rates
- ✅ Multi-currency price display (USD → TRY)
- ✅ Currency conversion API endpoints

---

## 🚀 Getting Started

### Prerequisites
- Java 21 or higher
- MySQL 8.x
- Maven 3.6+
- IDE (IntelliJ IDEA recommended)

### Installation Steps

1. **Clone the Repository**
   ```bash
   git clone https://github.com/mrblackcoder/InventoryManagementSystem.git
   cd InventoryManagementSystem
   ```

2. **Configure Database**
   - Ensure MySQL server is running
   - Database will be auto-created as configured in `application.properties`
   - Default database name: `inventory_db`

3. **Run the Application**
   ```bash
   ./mvnw spring-boot:run
   ```
   Or run from IDE: `InventoryManagementSystemApplication.java`

4. **Access the Application**
   - Home Page: http://localhost:8080/
   - Login: http://localhost:8080/login
   - Register: http://localhost:8080/register

---

## 📡 REST API Endpoints

### Products
- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID
- `GET /api/products/sku/{sku}` - Get product by SKU
- `GET /api/products/low-stock` - Get low stock products
- `POST /api/products` - Create new product
- `PUT /api/products/{id}` - Update product
- `DELETE /api/products/{id}` - Delete product
- `PATCH /api/products/{id}/quantity` - Update stock quantity

### Categories
- `GET /api/categories` - Get all categories
- `GET /api/categories/active` - Get active categories
- `POST /api/categories` - Create category
- `PUT /api/categories/{id}` - Update category
- `DELETE /api/categories/{id}` - Delete category

### Suppliers
- `GET /api/suppliers` - Get all suppliers
- `GET /api/suppliers/active` - Get active suppliers
- `GET /api/suppliers/search?name={name}` - Search suppliers
- `POST /api/suppliers` - Create supplier
- `PUT /api/suppliers/{id}` - Update supplier
- `DELETE /api/suppliers/{id}` - Delete supplier

### Stock Transactions
- `GET /api/stock-movements` - Get all movements
- `GET /api/stock-movements/recent` - Get recent movements (last 10)
- `GET /api/stock-movements/product/{productId}` - Get movements by product
- `POST /api/stock-movements` - Create new stock movement

### Currency (External API Integration)
- `GET /api/currency/rates` - Get current exchange rates
- `GET /api/currency/convert?amount={amount}&targetCurrency={currency}` - Convert currency
- `GET /api/currency/currencies` - Get available currencies

### Users (Admin Only)
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `DELETE /api/users/{id}` - Delete user

---

## 🔐 Default User Roles

After registration, users have the following default role:
- **Role**: USER
- **Permissions**: Access to products, categories, suppliers, and dashboard

To create an ADMIN user, manually update the database:
```sql
UPDATE users SET role = 'ADMIN' WHERE username = 'your_username';
```

---

## 📊 Project Structure

```
src/
├── main/
│   ├── java/com/ims/inventorymanagementsystem/
│   │   ├── config/           # Security configuration
│   │   ├── controller/       # REST & View controllers
│   │   ├── exception/        # Custom exceptions
│   │   ├── model/            # Entity classes
│   │   ├── repository/       # Data access layer
│   │   └── service/          # Business logic
│   └── resources/
│       ├── static/css/       # Stylesheets
│       └── templates/        # Thymeleaf templates
│           ├── admin/        # Admin panel pages
│           ├── error/        # Error pages
│           ├── home.html
│           ├── login.html
│           ├── register.html
│           └── dashboard.html
└── test/                     # Unit tests
```

---

## 📈 Future Enhancements (Optional)

- React.js frontend for dynamic SPA experience
- AWS deployment (EC2 + RDS)
- Docker containerization
- Swagger API documentation
- Unit and integration testing
- Barcode scanning integration
- Email notifications for low stock
- Advanced analytics and charts

---

## 📝 License

This project is developed as a term project for educational purposes.

© 2025 Mehmet Taha Boynikoğlu - All Rights Reserved

---

## 📞 Contact

**Student:** Mehmet Taha Boynikoğlu
**Student ID:** 212 125 10 34
**Institution:** Fatih Sultan Mehmet Vakıf Üniversitesi
