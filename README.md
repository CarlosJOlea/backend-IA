# Spring Boot Backend Template

A ready-to-run Spring Boot 3 template enforcing **hexagonal architecture** with **vertical slices**, **MapStruct** mapping, **H2 in-memory persistence**, and **Bean Validation**.

## Quick start

```bash
mvn spring-boot:run
```

The application starts on **http://localhost:8080** with no external dependencies.

| Tool | URL |
|------|-----|
| REST API | `http://localhost:8080/api/sample-entities` |
| H2 Console | `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:templatedb`) |

## Architecture

The project combines **hexagonal architecture** and **vertical slices**. Each feature lives in its own self-contained slice directory. Within that slice, code is organized into three hexagonal layers:

```
src/main/java/com/example/template/
├── TemplateApplication.java
├── shared/                          # Cross-cutting infrastructure (exception handler, error DTOs)
│   ├── GlobalExceptionHandler.java
│   └── ErrorResponse.java
└── <feature>/                       # One directory per feature (vertical slice)
    ├── domain/                      # Pure domain model — no framework dependencies
    │   └── <Feature>.java
    ├── application/                 # Use cases, ports (interfaces), domain exceptions
    │   ├── <Feature>Repository.java # Port: repository interface (implemented in infrastructure)
    │   ├── <Feature>Service.java    # Use case / application service
    │   └── <Feature>NotFoundException.java
    └── infrastructure/              # Adapters: all framework/library concerns
        ├── persistence/             # JPA adapter
        │   ├── <Feature>JpaEntity.java
        │   ├── <Feature>JpaRepository.java
        │   ├── <Feature>PersistenceAdapter.java
        │   └── <Feature>PersistenceMapper.java
        └── web/                     # REST adapter
            ├── <Feature>Controller.java
            ├── <Feature>Request.java  # Input DTO (with Bean Validation annotations)
            ├── <Feature>Response.java # Output DTO
            └── <Feature>WebMapper.java
```

### Layer responsibilities

| Layer | Package | Purpose | Rules |
|-------|---------|---------|-------|
| **Domain** | `<feature>/domain` | Core business entities | No imports from `application` or `infrastructure`; no framework annotations |
| **Application** | `<feature>/application` | Use cases, port interfaces, domain exceptions | May import `domain`; no imports from `infrastructure` |
| **Infrastructure** | `<feature>/infrastructure` | JPA adapters, REST controllers, MapStruct mappers | May import `application` and `domain`; implements ports |
| **Shared** | `shared/` | Global exception handler, error response DTOs | Used by infrastructure layer only |

### Adding a new feature

1. Create a new slice directory: `src/main/java/com/example/template/<yourfeature>/`
2. Add `domain/<YourFeature>.java` — plain Java, no annotations
3. Add `application/<YourFeature>Repository.java` — port interface
4. Add `application/<YourFeature>Service.java` — use case logic
5. Add `application/<YourFeature>NotFoundException.java` — domain exception
6. Add `infrastructure/persistence/` — JPA entity, Spring Data repository, adapter, MapStruct mapper
7. Add `infrastructure/web/` — REST controller, request/response DTOs, MapStruct mapper

Do **not** modify other feature slices.

## Key conventions

- **No Lombok** — explicit getters/setters and Java records for DTOs
- **Constructor injection** — no `@Autowired` field injection
- **MapStruct** — all DTO↔entity mapping is declarative; no manual field-by-field copying
- **Bean Validation** — `@NotBlank`, `@NotNull`, `@Size` etc. on request DTOs; `@Valid` on controller parameters
- **Google Java Format** — enforced automatically during `mvn process-sources` via Spotless

## Build

```bash
mvn clean install      # Compile, format, test, package
mvn spring-boot:run    # Run locally
```

## Sample resource: SampleEntity

The template ships with a fully working `SampleEntity` CRUD API as a reference:

| Method | Path | Status | Description |
|--------|------|--------|-------------|
| `POST` | `/api/sample-entities` | 201 | Create |
| `GET` | `/api/sample-entities` | 200 | List all |
| `GET` | `/api/sample-entities/{id}` | 200 / 404 | Get by ID |
| `PUT` | `/api/sample-entities/{id}` | 200 | Update |
| `DELETE` | `/api/sample-entities/{id}` | 204 | Delete |

Example:

```bash
# Create
curl -s -X POST http://localhost:8080/api/sample-entities \
  -H "Content-Type: application/json" \
  -d '{"name":"Widget","description":"A sample widget"}' | jq

# List
curl -s http://localhost:8080/api/sample-entities | jq

# Validation error (missing name)
curl -s -X POST http://localhost:8080/api/sample-entities \
  -H "Content-Type: application/json" \
  -d '{"description":"no name"}' | jq
```
