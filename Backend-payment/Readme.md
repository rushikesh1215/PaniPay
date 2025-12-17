# Pani-Pay: Backend Monolith

**Pani-Pay** is a robust, monolithic financial backend built with Spring Boot. It provides a complete ecosystem for managing digital wallets, processing secure payments, and tracking user loyalty rewards.

---

## 🚀 Key Features

* **Secure Authentication:** Token-based security using **JWT** (JSON Web Tokens).
* **Wallet Management:** Real-time balance tracking, top-ups, and fund management.
* **Payment Processing:** Secure peer-to-peer transfers and payment gateway integration.
* **Rewards & Loyalty:** Cashback calculations and referral point management.
* **Transaction Tracking:** Detailed history logs and downloadable statements.
* **User Analytics:** Spending insights and financial reports.
* **Admin Control:** Advanced oversight for system health and user administration.

---

## 🛠 Tech Stack

| Technology | Purpose |
| :--- | :--- |
| **Java 21+** | Primary programming language |
| **Spring Boot 3.4** | Application framework |
| **Spring Security** | Authentication and Authorization |
| **JSON Web Token (JWT)** | Stateless security tokens |
| **Spring Data JPA** | Object-Relational Mapping (ORM) |
| **SpringDoc OpenAPI** | API documentation (Swagger UI) |
| **Maven** | Dependency management and build tool |

---
## 🛠 Accessing the Documentation

Once the backend is running, you can access the interactive UI at:

👉 **http://localhost:8081/swagger-ui/index.html#/**

---

## 🔐 Authentication Flow

Most endpoints require a valid **JWT (JSON Web Token)**. Follow these steps to authenticate your requests in Swagger:

1.  **Login:** Use the `POST /auth/login` endpoint with your credentials.
2.  **Copy Token:** Copy the `accessToken` string from the response body.
3.  **Authorize:** Click the **"Authorize"** button (lock icon) at the top of the Swagger page.
4.  **Paste:** Enter your token in the field
5.  **Access:** All locked endpoints are now accessible for testing.

## 🗄️ Database Management (H2 Console)

For development purposes, this project uses an H2 In-Memory database. You can access the database UI to run SQL queries and inspect tables.

### How to Access:
1.  Ensure the backend application is running.
2.  Open your browser to: **[http://localhost:8081/h2-console](http://localhost:8081/h2-console)**
3.  Use the following credentials:
    * **JDBC URL:** `jdbc:h2:file:./data/appdb`
    * **User Name:** `sa`
    * **Password:** `password` (as defined in your .env or properties)
