package com.example.template.user.infrastructure.persistence;

import com.example.template.user.application.UserRepository;
import com.example.template.user.domain.User;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class UserPersistenceAdapter implements UserRepository {

  private final UserJpaRepository jpaRepository;
  private final UserPersistenceMapper mapper;

  public UserPersistenceAdapter(UserJpaRepository jpaRepository, UserPersistenceMapper mapper) {
    this.jpaRepository = jpaRepository;
    this.mapper = mapper;
  }

  @Override
  public User save(User user) {
    return mapper.toDomain(jpaRepository.save(mapper.toJpaEntity(user)));
  }

  @Override
  public Optional<User> findByEmail(String email) {
    return jpaRepository.findByEmail(email).map(mapper::toDomain);
  }

  @Override
  public boolean existsByEmail(String email) {
    return jpaRepository.existsByEmail(email);
  }
}
