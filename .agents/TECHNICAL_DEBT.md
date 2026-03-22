# Technical Debt

<!-- All content in English. Updated when approving refactor plan or after resolving known debt items.
     Used as input in future iteration evaluations so that evaluation and refactor cycles have a
     single place to look. -->

## From iteration 000002

> All items from this iteration were resolved and applied in the same iteration (refactor option a chosen).
> - Spring Boot version mismatch in PRD (FR-1) → PRD updated to "Spring Boot 3.x (latest stable)"
> - Missing README.md (US-004-AC03) → README.md created with full architecture guide
> - US-003-AC01 wording → PRD updated to reflect per-slice hexagonal layer pattern

## From iteration 000002

### GlobalExceptionHandler coupled to feature-specific exception

shared/GlobalExceptionHandler imports SampleEntityNotFoundException directly from sampleentity.application. Should depend on a base DomainEntityNotFoundException in shared/ to avoid cross-feature coupling as more features are added.

---

### Spring @Service annotation in application layer

SampleEntityService carries @Service (org.springframework.stereotype) in the application layer, introducing a framework dependency. Strict hexagonal architecture keeps the application layer framework-agnostic. Low impact for now but worth addressing for maximum portability.

---

### description field has no validation constraint

SampleEntityRequest only validates the name field (@NotBlank). The description field is unconstrained. Adding at least one constraint (e.g. @Size) would better showcase Bean Validation capabilities for developers using this as a reference.

---

### No integration tests for CRUD lifecycle

spring-boot-starter-test is declared but no test classes exist. Adding at least one @SpringBootTest integration test would provide regression coverage for the full request lifecycle.

---

### Progress file out of sync (US-003 to US-006 marked pending)

The progress file marks US-003 through US-006 as pending (attempt_count=0) despite the code fully implementing all four user stories. The progress tracking artifact should be updated to reflect the actual implementation state.

---
