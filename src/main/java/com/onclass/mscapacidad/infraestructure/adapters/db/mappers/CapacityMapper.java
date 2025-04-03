package com.onclass.mscapacidad.infraestructure.adapters.db.mappers;


import com.onclass.mscapacidad.application.api.CapacityDTO;
import com.onclass.mscapacidad.domain.model.Capacity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CapacityMapper {

    public static Capacity toDomain(CapacityDTO dto) {
        return Capacity.builder()
                .id(UUID.randomUUID().toString())
                .name(dto.getName())
                .description(dto.getDescription())
                .technologyIds(dto.getTechnologyIds())
                .build();
    }
}