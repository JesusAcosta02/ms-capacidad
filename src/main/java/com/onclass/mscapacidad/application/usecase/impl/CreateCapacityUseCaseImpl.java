package com.onclass.mscapacidad.application.usecase.impl;

import com.onclass.mscapacidad.application.usecase.CreateCapacityUseCase;
import com.onclass.mscapacidad.domain.model.Capacity;
import com.onclass.mscapacidad.domain.repository.CapacityRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CreateCapacityUseCaseImpl implements CreateCapacityUseCase {

    private final CapacityRepository repository;

    public CreateCapacityUseCaseImpl(CapacityRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<String> create(Capacity capacity) {
        return repository.create(capacity);
    }
}
