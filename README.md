# 🚀 FitStream-AI
### 🧠 AI-Powered Fitness Tracking Platform | Microservices Architecture

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5-brightgreen)
![React](https://img.shields.io/badge/Frontend-React_19-blue)
![Keycloak](https://img.shields.io/badge/Auth-Keycloak-red)
![Kafka](https://img.shields.io/badge/Messaging-Kafka-black)
![Gemini](https://img.shields.io/badge/AI-Gemini_API-blueviolet)
![License](https://img.shields.io/badge/License-MIT-green)
![Status](https://img.shields.io/badge/Status-Active-success)

---

## 🌟 Overview

**FitStream-AI** is a full-stack fitness tracking platform built on **Spring Boot microservices** and **React**. Users log workouts, and an AI service powered by **Google Gemini** asynchronously generates personalized fitness recommendations via **Apache Kafka**.

Authentication is handled end-to-end by **Keycloak** using **OAuth2 + PKCE**, with token validation enforced at the API Gateway before any request reaches a downstream service.

---

## 🏗️ Architecture

```
React (Vite) + Keycloak PKCE
        │
        ▼
API Gateway  :9099  ← validates JWT via Keycloak JWKS
        │
   ┌────┼──────────────┐
   ▼    ▼              ▼
User    Activity      AI Service
Service Service  ──►  (Kafka consumer)
:9090   :9091         :9093
  │       │              │
Postgres MongoDB       MongoDB
        │
      Kafka
   activity-topic
        │
        ▼
    AI Service
  (Gemini API call)
        │
   ┌────┴────┐
   ▼         ▼
Eureka     Config
Server     Server
:9098      :9097
```

All services register with **Eureka** for service discovery. Configuration is centralised in the **Config Server** (classpath-based native profiles).

---

## 📁 Project Structure

```
FitStream-AI/
│
├── fitness-frontend/                  # React + Vite frontend
│   └── src/
│       ├── components/
│       ├── services/
│       └── store/
│
├── fitness-backend/
│   ├── eureka/eureka/                 # Eureka Server         :9098
│   ├── configserver/configserver/     # Config Server         :9097
│   ├── gateway/gateway/               # API Gateway           :9099
│   ├── userservice/                   # User Service          :9090
│   ├── activityservice/activityservice/  # Activity Service   :9091
│   └── aiservice/aiservice/           # AI Service            :9093
│
└── README.md
```

---

## ⚙️ Tech Stack

### 🧩 Backend
- **Java 17** + **Spring Boot 3.5**
- **Spring Cloud** — Gateway, Eureka, Config Server
- **Spring Security** — OAuth2 Resource Server (JWT)
- **Spring Data JPA** (PostgreSQL) — User Service
- **Spring Data MongoDB** — Activity & AI Services
- **Apache Kafka** — async event streaming between Activity → AI Service
- **Lombok**

### 🎨 Frontend
- **React 19** + **Vite**
- **Material UI (MUI)**
- **Redux Toolkit**
- **Axios**
- **react-oauth2-code-pkce** — OAuth2 PKCE flow

### 🔐 Security
- **Keycloak** — Identity Provider (realm: `fitness-app`, client: `oauth2-pkce-client`)
- **OAuth2 Authorization Code + PKCE** — frontend login
- **JWT validation** — enforced at Gateway via Keycloak JWKS endpoint
- Auto user-sync: Gateway creates a User Service record on first login

### 🧠 AI Layer
- **Google Gemini API** — generates personalised fitness recommendations
- Triggered asynchronously: Activity Service publishes to Kafka → AI Service consumes and calls Gemini

### 🗄️ Databases
| Service | Database |
|---------|----------|
| User Service | PostgreSQL |
| Activity Service | MongoDB |
| AI Service | MongoDB |

---

## 🔑 Features

- Microservices architecture with service discovery and centralised config
- Secure OAuth2 + PKCE login via Keycloak
- JWT-validated API Gateway with automatic user provisioning
- Fitness activity logging (type, duration, calories)
- AI-generated workout recommendations powered by Google Gemini
- Event-driven architecture using Apache Kafka
- Load-balanced inter-service communication via Eureka (`lb://SERVICE-NAME`)

---

## 🔄 Request Flow

```
User Login → Keycloak (PKCE) → JWT token issued
     │
     ▼
React app sends request with Bearer token
     │
     ▼
API Gateway → validates JWT via Keycloak JWKS
     │         syncs user to User Service on first request
     ▼
Downstream microservice handles request
     │
     ▼  (on activity create)
Activity Service → publishes to Kafka (activity-topic)
     │
     ▼
AI Service → consumes event → calls Gemini API → saves recommendation to MongoDB
```

---

## 🚀 Getting Started

### Prerequisites

- Java 17+
- Node.js 20+
- Maven
- Docker (for Keycloak, Kafka, MongoDB, PostgreSQL)

### Clone

```bash
git clone https://github.com/tksr2907/FitStream-AI.git
cd FitStream-AI
```

### Start infrastructure

```bash
# Keycloak on :8181
docker run -p 8181:8080 \
  -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin \
  quay.io/keycloak/keycloak:latest start-dev

# Then create realm: fitness-app, client: oauth2-pkce-client (public, PKCE enabled)
```

Start PostgreSQL, MongoDB, and Kafka via your preferred method (Docker Compose recommended).

### Run backend services (in order)

```bash
# 1. Eureka Server
cd fitness-backend/eureka/eureka && ./mvnw spring-boot:run

# 2. Config Server
cd fitness-backend/configserver/configserver && ./mvnw spring-boot:run

# 3. User Service
cd fitness-backend/userservice && ./mvnw spring-boot:run

# 4. Activity Service
cd fitness-backend/activityservice/activityservice && ./mvnw spring-boot:run

# 5. AI Service  (set GEMINI_API_KEY env var first)
export GEMINI_API_KEY=your_key_here
cd fitness-backend/aiservice/aiservice && ./mvnw spring-boot:run

# 6. API Gateway
cd fitness-backend/gateway/gateway && ./mvnw spring-boot:run
```

### Run frontend

```bash
cd fitness-frontend
npm install
npm run dev
# Opens at http://localhost:5173
```

---

## 📡 API Endpoints

All routes go through the Gateway on `:9099`.

```
# Users
GET  /api/users/{id}

# Activities
GET  /api/activities
POST /api/activities
GET  /api/activities/{id}

# AI Recommendations
GET  /api/recommendation/{userId}
```

---

## 🧪 Future Enhancements

- Docker Compose for full local stack
- Kubernetes deployment
- Analytics dashboard
- Mobile app
- Distributed tracing (Zipkin / Micrometer)

---

## 👨‍💻 Author

**Tushar Kumar** — Full Stack Developer

🔗 [github.com/tksr2907](https://github.com/tksr2907)

---

## ⭐ Support

If you find this useful, star the repo and share it!
