package com.onclass.mscapacidad.infraestructure.adapters.db.mappers;

import com.onclass.mscapacidad.domain.model.Capacity;
import com.onclass.mscapacidad.infraestructure.adapters.db.entity.CapacityEntity;
import io.r2dbc.spi.Row;
import org.springframework.stereotype.Component;

@Component
public class CapacityEntityMapper {

    public static CapacityEntity fromRow(Row row) {
        return CapacityEntity.builder()
                .id(row.get("id", String.class))
                .name(row.get("name", String.class))
                .description(row.get("description", String.class))
                .build();
    }

    public static Capacity toDomain(CapacityEntity entity) {
        return Capacity.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }

}