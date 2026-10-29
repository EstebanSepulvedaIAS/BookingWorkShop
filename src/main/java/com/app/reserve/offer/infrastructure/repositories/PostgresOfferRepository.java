package com.app.reserve.offer.infrastructure.repositories;

import com.app.reserve.offer.domain.Offer;
import com.app.reserve.offer.domain.OfferRepository;
import com.app.reserve.offer.domain.exceptions.OfferNotFoundException;
import com.app.reserve.offer.infrastructure.entities.OfferMapper;
import com.app.reserve.shared.domain.Id;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public class PostgresOfferRepository implements OfferRepository {

    private final JpaOfferRepository jpaOfferRepository;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    public PostgresOfferRepository(JpaOfferRepository jpaOfferRepository, R2dbcEntityTemplate r2dbcEntityTemplate) {
        this.jpaOfferRepository = jpaOfferRepository;
        this.r2dbcEntityTemplate = r2dbcEntityTemplate;
    }

    @Override
    public Mono<Void> save(Offer offer) {
        var offersEntity = OfferMapper.toOfferEntity(offer);
        return r2dbcEntityTemplate.insert(offersEntity).then();
    }

    @Override
    public Mono<Offer> findById(Id id) {
        return jpaOfferRepository.findById(id.value())
                .map(OfferMapper::toDomainOffer);
    }

    @Override
    public Mono<Offer> findByTitle(String title) {
        return jpaOfferRepository.findByTitleIgnoreCase(title)
                .switchIfEmpty(Mono.error(() -> new OfferNotFoundException("Offer not found with title: " + title)))
                .map(OfferMapper::toDomainOffer);
    }

    @Override
    public Mono<Void> updateByTitle(String title, Offer offer) {
        return jpaOfferRepository.findByTitleIgnoreCase(title)
                .switchIfEmpty(Mono.error(() -> new OfferNotFoundException("Offer not found with title: " + title)))
                .flatMap(existingEntity -> {
                    var incomingEntity = OfferMapper.toOfferEntity(offer);
                    existingEntity.setQuantity(
                            incomingEntity.getQuantity() > 0 ? incomingEntity.getQuantity() : existingEntity.getQuantity()
                    );
                    existingEntity.setAvailableQuantity(
                            incomingEntity.getAvailableQuantity() > 0 ? incomingEntity.getAvailableQuantity() : existingEntity.getAvailableQuantity()
                    );
                    existingEntity.setDate(
                            incomingEntity.getDate() != null ? incomingEntity.getDate() : existingEntity.getDate()
                    );
                    existingEntity.setState(
                            incomingEntity.isState() != existingEntity.isState() ? incomingEntity.isState() : existingEntity.isState()
                    );
                    existingEntity.setTitle(
                            incomingEntity.getTitle() != null ? incomingEntity.getTitle() : existingEntity.getTitle()
                    );
                    return r2dbcEntityTemplate.insert(existingEntity);
                })
                .then();
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

    @Override
    public Mono<Void> softDeleteById(String offerId) {
        return jpaOfferRepository.softDeleteById(UUID.fromString(offerId))
                .filter(Boolean::booleanValue)
                .switchIfEmpty(Mono.error(new OfferNotFoundException("Offer not found")))
                .then();
    }
}
