package com.app.reserve.offer.domain.valueObjects;

public record OfferAvailableQuantity(int availableQuantity) {
    public OfferAvailableQuantity incrementPersonsForOffer(){return new OfferAvailableQuantity(availableQuantity + 1);}
    public OfferAvailableQuantity reducePersonsForOffer(){return new OfferAvailableQuantity(availableQuantity - 1);}
}
