package com.app.reserve.offer.domain.useCases;

import com.app.reserve.offer.domain.Offer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RetrieveOffer{
    Flux<Offer> getAllOffers();
    Mono<Offer> getOffer(String offerId);
    Mono<Offer> retrieveByTitle(String title);
}
