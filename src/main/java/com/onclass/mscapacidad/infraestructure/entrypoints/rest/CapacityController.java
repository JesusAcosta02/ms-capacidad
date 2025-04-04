package com.onclass.mscapacidad.infraestructure.entrypoints.rest;

import com.onclass.mscapacidad.application.api.CapacityDTO;
import com.onclass.mscapacidad.application.api.CapacityWithTechnologiesDTO;
import com.onclass.mscapacidad.application.usecase.CreateCapacityUseCase;
import com.onclass.mscapacidad.application.usecase.ListCapacitiesUseCase;
import com.onclass.mscapacidad.domain.model.Capacity;
import com.onclass.mscapacidad.domain.repository.CapacityRepository;
import com.onclass.mscapacidad.infraestructure.adapters.db.mappers.CapacityMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/capacities")
@RequiredArgsConstructor
public class CapacityController {

    private final CreateCapacityUseCase createCapacityUseCase;
    private final ListCapacitiesUseCase listCapacitiesUseCase;
    private final CapacityRepository capacityRepository;
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

    @GetMapping("/by-bootcamp/{bootcampId}")
    public Flux<Capacity> findByBootcamp(@PathVariable String bootcampId) {
        return capacityRepository.findByBootcampId(bootcampId);
    }

    @PostMapping("/by-ids")
    public Flux<Capacity> findByIds(@RequestBody List<String> ids) {
        return capacityRepository.findAllByIdIn(ids);
    }
}