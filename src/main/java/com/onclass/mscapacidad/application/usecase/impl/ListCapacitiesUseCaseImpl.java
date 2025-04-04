package com.onclass.mscapacidad.application.usecase.impl;

import com.onclass.mscapacidad.application.api.CapacityWithTechnologiesDTO;
import com.onclass.mscapacidad.application.api.TechnologyInfo;
import com.onclass.mscapacidad.application.usecase.ListCapacitiesUseCase;
import com.onclass.mscapacidad.domain.model.Capacity;
import com.onclass.mscapacidad.domain.repository.CapacityRepository;
import com.onclass.mscapacidad.domain.repository.TechnologyRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
@Component
public class ListCapacitiesUseCaseImpl implements ListCapacitiesUseCase {

    private final CapacityRepository capacityRepository;
    private final TechnologyRepository technologyRepository;

    public ListCapacitiesUseCaseImpl(CapacityRepository capacityRepository,
                                     TechnologyRepository technologyRepository) {
        this.capacityRepository = capacityRepository;
        this.technologyRepository = technologyRepository;
    }

    @Override
    public Flux<CapacityWithTechnologiesDTO> getAll(int page, int size, String sortBy, boolean ascending) {
        int offset = page * size;

        return capacityRepository.findAllPaged(offset, size)
                .flatMap(capacity ->
                        capacityRepository.findTechnologyIdsByCapacityId(capacity.getId())
                                .collectList()
                                .flatMapMany(ids -> {
                                    if (ids.isEmpty()) {
                                        return Mono.just(toDTO(capacity, List.of())).flux();
                                    }
                                    return technologyRepository.findByIds(ids)
                                            .collectList()
                                            .map(techList -> toDTO(capacity, techList));
                                })
                )
                .sort((a, b) -> {
                    int comparison = a.getName().compareToIgnoreCase(b.getName());
                    return ascending ? comparison : -comparison;
                });
    }

    private CapacityWithTechnologiesDTO toDTO(Capacity capacity, List<TechnologyInfo> technologies) {
        return CapacityWithTechnologiesDTO.builder()
                .id(capacity.getId())
                .name(capacity.getName())
                .description(capacity.getDescription())
                .technologies(technologies)
                .build();
    }
}



