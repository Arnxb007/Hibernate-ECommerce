# 🛒 Hibernate E-Commerce Management System

A Java-based **E-Commerce Management System** built with **Hibernate ORM 6, JPA, MySQL, Maven, and Java 17**.

The project demonstrates how an e-commerce domain can be modeled and persisted using Hibernate, including entity relationships, transactions, order processing, password hashing, advanced queries, soft deletion, and pagination.

---

## 📌 Project Overview

This project is designed as an academic/learning project for understanding:

- Java object-oriented programming
- Hibernate ORM and JPA
- Relational database design
- Entity relationships
- CRUD operations
- Database transactions
- HQL and named queries
- Criteria API
- Password hashing
- Order and inventory management
- Automated testing with JUnit 5

The application contains entities for **categories, products, users, orders, and order details**.

---

## ✨ Key Features

### 🗂️ Category Management
- Create and store product categories
- Unique category names
- Category-to-product one-to-many relationship
- Soft-delete support
- Named queries for active categories

### 📦 Product Management
- Product name and description through category association
- Product pricing
- Stock quantity tracking
- Category association
- Soft deletion
- Named queries
- Price-range filtering

### 👤 User Management
- Customer and administrator roles
- Unique usernames
- Unique email addresses
- BCrypt password hashing
- Password verification
- Soft-delete support
- Named queries for username, email, and role

### 🛒 Order Management
- Create orders for customers
- Multiple products per order
- Automatic order-total calculation
- Order date tracking
- User-to-order relationship
- Order soft deletion

### 📋 Order Details
- Quantity tracking
- Unit-price storage
- Product association
- Automatic total recalculation
- Cascaded persistence from orders

### 🔗 Hibernate/JPA Relationships

The project demonstrates:

- `@OneToMany`
- `@ManyToOne`
- `@JoinColumn`
- `CascadeType.ALL`
- `orphanRemoval`
- Lazy loading
- Bidirectional relationships

### 🔍 Advanced Queries

The project demonstrates:

- HQL queries
- `JOIN FETCH`
- Named queries
- `CriteriaBuilder`
- Dynamic predicates
- Price-range filtering
- Category filtering
- Pagination with `setFirstResult()` and `setMaxResults()`

### 🗑️ Soft Delete

Records are not physically removed from the database.

Instead:

```text
deleted = true
```

Active queries filter out deleted records while preserving the original database record.

### 🔐 Password Security

Passwords are never stored as plain text when created through the application.

The project uses:

```text
jBCrypt
```

with BCrypt work factor 12.

---

# 🏗️ Project Architecture

The application follows a simple layered structure:

```text
Application
    │
    ▼
Hibernate Session
    │
    ▼
JPA Entity Classes
    │
    ▼
Hibernate ORM
    │
    ▼
MySQL Database
```

---

# 📂 Project Structure

```text
Hibernate-ECommerce/
│
├── pom.xml
├── schema.sql
├── README.md
├── .gitignore
│
├── src/
│   │
│   ├── main/
│   │   │
│   │   ├── java/
│   │   │   │
│   │   │   └── com/
│   │   │       └── ecommerce/
│   │   │           │
│   │   │           ├── App.java
│   │   │           │
│   │   │           ├── entity/
│   │   │           │   ├── Category.java
│   │   │           │   ├── Product.java
│   │   │           │   ├── Users.java
│   │   │           │   ├── Orders.java
│   │   │           │   ├── OrderDetails.java
│   │   │           │   └── Role.java
│   │   │           │
│   │   │           └── util/
│   │   │               ├── HibernateUtil.java
│   │   │               └── PasswordUtil.java
│   │   │
│   │   └── resources/
│   │       ├── hibernate.cfg.xml
│   │       ├── hibernate-mysql.cfg.xml
│   │       └── logback.xml
│   │
│   └── test/
│       │
│       └── java/
│           └── com/
│               └── ecommerce/
│                   └── EcommerceHibernateTest.java
│
└── schema.sql
```

---

# 🧩 Important Classes

## `App.java`

Main demonstration application.

It demonstrates:

- Creating categories
- Creating products
- Registering users
- Creating orders
- Updating stock
- Fetching orders using `JOIN FETCH`
- Named queries
- CriteriaBuilder queries
- Soft deletion
- Pagination

---

## `Category.java`

Represents an e-commerce product category.

Relationship:

```text
Category 1 ─────────── * Product
```

Uses:

```java
@OneToMany
```

---

## `Product.java`

Represents products available for purchase.

Main fields:

```text
id
name
price
stockQuantity
category
deleted
```

---

## `Users.java`

Represents application users.

Supported roles:

```text
ADMIN
CUSTOMER
```

Passwords are stored as BCrypt hashes.

---

## `Orders.java`

Represents customer orders.

An order belongs to one user and can contain multiple order details.

```text
User
 │
 └── Orders
       │
       ├── OrderDetails
       ├── OrderDetails
       └── OrderDetails
```

---

## `OrderDetails.java`

Represents individual products inside an order.

Stores:

```text
quantity
unit price
product
order
```

The order total is calculated using:

```text
quantity × unit price
```

for every active order item.

---

## `HibernateUtil.java`

Responsible for:

- Creating the Hibernate `SessionFactory`
- Loading Hibernate configuration
- Registering entity classes
- Reconfiguring Hibernate
- Shutting down Hibernate safely

---

## `PasswordUtil.java`

Provides:

```java
hashPassword()
checkPassword()
```

using BCrypt.

---

# 🗄️ Database Design

The project uses the following main tables:

