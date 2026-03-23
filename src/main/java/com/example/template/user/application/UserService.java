package com.example.template.user.application;

import com.example.template.user.domain.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  public UserService(UserRepository repository) {
    this.repository = repository;
  }

  public User register(String email, String password) {
    if (repository.existsByEmail(email)) {
      throw new EmailAlreadyRegisteredException(email);
    }
    User user = new User();
    user.setEmail(email);
    user.setPasswordHash(passwordEncoder.encode(password));
    return repository.save(user);
  }
}
