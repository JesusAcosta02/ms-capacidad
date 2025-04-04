package com.onclass.mscapacidad.infraestructure.adapters.db.repository;

import com.onclass.mscapacidad.domain.model.Capacity;
import com.onclass.mscapacidad.domain.repository.CapacityRepository;
import com.onclass.mscapacidad.infraestructure.adapters.db.entity.CapacityEntity;
import com.onclass.mscapacidad.infraestructure.adapters.db.mappers.CapacityEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple4;
import reactor.util.function.Tuples;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

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
                ).then(Mono.just(id));
    }

    @Override
    public Flux<Capacity> findAllPaged(int offset, int limit) {
        return databaseClient.sql("SELECT * FROM capacidades LIMIT :limit OFFSET :offset")
                .bind("limit", limit)
                .bind("offset", offset)
                .map((row, metadata) ->
                        CapacityEntityMapper.toDomain(CapacityEntityMapper.fromRow(row)))
                .all();
    }

    @Override
    public Flux<String> findTechnologyIdsByCapacityId(String capacityId) {
        return databaseClient.sql("SELECT tecnologia_id FROM capacidad_tecnologia WHERE capacidad_id = :capacityId")
                .bind("capacityId", capacityId)
                .map((row, metadata) -> row.get("tecnologia_id", String.class))
                .all();
    }

    @Override
    public Flux<Capacity> findByBootcampId(String bootcampId) {
        String capacityQuery = """
            SELECT c.id, c.name, c.description
            FROM capacidades c
            JOIN bootcamp_capacity bc ON c.id = bc.capacidad_id
            WHERE bc.bootcamp_id = :bootcampId
        """;

        return databaseClient.sql(capacityQuery)
                .bind("bootcampId", bootcampId)
                .map((row, meta) -> Capacity.builder()
                        .id(row.get("id", String.class))
                        .name(row.get("name", String.class))
                        .description(row.get("description", String.class))
                        .build())
                .all()
                .flatMap(this::addTechnologyIds);
    }

    @Override
    public Flux<Capacity> findAllByIdIn(List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return Flux.empty();
        }

        String placeholders = ids.stream()
                .map(id -> "?")
                .collect(Collectors.joining(", "));

        String query = "SELECT c.id, c.name, c.description, tc.tecnologia_id " +
                "FROM capacidades c " +
                "LEFT JOIN capacidad_tecnologia tc ON c.id = tc.capacidad_id " +
                "WHERE c.id IN (" + placeholders + ")";

        return databaseClient.sql(query)
                .bindValues(ids)
                .map((row, meta) -> Tuples.of(
                        row.get("id", String.class),
                        row.get("name", String.class),
                        row.get("description", String.class),
                        row.get("tecnologia_id", String.class)
                ))
                .all()
                .groupBy(tuple -> tuple.getT1()) // Agrupar por ID de capacidad
                .flatMap(grouped -> grouped.collectList().map(list -> {
                    String id = grouped.key();
                    String name = list.get(0).getT2();
                    String description = list.get(0).getT3();
                    List<String> techIds = list.stream()
                            .map(Tuple4::getT4)
                            .filter(Objects::nonNull)
                            .distinct()
                            .collect(Collectors.toList());

                    return Capacity.builder()
                            .id(id)
                            .name(name)
                            .description(description)
                            .technologyIds(techIds)
                            .build();
                }));
    }
    private Mono<Capacity> addTechnologyIds(Capacity capacity) {
        String techQuery = """
            SELECT tecnologia_id FROM capacidad_tecnologia
            WHERE capacidad_id = :capacityId
        """;

        return databaseClient.sql(techQuery)
                .bind("capacityId", capacity.getId())
                .map((row, meta) -> row.get("tecnologia_id", String.class))
                .all()
                .collectList()
                .map(techIds -> {
                    capacity.setTechnologyIds(techIds);
                    return capacity;
                });
    }




}