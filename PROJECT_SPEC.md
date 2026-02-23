# VibeApp Project Specification

This document outlines the technical specifications and structure for the **VibeApp** project.

## Technical Stack

| Category | Specification |
| :--- | :--- |
| **JDK** | 25 or higher |
| **Language** | Java |
| **Spring Boot** | 4.0.1 or higher |
| **Build Tool** | Gradle 9.3.0 or higher |
| **DSL** | Groovy DSL |
| **Configuration** | YAML (`application.yml`) |
| **View Engine** | Thymeleaf |
| **CSS Framework** | Bootstrap 5 (CDN) & Tailwind CSS (CDN) |

## Project Metadata
- **Group ID:** `com.example`
- **Artifact ID:** `vibeapp`
- **Version:** `0.0.1-SNAPSHOT`
- **Main Class:** `com.example.vibeapp.VibeApp`

## Project Structure
- `src/main/java/com/example/vibeapp/VibeApp.java`: Main application entry point and basic controllers.
- `src/main/java/com/example/vibeapp/domain/Post.java`: Post entity with fields (no, title, content, createdAt, updatedAt, views).
- `src/main/java/com/example/vibeapp/repository/PostRepository.java`: In-memory storage for posts using `ArrayList`.
- `src/main/java/com/example/vibeapp/service/PostService.java`: Service layer handling post logic and example data generation.
- `src/main/java/com/example/vibeapp/controller/PostController.java`: Web controller for post-related views.
- `src/main/resources/templates/index.html`: Home page template with Bootstrap 5 and Tailwind CSS.
- `src/main/resources/templates/posts.html`: Post list page template with Bootstrap 5 styled table.
- `src/main/resources/application.yml`: Application configuration.
- `build.gradle`: Build configuration with `spring-boot-starter-thymeleaf` and `spring-boot-starter-web`.

## Features Implemented
- **REST API:** `/api/hello` endpoint returning plain text.
- **Thymeleaf Integration:** Server-side rendering for HTML views.
- **Bootstrap 5 UI:** Modern UI components using Bootstrap CDN.
- **Post List:** In-memory post management with example data and list view.
- **Git Convention:** Conventional Commits following `git-message-format.md`.
