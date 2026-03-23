package com.example.template.user.infrastructure.persistence;

import com.example.template.user.application.RefreshTokenRepository;
import com.example.template.user.domain.RefreshToken;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenPersistenceAdapter implements RefreshTokenRepository {

  private final RefreshTokenJpaRepository jpaRepository;
  private final RefreshTokenPersistenceMapper mapper;

  public RefreshTokenPersistenceAdapter(
      RefreshTokenJpaRepository jpaRepository, RefreshTokenPersistenceMapper mapper) {
    this.jpaRepository = jpaRepository;
    this.mapper = mapper;
  }

  @Override
  public RefreshToken save(RefreshToken refreshToken) {
    RefreshTokenJpaEntity entity = mapper.toJpaEntity(refreshToken);
    RefreshTokenJpaEntity saved = jpaRepository.save(entity);
    return mapper.toDomain(saved);
  }

  @Override
  public Optional<RefreshToken> findByToken(String token) {
    return jpaRepository.findByToken(token).map(mapper::toDomain);
  }

  @Override
  public void deleteByUserId(Long userId) {
    jpaRepository.deleteByUserId(userId);
  }
}
