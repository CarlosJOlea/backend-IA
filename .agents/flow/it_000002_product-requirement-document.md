# Requirement: Spring Boot 4 Backend Template — Hexagonal Architecture + Vertical Slice

## Context

Backend development teams lack a standardized starting point for building CRUD services. This template provides a company-wide scaffold using Spring Boot 4, hexagonal architecture, and vertical slice organization to ensure consistent structure, separation of concerns, and faster onboarding for new services.

## Goals

- Provide a ready-to-run Spring Boot 4 project that developers can clone and extend with zero friction.
- Enforce hexagonal architecture (domain, application, infrastructure separation) as a hard structural constraint.
- Organize code by feature using vertical slices so each slice is self-contained.
- Standardize DTO/entity mapping via MapStruct and persistence via Spring Data JPA.
- Serve as the official company-wide backend starter, distributed by the platform team.

## User Stories

### US-001: Run the project locally with no configuration

**As a** backend developer, **I want** to clone the template and start it immediately using `mvn spring-boot:run` **so that** I can evaluate and use it without setup overhead.

**Acceptance Criteria:**
- [ ] `mvn spring-boot:run` starts the application with no errors.
- [ ] H2 in-memory database is auto-configured with no external database required.
- [ ] Application starts on a default port (e.g., 8080) and is reachable.
- [ ] `mvn clean install` completes successfully with no compilation or test failures.

---

### US-002: Complete CRUD REST API for a sample resource

**As a** backend developer, **I want** the template to include a fully working CRUD REST API for a sample resource (e.g., `Product`) **so that** I have a concrete, runnable reference to follow when building my own features.

**Acceptance Criteria:**
- [ ] `POST /api/sample-entities` creates a new resource and returns `201 Created` with the created DTO.
- [ ] `GET /api/sample-entities/{id}` returns the resource and `200 OK`, or `404 Not Found` if it does not exist.
- [ ] `GET /api/sample-entities` returns all resources with `200 OK`.
- [ ] `PUT /api/sample-entities/{id}` updates the resource and returns `200 OK`.
- [ ] `DELETE /api/sample-entities/{id}` deletes the resource and returns `204 No Content`.
- [ ] All endpoints are verifiable via curl or Postman.
- [ ] H2 database persists data correctly during runtime (data is not lost between requests in the same session).
- [ ] Visually verified in browser (H2 console or Swagger/similar if included).

---

### US-003: Hexagonal architecture with clear layer separation

**As a** platform team member, **I want** the project to enforce hexagonal architecture **so that** the domain layer has no knowledge of infrastructure concerns and the architecture is maintainable and testable.

**Acceptance Criteria:**
- [ ] The project has three distinct top-level packages: `domain`, `application`, and `infrastructure`.
- [ ] No class in the `domain` package imports from `infrastructure` or `application`.
- [ ] No class in the `application` package imports from `infrastructure`.
- [ ] Ports (interfaces) are defined in `domain` or `application`; adapters are implemented in `infrastructure`.
- [ ] The structure is validated manually by reviewing package imports.
- [ ] Typecheck / lint passes (`mvn clean install`).

---

### US-004: Vertical slice structure per feature

**As a** backend developer, **I want** each feature to be organized as a self-contained vertical slice **so that** I can add or modify a feature without scattering changes across the entire codebase.

**Acceptance Criteria:**
- [ ] Each feature (e.g., `product`) has its own directory containing: controller, use case(s), domain model, and repository.
- [ ] Adding a new feature requires creating a new slice directory without modifying other slices.
- [ ] The slice structure is documented in a `README.md` or inline comments explaining where each type of class belongs.
- [ ] Typecheck / lint passes.

---

### US-005: DTO/Entity mapping via MapStruct

**As a** backend developer, **I want** all mapping between domain entities and DTOs to be handled by MapStruct **so that** mapping logic is declarative, type-safe, and not manually written.

**Acceptance Criteria:**
- [ ] MapStruct is declared as a Maven dependency.
- [ ] At least one `@Mapper`-annotated interface exists for `SampleEntity`.
- [ ] No manual field-by-field mapping exists in service or controller classes.
- [ ] Mapper is generated at compile time (verifiable via `mvn clean install`).
- [ ] Typecheck / lint passes.

---

### US-006: Input validation with proper HTTP error responses

**As a** backend developer, **I want** input validation to be enforced on request DTOs **so that** invalid data is rejected early with clear, consistent error responses.

**Acceptance Criteria:**
- [ ] Request DTOs use Bean Validation annotations (e.g., `@NotNull`, `@NotBlank`, `@Size`).
- [ ] `POST` and `PUT` requests with missing or invalid fields return `400 Bad Request`.
- [ ] Error response body includes a human-readable message indicating which fields failed validation.
- [ ] `200`/`201` responses are not returned for invalid inputs.
- [ ] Typecheck / lint passes.

---

## Functional Requirements

- **FR-1:** The project must use Java 21 and Spring Boot 4.
- **FR-2:** Build tool must be Maven; no Gradle alternative is required for this MVP.
- **FR-3:** Persistence layer must use Spring Data JPA with H2 in-memory database; no external database is required.
- **FR-4:** DTO/entity mapping must use MapStruct exclusively; no manual mapping or other mapping libraries.
- **FR-5:** The application must expose REST endpoints following standard HTTP semantics (correct status codes per operation).
- **FR-6:** Input validation must use Jakarta Bean Validation (integrated with Spring Boot); validation errors must return `400 Bad Request`.
- **FR-10:** A global exception handler (`@ControllerAdvice`) must be included to produce consistent, structured error response bodies for all validation and not-found errors.
- **FR-7:** The `domain` package must not import any class from `infrastructure` (enforced by code review or architecture test).
- **FR-8:** Each feature must reside in its own vertical slice directory containing all relevant layers.
- **FR-9:** No external services or databases beyond H2 are required to run the template.

## Non-Goals (Out of Scope)

- Authentication or authorization (Spring Security, JWT, OAuth2).
- PostgreSQL, MySQL, or any external database configuration.
- Docker or containerization setup.
- CI/CD pipeline configuration.
- API documentation tooling (Swagger/OpenAPI) — optional addition, not required for MVP.
- Pagination, filtering, or sorting on list endpoints.
- Unit or integration tests — not required for MVP validation.
- Multi-module Maven project structure.

## Open Questions

None. All questions resolved during requirements interview:
- Sample resource name: `SampleEntity` (neutral placeholder).
- Global exception handler: included via `@ControllerAdvice` (FR-10).
