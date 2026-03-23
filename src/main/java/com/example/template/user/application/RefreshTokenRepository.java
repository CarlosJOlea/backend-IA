package com.example.template.user.application;

import com.example.template.user.domain.RefreshToken;
import java.util.Optional;

public interface RefreshTokenRepository {

  RefreshToken save(RefreshToken refreshToken);

  Optional<RefreshToken> findByToken(String token);

  void deleteByUserId(Long userId);
}
