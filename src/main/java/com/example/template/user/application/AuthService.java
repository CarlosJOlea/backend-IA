package com.example.template.user.application;

import com.example.template.user.domain.RefreshToken;
import com.example.template.user.domain.User;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

  private final UserRepository userRepository;
  private final RefreshTokenRepository refreshTokenRepository;
  private final TokenService tokenService;
  private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  public AuthService(
      UserRepository userRepository,
      RefreshTokenRepository refreshTokenRepository,
      TokenService tokenService) {
    this.userRepository = userRepository;
    this.refreshTokenRepository = refreshTokenRepository;
    this.tokenService = tokenService;
  }

  @Transactional
  public LoginResult login(String email, String password) {
    User user =
        userRepository
            .findByEmail(email)
            .filter(u -> passwordEncoder.matches(password, u.getPasswordHash()))
            .orElseThrow(InvalidCredentialsException::new);

    String accessToken = tokenService.generateAccessToken(user);
    String refreshTokenValue = tokenService.generateRefreshToken();

    // Persist refresh token AC04
    refreshTokenRepository.deleteByUserId(user.getId()); // Optional: clean up old tokens
    RefreshToken refreshToken = new RefreshToken();
    refreshToken.setToken(refreshTokenValue);
    refreshToken.setUserId(user.getId());
    refreshToken.setExpiryDate(Instant.now().plus(7, ChronoUnit.DAYS)); // Default 7 days
    refreshTokenRepository.save(refreshToken);

    return new LoginResult(accessToken, refreshTokenValue);
  }

  public record LoginResult(String accessToken, String refreshToken) {}
}
