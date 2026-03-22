package com.example.template.sampleentity.application;

public class SampleEntityNotFoundException extends RuntimeException {

  public SampleEntityNotFoundException(Long id) {
    super("SampleEntity not found with id: " + id);
  }
}
