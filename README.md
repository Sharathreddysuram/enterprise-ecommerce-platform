# Enterprise E-Commerce Platform

A scalable Enterprise E-Commerce Microservices Application built using Java, Spring Boot, Spring Cloud, Docker, MySQL, Eureka, Config Server, API Gateway, Feign Clients, Kafka, and Resilience4j.

---

## 🚀 Tech Stack

### Backend
- Java 17
- Spring Boot
- Spring Cloud
- Spring Data JPA
- Spring Security
- OpenFeign
- Resilience4j
- Kafka

### Microservices
- Service Registry (Eureka)
- Config Server
- API Gateway
- Product Service
- Order Service
- Payment Service

### Database
- MySQL

### DevOps & Tools
- Docker
- Docker Compose
- Maven
- GitHub
- Postman

---

# 🏗️ Architecture

```text
Client
   ↓
API Gateway
   ↓
------------------------------------------------
|            |               |                 |
Product      Order          Payment        Config
Service      Service        Service        Server
   ↓             ↓              ↓
                Kafka
   ↓
MySQL
```

---

# 📦 Microservices

## 1. Service Registry
- Eureka Server
- Handles service discovery

Port:
```text
8761
```

---

## 2. Config Server
- Centralized configuration management
- Reads configs from GitHub repository

Port:
```text
8888
```

---

## 3. API Gateway
- Central entry point
- Routes requests to services

Port:
```text
9090
```

---

## 4. Product Service

Features:
- Add products
- Update products
- Delete products
- Get all products

Port:
```text
8081
```

---

## 5. Order Service

Features:
- Place order
- Get all orders
- Cancel order
- Feign communication with Product Service
- Kafka event publishing

Port:
```text
8082
```

---

## 6. Payment Service

Features:
- Payment processing
- Payment status management

Port:
```text
8083
```

---

# 🐳 Docker Setup

## Build Services

```bash
docker build -t service-registry .
docker build -t config-server .
docker build -t api-gateway .
docker build -t product-service .
docker build -t order-service .
docker build -t payment-service .
```

---

## Run Application

```bash
docker compose up -d
```

---

# 🧪 API Testing

## Product APIs

### Add Product

```http
POST /api/products
```

Sample JSON:

```json
{
  "productName": "iPhone 15",
  "category": "Mobiles",
  "price": 999.99,
  "quantity": 5
}
```

---

## Order APIs

### Place Order

```http
POST /api/orders
```

Sample JSON:

```json
{
  "productId": 1,
  "quantity": 2
}
```

---

# ⚙️ Features Implemented

✅ Microservices Architecture  
✅ Eureka Service Discovery  
✅ Centralized Configuration  
✅ API Gateway Routing  
✅ Dockerized Services  
✅ MySQL Integration  
✅ Feign Client Communication  
✅ Resilience4j Circuit Breaker  
✅ Kafka Configuration  
✅ REST APIs  
✅ Maven Multi-Service Build  

---

# 📌 Future Enhancements

- JWT Authentication & Authorization
- Swagger/OpenAPI Documentation
- Jenkins CI/CD Pipeline
- AWS Deployment
- Kubernetes Deployment
- React Frontend
- Redis Caching
- ELK Monitoring Stack

---

# 👨‍💻 Author

## Sharath Reddy

GitHub:
```text
https://github.com/Sharathreddysuram
```

Project Repository:
```text
https://github.com/Sharathreddysuram/enterprise-ecommerce-platform
```
