package com.onclass.mscapacidad.infraestructure.entrypoints.rest;

import com.onclass.mscapacidad.application.api.CapacityDTO;
import com.onclass.mscapacidad.application.api.CapacityWithTechnologiesDTO;
import com.onclass.mscapacidad.application.usecase.CreateCapacityUseCase;
import com.onclass.mscapacidad.application.usecase.ListCapacitiesUseCase;
import com.onclass.mscapacidad.infraestructure.adapters.db.mappers.CapacityMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/capacities")
@RequiredArgsConstructor
public class CapacityController {

    private final CreateCapacityUseCase createCapacityUseCase;
    private final ListCapacitiesUseCase listCapacitiesUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<String> createCapacity(@Valid @RequestBody CapacityDTO dto) {
        return createCapacityUseCase.create(CapacityMapper.toDomain(dto));
    }

    @GetMapping
    public Flux<CapacityWithTechnologiesDTO> getAllCapacities(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending
    ) {
        return listCapacitiesUseCase.getAll(page, size, sortBy, ascending);
    }
}