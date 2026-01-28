*Pastebin-Lite Project

Project Description
This project is a simple Pastebin-like application where users can create text pastes with optional time-to-live (TTL) and maximum view limits. Users receive a shareable URL to view the paste until it expires or the max views are reached.

*Project Overview

This project implements a basic paste creation and viewing service with the following features:

- Users can create a paste containing arbitrary text content.
- Optionally set a Time-To-Live (TTL) in seconds, after which the paste expires.
- Optionally set a maximum number of views, after which the paste becomes inaccessible.
- A unique, short URL is generated to share and view the paste.
- When a paste expires or reaches the max views, it is no longer available.
- Clear error messages for invalid inputs, expired pastes, or not found.

The backend is built with Spring Boot and Spring Data JPA, using a relational database for persistence.  
The frontend is built with React, providing a simple UI to create and view pastes.

*How To Run The Project

Prerequisites

- Java 17 or newer
- Maven 3.x
- Node.js 16 or newer
- npm 
- A relational database (MySQL, PostgreSQL)

Configuration

Backend Configuration Files

This project supports both:


*application.yml

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/pastebin
    username: postgres
    password: root
  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect

*Clone the repository
 git clone <your-github-repo-url>
 cd <your-project-folder>

*Navigate to the backend directory
 cd backend
 
*Build and run the backend with Maven
 mvn clean install
 mvn spring-boot:run

*Run the Frontend
 cd frontend

* Install Dependencies
 npm install

*Start the React development server

npm run dev

***And start the project make sure backend is running***








