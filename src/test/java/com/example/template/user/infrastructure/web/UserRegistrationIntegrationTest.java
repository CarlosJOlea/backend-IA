package com.example.template.user.infrastructure.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.template.user.application.UserRepository;
import com.example.template.user.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserRegistrationIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Autowired private UserRepository userRepository;

  private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Test
  void shouldRegisterUserSuccessfully() throws Exception {
    RegisterRequest request = new RegisterRequest("test@example.com", "password123");

    mockMvc
        .perform(
            post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.email").value("test@example.com"));

    // Verify password is hashed
    Optional<User> userOpt = userRepository.findByEmail("test@example.com");
    assertThat(userOpt).isPresent();
    assertThat(passwordEncoder.matches("password123", userOpt.get().getPasswordHash())).isTrue();
    assertThat(userOpt.get().getPasswordHash()).isNotEqualTo("password123");
  }

  @Test
  void shouldReturnBadRequestWhenEmailIsMissing() throws Exception {
    RegisterRequest request = new RegisterRequest("", "password123");

    mockMvc
        .perform(
            post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestWhenEmailIsInvalid() throws Exception {
    RegisterRequest request = new RegisterRequest("not-an-email", "password123");

    mockMvc
        .perform(
            post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnConflictWhenEmailAlreadyExists() throws Exception {
    User existingUser = new User();
    existingUser.setEmail("duplicate@example.com");
    existingUser.setPasswordHash("somehash");
    userRepository.save(existingUser);

    RegisterRequest request = new RegisterRequest("duplicate@example.com", "password123");

    mockMvc
        .perform(
            post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isConflict());
  }
}
