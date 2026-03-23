# Prototype Audit - Iteration 000003

## 1. Executive summary
Iteration 000003 focused on implementing API authentication via JWT. While User Registration (US-001) and User Login (US-002) are correctly implemented following the hexagonal vertical-slice architecture, several key components are missing: the logout and refresh token endpoints, the 'invalidated' flag in the refresh token model, and the Spring Security filter to enforce protection on other endpoints. Consequently, the API is currently open and session management is incomplete.

## 2. Verification by FR
| FR ID | Description | Assessment |
|-------|-------------|------------|
| FR-1  | `POST /auth/register` to create a new user with email and hashed password. | comply |
| FR-2  | `POST /auth/login` to validate credentials and issue a JWT access token and a persisted refresh token. | comply |
| FR-3  | `POST /auth/refresh` to exchange a valid refresh token for a new access token. | does_not_comply |
| FR-4  | `POST /auth/logout` to invalidate a refresh token in the database. | does_not_comply |
| FR-5  | A Spring Security filter must validate the JWT on every request to non-public endpoints and return `401` on failure. | does_not_comply |
| FR-6  | Passwords must be hashed with BCrypt via `PasswordEncoder`. | comply |
| FR-7  | JWTs must be signed and verified using the JJWT library (`io.jsonwebtoken`). | comply |
| FR-8  | Refresh tokens must be stored in the H2 database (JPA entity) with at minimum: token value, user reference, expiration timestamp, and an `invalidated` flag. | partially_comply |
| FR-9  | The auth feature must follow the hexagonal vertical-slice structure. | comply |
| FR-10 | Spring Security must be configured via `SecurityFilterChain` bean. | does_not_comply |

## 3. Verification by US
| US ID | Title | Assessment |
|-------|-------|------------|
| US-001| User Registration | comply |
| US-002| User Login | comply |
| US-003| Token Refresh | does_not_comply |
| US-004| User Logout | does_not_comply |
| US-005| Protected Endpoint Enforcement | does_not_comply |

## 4. Minor observations
- The 'spring-boot-starter-security' dependency is missing from pom.xml.
- The 'invalidated' flag is missing from the RefreshToken model and JPA entity, which is required for secure logout and token rotation.
- GlobalExceptionHandler correctly handles auth-specific exceptions, which is a good quality indicator.

## 5. Conclusions and recommendations
It is necessary to complete the implementation of the authentication filter (FR-5, FR-10) and the missing endpoints for logout and token refresh (US-003, US-004). Additionally, the RefreshToken entity must be updated to include the 'invalidated' flag to comply with FR-8. A refactor iteration is recommended to integrate these missing pieces and ensure the security of the application before proceeding with more functional features.

## 6. Refactor plan
The user has chosen to leave the findings as is for now and record them as technical debt. The recommended refactor plan (not yet applied) includes:
1. Adding `spring-boot-starter-security` to `pom.xml`.
2. Updating `RefreshTokenJpaEntity` and `RefreshToken` domain model to include `invalidated` flag.
3. Implementing `POST /auth/refresh` and `POST /auth/logout` endpoints in `UserController` and `AuthService`.
4. Implementing `SecurityConfig` with `SecurityFilterChain` and a JWT filter to enforce protected access.
