# Nacarqeqia 🔥 - The Final Glow-Up

![Build Status](https://img.shields.io/badge/build-smoldering-orange?style=for-the-badge)
![Tech Stack](https://img.shields.io/badge/tech-infernal_arts-black?style=for-the-badge)
![License](https://img.shields.io/badge/license-Do%20Not%20Resurrect-red?style=for-the-badge)

A satirical full-stack Spring Boot application for a modern, digital crematorium. Because your final journey deserves a little... flare.

## ✨ Features

*   Secure user registration & login system.
*   Service ordering system with image uploads.
*   Role-based admin dashboard to view messages and all user orders.
*   Internationalization (i18n) for English and, for a touch of class, Latin.
*   H2 database for data persistence.

## ⚙️ Tech Stack

*   **Backend:** Spring Boot, Spring Security, Spring Data JPA
*   **Frontend:** Thymeleaf, HTML5, CSS3
*   **Database:** H2 Database
*   **Build:** Apache Maven
*   **Containerization:** Docker

## 🚀 Running the Service

You have two options to bring the inferno to life.

### 1. Locally (The Kindling)

For development and direct database access.

1.  Clone the repo: `git clone https://github.com/your-username/nacarqeqia.git && cd nacarqeqia`
2.  Run the app: `./mvnw spring-boot:run`
3.  Access the service at **`https://localhost:25565`**.
4.  Access the H2 Console at **`https://localhost:25565/h2-console`** with these settings:
    *   **JDBC URL:** `jdbc:h2:file:~/nacarqeqia-db`
    *   **User Name:** `root`
    *   **Password:** `pass`

### 2. With Docker (Full Burn - Recommended)

This is the simplest way to run a self-contained, production-ready instance.

1.  **Build the image:**
    ```bash
    docker build -t nacarqeqia .
    ```
2.  **Run the container:**
    ```bash
    docker run -d -p 25565:25565 --name nacarqeqia-app nacarqeqia
    ```
3.  The application is now live at **`https://localhost:25565`**.

### Managing the Container

*   **View logs (check the temperature):** `docker logs nacarqeqia-app`
*   **Stop the service (douse the flames):** `docker stop nacarqeqia-app`
*   **Start the service again (re-ignite):** `docker start nacarqeqia-app`

---

Licensed under MIT. Pull requests are welcome, provided they have the appropriate amount of gallows humor.
