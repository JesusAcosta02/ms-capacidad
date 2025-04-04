package com.onclass.mscapacidad.infraestructure.adapters.db.repository;

import com.onclass.mscapacidad.infraestructure.adapters.db.entity.CapacityEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CapacityReactiveRepository extends ReactiveCrudRepository<CapacityEntity, String> {
}