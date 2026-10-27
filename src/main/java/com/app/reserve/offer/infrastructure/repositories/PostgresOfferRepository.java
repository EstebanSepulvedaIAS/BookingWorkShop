package com.app.reserve.offer.infrastructure.repositories;

import com.app.reserve.offer.domain.Offer;
import com.app.reserve.offer.domain.OfferRepository;
import com.app.reserve.offer.domain.exceptions.OfferNotFoundException;
import com.app.reserve.offer.infrastructure.entities.OfferMapper;
import com.app.reserve.shared.domain.Id;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public class PostgresOfferRepository implements OfferRepository {

    private final JpaOfferRepository jpaOfferRepository;

    public PostgresOfferRepository(JpaOfferRepository jpaOfferRepository) {
        this.jpaOfferRepository = jpaOfferRepository;
    }

    @Override
    public Mono<Void> save(Offer offer) {
        var offersEntity = OfferMapper.toOfferEntity(offer);
        return jpaOfferRepository.save(offersEntity).then();
    }

    @Override
    public Mono<Offer> findById(Id id) {
        return jpaOfferRepository.findById(id.value())
                .map(OfferMapper::toDomainOffer);
    }

    @Override
    public Flux<Offer> findAll() {
        return jpaOfferRepository.findAll()
                .map(OfferMapper::toDomainOffer);
    }

    @Override
    public Mono<Void> deleteOffer(String offerId) {
        return jpaOfferRepository.findById(UUID.fromString(offerId))
                .switchIfEmpty(Mono.error(new OfferNotFoundException("Offer not found")))
                .flatMap(jpaOfferRepository::delete);
    }
}
