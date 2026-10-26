package com.app.reserve.offer.domain.useCases;

import com.app.reserve.offer.domain.Offer;
import reactor.core.publisher.Mono;

public interface CreateOffer {
    Mono<Void> createOffer(Offer offer);
}
