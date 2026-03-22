package com.example.template.sampleentity.infrastructure.persistence;

import com.example.template.sampleentity.domain.SampleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SampleEntityPersistenceMapper {

  SampleEntity toDomain(SampleEntityJpaEntity jpaEntity);

  SampleEntityJpaEntity toJpaEntity(SampleEntity domain);
}
