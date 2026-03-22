package com.example.template.sampleentity.application;

import com.example.template.sampleentity.domain.SampleEntity;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SampleEntityService {

  private final SampleEntityRepository repository;

  public SampleEntityService(SampleEntityRepository repository) {
    this.repository = repository;
  }

  public SampleEntity create(SampleEntity entity) {
    return repository.save(entity);
  }

  public SampleEntity getById(Long id) {
    return repository.findById(id).orElseThrow(() -> new SampleEntityNotFoundException(id));
  }

  public List<SampleEntity> getAll() {
    return repository.findAll();
  }

  public SampleEntity update(Long id, SampleEntity updated) {
    if (!repository.existsById(id)) {
      throw new SampleEntityNotFoundException(id);
    }
    updated.setId(id);
    return repository.save(updated);
  }

  public void delete(Long id) {
    if (!repository.existsById(id)) {
      throw new SampleEntityNotFoundException(id);
    }
    repository.deleteById(id);
  }
}
