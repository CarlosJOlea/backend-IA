package com.example.template.user.infrastructure.web;

import com.example.template.user.domain.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserWebMapper {

  UserResponse toResponse(User user);
}
