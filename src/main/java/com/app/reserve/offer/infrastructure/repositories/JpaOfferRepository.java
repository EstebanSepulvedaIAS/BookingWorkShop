package com.app.reserve.offer.infrastructure.repositories;

import com.app.reserve.offer.infrastructure.entities.OfferEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface JpaOfferRepository extends ReactiveCrudRepository<OfferEntity, UUID> {
    Mono<OfferEntity> findByTitle(String title);
}
