package com.onclass.mscapacidad.application.usecase;

import com.onclass.mscapacidad.domain.model.Capacity;
import reactor.core.publisher.Mono;

public interface CreateCapacityUseCase {
    Mono<String> create(Capacity capacity);
}