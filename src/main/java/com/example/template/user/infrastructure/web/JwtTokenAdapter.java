package com.example.template.user.infrastructure.web;

import com.example.template.user.application.TokenService;
import com.example.template.user.domain.User;
import io.jsonwebtoken.Jwts;
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenAdapter implements TokenService {

  // For production this should be in properties, but for prototype/test this works.
  private static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
  private static final long ACCESS_TOKEN_EXPIRATION_MS = 15 * 60 * 1000; // 15 minutes

  @Override
  public String generateAccessToken(User user) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + ACCESS_TOKEN_EXPIRATION_MS);

    return Jwts.builder()
        .subject(user.getEmail())
        .issuedAt(now)
        .expiration(expiryDate)
        .signWith(SECRET_KEY)
        .compact();
  }

  @Override
  public String generateRefreshToken() {
    return UUID.randomUUID().toString();
  }
}
