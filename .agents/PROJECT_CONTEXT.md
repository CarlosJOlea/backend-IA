# Project Context

<!-- Created or updated by `nvst create project-context`. Cap: 250 lines. -->

## Conventions
- Naming: PascalCase for classes; camelCase for fields/methods; kebab-case for REST paths
- Formatting: Google Java Format (enforced via Maven plugin)
- Git flow: Feature branches per iteration (`feature/it_XXXXXX`); PRs merged to `main`
- Workflow: Hexagonal layer boundaries enforced by code review (no class in `domain` may import from `infrastructure` or `application`)

## Tech Stack
- Language: Java 21
- Runtime: JVM
- Frameworks: Spring Boot 4, Spring Data JPA, Spring Web MVC
- Key libraries: MapStruct (DTO/entity mapping), Jakarta Bean Validation, H2 (in-memory DB)
- Build / package manager: Maven
- Tooling: `mvn spring-boot:run` to run; `mvn clean install` to build and verify

## Code Standards
- Style patterns: Google Java Format; no Lombok (explicit getters/setters or records)
- Error handling: Global `@ControllerAdvice` handler for all validation (`400`) and not-found (`404`) errors; structured error response body
- Module organisation: Vertical slices — each feature owns its full stack under one package; shared utilities kept minimal
- Forbidden patterns: Manual field-by-field DTO↔entity mapping; any `domain` class importing from `infrastructure`; any `application` class importing from `infrastructure`

## Testing Strategy
- Approach: Not required for MVP (out of scope per PRD non-goals)
- Runner: N/A for MVP
- Coverage targets: None for MVP
- Test location convention: If added, `src/test/java` mirroring the main package structure

## Product Architecture
- Style: Hexagonal architecture (ports & adapters) combined with vertical slices
- Top-level packages: `domain` → `application` → `infrastructure` (dependency direction only inward)
- Ports defined in `domain` or `application`; adapters implemented in `infrastructure`
- REST layer lives in `infrastructure.web`; persistence adapters in `infrastructure.persistence`

## Modular Structure
```
com.example.template
├── <feature>/                   # one vertical slice per feature (e.g. product)
│   ├── domain/                  # entity, domain port interfaces
│   ├── application/             # use cases, application port interfaces
│   └── infrastructure/
│       ├── web/                 # @RestController, request/response DTOs, @Mapper
│       └── persistence/         # JPA entity, Spring Data repository, adapter impl
└── shared/                      # @ControllerAdvice, common error DTOs
```

## Implemented Capabilities
<!-- Updated at the end of each iteration by nvst create project-context -->
- (none yet — populated after first Refactor)
