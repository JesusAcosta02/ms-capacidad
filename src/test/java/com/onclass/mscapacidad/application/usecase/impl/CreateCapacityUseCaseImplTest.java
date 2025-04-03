package com.onclass.mscapacidad.application.usecase.impl;

import com.onclass.mscapacidad.domain.model.Capacity;
import com.onclass.mscapacidad.domain.repository.CapacityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CreateCapacityUseCaseImplTest {

    @Mock
    CapacityRepository repository;

    @InjectMocks
    CreateCapacityUseCaseImpl useCase;

    @Test
    void shouldCreateCapacitySuccessfully() {
        Capacity capacity = Capacity.builder()
                .id("123")
                .name("Backend")
                .description("Capacidad Backend")
                .technologyIds(List.of("1", "2", "3"))
                .build();

        Mockito.when(repository.create(capacity)).thenReturn(Mono.empty());

        StepVerifier.create(useCase.create(capacity))
                .verifyComplete();

        Mockito.verify(repository).create(capacity);
    }


}