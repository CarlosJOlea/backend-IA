package com.example.template.sampleentity.infrastructure.persistence;

import com.example.template.sampleentity.application.SampleEntityRepository;
import com.example.template.sampleentity.domain.SampleEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class SampleEntityPersistenceAdapter implements SampleEntityRepository {

  private final SampleEntityJpaRepository jpaRepository;
  private final SampleEntityPersistenceMapper mapper;

  public SampleEntityPersistenceAdapter(
      SampleEntityJpaRepository jpaRepository, SampleEntityPersistenceMapper mapper) {
    this.jpaRepository = jpaRepository;
    this.mapper = mapper;
  }

  @Override
  public SampleEntity save(SampleEntity entity) {
    return mapper.toDomain(jpaRepository.save(mapper.toJpaEntity(entity)));
  }

  @Override
  public Optional<SampleEntity> findById(Long id) {
    return jpaRepository.findById(id).map(mapper::toDomain);
  }

  @Override
  public List<SampleEntity> findAll() {
    return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
  }

  @Override
  public void deleteById(Long id) {
    jpaRepository.deleteById(id);
  }

  @Override
  public boolean existsById(Long id) {
    return jpaRepository.existsById(id);
  }
}
