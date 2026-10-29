package com.app.reserve.offer.domain.useCases;

import reactor.core.publisher.Mono;


public interface DeleteOffer {
    Mono<Void> removeOfferById(String offerId);
    Mono<Void> softDelete(String id);
}
