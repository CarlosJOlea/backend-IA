package com.example.template.sampleentity.application;

import com.example.template.sampleentity.domain.SampleEntity;
import java.util.List;
import java.util.Optional;

public interface SampleEntityRepository {

  SampleEntity save(SampleEntity entity);

  Optional<SampleEntity> findById(Long id);

  List<SampleEntity> findAll();

  void deleteById(Long id);

  boolean existsById(Long id);
}
