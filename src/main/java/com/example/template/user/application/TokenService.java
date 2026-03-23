package com.example.template.user.application;

import com.example.template.user.domain.User;

public interface TokenService {

  String generateAccessToken(User user);

  String generateRefreshToken();
}
