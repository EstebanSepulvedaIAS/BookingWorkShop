package com.app.reserve.offer.application;

import com.app.reserve.offer.domain.Offer;
import com.app.reserve.offer.domain.OfferRepository;
import com.app.reserve.offer.domain.useCases.CreateOffer;
import reactor.core.publisher.Mono;

public record OfferCreator(OfferRepository offerRepository) implements CreateOffer {
    @Override
    public Mono<Void> createOffer(Offer offer) {
        return offerRepository.save(offer);
    }
}
