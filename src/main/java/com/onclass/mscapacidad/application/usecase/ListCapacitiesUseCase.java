package com.onclass.mscapacidad.application.usecase;

import com.onclass.mscapacidad.application.api.CapacityWithTechnologiesDTO;
import reactor.core.publisher.Flux;

public interface ListCapacitiesUseCase {
    Flux<CapacityWithTechnologiesDTO> getAll(int page, int size, String sortBy, boolean ascending);
}
