package com.example.template.user.infrastructure.web;

import com.example.template.user.application.AuthService;
import com.example.template.user.application.UserService;
import com.example.template.user.domain.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {

  private final UserService userService;
  private final AuthService authService;
  private final UserWebMapper mapper;

  public UserController(UserService userService, AuthService authService, UserWebMapper mapper) {
    this.userService = userService;
    this.authService = authService;
    this.mapper = mapper;
  }

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public UserResponse register(@Valid @RequestBody RegisterRequest request) {
    User user = userService.register(request.email(), request.password());
    return mapper.toResponse(user);
  }

  @PostMapping("/login")
  public LoginResponse login(@Valid @RequestBody LoginRequest request) {
    AuthService.LoginResult result = authService.login(request.email(), request.password());
    return new LoginResponse(result.accessToken(), result.refreshToken());
  }
}
