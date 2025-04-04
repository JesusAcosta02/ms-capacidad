package com.onclass.mscapacidad.domain.repository;

import com.onclass.mscapacidad.domain.model.Capacity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CapacityRepository {
    Mono<String> create(Capacity capacity);
    Flux<Capacity> findAllPaged(int offset, int limit);
    Flux<String> findTechnologyIdsByCapacityId(String capacityId);
}
