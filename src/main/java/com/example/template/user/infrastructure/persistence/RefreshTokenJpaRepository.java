package com.example.template.user.infrastructure.persistence;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenJpaRepository extends JpaRepository<RefreshTokenJpaEntity, Long> {

  Optional<RefreshTokenJpaEntity> findByToken(String token);

  void deleteByUserId(Long userId);
}
