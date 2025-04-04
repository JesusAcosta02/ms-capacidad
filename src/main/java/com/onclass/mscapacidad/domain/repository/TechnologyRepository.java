package com.onclass.mscapacidad.domain.repository;

import com.onclass.mscapacidad.application.api.CapacityWithTechnologiesDTO;
import com.onclass.mscapacidad.application.api.TechnologyInfo;
import reactor.core.publisher.Flux;

import java.util.List;

public interface TechnologyRepository {
    Flux<TechnologyInfo> findByIds(List<String> ids);

}