package com.app.reserve.offer.infrastructure.api;

import com.app.reserve.offer.domain.Offer;
import com.app.reserve.offer.domain.useCases.CreateOffer;
import com.app.reserve.offer.domain.useCases.RetrieveOffer;
import com.app.reserve.offer.domain.useCases.UpdateOffer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/offers")
public class OfferController {

    private final CreateOffer offerCreator;
    private final RetrieveOffer offerRetriever;
    private final UpdateOffer offerUpdater;

    public OfferController(CreateOffer offerCreator, RetrieveOffer offerRetriever, UpdateOffer offerUpdater) {
        this.offerCreator = offerCreator;
        this.offerRetriever = offerRetriever;
        this.offerUpdater = offerUpdater;
    }

    public Mono<ResponseEntity<Void>> createOffer(@RequestBody OfferInput offerInput){
        return offerCreator.createOffer(Offer.create(offerInput.id(), offerInput.quantity(), offerInput.date(), offerInput.state(), offerInput.title()))
                .doOnSuccess(unused -> log.info("Offer created: {}", offerInput))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).build());
    }
}
