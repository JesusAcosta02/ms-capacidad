package com.onclass.mscapacidad.infraestructure.adapters.db.repository;

import com.onclass.mscapacidad.application.api.TechnologyInfo;
import com.onclass.mscapacidad.domain.repository.TechnologyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;

@Slf4j
@Repository
public class TechnologyRestClientAdapter implements TechnologyRepository {

    private final WebClient webClient;

    public TechnologyRestClientAdapter(@Qualifier("technologyWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Flux<TechnologyInfo> findByIds(List<String> ids) {
        log.info("📤 Enviando IDs de tecnologías al microservicio de tecnología: {}", ids);

        return webClient.post()
                .uri("/technologies/by-ids")
                .bodyValue(ids)
                .retrieve()
                .bodyToFlux(TechnologyInfo.class);
    }
}
