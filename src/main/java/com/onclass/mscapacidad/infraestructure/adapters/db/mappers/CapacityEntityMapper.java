package com.onclass.mscapacidad.infraestructure.adapters.db.mappers;

import com.onclass.mscapacidad.domain.model.Capacity;
import com.onclass.mscapacidad.infraestructure.adapters.db.entity.CapacityEntity;
import org.springframework.stereotype.Component;

@Component
public class CapacityEntityMapper {

    public static CapacityEntity toEntity(Capacity domain) {
        return CapacityEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .description(domain.getDescription())
                .technologyIds(String.join(",", domain.getTechnologyIds()))
                .build();
    }

}