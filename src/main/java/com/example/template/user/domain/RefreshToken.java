package com.example.template.user.domain;

import java.time.Instant;

public class RefreshToken {

  private Long id;
  private String token;
  private Long userId;
  private Instant expiryDate;

  public RefreshToken() {}

  public RefreshToken(Long id, String token, Long userId, Instant expiryDate) {
    this.id = id;
    this.token = token;
    this.userId = userId;
    this.expiryDate = expiryDate;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Instant getExpiryDate() {
    return expiryDate;
  }

  public void setExpiryDate(Instant expiryDate) {
    this.expiryDate = expiryDate;
  }
}
