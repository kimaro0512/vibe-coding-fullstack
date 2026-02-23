# 프로젝트 명세서: VibeApp

이 문서는 최소 기능 스프링부트 애플리케이션인 **VibeApp** 프로젝트의 구성과 구조를 설명합니다.

## 프로젝트 개요
- **프로젝트 명:** VibeApp
- **설명:** 최소 기능 스프링부트 애플리케이션 생성 프로젝트.
- **목표:** Java 및 Spring Boot의 최신 안정 버전을 사용하는 깨끗하고 현대적인 기반 제공.

## 기술 스택

| 항목 | 사양 |
| :--- | :--- |
| **JDK** | 25 이상 |
| **Language** | Java |
| **Spring Boot** | 4.0.1 이상 |
| **Build Tool** | Gradle 9.3.0 이상 |
| **DSL** | Groovy DSL |
| **Configuration** | YAML (`application.yml`) |
| **View Engine** | Thymeleaf |

## 프로젝트 메타데이터
- **Group ID:** `com.example`
- **Artifact ID:** `vibeapp`
- **Version:** `0.0.1-SNAPSHOT`
- **Main Class:** `com.example.vibeapp.VibeApp`

## 프로젝트 구조
- `src/main/java/com/example/vibeapp/VibeApp.java`: 메인 애플리케이션 진입점.
- `src/main/resources/application.yml`: 애플리케이션 설정 파일.
- `src/main/resources/templates/index.html`: Thymeleaf 뷰 템플릿.
- `build.gradle`: 프로젝트 의존성 및 빌드 설정 (Thymeleaf 추가).
- `settings.gradle`: 프로젝트 이름 정의.
