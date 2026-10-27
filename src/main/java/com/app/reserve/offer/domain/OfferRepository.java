package com.app.reserve.offer.domain;

import com.app.reserve.shared.domain.Id;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OfferRepository {
    Mono<Void> save(Offer offer);
    Mono<Offer> findById(Id id);
    Flux<Offer> findAll();
    Mono<Void> deleteOffer(String offerId);
}
