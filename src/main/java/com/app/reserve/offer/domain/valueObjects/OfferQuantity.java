package com.app.reserve.offer.domain.valueObjects;

public record OfferQuantity(int quantity) {
    public OfferQuantity incrementPersonsForOffer(){
        return new OfferQuantity(quantity + 1);
    }

    public OfferQuantity reducePersonsForOffer(){
        return new OfferQuantity(quantity - 1);
    }
}
