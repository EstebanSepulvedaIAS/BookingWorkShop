package com.app.reserve.offer.application;

import com.app.reserve.offer.domain.Offer;
import com.app.reserve.offer.domain.OfferRepository;
import com.app.reserve.offer.domain.useCases.CreateOffer;
import com.app.reserve.shared.domain.exceptions.InvalidArgumentException;
import reactor.core.publisher.Mono;

public record OfferCreator(OfferRepository offerRepository) implements CreateOffer {
    @Override
    public Mono<Void> createOffer(Offer offer) {
        String title = offer.getTitle();
        if (title == null || title.isBlank()) {
            return Mono.error(new InvalidArgumentException("The offer title is required and cannot be blank"));
        }
        return offerRepository.save(offer);
    }
}
