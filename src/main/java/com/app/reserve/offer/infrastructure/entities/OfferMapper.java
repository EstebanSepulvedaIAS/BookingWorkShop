package com.app.reserve.offer.infrastructure.entities;

import com.app.reserve.offer.domain.Offer;

public class OfferMapper {

    private OfferMapper(){}

    public static OfferEntity toOfferEntity(Offer offer){
        return new OfferEntity(
                offer.getId(),
                offer.getQuantity(),
                offer.getAvailableQuantity(),
                offer.getDate(),
                offer.getState(),
                offer.getTitle()
        );
    }

    public static Offer toDomainOffer(OfferEntity offerEntity){
        return Offer.create(
                offerEntity.getId().toString(),
                offerEntity.getQuantity(),
                offerEntity.getAvailableQuantity(),
                offerEntity.getDate(),
                offerEntity.isState(),
                offerEntity.getTitle()
        );
    }
}
