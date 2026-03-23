package com.example.template.user.infrastructure.persistence;

import com.example.template.user.domain.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserPersistenceMapper {

  User toDomain(UserJpaEntity jpaEntity);

  UserJpaEntity toJpaEntity(User domain);
}
