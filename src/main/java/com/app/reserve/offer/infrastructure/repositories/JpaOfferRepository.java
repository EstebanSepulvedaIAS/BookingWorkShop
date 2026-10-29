package com.app.reserve.offer.infrastructure.repositories;

import com.app.reserve.offer.infrastructure.entities.OfferEntity;


import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface JpaOfferRepository extends ReactiveCrudRepository<OfferEntity, UUID> {

    Mono<OfferEntity> findByTitleIgnoreCase(String title);

    @Modifying
    @Query("UPDATE \"Offer\" SET state = false WHERE id = :id AND state = true")
    Mono<Boolean> softDeleteById(@Param("id") UUID id);
}