```text
categories
    │
    └── products
            │
            └── order_details
                    │
                    └── orders
                            │
                            └── users
```

### Tables

| Table | Purpose |
|---|---|
| `categories` | Product categories |
| `products` | Product catalogue and inventory |
| `users` | Customers and administrators |
| `orders` | Customer orders |
| `order_details` | Products contained in orders |

---

# 🔗 Entity Relationship Diagram

```text
                 ┌──────────────┐
                 │  Categories  │
                 └──────┬───────┘
                        │
                    1   │   *
                        ▼
                 ┌──────────────┐
                 │   Products   │
                 └──────┬───────┘
                        │
                    1   │   *
                        ▼
                 ┌──────────────┐
                 │Order Details │
                 └──────┬───────┘
                        │
                    *   │   1
                        ▼
                 ┌──────────────┐
                 │    Orders    │
                 └──────┬───────┘
                        │
                    *   │   1
                        ▼
                 ┌──────────────┐
                 │    Users     │
                 └──────────────┘
```

---

# 🛠️ Technology Stack

| Technology | Version / Purpose |
|---|---|
| Java | 17+ |
| Hibernate ORM | 6.4.4.Final |
| JPA | Jakarta Persistence |
| MySQL | 8.x |
| Maven | Build & dependency management |
| jBCrypt | Password hashing |
| JUnit | 5.10.2 |
| AssertJ | Test assertions |
| Logback | Application logging |

---

# ⚙️ Requirements

Before running the project, install:

### 1. Java 17+

Verify:

```bash
java -version
```

### 2. Maven

Verify:

```bash
mvn -version
```

### 3. MySQL 8+

Make sure the MySQL server is running.

---

# 🚀 Installation & Setup

## Step 1 — Extract the Project

Extract:

```text
Hibernate-ECommerce.zip
```

Open the extracted folder in:

- IntelliJ IDEA
- Eclipse
- VS Code

Import it as a **Maven project**.

---

## Step 2 — Configure MySQL

Open:

```text
src/main/resources/hibernate.cfg.xml
```

Find:

```xml
<property name="hibernate.connection.password">
    YOUR_DB_PASSWORD
</property>
```

Replace it with your MySQL password.

Example:

```xml
<property name="hibernate.connection.username">root</property>
<property name="hibernate.connection.password">mypassword</property>
```

---

## Step 3 — Database

The configured database is:

```text
ecommerce_db
```

The JDBC URL automatically creates the database if necessary:

```text
jdbc:mysql://localhost:3306/ecommerce_db
```

You can also use the included:

```text
schema.sql
```

to create the database manually.

---

# ▶️ Running the Application

From the project directory:

```bash
mvn compile exec:java
```

Or run:

```text
src/main/java/com/ecommerce/App.java
```

directly from your IDE.

---

# 🧪 Running Tests

Run the complete test suite:

```bash
mvn clean test
```

The test suite covers important Hibernate operations including:

- Category and product persistence
- Password hashing
- Order creation
- Order total calculation
- Entity relationships
- Named queries
- Soft deletion
- Database retrieval

---

# 🔎 Example Hibernate Operations

### Persisting an entity

```java
session.persist(product);
```

### Loading an entity

```java
Product product = session.get(Product.class, productId);
```

### Named query

```java
session.createNamedQuery(
    "Product.findByCategory",
    Product.class
);
```

### CriteriaBuilder

```java
CriteriaBuilder cb = session.getCriteriaBuilder();
```

### Transaction

```java
Transaction tx = session.beginTransaction();

session.persist(entity);

tx.commit();
```

---

# 📦 Maven Commands

Compile:

```bash
mvn compile
```

Run tests:

```bash
mvn test
```

Clean and test:

```bash
mvn clean test
```

Run the application:

```bash
mvn compile exec:java
```

Package:

```bash
mvn package
```

---

# 🔐 Security Notes

For a real production deployment:

- Never commit database passwords to Git.
- Store secrets in environment variables or a secrets manager.
- Use separate development and production databases.
- Keep Hibernate and database dependencies updated.
- Use HTTPS for web-based deployments.
- Apply proper authentication and authorization controls.

The included configuration intentionally contains:

```text
YOUR_DB_PASSWORD
```

instead of a real database credential.

---

# 📚 Learning Objectives

This project can be used to learn:

1. Java 17 development
2. Maven project management
3. Hibernate ORM
4. JPA annotations
5. Entity relationships
6. Database transactions
7. HQL
8. Named queries
9. Criteria API
10. Cascading
11. Lazy loading
12. Password hashing
13. Soft deletion
14. Pagination
15. JUnit integration testing
16. MySQL database design

---

# 📌 Project Status

**Status:** Academic / Learning Project

The project is intended to demonstrate Hibernate ORM and relational database concepts through an e-commerce example.

---

# 👨‍💻 Original Project Reference

This project is a reproduction based on the publicly available:

**Hibernate-ECommerce** repository by **the-ayush-ch0udhary**.

Original repository:

https://github.com/the-ayush-ch0udhary/Hibernate-ECommerce

This README documents the reproduced project structure and functionality.

---

## 📄 License / Usage

Check the original repository for its applicable license and usage terms before redistributing the source.

---

### ⭐ Suggested Improvements

For a larger production-style version, the project could be extended with:

- REST API using Spring Boot
- JWT authentication
- Admin dashboard
- Shopping cart
- Product search
- Payment integration
- Image upload
- Order status tracking
- Email notifications
- Docker deployment
- React/Angular frontend
- Role-based authorization
