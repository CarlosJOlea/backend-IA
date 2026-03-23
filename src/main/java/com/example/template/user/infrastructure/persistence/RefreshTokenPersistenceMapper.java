package com.example.template.user.infrastructure.persistence;

import com.example.template.user.domain.RefreshToken;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RefreshTokenPersistenceMapper {

  RefreshTokenJpaEntity toJpaEntity(RefreshToken domain);

  RefreshToken toDomain(RefreshTokenJpaEntity jpaEntity);
}
