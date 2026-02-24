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

## Project Structure (Feature-based)
- `src/main/java/com/example/vibeapp/VibeApp.java`: Main application entry point (Spring Boot).
- `src/main/java/com/example/vibeapp/home/HomeController.java`: Home page controller (`/`).
- `src/main/java/com/example/vibeapp/post/Post.java`: Post entity with fields (`id`, `title`, `content`, `createdAt`, `updatedAt`, `views`).
- `src/main/java/com/example/vibeapp/post/PostRepository.java`: In-memory storage for posts.
- `src/main/java/com/example/vibeapp/post/PostService.java`: Service layer for post business logic.
- `src/main/java/com/example/vibeapp/post/PostController.java`: Web controller for post CRUD operations.
- `src/main/resources/templates/home/home.html`: Home page template.
- `src/main/resources/templates/post/`: Directory for post-related templates (`posts.html`, `post_detail.html`, etc.).
- `src/main/resources/application.yml`: Application configuration.

## Features Implemented
- **Home View**: Dedicated `HomeController` for the root path.
- **Post Management (CRUD)**:
    - **List**: Pagination support, descending order by ID.
    - **Detail**: View post content and increment view count.
    - **Create**: Add new posts with automatic ID generation.
    - **Update**: Edit existing post title and content.
    - **Delete**: Remove posts with confirmation.
- **Architecture**: Refactored from layered to feature-based package/template structure.
- **Convention**: Conventional Commits and clean code practices.
