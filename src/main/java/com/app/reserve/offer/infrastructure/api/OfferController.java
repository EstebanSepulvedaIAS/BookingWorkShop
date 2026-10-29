package com.app.reserve.offer.infrastructure.api;

import com.app.reserve.offer.domain.Offer;
import com.app.reserve.offer.domain.useCases.CreateOffer;
import com.app.reserve.offer.domain.useCases.DeleteOffer;
import com.app.reserve.offer.domain.useCases.RetrieveOffer;
import com.app.reserve.offer.domain.useCases.UpdateOffer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/offers")
public class OfferController {

    private final CreateOffer offerCreator;
    private final RetrieveOffer offerRetriever;
    private final UpdateOffer offerUpdater;
    private final DeleteOffer offerDeleter;

    public OfferController(CreateOffer offerCreator, RetrieveOffer offerRetriever, UpdateOffer offerUpdater, DeleteOffer offerDeleter) {
        this.offerCreator = offerCreator;
        this.offerRetriever = offerRetriever;
        this.offerUpdater = offerUpdater;
        this.offerDeleter = offerDeleter;
    }

    @PostMapping
    public Mono<ResponseEntity<Void>> createOffer(@RequestBody OfferInput offerInput){
        return offerCreator.createOffer(Offer.create(offerInput.id(), offerInput.quantity(), offerInput.availableQuantity(), offerInput.date(), offerInput.state(), offerInput.title()))
                .doOnSuccess(unused -> log.info("Offer created: {}", offerInput))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).build());
    }

    @GetMapping
    public Flux<OfferResponse> getOffers(){
        return offerRetriever.getAllOffers()
                .map(offer -> new OfferResponse(
                        offer.getId(),
                        offer.getQuantity(),
                        offer.getAvailableQuantity(),
                        offer.getDate(),
                        offer.getState(),
                        offer.getTitle()
                ))
                .doOnComplete(() -> log.info("Offers retrieved successfully"));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<OfferResponse>> getOffersById(@PathVariable("id") String id){
        return offerRetriever.getOffer(id)
                .map(offer -> {
                    var offerResponse = new OfferResponse(
                            offer.getId(),
                            offer.getQuantity(),
                            offer.getAvailableQuantity(),
                            offer.getDate(),
                            offer.getState(),
                            offer.getTitle()
                    );
                    log.info("Offer retrieved: {} {}", offer.getTitle());
                    return ResponseEntity.ok().body(offerResponse);
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/{title}")
    public Mono<ResponseEntity<OfferResponse>> retrieveOfferByTitle(@PathVariable("title") String title){
        return offerRetriever.retrieveByTitle(title)
                .map(offer -> new OfferResponse(
                        offer.getId(),
                        offer.getQuantity(),
                        offer.getAvailableQuantity(),
                        offer.getDate(),
                        offer.getState(),
                        offer.getTitle()
                ))
                .doOnSuccess(offerResponse -> log.info("Offer retrieved by title: {}", title))
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{title}")
    public Mono<ResponseEntity<Void>> updateOfferByTitle(@PathVariable("title") String title, @RequestBody OfferInput offerInput){
        return offerUpdater.updateOfferByTitle(
                title,
                Offer.create(offerInput.id(), offerInput.quantity(), offerInput.availableQuantity(), offerInput.date(), offerInput.state(), offerInput.title())
                )
                .doOnSuccess(unused -> log.info("Offer updated with title {}: {}", title, offerInput))
                .thenReturn(ResponseEntity.noContent().build());
    }

    @DeleteMapping("delete/{id}")
    public Mono<ResponseEntity<Void>> deleteOffer(@PathVariable("id") String id){
        return offerDeleter.removeOfferById(id)
                .doOnSuccess(unused -> log.info("Offer deleted: {}", id))
                .thenReturn(ResponseEntity.noContent().build());
    }

    @DeleteMapping("/cancel/{id}")
    public Mono<ResponseEntity<Void>> deleteSoftOffer(@PathVariable("id") String id){
        return offerDeleter.softDelete(id)
                .doOnSuccess(unused -> log.info("Offer cancelled: {}", id))
                .thenReturn(ResponseEntity.noContent().build());
    }
}
