# 🚀 FitStream-AI  
### 🧠 AI-Powered Fitness Tracking Platform | Microservices Architecture

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/SpringBoot-Microservices-brightgreen)
![React](https://img.shields.io/badge/Frontend-React-blue)
![Architecture](https://img.shields.io/badge/Architecture-Microservices-purple)
![License](https://img.shields.io/badge/License-MIT-green)
![Status](https://img.shields.io/badge/Status-Active-success)

---

## 🌟 Overview

**FitStream-AI** is a **full-stack, production-ready fitness platform** built using **Spring Boot microservices and React**.

It demonstrates **real-world backend architecture** including:
- API Gateway
- Service Discovery
- Centralized Configuration
- Secure Authentication
- AI extensibility

---

## 🏗️ Architecture

```

Client (React)
│
▼
API Gateway (Spring Cloud Gateway)
│
┌────┼───────────────┬───────────────┐
▼    ▼               ▼               ▼
User  Activity      AI Service     Other Services
Service Service
│
▼
Eureka Server (Service Discovery)
│
▼
Config Server (Centralized Config)

```

---

## 📁 Project Structure

```

FitStream-AI/
│
├── frontend/                     # React Application
│
├── fitness-backend/
│   ├── api-gateway/
│   ├── user-service/
│   ├── activity-service/
│   ├── ai-service/
│   ├── config-server/
│   ├── eureka-server/
│
└── README.md

```

---

## ⚙️ Tech Stack

### 🧩 Backend
- Java 17
- Spring Boot
- Spring Cloud (Gateway, Eureka, Config Server)
- REST APIs
- Lombok

### 🎨 Frontend
- React.js
- Material UI
- Axios

### 🔐 Security
- JWT Authentication
- API Gateway Authorization

### 🧠 AI Layer
- Extensible ML/AI integration

---

## 🔑 Features

- ✔ Microservices-based architecture  
- ✔ API Gateway routing  
- ✔ Service discovery (Eureka)  
- ✔ Centralized config server  
- ✔ JWT authentication  
- ✔ Fitness activity tracking  
- ✔ Scalable & modular design  

---

## 🔄 Request Flow

```

Client → API Gateway → Auth Filter → Microservice → Response

````

---

## 🚀 Getting Started

### 📦 Prerequisites

- Java 17+
- Node.js
- Maven
- Git

---

### 🔧 Clone Repository

```bash
git clone https://github.com/tksr2907/FitStream-AI.git
cd FitStream-AI
````

---

## ▶️ Run Backend Services (IMPORTANT ORDER)

1. Start **Eureka Server**
2. Start **Config Server**
3. Start **API Gateway**
4. Start:

    * user-service
    * activity-service
    * ai-service

---

## 🌐 Run Frontend

```bash
cd frontend
npm install
npm run dev
```

---

## 🔐 Authentication Flow

1. User logs in
2. JWT token is generated
3. Token stored in frontend
4. Sent in Authorization header
5. Gateway validates token

---

## 📡 API Endpoints (Sample)

```
POST /api/auth/register
POST /api/auth/login
GET  /api/activities
POST /api/activities
```

---

## 📖 API Documentation (Swagger)

Once services are running, access:

```
http://localhost:8080/swagger-ui.html
```

*(Update port based on your gateway config)*

---

## 🐳 Docker Setup (Optional)

### Build Images

```bash
docker build -t api-gateway ./fitness-backend/api-gateway
docker build -t user-service ./fitness-backend/user-service
docker build -t activity-service ./fitness-backend/activity-service
```

### Run Containers

```bash
docker-compose up --build
```

---

## 📸 Screenshots

> Add UI screenshots here for better visibility

---

## 🧪 Future Enhancements

* 🐳 Full Docker orchestration
* ☸️ Kubernetes deployment
* 📊 Analytics dashboard
* 📱 Mobile app
* 🧠 Advanced AI recommendations

---

## 🏆 Why This Project Stands Out

* ✔ Real-world microservices implementation
* ✔ Covers frontend + backend + security
* ✔ Scalable system design
* ✔ Strong portfolio project

---

## 🤝 Contributing

```bash
# Fork repo
# Create new branch
# Commit changes
# Push & create PR
```

---

## 📄 License

MIT License

---

## 👨‍💻 Author

**Tushar Kumar**

* 💼 Software Developer
* 🧠 DSA Enthusiast
* 🌐 Full Stack Developer

🔗 GitHub: [https://github.com/tksr2907](https://github.com/tksr2907)

---

## ⭐ Support

If you like this project:

* ⭐ Star this repo
* 🔁 Share it
* 🤝 Connect with me
