package com.app.reserve.offer.application;

import com.app.reserve.offer.domain.OfferRepository;
import com.app.reserve.offer.domain.useCases.DeleteOffer;
import reactor.core.publisher.Mono;


public record OfferDeleter(OfferRepository offerRepository) implements DeleteOffer {
    @Override
    public Mono<Void> removeOfferById(String offerId) {
        return offerRepository.deleteOffer(offerId);
    }

    @Override
    public Mono<Void> softDelete(String id) {
        return offerRepository.softDeleteById(id);
    }
}
