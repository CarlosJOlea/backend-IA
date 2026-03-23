package com.example.template.user.application;

import com.example.template.user.domain.User;
import java.util.Optional;

public interface UserRepository {

  User save(User user);

  Optional<User> findByEmail(String email);

  boolean existsByEmail(String email);
}
