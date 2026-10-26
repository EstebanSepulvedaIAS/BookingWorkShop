package com.app.reserve.offer.domain.useCases;

import com.app.reserve.offer.domain.Offer;
import reactor.core.publisher.Mono;

public interface UpdateOffer {
    Mono<Void> updateOffer(Offer offer);
    Mono<Offer> updateOfferByTitle(String title);
}
