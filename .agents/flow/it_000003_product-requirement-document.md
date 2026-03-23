# Requirement: JWT Authentication with Access Token and Refresh Token

## Context
The application currently has no authentication layer — all endpoints are publicly accessible.
This iteration adds a standard stateless auth flow using Spring Security and JJWT: users can
register, log in to receive a short-lived access token and a long-lived refresh token, silently
renew the access token, and explicitly log out to invalidate the refresh token.

## Goals
- Protect the API with token-based authentication (JWT).
- Allow end users to self-register and manage their own session.
- Enable token renewal without requiring re-login on every expiry.
- Guarantee that logout immediately invalidates the session server-side.

## User Stories

### US-001: User Registration
**As an** end user, **I want** to register with my email and password **so that** I can create an
account and access the protected API.

**Acceptance Criteria:**
- [ ] `POST /auth/register` accepts `{ "email": "...", "password": "..." }`.
- [ ] Returns `201 Created` with `{ "id": ..., "email": "..." }` on success.
- [ ] Password is stored hashed with BCrypt — never in plain text.
- [ ] Returns `400 Bad Request` if email or password is missing or email format is invalid.
- [ ] Returns `409 Conflict` if the email is already registered.
- [ ] Typecheck / lint passes (`mvn clean install` succeeds).

### US-002: User Login
**As an** end user, **I want** to log in with my email and password **so that** I receive an
access token and a refresh token to authenticate subsequent requests.

**Acceptance Criteria:**
- [ ] `POST /auth/login` accepts `{ "email": "...", "password": "..." }`.
- [ ] Returns `200 OK` with `{ "accessToken": "...", "refreshToken": "..." }` on success.
- [ ] Access token is a signed JWT (JJWT) with a short expiration (see Open Questions).
- [ ] Refresh token is persisted in the database linked to the user.
- [ ] Returns `401 Unauthorized` if credentials are invalid.
- [ ] Typecheck / lint passes.

### US-003: Token Refresh
**As an** end user, **I want** to exchange my refresh token for a new access token **so that**
I can stay logged in without re-entering my credentials.

**Acceptance Criteria:**
- [ ] `POST /auth/refresh` accepts `{ "refreshToken": "..." }`.
- [ ] Returns `200 OK` with `{ "accessToken": "..." }` when the refresh token is valid and not expired.
- [ ] Returns `401 Unauthorized` if the refresh token is invalid, expired, or already invalidated.
- [ ] Typecheck / lint passes.

### US-004: User Logout
**As an** end user, **I want** to log out **so that** my session is immediately invalidated and
the refresh token can no longer be used.

**Acceptance Criteria:**
- [ ] `POST /auth/logout` accepts `{ "refreshToken": "..." }` (or reads the token from a header).
- [ ] Marks the refresh token as invalidated in the database.
- [ ] Returns `204 No Content` on success.
- [ ] A subsequent `POST /auth/refresh` with the same token returns `401 Unauthorized`.
- [ ] Typecheck / lint passes.

### US-005: Protected Endpoint Enforcement
**As a** developer, **I want** all non-auth endpoints to require a valid access token **so that**
unauthenticated clients cannot access protected resources.

**Acceptance Criteria:**
- [ ] Any request to a protected endpoint without an `Authorization: Bearer <token>` header returns `401 Unauthorized`.
- [ ] Any request with an expired or malformed access token returns `401 Unauthorized`.
- [ ] Requests with a valid access token are processed normally.
- [ ] Auth endpoints (`/auth/**`) remain publicly accessible (no token required).
- [ ] Typecheck / lint passes.

## Functional Requirements
- FR-1: The system must expose `POST /auth/register` to create a new user with email and hashed password.
- FR-2: The system must expose `POST /auth/login` to validate credentials and issue a JWT access token and a persisted refresh token.
- FR-3: The system must expose `POST /auth/refresh` to exchange a valid refresh token for a new access token.
- FR-4: The system must expose `POST /auth/logout` to invalidate a refresh token in the database.
- FR-5: A Spring Security filter must validate the JWT on every request to non-public endpoints and return `401` on failure.
- FR-6: Passwords must be hashed with BCrypt via `PasswordEncoder`.
- FR-7: JWTs must be signed and verified using the JJWT library (`io.jsonwebtoken`).
- FR-8: Refresh tokens must be stored in the H2 database (JPA entity) with at minimum: token value, user reference, expiration timestamp, and an `invalidated` flag.
- FR-9: The auth feature must follow the hexagonal vertical-slice structure: `auth/domain`, `auth/application`, `auth/infrastructure/web`, `auth/infrastructure/persistence`.
- FR-10: Spring Security must be configured via `SecurityFilterChain` bean (no `WebSecurityConfigurerAdapter`).

## Non-Goals (Out of Scope)
- Role-based authorization (RBAC) — all authenticated users have equal access.
- OAuth2, OpenID Connect, or social login (Google, GitHub, etc.).
- Email verification flow.
- Password reset / forgot-password flow.
- User profile management (update email, change password).
- Automated tests (out of scope per project testing strategy).
- Token rotation on every refresh (refresh token is reused until logout or expiry).

## Open Questions
- ~~What should the access token expiration time be?~~ → **15 minutes**
- ~~What should the refresh token expiration time be?~~ → **7 days**
- ~~Should the existing `sampleentity` endpoints be protected by the auth filter?~~ → **Yes, protect them**
