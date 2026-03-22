package com.example.template.sampleentity.infrastructure.web;

import jakarta.validation.constraints.NotBlank;

public record SampleEntityRequest(
    @NotBlank(message = "Name is required") String name, String description) {}
