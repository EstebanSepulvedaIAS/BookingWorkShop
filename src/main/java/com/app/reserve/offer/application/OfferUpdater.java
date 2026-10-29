package com.app.reserve.offer.application;

import com.app.reserve.offer.domain.Offer;
import com.app.reserve.offer.domain.OfferRepository;
import com.app.reserve.offer.domain.useCases.UpdateOffer;
import reactor.core.publisher.Mono;

public record OfferUpdater(OfferRepository offerRepository) implements UpdateOffer {
    @Override
    public Mono<Void> updateOffer(Offer offer) {
        return null;
    }

    @Override
    public Mono<Void> updateOfferByTitle(String title, Offer offer) {
        return offerRepository.updateByTitle(title, offer);
    }
}
