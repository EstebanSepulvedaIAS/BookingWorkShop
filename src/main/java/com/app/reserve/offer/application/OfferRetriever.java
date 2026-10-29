package com.app.reserve.offer.application;

import com.app.reserve.offer.domain.Offer;
import com.app.reserve.offer.domain.OfferRepository;
import com.app.reserve.offer.domain.useCases.RetrieveOffer;
import com.app.reserve.shared.domain.Id;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public record OfferRetriever(OfferRepository offerRepository) implements RetrieveOffer {
    @Override
    public Flux<Offer> getAllOffers() {
        return offerRepository.findAll();
    }

    @Override
    public Mono<Offer> getOffer(String offerId) {
        return offerRepository.findById(new Id(offerId));
    }

    @Override
    public Mono<Offer> retrieveByTitle(String title) {
        return offerRepository.findByTitle(title);
    }
}
