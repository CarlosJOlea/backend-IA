package com.example.template.sampleentity.infrastructure.web;

import com.example.template.sampleentity.domain.SampleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SampleEntityWebMapper {

  SampleEntityResponse toResponse(SampleEntity entity);

  SampleEntity toDomain(SampleEntityRequest request);
}
