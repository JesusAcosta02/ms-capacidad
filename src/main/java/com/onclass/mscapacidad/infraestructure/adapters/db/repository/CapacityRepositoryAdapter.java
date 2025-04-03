package com.onclass.mscapacidad.infraestructure.adapters.db.repository;

import com.onclass.mscapacidad.domain.model.Capacity;
import com.onclass.mscapacidad.domain.repository.CapacityRepository;
import com.onclass.mscapacidad.infraestructure.adapters.db.entity.CapacityEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CapacityRepositoryAdapter implements CapacityRepository {

    private final DatabaseClient databaseClient;

    @Override
    public Mono<String> create(Capacity capacity) {
        String id = UUID.randomUUID().toString();
        CapacityEntity entity = CapacityEntity.builder()
                .id(id)
                .name(capacity.getName())
                .description(capacity.getDescription())
                .build();

        return databaseClient.sql("INSERT INTO capacidades (id, name, description) VALUES (?, ?, ?)")
                .bind(0, entity.getId())
                .bind(1, entity.getName())
                .bind(2, entity.getDescription())
                .then()
                .thenMany(Flux.fromIterable(capacity.getTechnologyIds())
                        .flatMap(techId ->
                                databaseClient.sql("INSERT INTO capacidad_tecnologia (capacidad_id, tecnologia_id) VALUES (?, ?)")
                                        .bind(0, entity.getId())
                                        .bind(1, techId)
                                        .then()
                        )
                ).then(Mono.just(id)); // <- aquí devolvemos el id
    }
}