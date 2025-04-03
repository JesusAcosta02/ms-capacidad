package com.onclass.mscapacidad.infraestructure.entrypoints.rest;

import com.onclass.mscapacidad.application.api.CapacityDTO;
import com.onclass.mscapacidad.application.usecase.CreateCapacityUseCase;
import com.onclass.mscapacidad.infraestructure.adapters.db.mappers.CapacityMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/capacities")
@RequiredArgsConstructor
public class CapacityController {

    private final CreateCapacityUseCase createCapacityUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<String> createCapacity(@Valid @RequestBody CapacityDTO dto) {
        return createCapacityUseCase.create(CapacityMapper.toDomain(dto));
    }
}