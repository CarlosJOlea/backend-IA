package com.example.template.sampleentity.infrastructure.web;

import com.example.template.sampleentity.application.SampleEntityService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sample-entities")
public class SampleEntityController {

  private final SampleEntityService service;
  private final SampleEntityWebMapper mapper;

  public SampleEntityController(SampleEntityService service, SampleEntityWebMapper mapper) {
    this.service = service;
    this.mapper = mapper;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public SampleEntityResponse create(@Valid @RequestBody SampleEntityRequest request) {
    return mapper.toResponse(service.create(mapper.toDomain(request)));
  }

  @GetMapping("/{id}")
  public SampleEntityResponse getById(@PathVariable Long id) {
    return mapper.toResponse(service.getById(id));
  }

  @GetMapping
  public List<SampleEntityResponse> getAll() {
    return service.getAll().stream().map(mapper::toResponse).toList();
  }

  @PutMapping("/{id}")
  public SampleEntityResponse update(
      @PathVariable Long id, @Valid @RequestBody SampleEntityRequest request) {
    return mapper.toResponse(service.update(id, mapper.toDomain(request)));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    service.delete(id);
  }
}
